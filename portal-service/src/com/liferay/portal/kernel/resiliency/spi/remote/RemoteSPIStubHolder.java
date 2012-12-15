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
import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.resiliency.mpi.MPI;
import com.liferay.portal.kernel.resiliency.mpi.MPIUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgent;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgentFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.rmi.RemoteException;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * A local peer reference for remote SPI implementation.
 * Holding the remote SPI RMI stub and the {@link SPIProvider} derived from.
 * All methods call are simply delegated to RMI stub, besides the
 * {@link getMPI()}, {@link getRegistrationReference()}, {@link getSPIAgent()},
 * {@link getSPIConfiguration()} and {@link #getSPIProviderName()}
 *
 * @author Shuyang Zhou
 */
public class RemoteSPIStubHolder implements SPI {

	public RemoteSPIStubHolder(
		SPI spiStub, SPIConfiguration spiConfiguration, String spiProviderName,
		Future<SPI> cancelHandlerFuture,
		RegistrationReference registrationReference) {

		_spiStub = spiStub;
		_spiConfiguration = spiConfiguration;
		_spiProviderName = spiProviderName;
		_cancelHandlerFuture = cancelHandlerFuture;
		_registrationReference = registrationReference;
		_spiAgent = SPIAgentFactoryUtil.createSPIAgent(
			spiConfiguration, registrationReference);
		_mpiStub = MPIUtil.getMPIStub();
	}

	public void addServlet(
			String contextPath, String docBasePath, String mappingPattern,
			String servletClassName)
		throws RemoteException {

		_spiStub.addServlet(
			contextPath, docBasePath, mappingPattern, servletClassName);
	}

	public void addWebapp(String contextPath, String docBasePath)
		throws RemoteException {

		_spiStub.addWebapp(contextPath, docBasePath);
	}

	public void destroy() throws RemoteException {
		try {
			_spiStub.destroy();

			_cancelHandlerFuture.get(
				_spiConfiguration.getShutdownTimeout(), TimeUnit.MILLISECONDS);
		}
		catch (Exception e) {
			_cancelHandlerFuture.cancel(true);

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Due to the Exception, sub-process with configuration : " +
						_spiConfiguration + ", has been forcibly destroyed.",
					e);
			}
		}
		finally {
			MPIUtil.unregisterSPI(this);
		}

		_spiAgent.destroy();
	}

	public MPI getMPI() {
		return _mpiStub;
	}

	public RegistrationReference getRegistrationReference() {
		return _registrationReference;
	}

	public SPIAgent getSPIAgent() {
		return _spiAgent;
	}

	public SPIConfiguration getSPIConfiguration() {
		return _spiConfiguration;
	}

	public String getSPIProviderName() {
		return _spiProviderName;
	}

	public void init() throws RemoteException {
		_spiStub.init();

		try {
			_spiAgent.init(this);
		}
		catch (PortalResiliencyException pre) {
			throw new RemoteException("Failed to initialize SPIAgent", pre);
		}
	}

	public boolean isAlive() throws RemoteException {
		try {
			return _spiStub.isAlive();
		}
		catch (RemoteException re) {
			try {
				_cancelHandlerFuture.get();
			}
			catch (Exception e) {
				throw new RemoteException(
					"SPI " + toString() + " dead unexpectedly", e);
			}

			return false;
		}
	}

	public void start() throws RemoteException {
		_spiStub.start();
	}

	public void stop() throws RemoteException {
		_spiStub.stop();
	}

	public String toString() {
		return _spiProviderName.concat(StringPool.POUND).concat(
			_spiConfiguration.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteSPIStubHolder.class);

	private final Future<SPI> _cancelHandlerFuture;
	private final MPI _mpiStub;
	private final RegistrationReference _registrationReference;
	private final SPIAgent _spiAgent;
	private final SPIConfiguration _spiConfiguration;
	private final String _spiProviderName;
	private final SPI _spiStub;

}