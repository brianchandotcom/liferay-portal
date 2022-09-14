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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SelectCollectionManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public SelectCollectionManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SelectLayoutCollectionDisplayContext
			selectLayoutCollectionDisplayContext) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			selectLayoutCollectionDisplayContext.
				getCollectionsSearchContainer());

		_selectLayoutCollectionDisplayContext =
			selectLayoutCollectionDisplayContext;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			_selectLayoutCollectionDisplayContext.getPortletURL()
		).setKeywords(
			StringPool.BLANK
		).buildString();
	}

	@Override
	public String getComponentId() {
		return "selectCollectionManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			_getAddAssetListEntryDropdownItemUnsafeConsumer(
				AssetListEntryTypeConstants.TYPE_MANUAL_LABEL,
				"manual-collection", AssetListEntryTypeConstants.TYPE_MANUAL)
		).addPrimaryDropdownItem(
			_getAddAssetListEntryDropdownItemUnsafeConsumer(
				AssetListEntryTypeConstants.TYPE_DYNAMIC_LABEL,
				"dynamic-collection", AssetListEntryTypeConstants.TYPE_DYNAMIC)
		).build();
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL =
			_selectLayoutCollectionDisplayContext.getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "entries";
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	public Boolean isShowCreationMenu() {
		return true;
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"title", "create-date"};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getAddAssetListEntryDropdownItemUnsafeConsumer(
			String label, String title, int type) {

		return dropdownItem -> {
			dropdownItem.putData("action", "addAssetListEntry");

			dropdownItem.putData(
				"addAssetListEntryURL",
				PortletURLBuilder.create(
					PortalUtil.getControlPanelPortletURL(
						liferayPortletRequest, AssetListPortletKeys.ASSET_LIST,
						PortletRequest.ACTION_PHASE)
				).setActionName(
					"/asset_list/add_asset_list_entry"
				).setBackURL(
					_themeDisplay.getURLCurrent()
				).setParameter(
					"type", type
				).buildString());

			dropdownItem.putData(
				"namespace",
				PortalUtil.getPortletNamespace(
					AssetListPortletKeys.ASSET_LIST));
			dropdownItem.putData(
				"title",
				LanguageUtil.format(
					httpServletRequest, "add-x-collection", label, true));
			dropdownItem.setHref("#");
			dropdownItem.setLabel(LanguageUtil.get(httpServletRequest, title));
		};
	}

	private final SelectLayoutCollectionDisplayContext
		_selectLayoutCollectionDisplayContext;
	private final ThemeDisplay _themeDisplay;

}