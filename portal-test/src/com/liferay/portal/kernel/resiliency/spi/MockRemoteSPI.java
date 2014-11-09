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

import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPI;

import java.rmi.RemoteException;

/**
 * @author Shuyang Zhou
 */
public class MockRemoteSPI extends RemoteSPI {

	public MockRemoteSPI(SPIConfiguration spiConfiguration) {
		super(spiConfiguration);
	}

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
	public String getSPIProviderName() {
		return _spiProviderName;
	}

	@Override
	public void init() {
		throw new UnsupportedOperationException();
	}

	public void setFailOnDestroy(boolean failOnDestroy) {
		_failOnDestroy = failOnDestroy;
	}

	public void setFailOnStop(boolean failOnStop) {
		_failOnStop = failOnStop;
	}

	public void setSpiProviderName(String spiProviderName) {
		_spiProviderName = spiProviderName;
	}

	@Override
	public void start() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void stop() throws RemoteException {
		if (_failOnStop) {
			throw new RemoteException();
		}
	}

	@Override
	protected void doDestroy() throws RemoteException {
		if (_failOnDestroy) {
			throw new RemoteException();
		}
	}

	private boolean _failOnDestroy;
	private boolean _failOnStop;
	private String _spiProviderName;

}