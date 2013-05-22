/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Shuyang Zhou
 */
public class AcceptorServlet extends HttpServlet {

	@Override
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		PortalUtil.setPortalPort(request);

		ServletContext servletContext = getServletContext();

		ServletContext portalServletContext = servletContext.getContext("/");

		RequestDispatcher requestDispatcher =
			portalServletContext.getRequestDispatcher("/c/portal/resiliency");

		SPI spi = SPIUtil.getSPI();

		SPIAgent spiAgent = spi.getSPIAgent();

		HttpServletRequest agentServletRequest = spiAgent.prepareRequest(
			request);

		HttpServletResponse agentServletResponse = spiAgent.prepareResponse(
			request, response);

		Exception exception = null;

		try {
			requestDispatcher.forward(
				agentServletRequest, agentServletResponse);
		}
		catch (Exception e) {
			exception = e;
		}

		spiAgent.transferResponse(
			agentServletRequest, agentServletResponse, exception);

		// Invalidate Session to free up memory

		HttpSession session = agentServletRequest.getSession();

		session.invalidate();
	}

}