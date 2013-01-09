/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.util;

import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;

/**
 * @author Sergio González
 */
public class AssetEntryTestUtil {

	public static AssetEntryQuery buildAssetEntryQueryCategories(
		AssetEntryQuery assetEntryQuery, long[] assetCategoryIds, boolean any,
		boolean not) {

		if (any && not) {
			assetEntryQuery.setNotAnyCategoryIds(assetCategoryIds);
		}
		else if (!any && not) {
			assetEntryQuery.setNotAllCategoryIds(assetCategoryIds);
		}
		else if (any && !not) {
			assetEntryQuery.setAnyCategoryIds(assetCategoryIds);
		}
		else {
			assetEntryQuery.setAllCategoryIds(assetCategoryIds);
		}

		return assetEntryQuery;
	}

	public static AssetEntryQuery buildAssetEntryQueryTags(
			AssetEntryQuery assetEntryQuery, long[] assetTagIds, boolean any,
		boolean not) {

		if (any && not) {
			assetEntryQuery.setNotAnyTagIds(assetTagIds);
		}
		else if (!any && not) {
			assetEntryQuery.setNotAllTagIds(assetTagIds);
		}
		else if (any && !not) {
			assetEntryQuery.setAnyTagIds(assetTagIds);
		}
		else {
			assetEntryQuery.setAllTagIds(assetTagIds);
		}

		return assetEntryQuery;
	}

}