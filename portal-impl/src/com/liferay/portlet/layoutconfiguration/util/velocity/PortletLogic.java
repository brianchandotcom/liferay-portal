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

package com.liferay.portlet.layoutconfiguration.util.velocity;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portlet.layoutconfiguration.util.RuntimePortletUtil;

import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public class PortletLogic extends RuntimeLogic {

	public PortletLogic(
		ServletContext servletContext, HttpServletRequest request,
		HttpServletResponse response, String portletId) {

		this(servletContext, request, response, null, null);

		_portletId = portletId;
	}

	public PortletLogic(
		ServletContext servletContext, HttpServletRequest request,
		HttpServletResponse response, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_servletContext = servletContext;
		_request = request;
		_response = response;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	@Override
	public String processContent(Map<String, String> attributes)
		throws Exception {

		String rootPortletId = attributes.get("name");
		String instanceId = attributes.get("instance");
		String queryString = attributes.get("queryString");

		String portletId = _portletId;

		if (portletId == null) {
			portletId = rootPortletId;

			if (Validator.isNotNull(instanceId)) {
				portletId += PortletConstants.INSTANCE_SEPARATOR + instanceId;
			}
		}

		return RuntimePortletUtil.processPortlet(
			_servletContext, _request, _response, _renderRequest,
			_renderResponse, portletId, queryString, false);
	}

	private String _portletId;
	private RenderRequest _renderRequest;
	private RenderResponse _renderResponse;
	private HttpServletRequest _request;
	private HttpServletResponse _response;
	private ServletContext _servletContext;

}