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

package com.liferay.portal.ldap.internal.portal.settings.web.servlet.taglib;

import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {"portal.settings.authentication.tabs.name=ldap"},
	service = DynamicInclude.class
)
public class PortalSettingsLDAPAuthenticationDynamicInclude
	extends PortalSettingsBaseDynamicInclude {

	@Override
	protected String getJSPPath() {
		return _JSP_PATH;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.ldap)", unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		super.servletContext = servletContext;
	}

	private static final String _JSP_PATH = "/portal_settings/ldap.jsp";

}