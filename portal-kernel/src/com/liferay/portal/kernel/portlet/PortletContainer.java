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

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;

import java.util.List;

import javax.portlet.Event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Shuyang Zhou
 * @author Neil Griffin
 */
@ProviderType
public interface PortletContainer {

	public void preparePortlet(
			HttpServletRequest httpServletRequest, Portlet portlet)
		throws PortletContainerException;

	public ActionResult processAction(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet)
		throws PortletContainerException;

	public List<Event> processEvent(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet,
			Layout layout, Event event)
		throws PortletContainerException;

	public void processPublicRenderParameters(
		HttpServletRequest httpServletRequest, Layout layout);

	public void processPublicRenderParameters(
		HttpServletRequest httpServletRequest, Layout layout, Portlet portlet);

	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet)
		throws PortletContainerException;

	public void renderHeaders(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet)
		throws PortletContainerException;

	public void serveResource(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet)
		throws PortletContainerException;

}