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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

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

	public String getAssetListEntryType(int type) {
		String assetListEntryType = StringPool.BLANK;

		if (type == AssetListEntryTypeConstants.TYPE_DYNAMIC) {
			assetListEntryType = "dynamic";
		}
		else if (type == AssetListEntryTypeConstants.TYPE_MANUAL) {
			assetListEntryType = "manual";
		}

		return LanguageUtil.get(_request, assetListEntryType);
	}

	private Integer _assetListEntriesCount;
	private SearchContainer _assetListEntriesSearchContainer;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;

}