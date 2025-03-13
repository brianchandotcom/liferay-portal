/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.util;

import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalServiceUtil;

import java.util.Locale;

/**
 * @author Víctor Galán
 */
public class DefaultStyleBookEntryUtil {

	public static StyleBookEntry getDefaultMasterStyleBookEntry(Layout layout) {
		StyleBookEntry styleBookEntry = _getMasterLayoutStyleBookEntry(layout);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		try {
			FrontendTokenDefinitionRegistry frontendTokenDefinitionRegistry =
				_frontendTokenDefinitionRegistrySnapshot.get();

			FrontendTokenDefinition frontendTokenDefinition =
				frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
					layout);

			styleBookEntry =
				StyleBookEntryLocalServiceUtil.fetchDefaultStyleBookEntry(
					StagingUtil.getLiveGroupId(layout.getGroupId()),
					frontendTokenDefinition.getThemeId());
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to get the layout's default style book entry",
				portalException);
		}

		return styleBookEntry;
	}

	public static StyleBookEntry getDefaultStyleBookEntry(Layout layout) {
		StyleBookEntry styleBookEntry = null;

		if (layout.getStyleBookEntryId() > 0) {
			styleBookEntry = StyleBookEntryLocalServiceUtil.fetchStyleBookEntry(
				layout.getStyleBookEntryId());
		}

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		return getDefaultMasterStyleBookEntry(layout);
	}

	public static String getStyleBookEntryName(
		Layout layout, Locale locale, StyleBookEntry styleBookEntry) {

		if ((styleBookEntry != null) &&
			(styleBookEntry.getStyleBookEntryId() > 0)) {

			return styleBookEntry.getName();
		}

		StyleBookEntry defaultStyleBookEntry = getDefaultStyleBookEntry(layout);

		if (defaultStyleBookEntry == null) {
			if (FeatureFlagManagerUtil.isEnabled(
					layout.getCompanyId(), "LPD-30204")) {

				return LanguageUtil.format(
					locale, "styles-from-x",
					StyleBookUtil.getThemeName(layout, locale));
			}

			return LanguageUtil.get(locale, "styles-from-theme");
		}

		StyleBookEntry masterLayoutStyleBookEntry =
			_getMasterLayoutStyleBookEntry(layout);

		if (masterLayoutStyleBookEntry != null) {
			return LanguageUtil.get(locale, "styles-from-master");
		}

		return LanguageUtil.get(locale, "styles-by-default");
	}

	private static StyleBookEntry _getMasterLayoutStyleBookEntry(
		Layout layout) {

		StyleBookEntry styleBookEntry = null;

		if (layout.getMasterLayoutPlid() > 0) {
			Layout masterLayout = LayoutLocalServiceUtil.fetchLayout(
				layout.getMasterLayoutPlid());

			if (masterLayout != null) {
				styleBookEntry =
					StyleBookEntryLocalServiceUtil.fetchStyleBookEntry(
						masterLayout.getStyleBookEntryId());
			}
		}

		return styleBookEntry;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultStyleBookEntryUtil.class);

	private static final Snapshot<FrontendTokenDefinitionRegistry>
		_frontendTokenDefinitionRegistrySnapshot = new Snapshot<>(
			StyleBookUtil.class, FrontendTokenDefinitionRegistry.class);

}