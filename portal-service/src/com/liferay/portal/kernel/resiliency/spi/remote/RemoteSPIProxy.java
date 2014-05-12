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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.process.TerminationProcessException;
import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.resiliency.mpi.MPI;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgent;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgentFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.rmi.RemoteException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Shuyang Zhou
 */
public class RemoteSPIProxy implements SPI {

	public static final int SIGINT = 130;

	public RemoteSPIProxy(
		SPI spi, SPIConfiguration spiConfiguration, String spiProviderName,
		Future<SPI> cancelHandlerFuture,
		RegistrationReference registrationReference) {

		_spi = spi;
		_spiConfiguration = spiConfiguration;
		_spiProviderName = spiProviderName;
		_cancelHandlerFuture = cancelHandlerFuture;
		_registrationReference = registrationReference;

		_mpi = MPIHelperUtil.getMPI();
		_spiAgent = SPIAgentFactoryUtil.createSPIAgent(
			spiConfiguration, registrationReference);
	}

	@Override
	public void addServlet(
			String contextPath, String docBasePath, String mappingPattern,
			String servletClassName)
		throws RemoteException {

		_spi.addServlet(
			contextPath, docBasePath, mappingPattern, servletClassName);
	}

	@Override
	public void addWebapp(String contextPath, String docBasePath)
		throws RemoteException {

		_spi.addWebapp(contextPath, docBasePath);
	}

	@Override
	public void destroy() {
		try {
			_spi.destroy();

			_cancelHandlerFuture.get(
				_spiConfiguration.getShutdownTimeout(), TimeUnit.MILLISECONDS);
		}
		catch (Exception e) {
			boolean forceDestroy = true;

			if (e instanceof ExecutionException) {
				Throwable throwable = e.getCause();

				if (throwable instanceof TerminationProcessException) {
					TerminationProcessException terminationProcessException =
						(TerminationProcessException)throwable;

					if (terminationProcessException.getExitCode() == SIGINT) {
						forceDestroy = false;
					}
				}
			}

			if (forceDestroy) {
				_cancelHandlerFuture.cancel(true);

				if (_log.isWarnEnabled()) {
					_log.warn("Forcibly destroyed SPI " + _spiConfiguration, e);
				}
			}
		}
		finally {
			MPIHelperUtil.unregisterSPI(this);
		}

		_spiAgent.destroy();
	}

	@Override
	public MPI getMPI() {
		return _mpi;
	}

	@Override
	public RegistrationReference getRegistrationReference() {
		return _registrationReference;
	}

	@Override
	public SPIAgent getSPIAgent() {
		return _spiAgent;
	}

	@Override
	public SPIConfiguration getSPIConfiguration() {
		return _spiConfiguration;
	}

	@Override
	public String getSPIProviderName() {
		return _spiProviderName;
	}

	@Override
	public void init() throws RemoteException {
		_spi.init();

		try {
			_spiAgent.init(this);
		}
		catch (PortalResiliencyException pre) {
			throw new RemoteException("Unable to initialize SPI agent", pre);
		}
	}

	@Override
	public boolean isAlive() throws RemoteException {
		try {
			return _spi.isAlive();
		}
		catch (RemoteException re) {
			try {
				_cancelHandlerFuture.get();
			}
			catch (Exception e) {
				throw new RemoteException(
					"SPI " + toString() + " died unexpectedly", e);
			}

			return false;
		}
	}

	@Override
	public void start() throws RemoteException {
		_spi.start();
	}

	@Override
	public void stop() throws RemoteException {
		_spi.stop();
	}

	@Override
	public String toString() {
		return _spiProviderName.concat(StringPool.POUND).concat(
			_spiConfiguration.toString());
	}

	private static Log _log = LogFactoryUtil.getLog(RemoteSPIProxy.class);

	private final Future<SPI> _cancelHandlerFuture;
	private final MPI _mpi;
	private final RegistrationReference _registrationReference;
	private final SPI _spi;
	private final SPIAgent _spiAgent;
	private final SPIConfiguration _spiConfiguration;
	private final String _spiProviderName;

}