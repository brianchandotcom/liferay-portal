/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.util;

import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.frontend.token.definition.constants.FrontendTokenDefinitionConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Evan Thibodeau
 */
public class StyleBookUtil {

	public static String getThemeName(
		HttpServletRequest httpServletRequest, Layout layout) {

		FrontendTokenDefinitionRegistry frontendTokenDefinitionRegistry =
			_frontendTokenDefinitionRegistrySnapshot.get();

		try {
			FrontendTokenDefinition frontendTokenDefinition =
				frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
					layout);

			return getThemeName(
				layout.getCompanyId(), httpServletRequest,
				frontendTokenDefinition.getThemeName(
					httpServletRequest.getLocale()));
		}
		catch (PortalException portalException) {
			_log.error("Unable to get the theme name", portalException);
		}

		return LanguageUtil.get(httpServletRequest, "theme");
	}

	public static String getThemeName(
		long companyId, HttpServletRequest httpServletRequest, String themeId) {

		FrontendTokenDefinition frontendTokenDefinition =
			_getFrontendTokenDefinition(companyId, themeId);

		if (frontendTokenDefinition == null) {
			return themeId;
		}

		Locale locale = (Locale)httpServletRequest.getAttribute(WebKeys.LOCALE);

		if (Objects.equals(
				frontendTokenDefinition.getThemeType(),
				FrontendTokenDefinitionConstants.THEME_TYPE_BUNDLE)) {

			return LanguageUtil.format(
				httpServletRequest, "x-theme",
				frontendTokenDefinition.getThemeName(locale));
		}

		return LanguageUtil.format(
			httpServletRequest, "x-theme-css-client-extension",
			frontendTokenDefinition.getThemeName(locale));
	}

	public static boolean isThemeInactive(long companyId, String themeId) {
		if (_getFrontendTokenDefinition(companyId, themeId) == null) {
			return true;
		}

		return false;
	}

	private static FrontendTokenDefinition _getFrontendTokenDefinition(
		long companyId, String themeId) {

		FrontendTokenDefinitionRegistry frontendTokenDefinitionRegistry =
			_frontendTokenDefinitionRegistrySnapshot.get();

		return frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
			companyId, themeId);
	}

	private static final Log _log = LogFactoryUtil.getLog(StyleBookUtil.class);

	private static final Snapshot<FrontendTokenDefinitionRegistry>
		_frontendTokenDefinitionRegistrySnapshot = new Snapshot<>(
			StyleBookUtil.class, FrontendTokenDefinitionRegistry.class);

}