/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.welder.Welder;
import com.liferay.portal.kernel.nio.intraband.welder.WelderFactoryUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.process.log.ProcessOutputStream;
import com.liferay.portal.kernel.resiliency.mpi.MPI;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgent;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgentFactoryUtil;
import com.liferay.portal.kernel.resiliency.spi.provider.SPIRegisterSynchronizer;
import com.liferay.portal.kernel.util.PropsKeys;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

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
			ProcessExecutor.ProcessContext.attach(
				spiConfiguration.getSPIId(), spiConfiguration.getPingInterval(),
				new SPIShutdownHook());

			SPI spi = (SPI)UnicastRemoteObject.exportObject(this, 0);

			RegisterCallback registerCallback = new RegisterCallback(uuid, spi);

			ProcessOutputStream processOutputStream =
				ProcessExecutor.ProcessContext.getProcessOutputStream();

			processOutputStream.writeProcessCallable(registerCallback);

			registrationReference = welder.weld(MPIHelperUtil.getIntraband());

			ConcurrentMap<String, Object> attributes =
				ProcessExecutor.ProcessContext.getAttributes();

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
				SPIRegisterSynchronizer.notifySynchronizer(_spiUUID, _spi);
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

	protected class SPIShutdownHook implements ProcessExecutor.ShutdownHook {

		@Override
		public boolean shutdown(int shutdownCode, Throwable shutdownThrowable) {
			try {
				RemoteSPI.this.stop();
			}
			catch (RemoteException re) {
				_log.error("Failed stop SPI.", re);
			}

			try {
				RemoteSPI.this.destroy();
			}
			catch (RemoteException re) {
				_log.error("Failed destroy SPI.", re);
			}

			return true;
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

		// Log4j log file postfix

		System.setProperty("spi.id", "-".concat(spiConfiguration.getSPIId()));
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

	private static Log _log = LogFactoryUtil.getLog(RemoteSPI.class);

}