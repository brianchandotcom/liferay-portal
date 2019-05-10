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

import javax.portlet.EventPortlet;
import javax.portlet.HeaderPortlet;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.ResourceServingPortlet;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael Young
 * @author Neil Griffin
 */
@ProviderType
public interface InvokerPortlet
	extends EventPortlet, HeaderPortlet, Portlet, ResourceServingPortlet {

	public static final String INIT_INVOKER_PORTLET_NAME =
		"com.liferay.portal.invokerPortletName";

	public Integer getExpCache();

	public Portlet getPortlet();

	public ClassLoader getPortletClassLoader();

	public PortletConfig getPortletConfig();

	public PortletContext getPortletContext();

	public Portlet getPortletInstance();

	public boolean isCheckAuthToken();

	public boolean isFacesPortlet();

	public boolean isHeaderPortlet();

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public boolean isStrutsBridgePortlet();

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public boolean isStrutsPortlet();

	public void setPortletFilters() throws PortletException;

}