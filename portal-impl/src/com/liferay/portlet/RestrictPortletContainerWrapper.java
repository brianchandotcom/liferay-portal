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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.ActionResult;
import com.liferay.portal.kernel.portlet.PortletContainer;
import com.liferay.portal.kernel.portlet.PortletContainerException;
import com.liferay.portal.kernel.portlet.RestrictPortletServletRequest;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.util.PropsValues;

import java.util.List;
import java.util.Map;

import javax.portlet.Event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public class RestrictPortletContainerWrapper implements PortletContainer {

	public static PortletContainer createRestrictPortletContainerWrapper(
		PortletContainer portletContainer) {

		if ((PropsValues.LAYOUT_PARALLEL_RENDER_ENABLE &&
			 ServerDetector.isTomcat()) ||
			PropsValues.PORTLET_CONTAINER_RESTRICT) {

			portletContainer = new RestrictPortletContainerWrapper(
				portletContainer);
		}

		return portletContainer;
	}

	public RestrictPortletContainerWrapper(PortletContainer portletContainer) {
		_portletContainer = portletContainer;
	}

	@Override
	public void preparePortlet(HttpServletRequest request, Portlet portlet)
		throws PortletContainerException {

		_portletContainer.preparePortlet(request, portlet);
	}

	@Override
	public ActionResult processAction(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		RestrictPortletServletRequest restrictPortletServletRequest =
			new RestrictPortletServletRequest(request);

		try {
			return _portletContainer.processAction(request, response, portlet);
		}
		finally {
			restrictPortletServletRequest.mergeSharedAttributes();
		}
	}

	@Override
	public List<Event> processEvent(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, Layout layout, Event event)
		throws PortletContainerException {

		RestrictPortletServletRequest restrictPortletServletRequest =
			new RestrictPortletServletRequest(request);

		try {
			return _portletContainer.processEvent(
				request, response, portlet, layout, event);
		}
		finally {
			restrictPortletServletRequest.mergeSharedAttributes();
		}
	}

	@Override
	public void render(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		RestrictPortletServletRequest restrictPortletServletRequest = null;

		if (request instanceof RestrictPortletServletRequest) {
			restrictPortletServletRequest =
				(RestrictPortletServletRequest)request;

			Map<String, Object> attributes =
				restrictPortletServletRequest.getAttributes();

			if (attributes.containsKey(WebKeys.RENDER_PORTLET)) {
				restrictPortletServletRequest =
					new RestrictPortletServletRequest(request);
			}
		}
		else {
			restrictPortletServletRequest = new RestrictPortletServletRequest(
				request);
		}

		try {
			_portletContainer.render(request, response, portlet);
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
		finally {
			restrictPortletServletRequest.removeAttribute(WebKeys.RENDER_PATH);
			restrictPortletServletRequest.removeAttribute(
				WebKeys.RENDER_PORTLET_COLUMN_COUNT);
			restrictPortletServletRequest.removeAttribute(
				WebKeys.RENDER_PORTLET_COLUMN_ID);
			restrictPortletServletRequest.removeAttribute(
				WebKeys.RENDER_PORTLET_COLUMN_POS);

			// Don't merge when parallel rendering a portlet. The caller (worker
			// thread) should decide whether or not to merge shared attributes.
			// If we did merge here and the caller cancelled the parallel
			// rendering, then we would have corrupted the set of shared
			// attributes. The only safe way to merge shared attributes is for
			// the caller to merge after it has the render result.

			Object lock = request.getAttribute(
				WebKeys.PARALLEL_RENDERING_MERGE_LOCK);

			if (lock == null) {
				restrictPortletServletRequest.mergeSharedAttributes();
			}
		}
	}

	@Override
	public void serveResource(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		RestrictPortletServletRequest restrictPortletServletRequest =
			new RestrictPortletServletRequest(request);

		try {
			_portletContainer.serveResource(request, response, portlet);
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
		finally {
			restrictPortletServletRequest.mergeSharedAttributes();
		}
	}

	private final PortletContainer _portletContainer;

}