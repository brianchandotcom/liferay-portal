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

package com.liferay.portal.security.sso.facebook.connect.servlet.taglib;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.security.common.servlet.taglib.BaseAuthenticationDynamicInclude;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(
	immediate = true,
	property = {"portal.settings.authentication.tabs.name=facebook"},
	service = DynamicInclude.class
)
public class PortalSettingsFacebookConnectAuthenticationDynamicInclude
	extends BaseAuthenticationDynamicInclude {

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.security.sso.facebook.connect)"
	)
	protected void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	protected String getJspPath() {
		return "/com.liferay.portal.settings.web/facebook.jsp";
	}

	protected Log getLog() {
		return _log;
	}
	
	private static final Log _log = LogFactoryUtil.getLog(
		PortalSettingsFacebookConnectAuthenticationDynamicInclude.class);

}