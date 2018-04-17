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

package com.liferay.meris.asset.category.demo.internal;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.meris.MerisSegment;

import java.util.Locale;

/**
 * @author Eduardo Garcia
 */
public class AssetCategoryMerisSegment
	implements MerisSegment, Comparable<AssetCategoryMerisSegment> {

	public AssetCategoryMerisSegment(AssetCategory assetCategory) {
		_assetCategory = assetCategory;
	}

	@Override
	public int compareTo(AssetCategoryMerisSegment assetCategoryMerisSegment) {
		String merisSegmentId = getMerisSegmentId();

		return merisSegmentId.compareTo(
			assetCategoryMerisSegment.getMerisSegmentId());
	}

	public long getAssetCategoryId() {
		return _assetCategory.getCategoryId();
	}

	@Override
	public String getDescription(Locale locale) {
		return _assetCategory.getDescription(locale);
	}

	@Override
	public String getMerisSegmentId() {
		return String.valueOf(_assetCategory.getCategoryId());
	}

	@Override
	public String getName(Locale locale) {
		return _assetCategory.getTitle(locale);
	}

	@Override
	public String getScopeId() {
		return String.valueOf(_assetCategory.getGroupId());
	}

	private final AssetCategory _assetCategory;

}