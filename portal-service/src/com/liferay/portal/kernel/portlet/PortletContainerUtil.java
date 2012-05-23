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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
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

	public static void preparePortlet(
			HttpServletRequest request, Portlet portlet)
		throws PortletContainerException {

		_portletContainer.preparePortlet(request, portlet);
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

		if (_PORTLET_CONTAINER_RESTRICT) {
			RestrictPortletServletRequest restrictPortletServletRequest =
				new RestrictPortletServletRequest(request);

			if (renderPath != null) {
				restrictPortletServletRequest.setAttribute(
					WebKeys.RENDER_PATH, renderPath);
			}

			if (columnId != null) {
				restrictPortletServletRequest.setAttribute(
					WebKeys.RENDER_PORTLET_COLUMN_ID, columnId);
			}

			if (columnPos != null) {
				restrictPortletServletRequest.setAttribute(
					WebKeys.RENDER_PORTLET_COLUMN_POS, columnPos);
			}

			if (columnCount != null) {
				restrictPortletServletRequest.setAttribute(
					WebKeys.RENDER_PORTLET_COLUMN_COUNT, columnCount);
			}

			return restrictPortletServletRequest;
		}
		else {
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
	}

	public void setPortletContainer(PortletContainer portletContainer) {
		if (_PORTLET_CONTAINER_RESTRICT) {
			portletContainer = new RestrictPortletContainerWrapper(
				portletContainer);
		}

		_portletContainer = portletContainer;
	}

	private static final boolean _PORTLET_CONTAINER_RESTRICT =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.PORTLET_CONTAINER_RESTRICT)) ||
				GetterUtil.getBoolean(
					PropsUtil.get(PropsKeys.LAYOUT_PARALLEL_RENDER_ENABLE));

	private static PortletContainer _portletContainer;

}