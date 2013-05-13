/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.Portlet;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;

/**
 * @author Brian Wing Shun Chan
 */
public interface LiferayPortletConfig extends PortletConfig {

	public static final String RUNTIME_OPTION_ESCAPE_XML =
		"javax.portlet.escapeXml";

	public static final String RUNTIME_OPTION_PORTAL_CONTEXT =
		"com.liferay.portal.portalContext";

	public Portlet getPortlet();

	@Override
	public PortletContext getPortletContext();

	public String getPortletId();

	public boolean isCopyRequestParameters();

	public boolean isWARFile();

}