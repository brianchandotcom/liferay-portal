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

import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface LiferayPortletRequest extends PortletRequest {

	public void cleanUp();

	public Map<String, String[]> clearRenderParameters();

	public void defineObjects(
		PortletConfig portletConfig, PortletResponse portletResponse);

	public HttpServletRequest getHttpServletRequest();

	public String getLifecycle();

	public HttpServletRequest getOriginalHttpServletRequest();

	public long getPlid();

	public Portlet getPortlet();

	public String getPortletName();

	public HttpServletRequest getPortletRequestDispatcherRequest();

	public void invalidateSession();

	public void setPortletRequestDispatcherRequest(
		HttpServletRequest httpServletRequest);

}