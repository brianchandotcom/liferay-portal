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
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.constants.SiteNavigationWebKeys;
import com.liferay.site.navigation.menu.item.display.page.internal.constants.SiteNavigationMenuItemTypeDisplayPageWebKeys;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
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

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = "site.navigation.menu.item.type=" + SiteNavigationMenuItemTypeConstants.DISPLAY_PAGE,
	service = SiteNavigationMenuItemType.class
)
public class DisplayPageSiteNavigationMenuItemType
	implements SiteNavigationMenuItemType {

	@Override
	public PortletURL getAddURL(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		return PortletURLBuilder.createActionURL(
			renderResponse
		).setActionName(
			"/navigation_menu/add_display_page_site_navigation_menu_item"
		).build();
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
			new UnicodeProperties();

		typeSettingsUnicodeProperties.fastLoad(
			siteNavigationMenuItem.getTypeSettings());

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

		return StringPool.BLANK;
	}

	@Override
	public String getSubtitle(
		SiteNavigationMenuItem siteNavigationMenuItem, Locale locale) {

		return LanguageUtil.get(locale, "display-page");
	}

	@Override
	public String getTarget(SiteNavigationMenuItem siteNavigationMenuItem) {
		UnicodeProperties typeSettingsUnicodeProperties =
			new UnicodeProperties();

		typeSettingsUnicodeProperties.fastLoad(
			siteNavigationMenuItem.getTypeSettings());

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
			new UnicodeProperties();

		typeSettingsUnicodeProperties.fastLoad(
			siteNavigationMenuItem.getTypeSettings());

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

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ffDisplayPageSiteNavigationMenuItemTypeConfiguration =
			ConfigurableUtil.createConfigurable(
				FFDisplayPageSiteNavigationMenuItemTypeConfiguration.class,
				properties);
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

}