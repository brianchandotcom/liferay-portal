/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.admin.web.internal.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.configuration.admin.display.ConfigurationScreenWrapper;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.settings.configuration.admin.display.SiteSettingsConfigurationScreenContributor;
import com.liferay.site.settings.configuration.admin.display.SiteSettingsConfigurationScreenFactory;

import jakarta.servlet.ServletContext;

import java.io.Serializable;

import java.util.Dictionary;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ConfigurationScreen.class)
public class LanguagesSiteSettingsConfigurationScreenWrapper
	extends ConfigurationScreenWrapper {

	@Override
	protected ConfigurationScreen getConfigurationScreen() {
		return _siteSettingsConfigurationScreenFactory.create(
			new LanguagesSiteSettingsConfigurationScreenContributor());
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.site.admin.web)")
	private ServletContext _servletContext;

	@Reference
	private SiteSettingsConfigurationScreenFactory
		_siteSettingsConfigurationScreenFactory;

	private class LanguagesSiteSettingsConfigurationScreenContributor
		implements SiteSettingsConfigurationScreenContributor {

		@Override
		public Dictionary<String, Object> exportProperties(
			Serializable scopePK) {

			Group group = _groupLocalService.fetchGroup((long)scopePK);

			return HashMapDictionaryBuilder.<String, Object>put(
				GroupConstants.TYPE_SETTINGS_KEY_INHERIT_LOCALES,
				() -> group.getTypeSettingsProperty(
					GroupConstants.TYPE_SETTINGS_KEY_INHERIT_LOCALES)
			).put(
				PropsKeys.LOCALES,
				() -> group.getTypeSettingsProperty(PropsKeys.LOCALES)
			).put(
				"languageId", () -> group.getTypeSettingsProperty("languageId")
			).build();
		}

		@Override
		public String getCategoryKey() {
			return "localization";
		}

		@Override
		public String getJspPath() {
			return "/site_settings/languages.jsp";
		}

		@Override
		public String getKey() {
			return "site-configuration-languages";
		}

		@Override
		public String getName(Locale locale) {
			return _language.get(locale, "languages");
		}

		@Override
		public String getSaveMVCActionCommandName() {
			return "/site_admin/edit_languages";
		}

		@Override
		public ServletContext getServletContext() {
			return _servletContext;
		}

		@Override
		public void importProperties(
				Dictionary<String, Object> properties, Serializable scopePK)
			throws Exception {

			Group group = _groupLocalService.fetchGroup((long)scopePK);

			UnicodeProperties typeSettingsUnicodeProperties =
				group.getTypeSettingsProperties();

			String inheritLocales = String.valueOf(
				properties.get(
					GroupConstants.TYPE_SETTINGS_KEY_INHERIT_LOCALES));

			if (Validator.isNotNull(inheritLocales)) {
				typeSettingsUnicodeProperties.put(
					GroupConstants.TYPE_SETTINGS_KEY_INHERIT_LOCALES,
					inheritLocales);
			}

			String locales = String.valueOf(properties.get(PropsKeys.LOCALES));

			if (Validator.isNotNull(locales)) {
				typeSettingsUnicodeProperties.put(PropsKeys.LOCALES, locales);
			}

			String languageId = String.valueOf(properties.get("languageId"));

			if (Validator.isNotNull(languageId)) {
				typeSettingsUnicodeProperties.put("languageId", languageId);
			}

			_groupLocalService.updateGroup(
				group.getGroupId(), typeSettingsUnicodeProperties.toString());
		}

		@Override
		public boolean isVisible(Group group) {
			return !group.isCompany();
		}

	}

}