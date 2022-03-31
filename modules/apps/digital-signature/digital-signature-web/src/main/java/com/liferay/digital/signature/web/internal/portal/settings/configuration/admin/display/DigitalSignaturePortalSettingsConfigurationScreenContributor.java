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

package com.liferay.digital.signature.web.internal.portal.settings.configuration.admin.display;

import com.liferay.digital.signature.configuration.DigitalSignatureConfiguration;
import com.liferay.digital.signature.configuration.DigitalSignatureConfigurationUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.settings.configuration.admin.display.PortalSettingsConfigurationScreenContributor;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author José Abelenda
 */
@Component(service = PortalSettingsConfigurationScreenContributor.class)
public class DigitalSignaturePortalSettingsConfigurationScreenContributor
	implements PortalSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "digital-signature";
	}

	@Override
	public String getJspPath() {
		return "/portal_settings/digital_signature.jsp";
	}

	@Override
	public String getKey() {
		return "digital-signature";
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(locale, "digital-signature-configuration-name");
	}

	@Override
	public String getSaveMVCActionCommandName() {
		return "/digital_signature/save_company_configuration";
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public void setAttributes(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		httpServletRequest.setAttribute(
			DigitalSignatureConfiguration.class.getName(),
			DigitalSignatureConfigurationUtil.getDigitalSignatureConfiguration(
				themeDisplay.getCompanyId(), themeDisplay.getSiteGroupId()));
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.digital.signature.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}