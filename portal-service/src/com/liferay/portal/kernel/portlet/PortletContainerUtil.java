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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.servlet.TempAttributesServletRequest;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;

import javax.portlet.Event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public class PortletContainerUtil {

	public static void preparePortletServing(
			HttpServletRequest request, Portlet portlet)
		throws PortletContainerException {

		_portletContainer.preparePortletServing(request, portlet);
	}

	public static void processAction(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		_portletContainer.processAction(request, response, portlet);
	}

	public static void processEvent(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, Layout layout, Event event)
		throws PortletContainerException {

		_portletContainer.processEvent(
			request, response, portlet, layout, event);
	}

	public static void render(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		_portletContainer.render(request, response, portlet);
	}

	public static void serveResource(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		_portletContainer.serveResource(request, response, portlet);
	}

	public static HttpServletRequest setupOptionalRenderParameters(
		HttpServletRequest request, String renderPath, String columnId,
		Integer columnPos, Integer columnCount) {

		TempAttributesServletRequest tempAttributesServletRequest =
			new TempAttributesServletRequest(request);

		if (renderPath != null) {
			tempAttributesServletRequest.setTempAttribute(
				WebKeys.RENDER_PATH, renderPath);
		}

		if (columnId != null) {
			tempAttributesServletRequest.setTempAttribute(
				WebKeys.RENDER_PORTLET_COLUMN_ID, columnId);
		}

		if (columnPos != null) {
			tempAttributesServletRequest.setTempAttribute(
				WebKeys.RENDER_PORTLET_COLUMN_POS, columnPos);
		}

		if (columnCount != null) {
			tempAttributesServletRequest.setTempAttribute(
				WebKeys.RENDER_PORTLET_COLUMN_COUNT, columnCount);
		}

		return tempAttributesServletRequest;
	}

	public void setPortletContainer(PortletContainer portletContainer) {
		_portletContainer = portletContainer;
	}

	private static PortletContainer _portletContainer;

}