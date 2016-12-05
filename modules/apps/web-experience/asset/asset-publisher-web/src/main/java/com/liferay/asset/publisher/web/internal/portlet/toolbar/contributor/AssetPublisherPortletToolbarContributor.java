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

package com.liferay.asset.publisher.web.internal.portlet.toolbar.contributor;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.web.constants.AssetPublisherWebKeys;
import com.liferay.asset.publisher.web.display.context.AssetPublisherDisplayContext;
import com.liferay.asset.publisher.web.util.AssetPublisherCustomizer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.util.AssetUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AssetPublisherPortletKeys.ASSET_PUBLISHER,
		"mvc.path=-", "mvc.path=/view.jsp"
	},
	service = {
		AssetPublisherPortletToolbarContributor.class,
		PortletToolbarContributor.class
	}
)
public class AssetPublisherPortletToolbarContributor
	implements PortletToolbarContributor {

	@Override
	public List<Menu> getPortletTitleMenus(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Menu addEntryPortletTitleMenu = getAddEntryPortletTitleMenu(
			portletRequest, portletResponse);

		if (addEntryPortletTitleMenu == null) {
			return Collections.emptyList();
		}

		List<Menu> menus = new ArrayList<>();

		menus.add(addEntryPortletTitleMenu);

		return menus;
	}

	protected void addPortletTitleAddAssetEntryMenuItems(
			List<MenuItem> menuItems, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group scopeGroup = themeDisplay.getScopeGroup();
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletName = portletDisplay.getPortletName();

		AssetPublisherCustomizer assetPublisherCustomizer =
			(AssetPublisherCustomizer)portletRequest.getAttribute(
				AssetPublisherWebKeys.ASSET_PUBLISHER_CUSTOMIZER);

		AssetPublisherDisplayContext assetPublisherDisplayContext =
			new AssetPublisherDisplayContext(
				assetPublisherCustomizer, portletRequest, portletResponse,
				portletRequest.getPreferences());

		if (!assetPublisherDisplayContext.isShowAddContentButton() ||
			(scopeGroup == null) || scopeGroup.isLayoutPrototype() ||
			(scopeGroup.hasStagingGroup() && !scopeGroup.isStagingGroup() &&
			 PropsValues.STAGING_LIVE_GROUP_LOCKING_ENABLED) ||
			portletName.equals(
				AssetPublisherPortletKeys.HIGHEST_RATED_ASSETS) ||
			portletName.equals(AssetPublisherPortletKeys.MOST_VIEWED_ASSETS) ||
			portletName.equals(AssetPublisherPortletKeys.RELATED_ASSETS)) {

			return;
		}

		Map<Long, Map<String, PortletURL>> scopeAddPortletURLs =
			assetPublisherDisplayContext.getScopeAddPortletURLs(1);

		if (MapUtil.isEmpty(scopeAddPortletURLs)) {
			return;
		}

		if (scopeAddPortletURLs.size() == 1) {
			Set<Map.Entry<Long, Map<String, PortletURL>>> entrySet =
				scopeAddPortletURLs.entrySet();

			Iterator<Map.Entry<Long, Map<String, PortletURL>>> iterator =
				entrySet.iterator();

			Map.Entry<Long, Map<String, PortletURL>> scopeAddPortletURL =
				iterator.next();

			long groupId = scopeAddPortletURL.getKey();

			Map<String, PortletURL> addPortletURLs =
				scopeAddPortletURL.getValue();

			for (Map.Entry<String, PortletURL> entry :
					addPortletURLs.entrySet()) {

				URLMenuItem urlMenuItem = _getPortletTitleAddAssetEntryMenuItem(
					themeDisplay, assetPublisherDisplayContext, groupId,
					entry.getKey(), entry.getValue());

				menuItems.add(urlMenuItem);
			}

			return;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		Map<String, Object> data = new HashMap<>();

		data.put(
			"id", HtmlUtil.escape(portletDisplay.getNamespace()) + "editAsset");

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", themeDisplay.getLocale(), getClass());

		String title = LanguageUtil.get(
			resourceBundle, "add-content-select-scope-and-type");

		data.put("title", title);

		urlMenuItem.setData(data);

		urlMenuItem.setLabel(title);

		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(portletResponse);

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/add_asset_selector.jsp");
		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		urlMenuItem.setURL(portletURL.toString());

		urlMenuItem.setUseDialog(true);

		menuItems.add(urlMenuItem);
	}

	protected Menu getAddEntryPortletTitleMenu(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		List<MenuItem> portletTitleMenuItems = getPortletTitleMenuItems(
			portletRequest, portletResponse);

		if (ListUtil.isEmpty(portletTitleMenuItems)) {
			return null;
		}

		Menu menu = new Menu();

		Map<String, Object> data = new HashMap<>();

		data.put("qa-id", "addButton");

		menu.setData(data);

		menu.setDirection("right");
		menu.setExtended(false);
		menu.setIcon("plus");
		menu.setMarkupView("lexicon");
		menu.setMenuItems(portletTitleMenuItems);
		menu.setScroll(false);
		menu.setShowArrow(false);
		menu.setShowWhenSingleIcon(true);

		return menu;
	}

	protected List<MenuItem> getPortletTitleMenuItems(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		List<MenuItem> menuItems = new ArrayList<>();

		try {
			addPortletTitleAddAssetEntryMenuItems(
				menuItems, portletRequest, portletResponse);
		}
		catch (Exception e) {
			_log.error("Unable to add folder menu item", e);
		}

		return menuItems;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	private URLMenuItem _getPortletTitleAddAssetEntryMenuItem(
		ThemeDisplay themeDisplay,
		AssetPublisherDisplayContext assetPublisherDisplayContext, long groupId,
		String className, PortletURL portletURL) {

		URLMenuItem urlMenuItem = new URLMenuItem();

		Map<String, Object> data = new HashMap<>();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		data.put(
			"id", HtmlUtil.escape(portletDisplay.getNamespace()) + "editAsset");

		String message = AssetUtil.getClassNameMessage(
			className, themeDisplay.getLocale());

		String title = LanguageUtil.format(
			themeDisplay.getLocale(), "new-x", message, false);

		data.put("title", title);

		urlMenuItem.setData(data);

		urlMenuItem.setLabel(HtmlUtil.escape(message));

		long curGroupId = groupId;

		Group group = _groupLocalService.fetchGroup(groupId);

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				AssetUtil.getClassName(className));

		if (!group.isStagedPortlet(assetRendererFactory.getPortletId()) &&
			!group.isStagedRemotely()) {

			curGroupId = group.getLiveGroupId();
		}

		boolean addDisplayPageParameter = AssetUtil.isDefaultAssetPublisher(
			themeDisplay.getLayout(), portletDisplay.getId(),
			assetPublisherDisplayContext.getPortletResource());

		String url = AssetUtil.getAddURLPopUp(
			curGroupId, themeDisplay.getPlid(), portletURL,
			assetRendererFactory.getPortletId(), addDisplayPageParameter,
			themeDisplay.getLayout());

		urlMenuItem.setURL(url);

		urlMenuItem.setUseDialog(true);

		return urlMenuItem;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetPublisherPortletToolbarContributor.class);

	private GroupLocalService _groupLocalService;

}