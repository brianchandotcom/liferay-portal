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

package com.liferay.site.navigation.menu.web.internal.display.context;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.site.navigation.constants.SiteNavigationConstants;
import com.liferay.site.navigation.item.selector.criterion.SiteNavigationMenuItemItemSelectorCriterion;
import com.liferay.site.navigation.item.selector.criterion.SiteNavigationMenuItemSelectorCriterion;
import com.liferay.site.navigation.menu.web.internal.configuration.SiteNavigationMenuPortletInstanceConfiguration;
import com.liferay.site.navigation.menu.web.internal.constants.SiteNavigationMenuWebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalServiceUtil;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juergen Kappler
 */
public class SiteNavigationMenuDisplayContext {

	public SiteNavigationMenuDisplayContext(
			HttpServletRequest httpServletRequest)
		throws ConfigurationException {

		_httpServletRequest = httpServletRequest;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_siteNavigationMenuPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				SiteNavigationMenuPortletInstanceConfiguration.class);
	}

	public String getDDMTemplateKey() {
		if (_ddmTemplateKey != null) {
			return _ddmTemplateKey;
		}

		String displayStyle = getDisplayStyle();

		if (displayStyle != null) {
			PortletDisplayTemplate portletDisplayTemplate =
				(PortletDisplayTemplate)_httpServletRequest.getAttribute(
					WebKeys.PORTLET_DISPLAY_TEMPLATE);

			_ddmTemplateKey = portletDisplayTemplate.getDDMTemplateKey(
				displayStyle);
		}

		return _ddmTemplateKey;
	}

	public int getDisplayDepth() {
		if (_displayDepth != -1) {
			return _displayDepth;
		}

		_displayDepth = ParamUtil.getInteger(
			_httpServletRequest, "displayDepth",
			_siteNavigationMenuPortletInstanceConfiguration.displayDepth());

		return _displayDepth;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle",
			_siteNavigationMenuPortletInstanceConfiguration.displayStyle());

		return _displayStyle;
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId = ParamUtil.getLong(
			_httpServletRequest, "displayStyleGroupId",
			_siteNavigationMenuPortletInstanceConfiguration.
				displayStyleGroupId());

		if (_displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = themeDisplay.getSiteGroupId();
		}

		return _displayStyleGroupId;
	}

	public String getExpandedLevels() {
		if (_expandedLevels != null) {
			return _expandedLevels;
		}

		String defaultExpandedLevels =
			_siteNavigationMenuPortletInstanceConfiguration.expandedLevels();

		_expandedLevels = ParamUtil.getString(
			_httpServletRequest, "expandedLevels", defaultExpandedLevels);

		return _expandedLevels;
	}

	public String getRootMenuItemEventName() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getNamespace() + "selectRootMenuItem";
	}

	public String getRootMenuItemId() {
		if (_rootMenuItemId != null) {
			return _rootMenuItemId;
		}

		String defaultRootMenuItemId =
			_siteNavigationMenuPortletInstanceConfiguration.rootMenuItemId();

		_rootMenuItemId = ParamUtil.getString(
			_httpServletRequest, "rootMenuItemId", defaultRootMenuItemId);

		return _rootMenuItemId;
	}

	public int getRootMenuItemLevel() {
		if (_rootMenuItemLevel != null) {
			return _rootMenuItemLevel;
		}

		int defaultRootMenuItemLevel =
			_siteNavigationMenuPortletInstanceConfiguration.rootMenuItemLevel();

		_rootMenuItemLevel = ParamUtil.getInteger(
			_httpServletRequest, "rootMenuItemLevel", defaultRootMenuItemLevel);

		return _rootMenuItemLevel;
	}

	public String getRootMenuItemSelectorURL() {
		String eventName = getRootMenuItemEventName();

		ItemSelector itemSelector =
			(ItemSelector)_httpServletRequest.getAttribute(
				SiteNavigationMenuWebKeys.ITEM_SELECTOR);

		ItemSelectorCriterion itemSelectorCriterion =
			new SiteNavigationMenuItemItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new UUIDItemSelectorReturnType());

		PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
			eventName, itemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	public String getRootMenuItemType() {
		if (_rootMenuItemType != null) {
			return _rootMenuItemType;
		}

		String defaultRootMenuItemType =
			_siteNavigationMenuPortletInstanceConfiguration.rootMenuItemType();

		_rootMenuItemType = ParamUtil.getString(
			_httpServletRequest, "rootMenuItemType", defaultRootMenuItemType);

		return _rootMenuItemType;
	}

	public long getSelectSiteNavigationMenuId() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		int siteNavigationMenuType = getSiteNavigationMenuType();

		long siteNavigationMenuId = getSiteNavigationMenuId();

		if ((siteNavigationMenuType == -1) && (siteNavigationMenuId <= 0)) {
			SiteNavigationMenu siteNavigationMenu =
				SiteNavigationMenuLocalServiceUtil.fetchSiteNavigationMenu(
					themeDisplay.getScopeGroupId(),
					_getDefaultSelectSiteNavigationMenuType());

			if (siteNavigationMenu != null) {
				return siteNavigationMenu.getSiteNavigationMenuId();
			}

			return 0;
		}

		if (siteNavigationMenuType > 0) {
			SiteNavigationMenu siteNavigationMenu =
				SiteNavigationMenuLocalServiceUtil.fetchSiteNavigationMenu(
					themeDisplay.getScopeGroupId(), siteNavigationMenuType);

			if (siteNavigationMenu != null) {
				return siteNavigationMenu.getSiteNavigationMenuId();
			}

			return 0;
		}

		return siteNavigationMenuId;
	}

	public int getSelectSiteNavigationMenuType() {
		int selectSiteNavigationMenuType = getSiteNavigationMenuType();

		if (selectSiteNavigationMenuType > 0) {
			return selectSiteNavigationMenuType;
		}

		return _getDefaultSelectSiteNavigationMenuType();
	}

	public SiteNavigationMenu getSiteNavigationMenu() {
		if (_siteNavigationMenu != null) {
			return _siteNavigationMenu;
		}

		_siteNavigationMenu =
			SiteNavigationMenuLocalServiceUtil.fetchSiteNavigationMenu(
				getSiteNavigationMenuId());

		return _siteNavigationMenu;
	}

	public String getSiteNavigationMenuEventName() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getNamespace() + "selectSiteNavigationMenu";
	}

	public long getSiteNavigationMenuId() {
		if (_siteNavigationMenuId != null) {
			return _siteNavigationMenuId;
		}

		long siteNavigationMenuId = ParamUtil.getLong(
			_httpServletRequest, "siteNavigationMenuId",
			_siteNavigationMenuPortletInstanceConfiguration.
				siteNavigationMenuId());

		if (siteNavigationMenuId > 0) {
			_siteNavigationMenuId = siteNavigationMenuId;
		}
		else {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			SiteNavigationMenu siteNavigationMenu =
				SiteNavigationMenuLocalServiceUtil.
					fetchSiteNavigationMenuByName(
						themeDisplay.getScopeGroupId(),
						_getSiteNavigationMenuName());

			if (siteNavigationMenu != null) {
				_siteNavigationMenuId =
					siteNavigationMenu.getSiteNavigationMenuId();
			}
			else {
				_siteNavigationMenuId = 0L;
			}
		}

		return _siteNavigationMenuId;
	}

	public String getSiteNavigationMenuItemSelectorURL() {
		String eventName = getSiteNavigationMenuEventName();

		ItemSelector itemSelector =
			(ItemSelector)_httpServletRequest.getAttribute(
				SiteNavigationMenuWebKeys.ITEM_SELECTOR);

		ItemSelectorCriterion itemSelectorCriterion =
			new SiteNavigationMenuItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new UUIDItemSelectorReturnType());

		PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
			eventName, itemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	public int getSiteNavigationMenuType() {
		if (_navigationMenuType != null) {
			return _navigationMenuType;
		}

		int siteNavigationMenuType =
			_siteNavigationMenuPortletInstanceConfiguration.
				siteNavigationMenuType();

		_navigationMenuType = ParamUtil.getInteger(
			_httpServletRequest, "siteNavigationMenuType",
			siteNavigationMenuType);

		return _navigationMenuType;
	}

	public String getSiteNavigationMenuTypeLabel() {
		int type = getSiteNavigationMenuType();

		String typeKey = "select";

		if (type == SiteNavigationConstants.TYPE_PRIMARY) {
			typeKey = "primary-navigation";
		}
		else if (type == SiteNavigationConstants.TYPE_PRIVATE_PAGES_HIERARCHY) {
			typeKey = "private-pages-hierarchy";
		}
		else if (type == SiteNavigationConstants.TYPE_PUBLIC_PAGES_HIERARCHY) {
			typeKey = "public-pages-hierarchy";
		}
		else if (type == SiteNavigationConstants.TYPE_SECONDARY) {
			typeKey = "secondary-navigation";
		}
		else if (type == SiteNavigationConstants.TYPE_SOCIAL) {
			typeKey = "social-navigation";
		}

		return LanguageUtil.get(_httpServletRequest, typeKey);
	}

	public boolean isPreview() {
		if (_preview != null) {
			return _preview;
		}

		_preview = ParamUtil.getBoolean(
			_httpServletRequest, "preview",
			_siteNavigationMenuPortletInstanceConfiguration.preview());

		return _preview;
	}

	public boolean isSiteNavigationMenuSelected() {
		long siteNavigationMenuId =
			_siteNavigationMenuPortletInstanceConfiguration.
				siteNavigationMenuId();
		int siteNavigationMenuType =
			_siteNavigationMenuPortletInstanceConfiguration.
				siteNavigationMenuType();

		if ((siteNavigationMenuId > 0) && (siteNavigationMenuType == -1)) {
			return true;
		}

		return false;
	}

	private int _getDefaultSelectSiteNavigationMenuType() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout.isPrivateLayout()) {
			return SiteNavigationConstants.TYPE_PRIVATE_PAGES_HIERARCHY;
		}

		return SiteNavigationConstants.TYPE_PUBLIC_PAGES_HIERARCHY;
	}

	private String _getSiteNavigationMenuName() {
		if (_siteNavigationMenuName != null) {
			return _siteNavigationMenuName;
		}

		_siteNavigationMenuName = ParamUtil.getString(
			_httpServletRequest, "siteNavigationMenuName",
			_siteNavigationMenuPortletInstanceConfiguration.
				siteNavigationMenuName());

		return _siteNavigationMenuName;
	}

	private String _ddmTemplateKey;
	private int _displayDepth = -1;
	private String _displayStyle;
	private long _displayStyleGroupId;
	private String _expandedLevels;
	private final HttpServletRequest _httpServletRequest;
	private Integer _navigationMenuType;
	private Boolean _preview;
	private String _rootMenuItemId;
	private Integer _rootMenuItemLevel;
	private String _rootMenuItemType;
	private SiteNavigationMenu _siteNavigationMenu;
	private Long _siteNavigationMenuId;
	private String _siteNavigationMenuName;
	private final SiteNavigationMenuPortletInstanceConfiguration
		_siteNavigationMenuPortletInstanceConfiguration;

}