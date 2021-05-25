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
import com.liferay.digital.signature.web.internal.constants.DigitalSignatureWebKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.settings.configuration.admin.display.SiteSettingsConfigurationScreenContributor;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author José Abelenda
 */
@Component(service = SiteSettingsConfigurationScreenContributor.class)
public class DigitalSignatureSiteSettingsConfigurationScreenContributor
	implements SiteSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "digital-signature";
	}

	@Override
	public String getJspPath() {
		return "/site_settings/digital_signature.jsp";
	}

	@Override
	public String getKey() {
		return "site-configuration-digital-signature";
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "digital-signature");
	}

	@Override
	public String getSaveMVCActionCommandName() {
		return "/digital_signature/save_site_configuration";
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

		DigitalSignatureConfiguration digitalSignatureConfiguration =
			DigitalSignatureConfigurationUtil.getDigitalSignatureConfiguration(
				themeDisplay.getCompanyId(), themeDisplay.getSiteGroupId());

		httpServletRequest.setAttribute(
			DigitalSignatureConfiguration.class.getName(),
			DigitalSignatureConfigurationUtil.getDigitalSignatureConfiguration(
				themeDisplay.getCompanyId(), 0));

		httpServletRequest.setAttribute(
			DigitalSignatureWebKeys.DIGITAL_SIGNATURE_ACCOUNT_BASE_URI,
			digitalSignatureConfiguration.accountBaseURI());
		httpServletRequest.setAttribute(
			DigitalSignatureWebKeys.DIGITAL_SIGNATURE_API_ACCOUNT_ID,
			digitalSignatureConfiguration.apiAccountId());
		httpServletRequest.setAttribute(
			DigitalSignatureWebKeys.DIGITAL_SIGNATURE_API_USERNAME,
			digitalSignatureConfiguration.apiUsername());
		httpServletRequest.setAttribute(
			DigitalSignatureWebKeys.DIGITAL_SIGNATURE_ENABLED,
			digitalSignatureConfiguration.enabled());
		httpServletRequest.setAttribute(
			DigitalSignatureWebKeys.DIGITAL_SIGNATURE_INTEGRATION_KEY,
			digitalSignatureConfiguration.integrationKey());
		httpServletRequest.setAttribute(
			DigitalSignatureWebKeys.DIGITAL_SIGNATURE_RSA_PRIVATE_KEY,
			digitalSignatureConfiguration.rsaPrivateKey());
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.digital.signature.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}