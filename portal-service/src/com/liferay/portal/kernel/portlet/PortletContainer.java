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

import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;

import java.util.List;

import javax.portlet.Event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public interface PortletContainer {

	public void preparePortlet(HttpServletRequest request, Portlet portlet)
		throws PortletContainerException;

	public ActionResult processAction(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException;

	public List<Event> processEvent(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, Layout layout, Event event)
		throws PortletContainerException;

	public void render(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException;

	public void serveResource(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException;

}