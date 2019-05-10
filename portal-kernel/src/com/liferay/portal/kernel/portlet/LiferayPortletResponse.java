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

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.servlet.URLEncoder;

import java.util.Map;

import javax.portlet.ActionURL;
import javax.portlet.MimeResponse;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderURL;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletResponse;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Raymond Augé
 * @author Neil Griffin
 */
@ProviderType
public interface LiferayPortletResponse extends PortletResponse {

	public void addDateHeader(String name, long date);

	public void addHeader(String name, String value);

	public void addIntHeader(String name, int value);

	public <T extends PortletURL & ActionURL> T createActionURL();

	public ActionURL createActionURL(MimeResponse.Copy copy);

	public LiferayPortletURL createActionURL(String portletName);

	public LiferayPortletURL createActionURL(
		String portletName, MimeResponse.Copy copy);

	public LiferayPortletURL createLiferayPortletURL(
		long plid, String portletName, String lifecycle);

	public LiferayPortletURL createLiferayPortletURL(
		long plid, String portletName, String lifecycle,
		boolean includeLinkToLayoutUuid);

	public LiferayPortletURL createLiferayPortletURL(
		long plid, String portletName, String lifecycle,
		MimeResponse.Copy copy);

	public LiferayPortletURL createLiferayPortletURL(
		long plid, String portletName, String lifecycle, MimeResponse.Copy copy,
		boolean includeLinkToLayoutUuid);

	public LiferayPortletURL createLiferayPortletURL(String lifecycle);

	public LiferayPortletURL createLiferayPortletURL(
		String portletName, String lifecycle);

	public LiferayPortletURL createLiferayPortletURL(
		String portletName, String lifecycle, MimeResponse.Copy copy);

	public <T extends PortletURL & RenderURL> T createRenderURL();

	public RenderURL createRenderURL(MimeResponse.Copy copy);

	public LiferayPortletURL createRenderURL(String portletName);

	public LiferayPortletURL createRenderURL(
		String portletName, MimeResponse.Copy copy);

	public ResourceURL createResourceURL();

	public LiferayPortletURL createResourceURL(String portletName);

	public HttpServletResponse getHttpServletResponse();

	public String getLifecycle();

	public Portlet getPortlet();

	public Map<String, String[]> getProperties();

	public void setDateHeader(String name, long date);

	public void setHeader(String name, String value);

	public void setIntHeader(String name, int value);

	public void setURLEncoder(URLEncoder urlEncoder);

	public void transferHeaders(HttpServletResponse httpServletResponse);

	public void transferMarkupHeadElements();

}