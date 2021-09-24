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

package com.liferay.layout.type.controller.asset.display.internal.portlet;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.display.page.util.AssetDisplayPageUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.servlet.filters.i18n.I18nFilter;
import com.liferay.portal.util.PropsValues;

import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = AssetDisplayPageFriendlyURLProvider.class)
public class AssetDisplayPageFriendlyURLProviderImpl
	implements AssetDisplayPageFriendlyURLProvider {

	@Override
	public String getFriendlyURL(
			String className, long classPK, Locale locale,
			ThemeDisplay themeDisplay)
		throws PortalException {

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			_layoutDisplayPageProviderTracker.
				getLayoutDisplayPageProviderByClassName(className);

		if (layoutDisplayPageProvider == null) {
			return null;
		}

		InfoItemReference infoItemReference = new InfoItemReference(
			className, classPK);

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				infoItemReference);

		if (layoutDisplayPageObjectProvider == null) {
			return null;
		}

		long groupId = themeDisplay.getScopeGroupId();

		if ((layoutDisplayPageObjectProvider.getGroupId() != 0) &&
			(groupId != layoutDisplayPageObjectProvider.getGroupId())) {

			Group layoutDisplayPageObjectGroup = _groupLocalService.getGroup(
				layoutDisplayPageObjectProvider.getGroupId());

			if (!layoutDisplayPageObjectGroup.isCompany() &&
				!layoutDisplayPageObjectGroup.isDepot()) {

				groupId = layoutDisplayPageObjectGroup.getGroupId();
			}
		}

		return _getFriendlyURL(
			groupId, layoutDisplayPageProvider, layoutDisplayPageObjectProvider,
			locale, themeDisplay);
	}

	@Override
	public String getFriendlyURL(
			String className, long classPK, ThemeDisplay themeDisplay)
		throws PortalException {

		return getFriendlyURL(
			className, classPK, themeDisplay.getLocale(), themeDisplay);
	}

	private String _getFriendlyURL(
			long groupId,
			LayoutDisplayPageProvider<?> layoutDisplayPageProvider,
			LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider,
			Locale locale, ThemeDisplay themeDisplay)
		throws PortalException {

		if (!AssetDisplayPageUtil.hasAssetDisplayPage(
				groupId, layoutDisplayPageObjectProvider.getClassNameId(),
				layoutDisplayPageObjectProvider.getClassPK(),
				layoutDisplayPageObjectProvider.getClassTypeId())) {

			return null;
		}

		return StringBundler.concat(
			_getGroupFriendlyURL(groupId, locale, themeDisplay),
			layoutDisplayPageProvider.getURLSeparator(),
			layoutDisplayPageObjectProvider.getURLTitle(locale));
	}

	private String _getGroupFriendlyURL(
			long groupId, Locale locale, ThemeDisplay themeDisplay)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		if (locale != null) {
			try {
				ThemeDisplay clonedThemeDisplay =
					(ThemeDisplay)themeDisplay.clone();

				_setThemeDisplayI18n(clonedThemeDisplay, locale);

				return _portal.getGroupFriendlyURL(
					group.getPublicLayoutSet(), clonedThemeDisplay, false,
					false);
			}
			catch (CloneNotSupportedException cloneNotSupportedException) {
				throw new PortalException(cloneNotSupportedException);
			}
		}

		return _portal.getGroupFriendlyURL(
			group.getPublicLayoutSet(), themeDisplay, false, false);
	}

	private String _getI18nPath(Locale locale) {
		Locale defaultLocale = _language.getLocale(locale.getLanguage());

		if (LocaleUtil.equals(defaultLocale, locale)) {
			return StringPool.SLASH + defaultLocale.getLanguage();
		}

		return StringPool.SLASH + locale.toLanguageTag();
	}

	private void _setThemeDisplayI18n(
		ThemeDisplay themeDisplay, Locale locale) {

		String i18nPath = null;

		Set<String> languageIds = I18nFilter.getLanguageIds();

		if ((languageIds.contains(locale.toString()) &&
			 (PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 1) &&
			 !locale.equals(LocaleUtil.getDefault())) ||
			(PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 2)) {

			i18nPath = _getI18nPath(locale);
		}

		themeDisplay.setI18nLanguageId(locale.toString());
		themeDisplay.setI18nPath(i18nPath);
		themeDisplay.setLanguageId(LocaleUtil.toLanguageId(locale));
		themeDisplay.setLocale(locale);
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private LayoutDisplayPageProviderTracker _layoutDisplayPageProviderTracker;

	@Reference
	private Portal _portal;

}