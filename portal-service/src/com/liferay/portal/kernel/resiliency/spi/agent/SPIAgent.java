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

import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.Direction;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.Distributed;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public interface SPIAgent {

	@Distributed(direction = Direction.RESPONSE)
	public static final String SPI_AGENT_ACTION_RESULT =
		"SPI_AGENT_ACTION_RESULT";

	@Distributed(direction = Direction.REQUEST)
	public static final String SPI_AGENT_EVENT = "SPI_AGENT_EVENT";

	@Distributed(direction = Direction.RESPONSE)
	public static final String SPI_AGENT_EVENT_RESULT =
		"SPI_AGENT_EVENT_RESULT";

	@Distributed(direction = Direction.REQUEST)
	public static final String SPI_AGENT_LAYOUT = "SPI_AGENT_LAYOUT";

	@Distributed(direction = Direction.RESPONSE)
	public static final String SPI_AGENT_LAYOUT_TYPE_SETTINGS =
		"SPI_AGENT_LAYOUT_TYPE_SETTINGS";

	@Distributed(direction = Direction.REQUEST)
	public static final String SPI_AGENT_LIFECYCLE = "SPI_AGENT_LIFECYCLE";

	@Distributed(direction = Direction.REQUEST)
	public static final String SPI_AGENT_PORTLET = "SPI_AGENT_PORTLET";

	public static final String SPI_AGENT_REQUEST = "SPI_AGENT_REQUEST";

	public static final String SPI_AGENT_RESPONSE = "SPI_AGENT_RESPONSE";

	public void destroy();

	public void init(SPI spi) throws PortalResiliencyException;

	public HttpServletRequest prepareRequest(HttpServletRequest request)
		throws IOException;

	public HttpServletResponse prepareResponse(
		HttpServletRequest request, HttpServletResponse response);

	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalResiliencyException;

	public void transferResponse(
			HttpServletRequest request, HttpServletResponse response,
			Exception e)
		throws IOException;

	public enum Lifecycle {

		ACTION("0"), EVENT("1"), RENDER("2"), RESOURCE("3");

		public String getValue() {
			return _value;
		}

		private Lifecycle(String value) {
			_value = value;
		}

		private String _value;

	}

}