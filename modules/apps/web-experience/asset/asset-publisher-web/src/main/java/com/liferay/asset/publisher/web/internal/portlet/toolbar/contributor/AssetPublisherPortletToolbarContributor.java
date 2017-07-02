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

import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.web.constants.AssetPublisherWebKeys;
import com.liferay.asset.publisher.web.display.context.AssetPublisherDisplayContext;
import com.liferay.asset.publisher.web.util.AssetPublisherCustomizer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.toolbar.contributor.BasePortletToolbarContributor;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.util.AssetPublisherAddItemHolder;
import com.liferay.portlet.asset.util.AssetUtil;

import java.util.ArrayList;
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
	extends BasePortletToolbarContributor {

	protected void addPortletTitleAddAssetEntryMenuItems(
			List<MenuItem> menuItems, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();
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
			layout.isLayoutPrototypeLinkActive() ||
			portletName.equals(
				AssetPublisherPortletKeys.HIGHEST_RATED_ASSETS) ||
			portletName.equals(AssetPublisherPortletKeys.MOST_VIEWED_ASSETS) ||
			portletName.equals(AssetPublisherPortletKeys.RELATED_ASSETS)) {

			return;
		}

		Map<Long, List<AssetPublisherAddItemHolder>>
			scopeAssetPublisherAddItemHolders =
				assetPublisherDisplayContext.
					getScopeAssetPublisherAddItemHolders(1);

		if (MapUtil.isEmpty(scopeAssetPublisherAddItemHolders)) {
			return;
		}

		if (scopeAssetPublisherAddItemHolders.size() == 1) {
			Set<Map.Entry<Long, List<AssetPublisherAddItemHolder>>> entrySet =
				scopeAssetPublisherAddItemHolders.entrySet();

			Iterator<Map.Entry<Long, List<AssetPublisherAddItemHolder>>>
				iterator = entrySet.iterator();

			Map.Entry<Long, List<AssetPublisherAddItemHolder>>
				scopeAddPortletURL = iterator.next();

			long groupId = scopeAddPortletURL.getKey();

			List<AssetPublisherAddItemHolder> assetPublisherAddItemHolders =
				scopeAddPortletURL.getValue();

			for (AssetPublisherAddItemHolder assetPublisherAddItemHolder :
					assetPublisherAddItemHolders) {

				URLMenuItem urlMenuItem = _getPortletTitleAddAssetEntryMenuItem(
					themeDisplay, assetPublisherDisplayContext, groupId,
					assetPublisherAddItemHolder);

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
			_portal.getLiferayPortletResponse(portletResponse);

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/add_asset_selector.jsp");
		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		urlMenuItem.setURL(portletURL.toString());

		urlMenuItem.setUseDialog(true);

		menuItems.add(urlMenuItem);
	}

	@Override
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
		AssetPublisherAddItemHolder assetPublisherAddItemHolder) {

		URLMenuItem urlMenuItem = new URLMenuItem();

		Map<String, Object> data = new HashMap<>();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		data.put(
			"id", HtmlUtil.escape(portletDisplay.getNamespace()) + "editAsset");

		String message = assetPublisherAddItemHolder.getModelResource();

		String title = LanguageUtil.format(
			themeDisplay.getLocale(), "new-x", message, false);

		data.put("title", title);

		urlMenuItem.setData(data);

		urlMenuItem.setLabel(HtmlUtil.escape(message));

		long curGroupId = groupId;

		Group group = _groupLocalService.fetchGroup(groupId);

		if (!group.isStagedPortlet(
				assetPublisherAddItemHolder.getPortletId()) &&
			!group.isStagedRemotely()) {

			curGroupId = group.getLiveGroupId();
		}

		boolean addDisplayPageParameter = AssetUtil.isDefaultAssetPublisher(
			themeDisplay.getLayout(), portletDisplay.getId(),
			assetPublisherDisplayContext.getPortletResource());

		String url = AssetUtil.getAddURLPopUp(
			curGroupId, themeDisplay.getPlid(),
			assetPublisherAddItemHolder.getPortletURL(),
			addDisplayPageParameter, themeDisplay.getLayout());

		urlMenuItem.setURL(url);

		urlMenuItem.setUseDialog(true);

		return urlMenuItem;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetPublisherPortletToolbarContributor.class);

	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}