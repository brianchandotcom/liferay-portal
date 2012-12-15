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

package com.liferay.portal.kernel.resiliency.spi;

import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.resiliency.mpi.MPI;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgent;

import java.rmi.RemoteException;

/**
 * @author Shuyang Zhou
 */
public class MockSPI implements SPI {

	public void addServlet(
			String contextPath, String docBasePath, String mappingPattern,
			String servletClassName)
		throws RemoteException {
		throw new UnsupportedOperationException();
	}

	public void addWebapp(String contextPath, String docBasePath)
		throws RemoteException {
		throw new UnsupportedOperationException();
	}

	public void destroy() throws RemoteException {
		if (failOnDestroy) {
			throw new RemoteException();
		}
	}

	public MPI getMPI() throws RemoteException {
		return mpi;
	}

	public RegistrationReference getRegistrationReference()
		throws RemoteException {
		throw new UnsupportedOperationException();
	}

	public SPIAgent getSPIAgent() throws RemoteException {
		throw new UnsupportedOperationException();
	}

	public SPIConfiguration getSPIConfiguration() throws RemoteException {
		if (failOnGetConfiguration) {
			throw new RemoteException();
		}

		return spiConfiguration;
	}

	public String getSPIProviderName() throws RemoteException {
		return spiProviderName;
	}

	public void init() throws RemoteException {
		throw new UnsupportedOperationException();
	}

	public boolean isAlive() throws RemoteException {
		if (failOnIsAlive) {
			throw new RemoteException();
		}

		return true;
	}

	public void start() throws RemoteException {
		throw new UnsupportedOperationException();
	}

	public void stop() throws RemoteException {
		throw new UnsupportedOperationException();
	}

	public boolean failOnDestroy;
	public boolean failOnGetConfiguration;
	public boolean failOnIsAlive;
	public MPI mpi;
	public SPIConfiguration spiConfiguration;
	public String spiProviderName;

}