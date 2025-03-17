/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.util;

import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.frontend.token.definition.constants.FrontendTokenDefinitionConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalServiceUtil;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Evan Thibodeau
 */
public class StyleBookUtil {

	public static StyleBookEntry getStyleFromThemeStyleBookEntry(
		FrontendTokenDefinition frontendTokenDefinition, long groupId,
		Locale locale) {

		StyleBookEntry styleFromThemeStyleBookEntry =
			StyleBookEntryLocalServiceUtil.create();

		styleFromThemeStyleBookEntry.setHeadId(-1);
		styleFromThemeStyleBookEntry.setStyleBookEntryId(0);
		styleFromThemeStyleBookEntry.setGroupId(groupId);

		if (frontendTokenDefinition == null) {
			return styleFromThemeStyleBookEntry;
		}

		styleFromThemeStyleBookEntry.setThemeId(
			frontendTokenDefinition.getThemeId());

		StyleBookEntry defaultStyleBookEntry =
			StyleBookEntryLocalServiceUtil.fetchDefaultStyleBookEntry(
				groupId, frontendTokenDefinition.getThemeId());

		if (defaultStyleBookEntry == null) {
			styleFromThemeStyleBookEntry.setDefaultStyleBookEntry(true);
		}

		if (FeatureFlagManagerUtil.isEnabled("LPD-30204")) {
			styleFromThemeStyleBookEntry.setName(
				LanguageUtil.format(
					locale, "styles-from-x",
					_getThemeName(frontendTokenDefinition, locale)));
		}
		else {
			styleFromThemeStyleBookEntry.setName(
				LanguageUtil.get(locale, "styles-from-theme"));
		}

		return styleFromThemeStyleBookEntry;
	}

	public static StyleBookEntry getStyleFromThemeStyleBookEntry(
		Layout layout, Locale locale) {

		return getStyleFromThemeStyleBookEntry(
			_getFrontendTokenDefinition(layout), layout.getGroupId(), locale);
	}

	public static String getThemeName(Layout layout, Locale locale) {
		FrontendTokenDefinitionRegistry frontendTokenDefinitionRegistry =
			_frontendTokenDefinitionRegistrySnapshot.get();

		try {
			FrontendTokenDefinition frontendTokenDefinition =
				frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
					layout);

			if (frontendTokenDefinition != null) {
				return _getThemeName(frontendTokenDefinition, locale);
			}
		}
		catch (PortalException portalException) {
			_log.error("Unable to get the theme name", portalException);
		}

		return LanguageUtil.get(locale, "theme");
	}

	public static String getThemeName(
		long companyId, Locale locale, String themeId) {

		FrontendTokenDefinition frontendTokenDefinition =
			_getFrontendTokenDefinition(companyId, themeId);

		if (frontendTokenDefinition == null) {
			return themeId;
		}

		return _getThemeName(frontendTokenDefinition, locale);
	}

	public static boolean isThemeInactive(long companyId, String themeId) {
		if (_getFrontendTokenDefinition(companyId, themeId) == null) {
			return true;
		}

		return false;
	}

	private static FrontendTokenDefinition _getFrontendTokenDefinition(
		Layout layout) {

		FrontendTokenDefinitionRegistry frontendTokenDefinitionRegistry =
			_frontendTokenDefinitionRegistrySnapshot.get();

		try {
			return frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
				layout);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to get the frontendTokenDefinition", portalException);
		}

		return null;
	}

	private static FrontendTokenDefinition _getFrontendTokenDefinition(
		long companyId, String themeId) {

		FrontendTokenDefinitionRegistry frontendTokenDefinitionRegistry =
			_frontendTokenDefinitionRegistrySnapshot.get();

		return frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
			companyId, themeId);
	}

	private static String _getThemeName(
		FrontendTokenDefinition frontendTokenDefinition, Locale locale) {

		if (Objects.equals(
				frontendTokenDefinition.getThemeType(),
				FrontendTokenDefinitionConstants.THEME_TYPE_BUNDLE)) {

			return LanguageUtil.format(
				locale, "x-theme",
				frontendTokenDefinition.getThemeName(locale));
		}

		return LanguageUtil.format(
			locale, "x-theme-css-client-extension",
			frontendTokenDefinition.getThemeName(locale));
	}

	private static final Log _log = LogFactoryUtil.getLog(StyleBookUtil.class);

	private static final Snapshot<FrontendTokenDefinitionRegistry>
		_frontendTokenDefinitionRegistrySnapshot = new Snapshot<>(
			StyleBookUtil.class, FrontendTokenDefinitionRegistry.class);

}