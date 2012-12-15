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

import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPI;

import java.rmi.RemoteException;

/**
 * @author Shuyang Zhou
 */
public class MockRemoteSPI extends RemoteSPI {

	public MockRemoteSPI(SPIConfiguration spiConfiguration) {
		super(spiConfiguration);
	}

	public void addServlet(
			String contextPath, String docBasePath, String mappingPattern,
			String servletClassName)
		throws RemoteException {

		throw new UnsupportedOperationException("");
	}

	public void addWebapp(String contextPath, String docBasePath)
		throws RemoteException {

		throw new UnsupportedOperationException("");
	}

	public void destroy() throws RemoteException {
		if (_failOnDestroy) {
			throw new RemoteException();
		}
	}

	public String getSPIProviderName() throws RemoteException {
		throw new UnsupportedOperationException("");
	}

	public void init() throws RemoteException {
		throw new UnsupportedOperationException("");
	}

	public void setFailOnDestroy(boolean failOnDestroy) {
		_failOnDestroy = failOnDestroy;
	}

	public void setFailOnStop(boolean failOnStop) {
		_failOnStop = failOnStop;
	}

	public void start() throws RemoteException {
		throw new UnsupportedOperationException("");
	}

	public void stop() throws RemoteException {
		if (_failOnStop) {
			throw new RemoteException();
		}
	}

	private boolean _failOnDestroy;
	private boolean _failOnStop;

}