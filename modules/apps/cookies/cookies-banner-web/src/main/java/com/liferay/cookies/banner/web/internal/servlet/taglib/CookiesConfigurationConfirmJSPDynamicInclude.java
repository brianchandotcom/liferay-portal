/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.banner.web.internal.servlet.taglib;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.cookies.configuration.CookiesConfigurationProvider;
import com.liferay.cookies.configuration.CookiesPreferenceHandlingConfiguration;
import com.liferay.cookies.configuration.banner.CookiesBannerConfiguration;
import com.liferay.cookies.configuration.consent.CookiesConsentConfiguration;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alvaro Saugar
 */
@Component(service = DynamicInclude.class)
public class CookiesConfigurationConfirmJSPDynamicInclude
	extends BaseJSPDynamicInclude {

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		String portletId = _portal.getPortletId(httpServletRequest);

		ExtendedObjectClassDefinition.Scope scope =
			ExtendedObjectClassDefinition.Scope.SYSTEM;
		long scopePK = 0L;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (ConfigurationAdminPortletKeys.INSTANCE_SETTINGS.equals(portletId)) {
			scope = ExtendedObjectClassDefinition.Scope.COMPANY;
			scopePK = themeDisplay.getCompanyId();
		}
		else if (ConfigurationAdminPortletKeys.SITE_SETTINGS.equals(
					portletId)) {

			scope = ExtendedObjectClassDefinition.Scope.GROUP;
			scopePK = themeDisplay.getScopeGroupId();
		}

		if (!_cookiesConfigurationProvider.isCookiesPreferenceHandlingActive(
				scope, scopePK)) {

			return;
		}

		super.include(httpServletRequest, httpServletResponse, key);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		for (Class<?> configurationClass : _CONFIGURATION_CLASSES) {
			dynamicIncludeRegistry.register(
				"com.liferay.configuration.admin.web#/edit_configuration.jsp#" +
					configurationClass.getName() + "#post");
		}
	}

	@Override
	protected String getJspPath() {
		return "/dynamic_include/edit_configuration.jsp";
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	private static final Class<?>[] _CONFIGURATION_CLASSES = {
		CookiesBannerConfiguration.class, CookiesConsentConfiguration.class,
		CookiesPreferenceHandlingConfiguration.class
	};

	private static final Log _log = LogFactoryUtil.getLog(
		CookiesConfigurationConfirmJSPDynamicInclude.class);

	@Reference
	private CookiesConfigurationProvider _cookiesConfigurationProvider;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.cookies.banner.web)"
	)
	private ServletContext _servletContext;

}