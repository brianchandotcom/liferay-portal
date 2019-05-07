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

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.servlet.TempAttributesServletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.QName;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.Event;
import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 * @author Raymond Augé
 * @author Neil Griffin
 */
public class PortletContainerUtil {

	public static List<LayoutTypePortlet> getLayoutTypePortlets(Layout layout)
		throws PortletContainerException {

		if (_PORTLET_EVENT_DISTRIBUTION_LAYOUT_SET) {
			List<Layout> layouts = null;

			try {
				layouts = LayoutLocalServiceUtil.getLayouts(
					layout.getGroupId(), layout.isPrivateLayout(),
					LayoutConstants.TYPE_PORTLET);
			}
			catch (PortalException pe) {
				throw new PortletContainerException(pe);
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
		return _portletContainer;
	}

	public static void preparePortlet(
			HttpServletRequest httpServletRequest, Portlet portlet)
		throws PortletContainerException {

		getPortletContainer().preparePortlet(httpServletRequest, portlet);
	}

	public static void processAction(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet)
		throws PortletContainerException {

		PortletContainer portletContainer = getPortletContainer();

		ActionResult actionResult = portletContainer.processAction(
			httpServletRequest, httpServletResponse, portlet);

		String location = actionResult.getLocation();

		if (Validator.isNull(location) ||
			(Validator.isNotNull(location) && portlet.isActionURLRedirect())) {

			List<Event> events = actionResult.getEvents();

			if (!events.isEmpty()) {
				_processEvents(httpServletRequest, httpServletResponse, events);
			}
		}

		if (Validator.isNull(location) || httpServletResponse.isCommitted()) {
			return;
		}

		PortletApp portletApp = portlet.getPortletApp();

		if ((portletApp.getSpecMajorVersion() >= 3) &&
			portlet.isActionURLRedirect()) {

			Layout layout = (Layout)httpServletRequest.getAttribute(
				WebKeys.LAYOUT);

			LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
				httpServletRequest, portlet, layout,
				PortletRequest.RENDER_PHASE, MimeResponse.Copy.ALL);

			try {
				URL locationURL = new URL(location);

				URL renderURL = new URL(liferayPortletURL.toString());

				String protocol = locationURL.getProtocol();
				String host = locationURL.getHost();
				int port = locationURL.getPort();

				if (protocol.equals(renderURL.getProtocol()) &&
					host.equals(renderURL.getHost()) &&
					(port == renderURL.getPort()) &&
					_hasSamePortletIdParameter(
						locationURL.getQuery(), renderURL.getQuery())) {

					location = liferayPortletURL.toString();
				}
			}
			catch (MalformedURLException murle) {
				throw new PortletContainerException(murle);
			}
		}

		try {
			httpServletResponse.sendRedirect(location);
		}
		catch (IOException ioe) {
			throw new PortletContainerException(ioe);
		}
	}

	public static void processEvent(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet,
			Layout layout, Event event)
		throws PortletContainerException {

		PortletContainer portletContainer = getPortletContainer();

		List<Event> events = portletContainer.processEvent(
			httpServletRequest, httpServletResponse, portlet, layout, event);

		if (!events.isEmpty()) {
			_processEvents(httpServletRequest, httpServletResponse, events);
		}
	}

	public static void processPublicRenderParameters(
		HttpServletRequest httpServletRequest, Layout layout) {

		getPortletContainer().processPublicRenderParameters(
			httpServletRequest, layout);
	}

	public static void processPublicRenderParameters(
		HttpServletRequest httpServletRequest, Layout layout, Portlet portlet) {

		getPortletContainer().processPublicRenderParameters(
			httpServletRequest, layout, portlet);
	}

	public static void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet)
		throws PortletContainerException {

		getPortletContainer().render(
			httpServletRequest, httpServletResponse, portlet);
	}

	public static void renderHeaders(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet)
		throws PortletContainerException {

		getPortletContainer().renderHeaders(
			httpServletRequest, httpServletResponse, portlet);
	}

	public static void serveResource(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet)
		throws PortletContainerException {

		getPortletContainer().serveResource(
			httpServletRequest, httpServletResponse, portlet);
	}

	public static HttpServletRequest setupOptionalRenderParameters(
		HttpServletRequest httpServletRequest, String renderPath,
		String columnId, Integer columnPos, Integer columnCount) {

		return setupOptionalRenderParameters(
			httpServletRequest, renderPath, columnId, columnPos, columnCount,
			null, null);
	}

	public static HttpServletRequest setupOptionalRenderParameters(
		HttpServletRequest httpServletRequest, String renderPath,
		String columnId, Integer columnPos, Integer columnCount,
		Boolean boundary, Boolean decorate) {

		if ((_LAYOUT_PARALLEL_RENDER_ENABLE && ServerDetector.isTomcat()) ||
			_PORTLET_CONTAINER_RESTRICT) {

			RestrictPortletServletRequest restrictPortletServletRequest =
				new RestrictPortletServletRequest(httpServletRequest);

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
			new TempAttributesServletRequest(httpServletRequest);

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

	private static boolean _hasSamePortletIdParameter(
		String queryString1, String queryString2) {

		if ((queryString1 == null) || (queryString2 == null)) {
			return false;
		}

		int x1 = queryString1.indexOf("p_p_id=");

		if (x1 < 0) {
			return false;
		}

		int x2 = queryString2.indexOf("p_p_id=");

		if (x2 < 0) {
			return false;
		}

		x1 += 7;
		x2 += 7;

		int y1 = queryString1.indexOf(CharPool.AMPERSAND, x1);

		if (y1 < 0) {
			y1 = queryString1.length();
		}

		int length = y1 - x1;

		int y2 = length + x2;

		if (y2 > queryString2.length()) {
			return false;
		}

		if ((y2 != queryString2.length()) &&
			(queryString2.charAt(y2) != CharPool.AMPERSAND)) {

			return false;
		}

		if (queryString1.regionMatches(x1, queryString2, x2, length)) {
			return true;
		}

		return false;
	}

	private static void _processEvents(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, List<Event> events)
		throws PortletContainerException {

		Layout layout = (Layout)httpServletRequest.getAttribute(WebKeys.LAYOUT);

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
						httpServletRequest, httpServletResponse, portlet,
						layoutTypePortlet.getLayout(), event);
				}
			}
		}
	}

	private static final boolean _LAYOUT_PARALLEL_RENDER_ENABLE = false;

	private static final boolean _PORTLET_CONTAINER_RESTRICT =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.PORTLET_CONTAINER_RESTRICT));

	private static final boolean _PORTLET_EVENT_DISTRIBUTION_LAYOUT_SET =
		!StringUtil.equalsIgnoreCase(
			PropsUtil.get(PropsKeys.PORTLET_EVENT_DISTRIBUTION), "layout");

	private static PortletContainer _portletContainer;

}