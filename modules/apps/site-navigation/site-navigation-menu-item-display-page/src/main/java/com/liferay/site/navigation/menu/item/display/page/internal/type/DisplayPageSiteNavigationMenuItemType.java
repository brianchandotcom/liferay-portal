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
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
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
import com.liferay.site.navigation.menu.item.display.page.internal.constants.SiteNavigationMenuItemTypeDisplayPageWebKeys;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;

import java.io.IOException;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"service.ranking:Integer=300",
		"site.navigation.menu.item.type=" + SiteNavigationMenuItemTypeConstants.DISPLAY_PAGE
	},
	service = SiteNavigationMenuItemType.class
)
public class DisplayPageSiteNavigationMenuItemType
	implements SiteNavigationMenuItemType {

	@Override
	public boolean exportData(
		PortletDataContext portletDataContext,
		Element siteNavigationMenuItemElement,
		SiteNavigationMenuItem siteNavigationMenuItem) {

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem.getTypeSettings()
			).build();

		long classNameId = GetterUtil.getLong(
			typeSettingsUnicodeProperties.get("classNameId"));

		if (classNameId <= 0) {
			return false;
		}

		String className = _portal.getClassName(classNameId);

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			_layoutDisplayPageProviderTracker.
				getLayoutDisplayPageProviderByClassName(className);

		if (layoutDisplayPageProvider == null) {
			return false;
		}

		long classPK = GetterUtil.getLong(
			typeSettingsUnicodeProperties.get("classPK"));

		try {
			LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
				layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
					new InfoItemReference(className, classPK));

			siteNavigationMenuItemElement.addAttribute(
				"display-page-class-name", className);
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
		return LanguageUtil.get(locale, "select-item");
	}

	@Override
	public PortletURL getAddURL(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		return PortletURLBuilder.createActionURL(
			renderResponse
		).setActionName(
			"/navigation_menu/add_display_page_site_navigation_menu_item"
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
		return LanguageUtil.get(locale, "display-page");
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
				_portal.getClassName(
					GetterUtil.getLong(
						typeSettingsUnicodeProperties.get("classNameId"))),
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

		return LanguageUtil.get(locale, "display-page");
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
			_getLayoutDisplayPageObjectProvider(typeSettingsUnicodeProperties);

		if (layoutDisplayPageObjectProvider == null) {
			return typeSettingsUnicodeProperties.getProperty("title");
		}

		return layoutDisplayPageObjectProvider.getTitle(locale);
	}

	@Override
	public String getType() {
		return SiteNavigationMenuItemTypeConstants.DISPLAY_PAGE;
	}

	@Override
	public boolean importData(
		PortletDataContext portletDataContext,
		SiteNavigationMenuItem siteNavigationMenuItem,
		SiteNavigationMenuItem importedSiteNavigationMenuItem) {

		Element element = portletDataContext.getImportDataElement(
			siteNavigationMenuItem);

		String className = element.attributeValue("display-page-class-name");
		long classPK = GetterUtil.getLong(
			element.attributeValue("display-page-class-pk"));

		if (Validator.isNull(className) || (classPK <= 0)) {
			return false;
		}

		Map<Long, Long> newClassPKsMap =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(className);

		importedSiteNavigationMenuItem.setTypeSettings(
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem.getTypeSettings()
			).put(
				"classNameId", String.valueOf(_portal.getClassNameId(className))
			).put(
				"classPK",
				String.valueOf(
					MapUtil.getLong(newClassPKsMap, classPK, classPK))
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
				INFO_ITEM_SERVICE_TRACKER,
			_infoItemServiceTracker);
		httpServletRequest.setAttribute(
			SiteNavigationMenuItemTypeDisplayPageWebKeys.ITEM_SELECTOR,
			_itemSelector);
		httpServletRequest.setAttribute(
			SiteNavigationMenuItemTypeDisplayPageWebKeys.
				LAYOUT_DISPLAY_PAGE_PROVIDER_TRACKER,
			_layoutDisplayPageProviderTracker);
		httpServletRequest.setAttribute(
			SiteNavigationWebKeys.SITE_NAVIGATION_MENU_ITEM,
			siteNavigationMenuItem);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/edit_display_page.jsp");
	}

	private LayoutDisplayPageObjectProvider<?>
		_getLayoutDisplayPageObjectProvider(
			UnicodeProperties typeSettingsUnicodeProperties) {

		long classNameId = GetterUtil.getLong(
			typeSettingsUnicodeProperties.get("classNameId"));

		String className = _portal.getClassName(classNameId);

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			_layoutDisplayPageProviderTracker.
				getLayoutDisplayPageProviderByClassName(className);

		if (layoutDisplayPageProvider == null) {
			return null;
		}

		long classPK = GetterUtil.getLong(
			typeSettingsUnicodeProperties.get("classPK"));

		return layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
			new InfoItemReference(className, classPK));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DisplayPageSiteNavigationMenuItemType.class);

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private LayoutDisplayPageProviderTracker _layoutDisplayPageProviderTracker;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.navigation.menu.item.display.page)",
		unbind = "-"
	)
	private ServletContext _servletContext;

	@Reference
	private SiteNavigationMenuItemService _siteNavigationMenuItemService;

}