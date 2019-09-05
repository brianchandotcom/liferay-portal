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

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.ActionResult;
import com.liferay.portal.kernel.portlet.PortletContainer;
import com.liferay.portal.kernel.portlet.PortletContainerException;
import com.liferay.portal.kernel.portlet.RestrictPortletServletRequest;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.WebKeys;
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
	public void preparePortlet(
			HttpServletRequest httpServletRequest, Portlet portlet)
		throws PortletContainerException {

		_portletContainer.preparePortlet(httpServletRequest, portlet);
	}

	@Override
	public ActionResult processAction(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet)
		throws PortletContainerException {

		RestrictPortletServletRequest restrictPortletServletRequest =
			new RestrictPortletServletRequest(httpServletRequest);

		try {
			return _portletContainer.processAction(
				httpServletRequest, httpServletResponse, portlet);
		}
		finally {
			restrictPortletServletRequest.mergeSharedAttributes();
		}
	}

	@Override
	public List<Event> processEvent(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet,
			Layout layout, Event event)
		throws PortletContainerException {

		RestrictPortletServletRequest restrictPortletServletRequest =
			new RestrictPortletServletRequest(httpServletRequest);

		try {
			return _portletContainer.processEvent(
				httpServletRequest, httpServletResponse, portlet, layout,
				event);
		}
		finally {
			restrictPortletServletRequest.mergeSharedAttributes();
		}
	}

	@Override
	public void processPublicRenderParameters(
		HttpServletRequest httpServletRequest, Layout layout) {

		_portletContainer.processPublicRenderParameters(
			httpServletRequest, layout);
	}

	@Override
	public void processPublicRenderParameters(
		HttpServletRequest httpServletRequest, Layout layout, Portlet portlet) {

		_portletContainer.processPublicRenderParameters(
			httpServletRequest, layout, portlet);
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet)
		throws PortletContainerException {

		_render(
			httpServletRequest,
			() -> _portletContainer.render(
				httpServletRequest, httpServletResponse, portlet));
	}

	@Override
	public void renderHeaders(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet)
		throws PortletContainerException {

		_render(
			httpServletRequest,
			() -> _portletContainer.renderHeaders(
				httpServletRequest, httpServletResponse, portlet));
	}

	@Override
	public void serveResource(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet)
		throws PortletContainerException {

		RestrictPortletServletRequest restrictPortletServletRequest =
			new RestrictPortletServletRequest(httpServletRequest);

		try {
			_portletContainer.serveResource(
				httpServletRequest, httpServletResponse, portlet);
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
		finally {
			restrictPortletServletRequest.mergeSharedAttributes();
		}
	}

	private void _render(
			HttpServletRequest httpServletRequest, Renderable renderable)
		throws PortletContainerException {

		RestrictPortletServletRequest restrictPortletServletRequest = null;

		if (httpServletRequest instanceof RestrictPortletServletRequest) {
			restrictPortletServletRequest =
				(RestrictPortletServletRequest)httpServletRequest;

			Map<String, Object> attributes =
				restrictPortletServletRequest.getAttributes();

			if (attributes.containsKey(WebKeys.RENDER_PORTLET)) {
				restrictPortletServletRequest =
					new RestrictPortletServletRequest(httpServletRequest);
			}
		}
		else {
			restrictPortletServletRequest = new RestrictPortletServletRequest(
				httpServletRequest);
		}

		try {
			renderable.render();
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

			Object lock = httpServletRequest.getAttribute(
				WebKeys.PARALLEL_RENDERING_MERGE_LOCK);

			if (lock == null) {
				restrictPortletServletRequest.mergeSharedAttributes();
			}
		}
	}

	private final PortletContainer _portletContainer;

	@FunctionalInterface
	private interface Renderable {

		public void render() throws PortletContainerException;

	}

}