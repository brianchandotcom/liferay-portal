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

package com.liferay.portal.security.sso.opensso.servlet.taglib;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Adds an OpenSSO tab to the Authentication section of the Portal Settings user
 * interface in the Control Panel.
 * 
 * @author Philip Jones
 */
@Component(
	immediate = true,
	property = {"portal.settings.authentication.tabs.name=opensso"},
	service = DynamicInclude.class
)
public class PortalSettingsOpenSSOAuthenticationDynamicInclude
	extends BaseJSPDynamicInclude {

	@Override
	protected String getJspPath() {
		return "/com.liferay.portal.settings.web/opensso.jsp";
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.security.sso.opensso)",
		unbind = "-"

	)
	protected void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalSettingsOpenSSOAuthenticationDynamicInclude.class);

}