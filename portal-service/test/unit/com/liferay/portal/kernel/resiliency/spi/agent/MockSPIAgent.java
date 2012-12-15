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

package com.liferay.portal.kernel.resiliency.spi.agent;

import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public class MockSPIAgent implements SPIAgent {

	public MockSPIAgent(
		SPIConfiguration spiConfiguration,
		RegistrationReference registrationReference) {
	}

	public void destroy() {
		throw new UnsupportedOperationException();
	}

	public void init(SPI spi) throws PortalResiliencyException {
		throw new UnsupportedOperationException();
	}

	public HttpServletRequest prepareRequest(HttpServletRequest request)
		throws IOException {

		throw new UnsupportedOperationException();
	}

	public HttpServletResponse prepareResponse(
		HttpServletRequest request, HttpServletResponse response) {

		throw new UnsupportedOperationException();
	}

	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalResiliencyException {

		throw new UnsupportedOperationException();
	}

	public void transferResponse(
			HttpServletRequest request, HttpServletResponse response,
			Exception e)
		throws IOException {

		throw new UnsupportedOperationException();
	}

}