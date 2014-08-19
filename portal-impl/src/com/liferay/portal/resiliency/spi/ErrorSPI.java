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

package com.liferay.portal.resiliency.spi;

import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.resiliency.mpi.MPI;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgent;
import com.liferay.portal.resiliency.spi.agent.ErrorSPIAgent;

/**
 * @author Shuyang Zhou
 */
public class ErrorSPI implements SPI {

	@Override
	public void addServlet(
		String contextPath, String docBasePath, String mappingPattern,
		String servletClassName) {
	}

	@Override
	public void addWebapp(String contextPath, String docBasePath) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public MPI getMPI() {
		return null;
	}

	@Override
	public RegistrationReference getRegistrationReference() {
		return null;
	}

	@Override
	public SPIAgent getSPIAgent() {
		return _spiAgent;
	}

	@Override
	public SPIConfiguration getSPIConfiguration() {
		return null;
	}

	@Override
	public String getSPIProviderName() {
		return null;
	}

	@Override
	public void init() {
	}

	@Override
	public boolean isAlive() {
		return true;
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}

	private static final SPIAgent _spiAgent = new ErrorSPIAgent();

}