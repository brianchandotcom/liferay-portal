/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.llms.web.internal.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.configuration.admin.display.ConfigurationScreenWrapper;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.configuration.manager.LLMSConfigurationManager;
import com.liferay.site.llms.web.internal.display.context.LLMSGroupConfigurationDisplayContext;
import com.liferay.site.settings.configuration.admin.display.SiteSettingsConfigurationScreenContributor;
import com.liferay.site.settings.configuration.admin.display.SiteSettingsConfigurationScreenFactory;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jonathan McCann
 */
@Component(service = ConfigurationScreen.class)
public class LLMSSiteSettingsConfigurationScreenWrapper
	extends ConfigurationScreenWrapper {

	@Override
	protected ConfigurationScreen getConfigurationScreen() {
		return _siteSettingsConfigurationScreenFactory.create(
			new LLMSSiteSettingsConfigurationScreenContributor());
	}

	@Reference
	private Language _language;

	@Reference
	private LLMSConfigurationManager _llmsConfigurationManager;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.site.llms.web)")
	private ServletContext _servletContext;

	@Reference
	private SiteSettingsConfigurationScreenFactory
		_siteSettingsConfigurationScreenFactory;

	private class LLMSSiteSettingsConfigurationScreenContributor
		implements SiteSettingsConfigurationScreenContributor {

		@Override
		public String getCategoryKey() {
			return "seo";
		}

		@Override
		public String getJspPath() {
			return "/configuration/llms_group_configuration.jsp";
		}

		@Override
		public String getKey() {
			return "llms-group-configuration";
		}

		@Override
		public String getName(Locale locale) {
			return _language.get(locale, "llms-txt");
		}

		@Override
		public String getSaveMVCActionCommandName() {
			return "/site_llms/save_group_configuration";
		}

		@Override
		public ServletContext getServletContext() {
			return _servletContext;
		}

		@Override
		public boolean isVisible(Group group) {
			if (!FeatureFlagManagerUtil.isEnabled(
					group.getCompanyId(), "LPD-80518")) {

				return false;
			}

			LayoutSet layoutSet = group.getPublicLayoutSet();

			if ((layoutSet != null) &&
				MapUtil.isNotEmpty(layoutSet.getVirtualHostnames())) {

				return true;
			}

			return false;
		}

		@Override
		public void setAttributes(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

			SiteSettingsConfigurationScreenContributor.super.setAttributes(
				httpServletRequest, httpServletResponse);

			httpServletRequest.setAttribute(
				LLMSGroupConfigurationDisplayContext.class.getName(),
				new LLMSGroupConfigurationDisplayContext(
					_llmsConfigurationManager,
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY)));
		}

	}

}