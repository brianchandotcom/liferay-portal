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

package com.liferay.asset.list.web.internal.display.context;

import com.liferay.asset.list.constants.AssetListActionKeys;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.web.internal.security.permission.resource.AssetListEntryPermission;
import com.liferay.asset.list.web.internal.security.permission.resource.AssetListPermission;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetListManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public AssetListManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		AssetListDisplayContext assetListDisplayContext) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			assetListDisplayContext.getAssetListEntriesSearchContainer());
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (_isLiveGroup(themeDisplay)) {
			return null;
		}

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData(
					"action", "deleteSelectedAssetListEntries");
				dropdownItem.setIcon("trash");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	public String getAvailableActions(AssetListEntry assetListEntry)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!_isLiveGroup(themeDisplay) &&
			AssetListEntryPermission.contains(
				themeDisplay.getPermissionChecker(), assetListEntry,
				ActionKeys.DELETE)) {

			return "deleteSelectedAssetListEntries";
		}

		return StringPool.BLANK;
	}

	@Override
	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setKeywords(
			StringPool.BLANK
		).buildString();
	}

	@Override
	public String getComponentId() {
		return "assetListEntriesEntriesManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "addAssetListEntry");
				dropdownItem.putData(
					"addAssetListEntryURL",
					PortletURLBuilder.createActionURL(
						liferayPortletResponse
					).setActionName(
						"/asset_list/add_asset_list_entry"
					).setParameter(
						"type", AssetListEntryTypeConstants.TYPE_MANUAL
					).buildString());
				dropdownItem.putData(
					"title",
					LanguageUtil.format(
						httpServletRequest, "add-x-collection",
						AssetListEntryTypeConstants.TYPE_MANUAL_LABEL, true));
				dropdownItem.setHref("#");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "manual-collection"));
			}
		).addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "addAssetListEntry");
				dropdownItem.putData(
					"addAssetListEntryURL",
					PortletURLBuilder.createActionURL(
						liferayPortletResponse
					).setActionName(
						"/asset_list/add_asset_list_entry"
					).setParameter(
						"type", AssetListEntryTypeConstants.TYPE_DYNAMIC
					).buildString());
				dropdownItem.putData(
					"title",
					LanguageUtil.format(
						httpServletRequest, "add-x-collection",
						AssetListEntryTypeConstants.TYPE_DYNAMIC_LABEL, true));
				dropdownItem.setHref("#");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "dynamic-collection"));
			}
		).build();
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "assetListEntries";
	}

	@Override
	public Boolean isShowCreationMenu() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (_isLiveGroup(themeDisplay)) {
			return false;
		}

		if (AssetListPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				AssetListActionKeys.ADD_ASSET_LIST_ENTRY)) {

			return true;
		}

		return false;
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"title", "create-date"};
	}

	private boolean _isLiveGroup(ThemeDisplay themeDisplay) {
		Group group = themeDisplay.getScopeGroup();

		if (group.isLayout()) {
			group = group.getParentGroup();
		}

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		if (stagingGroupHelper.isLiveGroup(group) &&
			stagingGroupHelper.isStagedPortlet(
				group, AssetListPortletKeys.ASSET_LIST)) {

			return true;
		}

		return false;
	}

}