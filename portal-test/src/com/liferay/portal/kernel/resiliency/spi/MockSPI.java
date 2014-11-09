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

package com.liferay.portal.kernel.resiliency.spi;

import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.resiliency.mpi.MPI;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgent;

import java.rmi.RemoteException;

/**
 * @author Shuyang Zhou
 */
public class MockSPI implements SPI {

	@Override
	public void addServlet(
		String contextPath, String docBasePath, String mappingPattern,
		String servletClassName) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void addWebapp(String contextPath, String docBasePath) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void destroy() throws RemoteException {
		if (failOnDestroy) {
			throw new RemoteException();
		}

		destroyed = true;
	}

	@Override
	public MPI getMPI() {
		return mpi;
	}

	@Override
	public RegistrationReference getRegistrationReference()
		throws RemoteException {

		if (registrationReference == null) {
			throw new RemoteException();
		}

		return registrationReference;
	}

	@Override
	public SPIAgent getSPIAgent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public SPIConfiguration getSPIConfiguration() throws RemoteException {
		if (failOnGetConfiguration) {
			throw new RemoteException();
		}

		return spiConfiguration;
	}

	@Override
	public String getSPIProviderName() {
		return spiProviderName;
	}

	@Override
	public void init() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isAlive() throws RemoteException {
		if (failOnIsAlive) {
			throw new RemoteException();
		}

		return true;
	}

	@Override
	public void start() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void stop() throws RemoteException {
		if (failOnStop) {
			throw new RemoteException();
		}

		stopped = true;
	}

	public boolean destroyed;
	public boolean failOnDestroy;
	public boolean failOnGetConfiguration;
	public boolean failOnIsAlive;
	public boolean failOnStop;
	public MPI mpi;
	public RegistrationReference registrationReference;
	public SPIConfiguration spiConfiguration;
	public String spiProviderName;
	public boolean stopped;

}