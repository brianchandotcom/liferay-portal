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

package com.liferay.portal.kernel.resiliency.spi.agent;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Shuyang Zhou
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Deprecated
public class AcceptorServlet extends HttpServlet {

	protected void doService(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		PortalUtil.setPortalInetSocketAddresses(httpServletRequest);

		ServletContext servletContext = getServletContext();

		String uriPath = PortalUtil.getPathContext();

		if (uriPath.isEmpty()) {
			uriPath = StringPool.SLASH;
		}

		ServletContext portalServletContext = servletContext.getContext(
			uriPath);

		RequestDispatcher requestDispatcher =
			portalServletContext.getRequestDispatcher("/c/portal/resiliency");

		SPI spi = SPIUtil.getSPI();

		SPIAgent spiAgent = spi.getSPIAgent();

		HttpServletRequest spiAgentHttpServletRequest = spiAgent.prepareRequest(
			httpServletRequest);

		HttpServletResponse spiAgentHttpServletResponse =
			spiAgent.prepareResponse(httpServletRequest, httpServletResponse);

		Exception exception1 = null;

		try {
			requestDispatcher.forward(
				spiAgentHttpServletRequest, spiAgentHttpServletResponse);
		}
		catch (Exception exception2) {
			exception1 = exception2;
		}

		spiAgent.transferResponse(
			spiAgentHttpServletRequest, spiAgentHttpServletResponse,
			exception1);

		HttpSession session = spiAgentHttpServletRequest.getSession();

		session.invalidate();
	}

	@Override
	protected void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			doService(httpServletRequest, httpServletResponse);
		}
		catch (IOException ioException) {
			_log.error(ioException, ioException);

			throw ioException;
		}
		catch (RuntimeException runtimeException) {
			_log.error(runtimeException, runtimeException);

			throw runtimeException;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AcceptorServlet.class);

}