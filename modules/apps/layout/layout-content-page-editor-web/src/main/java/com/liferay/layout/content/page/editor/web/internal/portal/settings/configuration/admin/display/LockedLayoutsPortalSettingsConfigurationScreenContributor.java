/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portal.settings.configuration.admin.display;

import com.liferay.layout.content.page.editor.web.internal.configuration.LockedLayoutsConfiguration;
import com.liferay.layout.content.page.editor.web.internal.display.context.LockedLayoutsConfigurationDisplayContext;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
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
 * @author Lourdes Fernández Besada
 */
@Component(
	configurationPid = "com.liferay.layout.content.page.editor.web.internal.configuration.LockedLayoutsConfiguration",
	service = PortalSettingsConfigurationScreenContributor.class
)
public class LockedLayoutsPortalSettingsConfigurationScreenContributor
	implements PortalSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "pages";
	}

	@Override
	public String getJspPath() {
		return "/configuration/locked_layouts.jsp";
	}

	@Override
	public String getKey() {
		return "locked-layouts-portal-settings";
	}

	@Override
	public String getName(Locale locale) {
		return _language.get(locale, "locked-pages");
	}

	@Override
	public String getSaveMVCActionCommandName() {
		return "/instance_settings/save_locked_layouts_configuration";
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public boolean isVisible() {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-180328")) {
			return false;
		}

		return true;
	}

	@Override
	public void setAttributes(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			httpServletRequest.setAttribute(
				LockedLayoutsConfigurationDisplayContext.class.getName(),
				new LockedLayoutsConfigurationDisplayContext(
					_configurationProvider.getCompanyConfiguration(
						LockedLayoutsConfiguration.class,
						themeDisplay.getCompanyId())));
		}
		catch (PortalException portalException) {
			ReflectionUtil.throwException(portalException);
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.content.page.editor.web)"
	)
	private ServletContext _servletContext;

}