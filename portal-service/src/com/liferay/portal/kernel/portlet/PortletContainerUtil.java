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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.servlet.TempAttributesServletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.LayoutLocalServiceUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.Event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 * @author Raymond Augé
 */
public class PortletContainerUtil {

	public static List<LayoutTypePortlet> getLayoutTypePortlets(Layout layout)
		throws PortletContainerException {

		if (_PORTLET_EVENT_DISTRIBUTION_LAYOUT_SET) {
			List<Layout> layouts = null;

			try {
				layouts = LayoutLocalServiceUtil.getLayouts(
					layout.getGroupId(), LayoutConstants.TYPE_PORTLET);
			}
			catch (SystemException se) {
				throw new PortletContainerException(se);
			}

			List<LayoutTypePortlet> layoutTypePortlets = new ArrayList<>(
				layouts.size());

			for (Layout curLayout : layouts) {
				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)curLayout.getLayoutType();

				layoutTypePortlets.add(layoutTypePortlet);
			}

			return layoutTypePortlets;
		}

		if (layout.isTypePortlet()) {
			List<LayoutTypePortlet> layoutTypePortlets = new ArrayList<>(1);

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			layoutTypePortlets.add(layoutTypePortlet);

			return layoutTypePortlets;
		}

		return Collections.emptyList();
	}

	public static PortletContainer getPortletContainer() {
		PortalRuntimePermission.checkGetBeanProperty(
			PortletContainerUtil.class);

		return _portletContainer;
	}

	public static void preparePortlet(
			HttpServletRequest request, Portlet portlet)
		throws PortletContainerException {

		getPortletContainer().preparePortlet(request, portlet);
	}

	public static void processAction(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		PortletContainer portletContainer = getPortletContainer();

		ActionResult actionResult = portletContainer.processAction(
			request, response, portlet);

		List<Event> events = actionResult.getEvents();

		if (!events.isEmpty()) {
			_processEvents(request, response, events);
		}

		String location = actionResult.getLocation();

		if (Validator.isNotNull(location)) {
			try {
				response.sendRedirect(location);
			}
			catch (IOException ioe) {
				throw new PortletContainerException(ioe);
			}
		}
	}

	public static void processEvent(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, Layout layout, Event event)
		throws PortletContainerException {

		PortletContainer portletContainer = getPortletContainer();

		List<Event> events = portletContainer.processEvent(
			request, response, portlet, layout, event);

		if (!events.isEmpty()) {
			_processEvents(request, response, events);
		}
	}

	public static void render(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		getPortletContainer().render(request, response, portlet);
	}

	public static void serveResource(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		getPortletContainer().serveResource(request, response, portlet);
	}

	public static HttpServletRequest setupOptionalRenderParameters(
		HttpServletRequest request, String renderPath, String columnId,
		Integer columnPos, Integer columnCount) {

		return setupOptionalRenderParameters(
			request, renderPath, columnId, columnPos, columnCount, null, null);
	}

	public static HttpServletRequest setupOptionalRenderParameters(
		HttpServletRequest request, String renderPath, String columnId,
		Integer columnPos, Integer columnCount, Boolean boundary,
		Boolean decorate) {

		if ((_LAYOUT_PARALLEL_RENDER_ENABLE && ServerDetector.isTomcat()) ||
			_PORTLET_CONTAINER_RESTRICT) {

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

			if (boundary != null) {
				restrictPortletServletRequest.setAttribute(
					WebKeys.RENDER_PORTLET_BOUNDARY, boundary);
			}

			if (decorate != null) {
				restrictPortletServletRequest.setAttribute(
					WebKeys.PORTLET_DECORATE, decorate);
			}

			return restrictPortletServletRequest;
		}

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
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portletContainer = portletContainer;
	}

	private static void _processEvents(
			HttpServletRequest request, HttpServletResponse response,
			List<Event> events)
		throws PortletContainerException {

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		List<LayoutTypePortlet> layoutTypePortlets = getLayoutTypePortlets(
			layout);

		for (LayoutTypePortlet layoutTypePortlet : layoutTypePortlets) {
			List<Portlet> portlets = null;

			try {
				portlets = layoutTypePortlet.getAllPortlets();
			}
			catch (Exception e) {
				throw new PortletContainerException(e);
			}

			for (Portlet portlet : portlets) {
				for (Event event : events) {
					javax.xml.namespace.QName qName = event.getQName();

					QName processingQName = portlet.getProcessingEvent(
						qName.getNamespaceURI(), qName.getLocalPart());

					if (processingQName == null) {
						continue;
					}

					processEvent(
						request, response, portlet,
						layoutTypePortlet.getLayout(), event);
				}
			}
		}
	}

	private static final boolean _LAYOUT_PARALLEL_RENDER_ENABLE =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_PARALLEL_RENDER_ENABLE));

	private static final boolean _PORTLET_CONTAINER_RESTRICT =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.PORTLET_CONTAINER_RESTRICT));

	private static final boolean _PORTLET_EVENT_DISTRIBUTION_LAYOUT_SET =
		!StringUtil.equalsIgnoreCase(
			PropsUtil.get(PropsKeys.PORTLET_EVENT_DISTRIBUTION), "layout");

	private static PortletContainer _portletContainer;

}