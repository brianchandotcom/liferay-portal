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

package com.liferay.portal.settings.authentication.cas.web.internal.portlet.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.security.sso.cas.configuration.CASConfiguration;
import com.liferay.portal.settings.authentication.cas.web.internal.constants.PortalSettingsCASConstants;
import com.liferay.portal.settings.portlet.action.PortalSettingsConfigurationScreenContributor;

import java.util.Locale;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = PortalSettingsConfigurationScreenContributor.class)
public class CASPortalSettingsConfigurationScreenContributor
	implements PortalSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "sso";
	}

	@Override
	public String getDeleteMVCActionCommandName() {
		return PortalSettingsCASConstants.DELETE_MVC_ACTION_COMMAND_NAME;
	}

	@Override
	public String getJspPath() {
		return "/dynamic_include/com.liferay.portal.settings.web/cas.jsp";
	}

	@Override
	public String getKey() {
		return "cas";
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(
			ResourceBundleUtil.getBundle(locale, CASConfiguration.class),
			"cas-configuration-name");
	}

	@Override
	public String getSaveMVCActionCommandName() {
		return PortalSettingsCASConstants.SAVE_MVC_ACTION_COMMAND_NAME;
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public String getTestButtonLabel(Locale locale) {
		return "test-cas-configuration";
	}

	@Override
	public String getTestButtonOnClick(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		return renderResponse.getNamespace() + "testCasSettings();";
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.settings.authentication.cas.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}