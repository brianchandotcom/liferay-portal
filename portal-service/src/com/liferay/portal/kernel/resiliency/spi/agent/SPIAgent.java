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

	@Distributed(direction = Direction.Response)
	public static final String ACTION_RESULT = "SPIAGENT_ACTION_RESULT";

	public static final String AGENT_REQUEST = "AGENT_REQUEST";

	public static final String AGENT_RESPONSE = "AGENT_RESPONSE";

	@Distributed(direction = Direction.Request)
	public static final String EVENT = "SPIAGENT_EVENT";

	@Distributed(direction = Direction.Response)
	public static final String EVENT_RESULT = "SPIAGENT_EVENT_RESULT";

	@Distributed(direction = Direction.Request)
	public static final String LAYOUT = "SPIAGENT_LAYOUT";

	@Distributed(direction = Direction.Response)
	public static final String LAYOUT_TYPE_SETTINGS =
		"SPIAGENT_LAYOUT_TYPE_SETTINGS";

	@Distributed(direction = Direction.Request)
	public static final String LIFECYCLE = "SPIAGENT_LIFECYCLE";

	@Distributed(direction = Direction.Request)
	public static final String PORTLET = "SPIAGENT_PORTLET";

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

		Render("0"), Action("1"), Resource("2"), Event("3");

		private Lifecycle(String value) {
			_value = value;
		}

		public String getValue() {
			return _value;
		}

		private String _value;

	}

}