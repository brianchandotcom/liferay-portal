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

package com.liferay.portal.layoutconfiguration.util;

import com.liferay.portal.kernel.portlet.PortletContainerException;
import com.liferay.portal.kernel.portlet.PortletContainerUtil;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Portlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public class PortletRenderer {

	public PortletRenderer(
		Portlet portlet, String columnId, Integer columnCount,
		Integer columnPos) {

		_portlet = portlet;
		_columnId = columnId;
		_columnCount = columnCount;
		_columnPos = columnPos;
	}

	public Portlet getPortlet() {
		return _portlet;
	}

	public StringBundler render(
			HttpServletRequest request, HttpServletResponse response)
		throws PortletContainerException {

		request = PortletContainerUtil.setupOptionalRenderParameters(
			request, null, _columnId, _columnPos, _columnCount);

		return _render(request, response);
	}

	public StringBundler renderAjax(
			HttpServletRequest request, HttpServletResponse response)
		throws PortletContainerException {

		request = PortletContainerUtil.setupOptionalRenderParameters(
			request, _RENDER_PATH, _columnId, _columnPos, _columnCount);

		return _render(request, response);
	}

	public StringBundler renderError(
			HttpServletRequest request, HttpServletResponse response)
		throws PortletContainerException {

		request = PortletContainerUtil.setupOptionalRenderParameters(
			request, null, _columnId, _columnPos, _columnCount);

		return _render(request, response);
	}

	private StringBundler _render(
			HttpServletRequest request, HttpServletResponse response)
		throws PortletContainerException {

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(response);

		try {
			PortletContainerUtil.render(
				request, bufferCacheServletResponse, _portlet);

			return bufferCacheServletResponse.getStringBundler();
		}
		catch (IOException ioe) {
			throw new PortletContainerException(ioe);
		}
	}

	private static final String _RENDER_PATH =
		"/html/portal/load_render_portlet.jsp";

	private final Integer _columnCount;
	private final String _columnId;
	private final Integer _columnPos;
	private final Portlet _portlet;

}