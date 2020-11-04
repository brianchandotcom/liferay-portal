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

package com.liferay.layout.page.template.admin.web.internal.display.context;

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rubén Pulido
 */
public class AssetDisplayPageUsagesManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public AssetDisplayPageUsagesManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<AssetDisplayPageEntry> searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteAssetDisplayPageEntry");
				dropdownItem.putData(
					"deleteAssetDisplayPageEntryURL",
					_getPortletURL(
						"/layout_page_template/delete_asset_display_page_" +
							"entry"));
				dropdownItem.setLabel(
					LanguageUtil.get(
						httpServletRequest, "set-to-default-display-page"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.putData("action", "updateAssetDisplayPageEntry");
				dropdownItem.putData(
					"updateAssetDisplayPageEntryURL",
					_getPortletURL(
						"/layout_page_template/update_asset_display_page_" +
							"entry"));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "unassign"));
			}
		).build();
	}

	@Override
	public String getComponentId() {
		return "assetDisplayPageUsagesManagementToolbar";
	}

	@Override
	public String getDefaultEventHandler() {
		return "assetDisplayPageUsagesManagementToolbarDefaultEventHandler";
	}

	@Override
	public String getSearchContainerId() {
		return "assetDisplayPageEntries";
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"modified-date"};
	}

	private String _getPortletURL(String actionName) {
		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter(ActionRequest.ACTION_NAME, actionName);
		portletURL.setParameter("redirect", _themeDisplay.getURLCurrent());

		return portletURL.toString();
	}

	private final ThemeDisplay _themeDisplay;

}