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

package com.liferay.saml.web.internal.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saml.web.internal.constants.SamlAdminPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mika Koivisto
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=saml-portlet-admin",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/admin/css/main.css",
		"com.liferay.portlet.layout-cacheable=true",
		"javax.portlet.display-name=SAML Admin",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.mvc-command-names-default-views=/admin",
		"javax.portlet.name=" + SamlAdminPortletKeys.SAML_ADMIN,
		"javax.portlet.portlet.info.keywords=SAML Admin",
		"javax.portlet.portlet.info.short-title=SAML Admin",
		"javax.portlet.portlet.info.title=SAML Admin",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {Portlet.class, SamlAdminPortlet.class}
)
public class SamlAdminPortlet extends MVCPortlet {

	@Override
	protected void checkPermissions(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin()) {
			throw new PrincipalException();
		}
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (_log.isDebugEnabled()) {
			_log.debug(cause.getMessage(), cause);
		}
		else if (_log.isInfoEnabled()) {
			_log.info(cause.getMessage());
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SamlAdminPortlet.class);

}