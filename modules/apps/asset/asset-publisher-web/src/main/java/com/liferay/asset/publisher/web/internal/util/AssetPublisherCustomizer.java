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

package com.liferay.asset.publisher.web.internal.util;

import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.util.AssetEntryQueryProcessor;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public interface AssetPublisherCustomizer {

	public Integer getDelta(HttpServletRequest httpServletRequest);

	public String getPortletId();

	public boolean isEnablePermissions(HttpServletRequest httpServletRequest);

	public boolean isOrderingAndGroupingEnabled(
		HttpServletRequest httpServletRequest);

	public boolean isOrderingByTitleEnabled(
		HttpServletRequest httpServletRequest);

	public boolean isSelectionStyleEnabled(
		HttpServletRequest httpServletRequest);

	public boolean isShowAssetEntryQueryProcessor(
		AssetEntryQueryProcessor assetEntryQueryProcessor);

	public boolean isShowEnableAddContentButton(
		HttpServletRequest httpServletRequest);

	public boolean isShowEnableRelatedAssets(
		HttpServletRequest httpServletRequest);

	public boolean isShowScopeSelector(HttpServletRequest httpServletRequest);

	public boolean isShowSubtypeFieldsFilter(
		HttpServletRequest httpServletRequest);

	public void setAssetEntryQueryOptions(
		AssetEntryQuery assetEntryQuery, HttpServletRequest httpServletRequest);

}