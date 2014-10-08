/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.resiliency.spi.remote;

import com.liferay.portal.kernel.deploy.hot.DependencyManagementThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.rpc.IntrabandRPCUtil;
import com.liferay.portal.kernel.nio.intraband.welder.Welder;
import com.liferay.portal.kernel.nio.intraband.welder.WelderFactoryUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.local.LocalProcessLauncher;
import com.liferay.portal.kernel.process.log.ProcessOutputStream;
import com.liferay.portal.kernel.resiliency.mpi.MPI;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgent;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgentFactoryUtil;
import com.liferay.portal.kernel.resiliency.spi.provider.SPISynchronousQueueUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.lang.reflect.Field;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Shuyang Zhou
 */
public abstract class RemoteSPI implements ProcessCallable<SPI>, Remote, SPI {

	public RemoteSPI(SPIConfiguration spiConfiguration) {
		this.spiConfiguration = spiConfiguration;

		mpi = MPIHelperUtil.getMPI();

		UUID uuidObject = UUID.randomUUID();

		uuid = uuidObject.toString();

		welder = WelderFactoryUtil.createWelder();
	}

	@Override
	public SPI call() throws ProcessException {
		try {
			SPIShutdownHook spiShutdownHook = new SPIShutdownHook();

			LocalProcessLauncher.ProcessContext.attach(
				spiConfiguration.getSPIId(), spiConfiguration.getPingInterval(),
				spiShutdownHook);

			Runtime runtime = Runtime.getRuntime();

			runtime.addShutdownHook(spiShutdownHook);

			SPI spi = (SPI)UnicastRemoteObject.exportObject(this, 0);

			RegisterCallback registerCallback = new RegisterCallback(uuid, spi);

			ProcessOutputStream processOutputStream =
				LocalProcessLauncher.ProcessContext.getProcessOutputStream();

			processOutputStream.writeProcessCallable(registerCallback);

			registrationReference = welder.weld(MPIHelperUtil.getIntraband());

			ConcurrentMap<String, Object> attributes =
				LocalProcessLauncher.ProcessContext.getAttributes();

			attributes.put(SPI.SPI_INSTANCE_PUBLICATION_KEY, this);

			return spi;
		}
		catch (RemoteException re) {
			throw new ProcessException("Failed to export SPI as RMI stub.", re);
		}
		catch (IOException ioe) {
			throw new ProcessException(ioe);
		}
	}

	@Override
	public void destroy() throws RemoteException {
		try {
			doDestroy();

			if (countDownLatch != null) {
				countDownLatch.countDown();
			}
		}
		finally {
			UnicastRemoteObject.unexportObject(RemoteSPI.this, true);
		}
	}

	@Override
	public MPI getMPI() {
		return mpi;
	}

	@Override
	public RegistrationReference getRegistrationReference() {
		return registrationReference;
	}

	@Override
	public SPIAgent getSPIAgent() {
		if (spiAgent == null) {
			spiAgent = SPIAgentFactoryUtil.createSPIAgent(
				spiConfiguration, registrationReference);
		}

		return spiAgent;
	}

	@Override
	public SPIConfiguration getSPIConfiguration() {
		return spiConfiguration;
	}

	public String getUUID() {
		return uuid;
	}

	public Welder getWelder() {
		return welder;
	}

	@Override
	public boolean isAlive() {
		return true;
	}

	protected abstract void doDestroy() throws RemoteException;

	protected transient CountDownLatch countDownLatch;
	protected final MPI mpi;
	protected RegistrationReference registrationReference;
	protected transient volatile SPIAgent spiAgent;
	protected final SPIConfiguration spiConfiguration;
	protected final String uuid;
	protected final Welder welder;

	protected static class RegisterCallback implements ProcessCallable<SPI> {

		public RegisterCallback(String spiUUID, SPI spi) {
			_spiUUID = spiUUID;
			_spi = spi;
		}

		@Override
		public SPI call() throws ProcessException {
			try {
				SPISynchronousQueueUtil.notifySynchronousQueue(_spiUUID, _spi);
			}
			catch (InterruptedException ie) {
				throw new ProcessException(ie);
			}

			return _spi;
		}

		private static final long serialVersionUID = 1L;

		private final SPI _spi;
		private final String _spiUUID;

	}

	protected static class UnregisterSPIProcessCallable
		implements ProcessCallable<Boolean> {

		public UnregisterSPIProcessCallable(
			String spiProviderName, String spiId) {

			_spiProviderName = spiProviderName;
			_spiId = spiId;
		}

		@Override
		public Boolean call() {
			SPI spi = MPIHelperUtil.getSPI(_spiProviderName, _spiId);

			if (spi != null) {
				return MPIHelperUtil.unregisterSPI(spi);
			}

			return false;
		}

		private static final long serialVersionUID = 1L;

		private final String _spiId;
		private final String _spiProviderName;

	}

	protected class SPIShutdownHook
		extends Thread implements LocalProcessLauncher.ShutdownHook {

		public SPIShutdownHook() {
			setDaemon(true);
			setName(SPIShutdownHook.class.getSimpleName());
		}

		@Override
		public void run() {
			if (countDownLatch.getCount() == 0) {
				return;
			}

			boolean unregistered = false;

			try {
				Future<Boolean> future = IntrabandRPCUtil.execute(
					registrationReference,
					new UnregisterSPIProcessCallable(
						getSPIProviderName(), spiConfiguration.getSPIId()));

				unregistered = future.get();
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to unregister SPI from MPI", e);
				}
			}

			if (unregistered || !waitForMPI()) {
				doShutdown();
			}
		}

		@Override
		public boolean shutdown(int shutdownCode, Throwable shutdownThrowable) {
			Runtime runtime = Runtime.getRuntime();

			runtime.removeShutdownHook(this);

			doShutdown();

			return true;
		}

		private void doShutdown() {
			try {
				RemoteSPI.this.stop();
			}
			catch (RemoteException re) {
				_log.error("Unable to stop SPI", re);
			}

			try {
				RemoteSPI.this.destroy();
			}
			catch (RemoteException re) {
				_log.error("Unable to destroy SPI", re);
			}
		}

		private boolean waitForMPI() {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Wait up to " + spiConfiguration.getShutdownTimeout() +
						" ms for MPI shutdown request");
			}

			try {
				if (countDownLatch.await(
						spiConfiguration.getShutdownTimeout(),
						TimeUnit.MILLISECONDS)) {

					if (_log.isInfoEnabled()) {
						_log.info("MPI shutdown request received");
					}

					return true;
				}
			}
			catch (InterruptedException ie) {
			}

			if (_log.isInfoEnabled()) {
				_log.info("Proceed with SPI shutdown");
			}

			return false;
		}

	}

	private void readObject(ObjectInputStream objectInputStream)
		throws ClassNotFoundException, IOException {

		objectInputStream.defaultReadObject();

		System.setProperty(
			PropsKeys.INTRABAND_IMPL, objectInputStream.readUTF());
		System.setProperty(
			PropsKeys.INTRABAND_TIMEOUT_DEFAULT, objectInputStream.readUTF());
		System.setProperty(
			PropsKeys.INTRABAND_WELDER_IMPL, objectInputStream.readUTF());
		System.setProperty(
			"portal:" + PropsKeys.LIFERAY_HOME, objectInputStream.readUTF());

		// Disable auto deploy

		System.setProperty("portal:" + PropsKeys.AUTO_DEPLOY_ENABLED, "false");

		// Disable cluster link

		System.setProperty("portal:" + PropsKeys.CLUSTER_LINK_ENABLED, "false");

		// Disable dependency management

		try {
			Field enabledField = ReflectionUtil.getDeclaredField(
				DependencyManagementThreadLocal.class, "_enabled");

			enabledField.set(
				null,
				new ThreadLocal<Boolean>() {

					@Override
					public Boolean get() {
						return Boolean.FALSE;
					}

				});
		}
		catch (Exception e) {
			throw new IOException("Unable to disable dependency management", e);
		}

		// Log4j log file postfix

		System.setProperty("spi.id", "-" + spiConfiguration.getSPIId());

		countDownLatch = new CountDownLatch(1);
	}

	private void writeObject(ObjectOutputStream objectOutputStream)
		throws IOException {

		objectOutputStream.defaultWriteObject();

		objectOutputStream.writeUTF(
			System.getProperty(PropsKeys.INTRABAND_IMPL));
		objectOutputStream.writeUTF(
			System.getProperty(PropsKeys.INTRABAND_TIMEOUT_DEFAULT));
		objectOutputStream.writeUTF(
			System.getProperty(PropsKeys.INTRABAND_WELDER_IMPL));
		objectOutputStream.writeUTF(System.getProperty(PropsKeys.LIFERAY_HOME));
	}

	private static final Log _log = LogFactoryUtil.getLog(RemoteSPI.class);

}