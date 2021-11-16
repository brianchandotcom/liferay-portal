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

package com.liferay.asset.tags.navigation.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagServiceUtil;
import com.liferay.asset.util.comparator.AssetTagCountComparator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Vendel Toreki
 */
public class AssetTagsNavigationDisplayContext {

	public AssetTagsNavigationDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest) {

		_httpServletRequest = httpServletRequest;

		_renderRequest = renderRequest;

		_classNameId = PrefsParamUtil.getLong(
			_renderRequest.getPreferences(), _renderRequest, "classNameId");

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_scopeGroupId = _themeDisplay.getScopeGroupId();
	}

	public List<AssetTag> getAssetTags() {
		if (_assetTags != null) {
			return _assetTags;
		}

		int maxAssetTags = PrefsParamUtil.getInteger(
			_renderRequest.getPreferences(), _renderRequest, "maxAssetTags",
			10);
		boolean showAssetCount = PrefsParamUtil.getBoolean(
			_renderRequest.getPreferences(), _renderRequest, "showAssetCount");

		if (showAssetCount && (_classNameId > 0)) {
			_assetTags = AssetTagServiceUtil.getTags(
				PortalUtil.getSiteGroupId(_scopeGroupId), _classNameId, null, 0,
				maxAssetTags, new AssetTagCountComparator());
		}
		else {
			_assetTags = AssetTagServiceUtil.getGroupTags(
				PortalUtil.getSiteGroupId(_scopeGroupId), 0, maxAssetTags,
				new AssetTagCountComparator());
		}

		_assetTags = ListUtil.sort(_assetTags);

		return _assetTags;
	}

	public Map<String, Integer> getScopedAssetCounts() {
		if (_scopedAssetCounts != null) {
			return _scopedAssetCounts;
		}

		_scopedAssetCounts = new HashMap<>();

		for (AssetTag assetTag : getAssetTags()) {
			int count = 0;

			if (_classNameId > 0) {
				count = AssetTagServiceUtil.getVisibleAssetsTagsCount(
					_scopeGroupId, _classNameId, assetTag.getName());
			}
			else {
				count = AssetTagServiceUtil.getVisibleAssetsTagsCount(
					_scopeGroupId, assetTag.getName());
			}

			_scopedAssetCounts.put(assetTag.getName(), count);
		}

		return _scopedAssetCounts;
	}

	private List<AssetTag> _assetTags;
	private final long _classNameId;
	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private Map<String, Integer> _scopedAssetCounts;
	private final long _scopeGroupId;
	private final ThemeDisplay _themeDisplay;

}