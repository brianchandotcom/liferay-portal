/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.resiliency.mpi.MPIUtil;
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
 * Base class for SPI implementation.<p>
 * Subclasses should provide actual SPI logic, be initialized by
 * {@link SPIProvider} in MPI, invoked through ProcessExecutor<p>
 * On execution of call() in SPI process, exports itself as RMI stub, wraps the
 * stub into {@link RegisterCallback}, then write back the
 * {@link RegisterCallback} to MPI through process piping.<p>
 * MPI invokes the {@link RegisterCallback} on receiving which will notify the
 * {@link SPIRegisterSynchronizer} to unblock the {@link SPIProvider}.
 *
 * @author Shuyang Zhou
 */
public abstract class RemoteSPI implements ProcessCallable<SPI>, Remote, SPI {

	public RemoteSPI(SPIConfiguration spiConfiguration) {
		this.spiConfiguration = spiConfiguration;
		mpiStub = MPIUtil.getMPIStub();
		uuid = UUID.randomUUID().toString();
		welder = WelderFactoryUtil.createWelder();
	}

	/**
	 * SPI process execution entry point.<p>
	 * Export this SPI instance as RMI stub, wraps the stub into
	 * {@link RegisterCallback}, then write back the {@link RegisterCallback}
	 * to MPI through process piping.<p>
	 * The execution of {@link RegisterCallback} on MPI will unblocked the
	 * {@link SPIProvider} who created this SPI instance.
	 *
	 * @return The RMI stub for this SPI instance.
	 * @throws ProcessException
	 */
	public SPI call() throws ProcessException {
		try {
			ProcessExecutor.ProcessContext.attach(
				spiConfiguration.getId(), spiConfiguration.getPingInterval(),
				new SPIShutdownHook());

			SPI stubSPI = (SPI)UnicastRemoteObject.exportObject(this, 0);

			String spiUUID = getUUID();

			RegisterCallback registerCallback = new RegisterCallback(
				spiUUID, stubSPI);

			ProcessOutputStream processOutputStream =
				ProcessExecutor.ProcessContext.getProcessOutputStream();

			processOutputStream.writeProcessCallable(registerCallback);

			registrationReference = welder.weldClient(MPIUtil.getIntraBand());

			ConcurrentMap<String, Object> attributes =
				ProcessExecutor.ProcessContext.getAttributes();

			attributes.put(SPI.SPI_INSTANCE_PUBLICATION_KEY, this);

			return stubSPI;
		}
		catch (RemoteException re) {
			throw new ProcessException(
				"Failed to export current SPI as RMI stub.", re);
		}
		catch (IOException ioe) {
			throw new ProcessException(ioe);
		}
	}

	public MPI getMPI() {
		return mpiStub;
	}

	public RegistrationReference getRegistrationReference() {
		return registrationReference;
	}

	public SPIAgent getSPIAgent() {
		if (spiAgent == null) {
			spiAgent = SPIAgentFactoryUtil.createSPIAgent(
				spiConfiguration, registrationReference);
		}

		return spiAgent;
	}

	public SPIConfiguration getSPIConfiguration() {
		return spiConfiguration;
	}

	public String getUUID() {
		return uuid;
	}

	public Welder getWelder() {
		return welder;
	}

	public boolean isAlive() {
		return true;
	}

	protected final MPI mpiStub;

	// No need to be volatile as it is initialized before all reading threads'
	// creation.

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

		public SPI call() throws ProcessException {
			try {
				SPIRegisterSynchronizer.notifySynchronizer(_spiUUID, _spi);
			}
			catch (InterruptedException ie) {
				throw new ProcessException(ie);
			}

			return _spi;
		}

		private final SPI _spi;
		private final String _spiUUID;

	}

	protected class SPIShutdownHook implements ProcessExecutor.ShutdownHook {

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

		String intraBandDefaultTimeout = objectInputStream.readUTF();
		String intraBandImplClass = objectInputStream.readUTF();
		String intraBandWelderImplClass = objectInputStream.readUTF();
		String liferayHome = objectInputStream.readUTF();

		System.setProperty(
			PropsKeys.INTRABAND_DEFAULT_TIMEOUT, intraBandDefaultTimeout);
		System.setProperty(PropsKeys.INTRABAND_IMPL_CLASS, intraBandImplClass);
		System.setProperty(
			PropsKeys.INTRABAND_WELDER_IMPL_CLASS, intraBandWelderImplClass);
		System.setProperty("portal:" + PropsKeys.LIFERAY_HOME, liferayHome);

		// log4j file postfix

		System.setProperty("spi.name", "-".concat(spiConfiguration.getId()));

		// Disable auto deploy

		System.setProperty("portal:" + PropsKeys.AUTO_DEPLOY_ENABLED, "false");

		// Disable cluster link

		System.setProperty("portal:" + PropsKeys.CLUSTER_LINK_ENABLED, "false");
	}

	// Serialization spec requied signature, don't change

	private void writeObject(ObjectOutputStream objectOutputStream)
		throws IOException {

		objectOutputStream.defaultWriteObject();

		String intraBandDefaultTimeout = System.getProperty(
			PropsKeys.INTRABAND_DEFAULT_TIMEOUT);
		String intraBandImplClass = System.getProperty(
			PropsKeys.INTRABAND_IMPL_CLASS);
		String intraBandWelderImplClass = System.getProperty(
			PropsKeys.INTRABAND_WELDER_IMPL_CLASS);
		String liferayHome = System.getProperty(PropsKeys.LIFERAY_HOME);

		objectOutputStream.writeUTF(intraBandDefaultTimeout);
		objectOutputStream.writeUTF(intraBandImplClass);
		objectOutputStream.writeUTF(intraBandWelderImplClass);
		objectOutputStream.writeUTF(liferayHome);
	}

	private static final Log _log = LogFactoryUtil.getLog(RemoteSPI.class);

}