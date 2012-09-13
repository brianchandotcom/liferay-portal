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

import java.util.Map;

import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 */
public interface LiferayPortletResponse extends PortletResponse {

	public void addDateHeader(String name, long date);

	public void addHeader(String name, String value);

	public void addIntHeader(String name, int value);

	public PortletURL createActionURL();

	public LiferayPortletURL createActionURL(String portletName);

	public LiferayPortletURL createLiferayPortletURL(
		long plid, String portletName, String lifecycle);

	public LiferayPortletURL createLiferayPortletURL(
		long plid, String portletName, String lifecycle,
		boolean includeLinkToLayoutUuid);

	public LiferayPortletURL createLiferayPortletURL(String lifecycle);

	public LiferayPortletURL createLiferayPortletURL(
		String portletName, String lifecycle);

	public PortletURL createRenderURL();

	public LiferayPortletURL createRenderURL(String portletName);

	public ResourceURL createResourceURL();

	public LiferayPortletURL createResourceURL(String portletName);

	public HttpServletResponse getHttpServletResponse();

	public Map<String, String[]> getProperties();

	public void setDateHeader(String name, long date);

	public void setHeader(String name, String value);

	public void setIntHeader(String name, int value);

	public void transferMarkupHeadElements();

}