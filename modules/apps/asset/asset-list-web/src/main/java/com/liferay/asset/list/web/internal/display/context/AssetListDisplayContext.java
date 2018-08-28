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

import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class AssetListDisplayContext {

	public AssetListDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_request = PortalUtil.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getAddAssetListEntryDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref(
							_addAssetListEntryURL(
								AssetListEntryTypeConstants.TYPE_MANUAL));
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "manual-selection"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setHref(
							_addAssetListEntryURL(
								AssetListEntryTypeConstants.TYPE_DYNAMIC));
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "dynamic-selection"));
					});
			}
		};
	}

	public int getAssetListEntriesCount() {
		if (_assetListEntriesCount != null) {
			return _assetListEntriesCount;
		}

		int assetListEntriesCount =
			AssetListEntryServiceUtil.getAssetListEntriesCount(
				_themeDisplay.getScopeGroupId());

		_assetListEntriesCount = assetListEntriesCount;

		return _assetListEntriesCount;
	}

	public SearchContainer getAssetListEntriesSearchContainer() {
		if (_assetListEntriesSearchContainer != null) {
			return _assetListEntriesSearchContainer;
		}

		SearchContainer assetListEntriesSearchContainer = new SearchContainer(
			_renderRequest, _renderResponse.createRenderURL(), null,
			"there-are-no-asset-lists");

		assetListEntriesSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		List<AssetListEntry> assetListEntries =
			AssetListEntryServiceUtil.getAssetListEntries(
				_themeDisplay.getScopeGroupId(),
				assetListEntriesSearchContainer.getStart(),
				assetListEntriesSearchContainer.getEnd(), null);

		assetListEntriesSearchContainer.setResults(assetListEntries);

		assetListEntriesSearchContainer.setTotal(getAssetListEntriesCount());

		_assetListEntriesSearchContainer = assetListEntriesSearchContainer;

		return _assetListEntriesSearchContainer;
	}

	public AssetListEntry getAssetListEntry() {
		if (_assetListEntry != null) {
			return _assetListEntry;
		}

		long assetListEntryId = getAssetListEntryId();

		_assetListEntry = AssetListEntryServiceUtil.fetchAssetListEntry(
			assetListEntryId);

		return _assetListEntry;
	}

	public List<DropdownItem> getAssetListEntryActionItemsDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData(
							"action", "deleteSelectedAssetListEntries");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public long getAssetListEntryId() {
		if (_assetListEntryId != null) {
			return _assetListEntryId;
		}

		_assetListEntryId = ParamUtil.getLong(_request, "assetListEntryId");

		return _assetListEntryId;
	}

	public String getAssetListEntryTitle() {
		String title = StringPool.BLANK;

		int assetListEntryType = getAssetListEntryType();

		AssetListEntry assetListEntry = getAssetListEntry();

		if (assetListEntry != null) {
			title = HtmlUtil.escape(assetListEntry.getTitle());
		}
		else if (assetListEntryType ==
					 AssetListEntryTypeConstants.TYPE_DYNAMIC) {

			title = "new-dynamic-asset-list";
		}
		else if (assetListEntryType ==
					 AssetListEntryTypeConstants.TYPE_MANUAL) {

			title = "new-manual-asset-list";
		}

		return LanguageUtil.get(_request, title);
	}

	public int getAssetListEntryType() {
		if (_assetListEntryType != null) {
			return _assetListEntryType;
		}

		AssetListEntry assetListEntry = getAssetListEntry();

		int assetListEntryType = ParamUtil.getInteger(
			_request, "assetListEntryType");

		if (assetListEntry != null) {
			assetListEntryType = assetListEntry.getType();
		}

		_assetListEntryType = assetListEntryType;

		return _assetListEntryType;
	}

	public CreationMenu getCreationMenu() {
		return new CreationMenu() {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							_addAssetListEntryURL(
								AssetListEntryTypeConstants.TYPE_MANUAL));
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "manual-selection"));
					});

				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							_addAssetListEntryURL(
								AssetListEntryTypeConstants.TYPE_DYNAMIC));
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "dynamic-selection"));
					});
			}
		};
	}

	private String _addAssetListEntryURL(int type) {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL addAssetListEntry = _renderResponse.createRenderURL();

		addAssetListEntry.setParameter("mvcPath", "/edit_asset_list_entry.jsp");
		addAssetListEntry.setParameter(
			"redirect", themeDisplay.getURLCurrent());
		addAssetListEntry.setParameter(
			"assetListEntryType", String.valueOf(type));

		return addAssetListEntry.toString();
	}

	private Integer _assetListEntriesCount;
	private SearchContainer _assetListEntriesSearchContainer;
	private AssetListEntry _assetListEntry;
	private Long _assetListEntryId;
	private Integer _assetListEntryType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;

}