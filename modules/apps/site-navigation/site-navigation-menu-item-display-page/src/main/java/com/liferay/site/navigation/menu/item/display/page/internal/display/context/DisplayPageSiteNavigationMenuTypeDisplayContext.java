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

package com.liferay.site.navigation.menu.item.display.page.internal.display.context;

import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.constants.SiteNavigationWebKeys;
import com.liferay.site.navigation.menu.item.display.page.internal.constants.SiteNavigationMenuItemTypeDisplayPageWebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class DisplayPageSiteNavigationMenuTypeDisplayContext {

	public DisplayPageSiteNavigationMenuTypeDisplayContext(
		HttpServletRequest httpServletRequest) {

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_infoItemServiceTracker =
			(InfoItemServiceTracker)httpServletRequest.getAttribute(
				SiteNavigationMenuItemTypeDisplayPageWebKeys.
					INFO_ITEM_SERVICE_TRACKER);
		_layoutDisplayPageProviderTracker =
			(LayoutDisplayPageProviderTracker)httpServletRequest.getAttribute(
				SiteNavigationMenuItemTypeDisplayPageWebKeys.
					LAYOUT_DISPLAY_PAGE_PROVIDER_TRACKER);
		_siteNavigationMenuItem =
			(SiteNavigationMenuItem)httpServletRequest.getAttribute(
				SiteNavigationWebKeys.SITE_NAVIGATION_MENU_ITEM);
	}

	public long getClassNameId() {
		if (_classNameId != null) {
			return _classNameId;
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			new UnicodeProperties();

		typeSettingsUnicodeProperties.fastLoad(
			_siteNavigationMenuItem.getTypeSettings());

		_classNameId = GetterUtil.getLong(
			typeSettingsUnicodeProperties.get("classNameId"));

		return _classNameId;
	}

	public long getClassPK() {
		if (_classPK != null) {
			return _classPK;
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			new UnicodeProperties();

		typeSettingsUnicodeProperties.fastLoad(
			_siteNavigationMenuItem.getTypeSettings());

		_classPK = GetterUtil.getLong(
			typeSettingsUnicodeProperties.get("classPK"));

		return _classPK;
	}

	public long getClassTypeId() {
		if (_classTypeId != null) {
			return _classTypeId;
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			new UnicodeProperties();

		typeSettingsUnicodeProperties.fastLoad(
			_siteNavigationMenuItem.getTypeSettings());

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			_getLayoutDisplayPageObjectProvider(typeSettingsUnicodeProperties);

		if (layoutDisplayPageObjectProvider != null) {
			_classTypeId = layoutDisplayPageObjectProvider.getClassTypeId();
		}
		else {
			_classTypeId = GetterUtil.getLong(
				typeSettingsUnicodeProperties.get("classTypeId"));
		}

		return _classTypeId;
	}

	public String getItemSubtype() {
		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				PortalUtil.getClassName(getClassNameId()));

		if (infoItemFormVariationsProvider == null) {
			return StringPool.BLANK;
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			new UnicodeProperties();

		typeSettingsUnicodeProperties.fastLoad(
			_siteNavigationMenuItem.getTypeSettings());

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			_getLayoutDisplayPageObjectProvider(typeSettingsUnicodeProperties);

		if (layoutDisplayPageObjectProvider == null) {
			return StringPool.BLANK;
		}

		InfoItemFormVariation infoItemFormVariation =
			infoItemFormVariationsProvider.getInfoItemFormVariation(
				layoutDisplayPageObjectProvider.getGroupId(),
				String.valueOf(getClassTypeId()));

		if (infoItemFormVariation != null) {
			return infoItemFormVariation.getLabel(_themeDisplay.getLocale());
		}

		return StringPool.BLANK;
	}

	public String getItemType() {
		InfoItemDetailsProvider<?> infoItemDetailsProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemDetailsProvider.class,
				PortalUtil.getClassName(getClassNameId()));

		if (infoItemDetailsProvider == null) {
			return StringPool.BLANK;
		}

		InfoItemClassDetails infoItemClassDetails =
			infoItemDetailsProvider.getInfoItemClassDetails();

		if (infoItemClassDetails != null) {
			return infoItemClassDetails.getLabel(_themeDisplay.getLocale());
		}

		return getType();
	}

	public String getItemTypeURL(
		LiferayPortletResponse liferayPortletResponse) {

		LiferayPortletURL itemTypeURL =
			(LiferayPortletURL)liferayPortletResponse.createResourceURL();

		itemTypeURL.setCopyCurrentRenderParameters(false);
		itemTypeURL.setResourceID("/navigation_menu/get_item_type");

		return itemTypeURL.toString();
	}

	public String getOriginalTitle() {
		if (Validator.isNotNull(_originalTitle)) {
			return _originalTitle;
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			new UnicodeProperties();

		typeSettingsUnicodeProperties.fastLoad(
			_siteNavigationMenuItem.getTypeSettings());

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			_getLayoutDisplayPageObjectProvider(typeSettingsUnicodeProperties);

		if (layoutDisplayPageObjectProvider == null) {
			_originalTitle = typeSettingsUnicodeProperties.getProperty("title");
		}
		else {
			_originalTitle = layoutDisplayPageObjectProvider.getTitle(
				_themeDisplay.getLocale());
		}

		return _originalTitle;
	}

	public String getTitle() {
		if (Validator.isNotNull(_title)) {
			return _title;
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			new UnicodeProperties();

		typeSettingsUnicodeProperties.fastLoad(
			_siteNavigationMenuItem.getTypeSettings());

		_title = typeSettingsUnicodeProperties.get("title");

		return _title;
	}

	public String getType() {
		if (Validator.isNotNull(_type)) {
			return _type;
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			new UnicodeProperties();

		typeSettingsUnicodeProperties.fastLoad(
			_siteNavigationMenuItem.getTypeSettings());

		_type = typeSettingsUnicodeProperties.get("type");

		return _type;
	}

	private LayoutDisplayPageObjectProvider<?>
		_getLayoutDisplayPageObjectProvider(
			UnicodeProperties typeSettingsUnicodeProperties) {

		if (_layoutDisplayPageObjectProvider != null) {
			return _layoutDisplayPageObjectProvider;
		}

		String className = PortalUtil.getClassName(getClassNameId());

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			_layoutDisplayPageProviderTracker.
				getLayoutDisplayPageProviderByClassName(className);

		if (layoutDisplayPageProvider == null) {
			return null;
		}

		long classPK = GetterUtil.getLong(
			typeSettingsUnicodeProperties.get("classPK"));

		_layoutDisplayPageObjectProvider =
			layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				new InfoItemReference(className, classPK));

		return _layoutDisplayPageObjectProvider;
	}

	private Long _classNameId;
	private Long _classPK;
	private Long _classTypeId;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private LayoutDisplayPageObjectProvider<?> _layoutDisplayPageObjectProvider;
	private final LayoutDisplayPageProviderTracker
		_layoutDisplayPageProviderTracker;
	private String _originalTitle;
	private final SiteNavigationMenuItem _siteNavigationMenuItem;
	private final ThemeDisplay _themeDisplay;
	private String _title;
	private String _type;

}