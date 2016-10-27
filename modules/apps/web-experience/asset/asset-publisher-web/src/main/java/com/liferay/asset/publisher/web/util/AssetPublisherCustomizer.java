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

import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.kernel.util.AssetEntryQueryProcessor;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public interface AssetPublisherCustomizer {

	public Integer getDelta(HttpServletRequest request);

	public String getPortletId();

	public boolean isEnablePermissions(HttpServletRequest request);

	public boolean isOrderingAndGroupingEnabled(HttpServletRequest request);

	public boolean isOrderingByTitleEnabled(HttpServletRequest request);

	public boolean isSelectionStyleEnabled(HttpServletRequest request);

	public boolean isShowAssetEntryQueryProcessor(
		AssetEntryQueryProcessor assetEntryQueryProcessor);

	public boolean isShowEnableAddContentButton(HttpServletRequest request);

	public boolean isShowEnableRelatedAssets(HttpServletRequest request);

	public boolean isShowScopeSelector(HttpServletRequest request);

	public boolean isShowSubtypeFieldsFilter(HttpServletRequest request);

	public void setAssetEntryQueryOptions(
		AssetEntryQuery assetEntryQuery, HttpServletRequest request);

}