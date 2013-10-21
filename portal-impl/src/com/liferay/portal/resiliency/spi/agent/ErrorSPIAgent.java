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

package com.liferay.portal.resiliency.spi.agent;

import com.liferay.portal.kernel.portlet.ActionResult;
import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgent;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Collections;

import javax.portlet.Event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public class ErrorSPIAgent implements SPIAgent {

	@Override
	public void destroy() {
	}

	@Override
	public void init(SPI spi) {
	}

	@Override
	public HttpServletRequest prepareRequest(HttpServletRequest request) {
		return request;
	}

	@Override
	public HttpServletResponse prepareResponse(
		HttpServletRequest request, HttpServletResponse response) {

		return response;
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalResiliencyException {

		SPIAgent.Lifecycle lifecycle = (SPIAgent.Lifecycle)request.getAttribute(
			WebKeys.SPI_AGENT_LIFECYCLE);

		switch (lifecycle) {
			case ACTION :
				request.setAttribute(
					WebKeys.SPI_AGENT_ACTION_RESULT,
					new ActionResult(
						Collections.<Event>emptyList(), StringPool.SLASH));

				return;
			case EVENT :
				request.setAttribute(
					WebKeys.SPI_AGENT_EVENT_RESULT, Collections.emptyList());

				return;
			case RENDER :
				try {
					PrintWriter printWriter = response.getWriter();

					printWriter.write("<div class=\"alert alert-error\">");
					printWriter.write("SPI is temporarily unavailable.");
					printWriter.write("</div>");
				}
				catch (IOException ioe) {
					throw new PortalResiliencyException(ioe);
				}

				return;
			case RESOURCE :
		}
	}

	@Override
	public void transferResponse(
		HttpServletRequest request, HttpServletResponse response, Exception e) {
	}

}