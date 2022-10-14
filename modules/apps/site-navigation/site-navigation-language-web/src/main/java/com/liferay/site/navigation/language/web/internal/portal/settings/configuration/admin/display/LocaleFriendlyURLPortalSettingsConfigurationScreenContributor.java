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

package com.liferay.site.navigation.language.web.internal.portal.settings.configuration.admin.display;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.settings.configuration.admin.display.PortalSettingsConfigurationScreenContributor;
import com.liferay.site.navigation.language.web.internal.configuration.SiteNavigationLocaleFriendlyURLConfiguration;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Albert Gomes Cabral
 */
@Component(service = PortalSettingsConfigurationScreenContributor.class)
public class LocaleFriendlyURLPortalSettingsConfigurationScreenContributor
	implements PortalSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "localization";
	}

	@Override
	public String getJspPath() {
		return "/portal_settings/locale_friendly_url.jsp";
	}

	@Override
	public String getKey() {
		return "locale-friendly-url-configuration";
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return _language.get(
			resourceBundle,
			"site-navigation-locale-friendly-url-configuration-name");
	}

	@Override
	public String getSaveMVCActionCommandName() {
		return "/portal_settings/save_locale_friendly_url_configuration";
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public void setAttributes(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		SiteNavigationLocaleFriendlyURLConfiguration
			siteNavigationLocaleFriendlyURLConfiguration = null;

		try {
			siteNavigationLocaleFriendlyURLConfiguration =
				_configurationProvider.getCompanyConfiguration(
					SiteNavigationLocaleFriendlyURLConfiguration.class,
					CompanyThreadLocal.getCompanyId());
		}
		catch (PortalException portalException) {
			ReflectionUtil.throwException(portalException);
		}

		httpServletRequest.setAttribute(
			SiteNavigationLocaleFriendlyURLConfiguration.class.getName(),
			siteNavigationLocaleFriendlyURLConfiguration);
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.navigation.language.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}