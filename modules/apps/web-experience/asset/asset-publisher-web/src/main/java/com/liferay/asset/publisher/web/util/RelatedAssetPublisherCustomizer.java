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

package com.liferay.asset.publisher.web.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.kernel.util.AssetEntryQueryProcessor;
import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = AssetPublisherCustomizer.class)
public class RelatedAssetPublisherCustomizer
	extends DefaultAssetPublisherCustomizer {

	@Override
	public String getPortletId() {
		return AssetPublisherPortletKeys.RELATED_ASSETS;
	}

	@Override
	public boolean isEnablePermissions(HttpServletRequest request) {
		return true;
	}

	@Override
	public boolean isOrderingAndGroupingEnabled(HttpServletRequest request) {
		return true;
	}

	@Override
	public boolean isOrderingByTitleEnabled(HttpServletRequest request) {
		return false;
	}

	@Override
	public boolean isSelectionStyleEnabled(HttpServletRequest request) {
		return false;
	}

	@Override
	public boolean isShowAssetEntryQueryProcessor(
		AssetEntryQueryProcessor assetEntryQueryProcessor) {

		return true;
	}

	@Override
	public boolean isShowEnableAddContentButton(HttpServletRequest request) {
		return true;
	}

	@Override
	public boolean isShowEnableRelatedAssets(HttpServletRequest request) {
		return false;
	}

	@Override
	public boolean isShowScopeSelector(HttpServletRequest request) {
		return false;
	}

	@Override
	public boolean isShowSubtypeFieldsFilter(HttpServletRequest request) {
		return false;
	}

	@Override
	public void setAssetEntryQueryOptions(
		AssetEntryQuery assetEntryQuery, HttpServletRequest request) {

		AssetEntry layoutAssetEntry = (AssetEntry)request.getAttribute(
			WebKeys.LAYOUT_ASSET_ENTRY);

		if (layoutAssetEntry != null) {
			assetEntryQuery.setLinkedAssetEntryId(
				layoutAssetEntry.getEntryId());
		}
	}

}