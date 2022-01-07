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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.list.asset.entry.provider.AssetListAssetEntryProvider;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalServiceUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsEntryConstants;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class AssetListItemsDisplayContext {

	public AssetListItemsDisplayContext(
		AssetListAssetEntryProvider assetListAssetEntryProvider,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_assetListAssetEntryProvider = assetListAssetEntryProvider;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public SearchContainer<AssetEntry> getAssetListContentSearchContainer() {
		if (_assetListContentSearchContainer != null) {
			return _assetListContentSearchContainer;
		}

		SearchContainer<AssetEntry> searchContainer = new SearchContainer(
			_renderRequest, _getAssetListContentURL(), null,
			"there-are-no-asset-entries");

		searchContainer.setResultsAndTotal(
			() -> _assetListAssetEntryProvider.getAssetEntries(
				getAssetListEntry(), getSegmentsEntryId(),
				searchContainer.getStart(), searchContainer.getEnd()),
			_assetListAssetEntryProvider.getAssetEntriesCount(
				getAssetListEntry(), getSegmentsEntryId()));

		_assetListContentSearchContainer = searchContainer;

		return _assetListContentSearchContainer;
	}

	public AssetListEntry getAssetListEntry() {
		if (_assetListEntry != null) {
			return _assetListEntry;
		}

		_assetListEntry = AssetListEntryLocalServiceUtil.fetchAssetListEntry(
			getAssetListEntryId());

		return _assetListEntry;
	}

	public long getAssetListEntryId() {
		if (_assetListEntryId != null) {
			return _assetListEntryId;
		}

		long assetListEntryId = ParamUtil.getLong(
			_httpServletRequest, "assetListEntryId");

		if (assetListEntryId <= 0) {
			assetListEntryId = ParamUtil.getLong(
				_httpServletRequest, "collectionPK");
		}

		_assetListEntryId = assetListEntryId;

		return _assetListEntryId;
	}

	public long getSegmentsEntryId() {
		if (_segmentsEntryId != null) {
			return _segmentsEntryId;
		}

		_segmentsEntryId = ParamUtil.getLong(
			_httpServletRequest, "segmentsEntryId",
			SegmentsEntryConstants.ID_DEFAULT);

		return _segmentsEntryId;
	}

	public boolean isShowActions() {
		if (_showActions != null) {
			return _showActions;
		}

		_showActions = ParamUtil.get(_renderRequest, "showActions", false);

		return _showActions;
	}

	private PortletURL _getAssetListContentURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/view_asset_list_items.jsp"
		).setRedirect(
			_getRedirect()
		).setParameter(
			"assetListEntryId", getAssetListEntryId()
		).setParameter(
			"segmentsEntryId", getSegmentsEntryId()
		).buildPortletURL();
	}

	private String _getRedirect() {
		return ParamUtil.get(
			_renderRequest, "redirect", _themeDisplay.getURLCurrent());
	}

	private final AssetListAssetEntryProvider _assetListAssetEntryProvider;
	private SearchContainer<AssetEntry> _assetListContentSearchContainer;
	private AssetListEntry _assetListEntry;
	private Long _assetListEntryId;
	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private Long _segmentsEntryId;
	private Boolean _showActions;
	private final ThemeDisplay _themeDisplay;

}