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

package com.liferay.asset.list.web.internal.servlet.taglib.util;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.web.internal.security.permission.resource.AssetListEntryPermission;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.List;
import java.util.function.Consumer;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetEntryListActionDropdownItems {

	public AssetEntryListActionDropdownItems(
		AssetListEntry assetListEntry,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_assetListEntry = assetListEntry;
		_liferayPortletResponse = liferayPortletResponse;

		_request = PortalUtil.getHttpServletRequest(liferayPortletRequest);
		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		return new DropdownItemList() {
			{
				if (AssetListEntryPermission.contains(
						_themeDisplay.getPermissionChecker(), _assetListEntry,
						ActionKeys.UPDATE)) {

					add(_getEditAssetListEntryActionConsumer());
					add(_getRenameAssetListEntryActionConsumer());
				}

				if (AssetListEntryPermission.contains(
						_themeDisplay.getPermissionChecker(), _assetListEntry,
						ActionKeys.PERMISSIONS)) {

					add(_getPermissionsAssetListEntryActionConsumer());
				}

				add(_getViewAssetListContentActionConsumer());
				add(_getViewAssetListEntryUsagesActionConsumer());

				if (AssetListEntryPermission.contains(
						_themeDisplay.getPermissionChecker(), _assetListEntry,
						ActionKeys.DELETE)) {

					add(_getDeleteAssetListEntryActionConsumer());
				}
			}
		};
	}

	private Consumer<DropdownItem> _getDeleteAssetListEntryActionConsumer() {
		PortletURL deleteAssetListEntryURL =
			_liferayPortletResponse.createActionURL();

		deleteAssetListEntryURL.setParameter(
			ActionRequest.ACTION_NAME, "/asset_list/delete_asset_list_entry");
		deleteAssetListEntryURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		deleteAssetListEntryURL.setParameter(
			"assetListEntryId",
			String.valueOf(_assetListEntry.getAssetListEntryId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteAssetListEntry");
			dropdownItem.putData(
				"deleteAssetListEntryURL", deleteAssetListEntryURL.toString());
			dropdownItem.setLabel(LanguageUtil.get(_request, "delete"));
		};
	}

	private Consumer<DropdownItem> _getEditAssetListEntryActionConsumer() {
		return dropdownItem -> {
			dropdownItem.setHref(
				_liferayPortletResponse.createRenderURL(), "mvcPath",
				"/edit_asset_list_entry.jsp", "redirect",
				_themeDisplay.getURLCurrent(), "assetListEntryId",
				_assetListEntry.getAssetListEntryId());
			dropdownItem.setLabel(LanguageUtil.get(_request, "edit"));
		};
	}

	private Consumer<DropdownItem> _getPermissionsAssetListEntryActionConsumer()
		throws Exception {

		String permissionsAssetEntryListURL = PermissionsURLTag.doTag(
			StringPool.BLANK, AssetListEntry.class.getName(),
			_assetListEntry.getTitle(), null,
			String.valueOf(_assetListEntry.getAssetListEntryId()),
			LiferayWindowState.POP_UP.toString(), null, _request);

		return dropdownItem -> {
			dropdownItem.putData("action", "permissionsAssetEntryList");
			dropdownItem.putData(
				"permissionsAssetEntryListURL", permissionsAssetEntryListURL);
			dropdownItem.setLabel(LanguageUtil.get(_request, "permissions"));
		};
	}

	private Consumer<DropdownItem> _getRenameAssetListEntryActionConsumer() {
		PortletURL renameAssetListEntryURL =
			_liferayPortletResponse.createActionURL();

		renameAssetListEntryURL.setParameter(
			ActionRequest.ACTION_NAME, "/asset_list/update_asset_list_entry");
		renameAssetListEntryURL.setParameter(
			"assetListEntryId",
			String.valueOf(_assetListEntry.getAssetListEntryId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "renameAssetListEntry");
			dropdownItem.putData(
				"assetListEntryId",
				String.valueOf(_assetListEntry.getAssetListEntryId()));
			dropdownItem.putData(
				"assetListEntryTitle", _assetListEntry.getTitle());
			dropdownItem.putData(
				"renameAssetListEntryURL", renameAssetListEntryURL.toString());
			dropdownItem.setLabel(LanguageUtil.get(_request, "rename"));
		};
	}

	private Consumer<DropdownItem> _getViewAssetListContentActionConsumer()
		throws Exception {

		PortletURL viewAssetListContentURL =
			_liferayPortletResponse.createRenderURL();

		viewAssetListContentURL.setParameter("mvcPath", "/view_content.jsp");
		viewAssetListContentURL.setParameter(
			"assetListEntryId",
			String.valueOf(_assetListEntry.getAssetListEntryId()));
		viewAssetListContentURL.setWindowState(LiferayWindowState.POP_UP);

		return dropdownItem -> {
			dropdownItem.putData("action", "viewAssetListContent");
			dropdownItem.putData(
				"viewAssetListContentURL", viewAssetListContentURL.toString());
			dropdownItem.setLabel(LanguageUtil.get(_request, "view-content"));
		};
	}

	private Consumer<DropdownItem>
		_getViewAssetListEntryUsagesActionConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_liferayPortletResponse.createRenderURL(), "mvcPath",
				"/view_asset_list_entry_usages.jsp", "redirect",
				_themeDisplay.getURLCurrent(), "assetListEntryId",
				_assetListEntry.getAssetListEntryId());
			dropdownItem.setLabel(LanguageUtil.get(_request, "view-usages"));
		};
	}

	private final AssetListEntry _assetListEntry;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;

}