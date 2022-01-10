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

package com.liferay.site.navigation.menu.item.display.page.internal.type;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.layout.display.page.LayoutDisplayPageMultiSelectionProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.site.navigation.constants.SiteNavigationWebKeys;
import com.liferay.site.navigation.menu.item.display.page.internal.configuration.FFDisplayPageSiteNavigationMenuItemConfigurationUtil;
import com.liferay.site.navigation.menu.item.display.page.internal.constants.SiteNavigationMenuItemTypeDisplayPageWebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;

import java.io.IOException;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Lourdes Fernández Besada
 */
public class DisplayPageTypeSiteNavigationMenuItemType
	implements SiteNavigationMenuItemType {

	public DisplayPageTypeSiteNavigationMenuItemType(
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		DisplayPageTypeContext displayPageTypeContext,
		ItemSelector itemSelector, JSPRenderer jspRenderer, Portal portal,
		ServletContext servletContext) {

		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_displayPageTypeContext = displayPageTypeContext;
		_itemSelector = itemSelector;
		_jspRenderer = jspRenderer;
		_portal = portal;
		_servletContext = servletContext;
	}

	@Override
	public boolean exportData(
		PortletDataContext portletDataContext,
		Element siteNavigationMenuItemElement,
		SiteNavigationMenuItem siteNavigationMenuItem) {

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem.getTypeSettings()
			).build();

		long classPK = GetterUtil.getLong(
			typeSettingsUnicodeProperties.get("classPK"));

		try {
			LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
				_displayPageTypeContext.getLayoutDisplayPageObjectProvider(
					classPK);

			if (layoutDisplayPageObjectProvider == null) {
				return false;
			}

			siteNavigationMenuItemElement.addAttribute(
				"display-page-class-name",
				_displayPageTypeContext.getClassName());
			siteNavigationMenuItemElement.addAttribute(
				"display-page-class-pk", String.valueOf(classPK));

			portletDataContext.addReferenceElement(
				siteNavigationMenuItem, siteNavigationMenuItemElement,
				(ClassedModel)
					layoutDisplayPageObjectProvider.getDisplayObject(),
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY, false);

			return true;
		}
		catch (RuntimeException runtimeException) {
			_log.error(runtimeException, runtimeException);
		}

		return false;
	}

	@Override
	public String getAddTitle(Locale locale) {
		if (!FFDisplayPageSiteNavigationMenuItemConfigurationUtil.
				multipleSelectionEnabled()) {

			return LanguageUtil.format(
				locale, "select-x", _displayPageTypeContext.getLabel(locale));
		}

		String label = _displayPageTypeContext.getLabel(locale);

		Optional<LayoutDisplayPageMultiSelectionProvider<?>>
			layoutDisplayPageMultiSelectionProviderOptional =
				_displayPageTypeContext.
					getLayoutDisplayPageMultiSelectionProviderOptional();

		if (layoutDisplayPageMultiSelectionProviderOptional.isPresent()) {
			LayoutDisplayPageMultiSelectionProvider<?>
				layoutDisplayPageMultiSelectionProvider =
					layoutDisplayPageMultiSelectionProviderOptional.get();

			label = layoutDisplayPageMultiSelectionProvider.getPluralLabel(
				locale);
		}

		return LanguageUtil.format(locale, "select-x", label);
	}

	@Override
	public PortletURL getAddURL(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		return PortletURLBuilder.createActionURL(
			renderResponse
		).setActionName(
			() -> {
				if (isMultiSelection()) {
					return "/navigation_menu" +
						"/add_multiple_display_page_type_site_navigation_" +
							"menu_item";
				}

				return "/navigation_menu" +
					"/add_display_page_type_site_navigation_menu_item";
			}
		).setParameter(
			"siteNavigationMenuItemType", getType()
		).buildPortletURL();
	}

	@Override
	public String getIcon() {
		return "page";
	}

	@Override
	public String getItemSelectorURL(HttpServletRequest httpServletRequest) {
		RenderResponse renderResponse =
			(RenderResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		InfoItemItemSelectorCriterion itemSelectorCriterion =
			new InfoItemItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new InfoItemItemSelectorReturnType());
		itemSelectorCriterion.setItemType(
			_displayPageTypeContext.getClassName());
		itemSelectorCriterion.setMultiSelection(isMultiSelection());

		PortletURL infoItemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			renderResponse.getNamespace() + "selectItem",
			itemSelectorCriterion);

		if (infoItemSelectorURL == null) {
			return StringPool.BLANK;
		}

		return infoItemSelectorURL.toString();
	}

	@Override
	public String getLabel(Locale locale) {
		return _displayPageTypeContext.getLabel(locale);
	}

	@Override
	public String getName(String typeSettings) {
		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				typeSettings
			).build();

		return typeSettingsUnicodeProperties.get("title");
	}

	@Override
	public String getRegularURL(
			HttpServletRequest httpServletRequest,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			return StringPool.BLANK;
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem.getTypeSettings()
			).build();

		String friendlyURL =
			_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
				_displayPageTypeContext.getClassName(),
				GetterUtil.getLong(
					typeSettingsUnicodeProperties.get("classPK")),
				themeDisplay);

		if (Validator.isNotNull(friendlyURL)) {
			return friendlyURL;
		}

		return themeDisplay.getURLCurrent() + StringPool.POUND;
	}

	@Override
	public String getSubtitle(
		SiteNavigationMenuItem siteNavigationMenuItem, Locale locale) {

		return _displayPageTypeContext.getLabel(locale);
	}

	@Override
	public String getTarget(SiteNavigationMenuItem siteNavigationMenuItem) {
		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem.getTypeSettings()
			).build();

		boolean useNewTab = GetterUtil.getBoolean(
			typeSettingsUnicodeProperties.getProperty(
				"useNewTab", Boolean.FALSE.toString()));

		if (!useNewTab) {
			return StringPool.BLANK;
		}

		return "target=\"_blank\"";
	}

	@Override
	public String getTitle(
		SiteNavigationMenuItem siteNavigationMenuItem, Locale locale) {

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem.getTypeSettings()
			).build();

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			_displayPageTypeContext.getLayoutDisplayPageObjectProvider(
				GetterUtil.getLong(
					typeSettingsUnicodeProperties.get("classPK")));

		if (layoutDisplayPageObjectProvider == null) {
			return typeSettingsUnicodeProperties.getProperty("title");
		}

		return layoutDisplayPageObjectProvider.getTitle(locale);
	}

	@Override
	public String getType() {
		return _displayPageTypeContext.getClassName();
	}

	@Override
	public boolean importData(
		PortletDataContext portletDataContext,
		SiteNavigationMenuItem siteNavigationMenuItem,
		SiteNavigationMenuItem importedSiteNavigationMenuItem) {

		Element element = portletDataContext.getImportDataElement(
			siteNavigationMenuItem);

		long classPK = GetterUtil.getLong(
			element.attributeValue("display-page-class-pk"));

		if (classPK <= 0) {
			return false;
		}

		importedSiteNavigationMenuItem.setTypeSettings(
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem.getTypeSettings()
			).put(
				"classNameId",
				String.valueOf(
					_portal.getClassNameId(
						_displayPageTypeContext.getClassName()))
			).put(
				"classPK",
				String.valueOf(
					MapUtil.getLong(
						(Map<Long, Long>)
							portletDataContext.getNewPrimaryKeysMap(
								_displayPageTypeContext.getClassName()),
						classPK, classPK))
			).buildString());

		return true;
	}

	@Override
	public boolean isBrowsable(SiteNavigationMenuItem siteNavigationMenuItem) {
		return true;
	}

	@Override
	public boolean isItemSelector() {
		return true;
	}

	@Override
	public boolean isMultiSelection() {
		if (!FFDisplayPageSiteNavigationMenuItemConfigurationUtil.
				multipleSelectionEnabled()) {

			return false;
		}

		Optional<LayoutDisplayPageMultiSelectionProvider<?>>
			layoutDisplayPageMultiSelectionProviderOptional =
				_displayPageTypeContext.
					getLayoutDisplayPageMultiSelectionProviderOptional();

		if (layoutDisplayPageMultiSelectionProviderOptional.isPresent()) {
			return true;
		}

		return false;
	}

	@Override
	public void renderAddPage(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {
	}

	@Override
	public void renderEditPage(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws IOException {

		httpServletRequest.setAttribute(
			SiteNavigationMenuItemTypeDisplayPageWebKeys.
				DISPLAY_PAGE_TYPE_CONTEXT,
			_displayPageTypeContext);
		httpServletRequest.setAttribute(
			SiteNavigationMenuItemTypeDisplayPageWebKeys.ITEM_SELECTOR,
			_itemSelector);
		httpServletRequest.setAttribute(
			SiteNavigationWebKeys.SITE_NAVIGATION_MENU_ITEM,
			siteNavigationMenuItem);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/edit_display_page_type.jsp");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DisplayPageTypeSiteNavigationMenuItemType.class);

	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private final DisplayPageTypeContext _displayPageTypeContext;
	private final ItemSelector _itemSelector;
	private final JSPRenderer _jspRenderer;
	private final Portal _portal;
	private final ServletContext _servletContext;

}