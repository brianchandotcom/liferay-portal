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

package com.liferay.portlet.asset.service.persistence.test;

import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Eudaldo Alonso
 */
public class AssetEntryQueryTestUtil {

	public static AssetEntryQuery createAssetEntryQuery(
			long groupId, long[] classNameIds)
		throws Exception {

		return createAssetEntryQuery(
			new long[] {groupId}, classNameIds, null, null, null, null, null,
			null, null, null, null);
	}

	public static AssetEntryQuery createAssetEntryQuery(
			long groupId, String className, long[] notAllCategoryIds,
			long[] notAnyCategoryIds, long[] allCategoryIds,
			long[] anyCategoryIds)
		throws Exception {

		return createAssetEntryQuery(
			new long[] {groupId}, className, notAllCategoryIds,
			notAnyCategoryIds, allCategoryIds, anyCategoryIds);
	}

	public static AssetEntryQuery createAssetEntryQuery(
			long groupId, String className, String[] notAllTagNames,
			String[] notAnyTagNames, String[] allTagNames, String[] anyTagNames)
		throws Exception {

		return createAssetEntryQuery(
			new long[] {groupId}, className, notAllTagNames, notAnyTagNames,
			allTagNames, anyTagNames);
	}

	public static AssetEntryQuery createAssetEntryQuery(
			long groupId, String[] classNames)
		throws Exception {

		return createAssetEntryQuery(new long[] {groupId}, classNames);
	}

	public static AssetEntryQuery createAssetEntryQuery(
			long groupId, String[] classNames, long[] classTypeIds)
		throws Exception {

		return createAssetEntryQuery(
			new long[] {groupId}, classNames, classTypeIds);
	}

	public static AssetEntryQuery createAssetEntryQuery(
			long groupId, String[] classNames, long[] classTypeIds,
			long[] notAllCategoryIds, long[] notAnyCategoryIds,
			long[] allCategoryIds, long[] anyCategoryIds,
			String[] notAllTagNames, String[] notAnyTagNames,
			String[] allTagNames, String[] anyTagNames)
		throws Exception {

		return createAssetEntryQuery(
			new long[] {groupId}, classNames, classTypeIds, notAllCategoryIds,
			notAnyCategoryIds, allCategoryIds, anyCategoryIds, notAllTagNames,
			notAnyTagNames, allTagNames, anyTagNames);
	}

	public static AssetEntryQuery createAssetEntryQuery(
			long[] groupIds, long[] classNameIds, long[] classTypeIds,
			long[] notAllCategoryIds, long[] notAnyCategoryIds,
			long[] allCategoryIds, long[] anyCategoryIds,
			String[] notAllTagNames, String[] notAnyTagNames,
			String[] allTagNames, String[] anyTagNames)
		throws Exception {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		// Class name IDs

		assetEntryQuery.setClassNameIds(classNameIds);

		// Class type IDs

		if (classTypeIds != null) {
			assetEntryQuery.setClassTypeIds(classTypeIds);
		}

		// Categories

		if (notAllCategoryIds != null) {
			assetEntryQuery.setNotAllCategoryIds(notAllCategoryIds);
		}

		if (notAnyCategoryIds != null) {
			assetEntryQuery.setNotAnyCategoryIds(notAnyCategoryIds);
		}

		if (anyCategoryIds != null) {
			assetEntryQuery.setAnyCategoryIds(anyCategoryIds);
		}

		if (allCategoryIds != null) {
			assetEntryQuery.setAllCategoryIds(allCategoryIds);
		}

		// Tags

		if (ArrayUtil.isNotEmpty(notAllTagNames)) {
			for (String assetTagName : notAllTagNames) {
				long[] notAllAssetTagIds = AssetTagLocalServiceUtil.getTagIds(
					groupIds, assetTagName);

				assetEntryQuery.addNotAllTagIdsArray(notAllAssetTagIds);
			}
		}

		if (ArrayUtil.isNotEmpty(notAnyTagNames)) {
			assetEntryQuery.setNotAnyTagIds(
				getAssetTagsIds(groupIds, notAnyTagNames));
		}

		if (ArrayUtil.isNotEmpty(anyTagNames)) {
			assetEntryQuery.setAnyTagIds(
				getAssetTagsIds(groupIds, anyTagNames));
		}

		if (ArrayUtil.isNotEmpty(allTagNames)) {
			for (String assetTagName : allTagNames) {
				long[] allAssetTagIds = AssetTagLocalServiceUtil.getTagIds(
					groupIds, assetTagName);

				assetEntryQuery.addAllTagIdsArray(allAssetTagIds);
			}
		}

		// Group IDs

		assetEntryQuery.setGroupIds(groupIds);

		return assetEntryQuery;
	}

	public static AssetEntryQuery createAssetEntryQuery(
			long[] groupIds, String className, long[] notAllCategoryIds,
			long[] notAnyCategoryIds, long[] allCategoryIds,
			long[] anyCategoryIds)
		throws Exception {

		return createAssetEntryQuery(
			groupIds, new String[] {className}, null, notAllCategoryIds,
			notAnyCategoryIds, allCategoryIds, anyCategoryIds, null, null, null,
			null);
	}

	public static AssetEntryQuery createAssetEntryQuery(
			long[] groupIds, String className, String[] notAllTagNames,
			String[] notAnyTagNames, String[] allTagNames, String[] anyTagNames)
		throws Exception {

		return createAssetEntryQuery(
			groupIds, new String[] {className}, null, null, null, null, null,
			notAllTagNames, notAnyTagNames, allTagNames, anyTagNames);
	}

	public static AssetEntryQuery createAssetEntryQuery(
			long[] groupIds, String[] classNames)
		throws Exception {

		return createAssetEntryQuery(
			groupIds, classNames, null, null, null, null, null, null, null,
			null, null);
	}

	public static AssetEntryQuery createAssetEntryQuery(
			long[] groupIds, String[] classNames, long[] classTypeIds)
		throws Exception {

		return createAssetEntryQuery(
			groupIds, classNames, classTypeIds, null, null, null, null, null,
			null, null, null);
	}

	public static AssetEntryQuery createAssetEntryQuery(
			long[] groupIds, String[] classNames, long[] classTypeIds,
			long[] notAllCategoryIds, long[] notAnyCategoryIds,
			long[] allCategoryIds, long[] anyCategoryIds,
			String[] notAllTagNames, String[] notAnyTagNames,
			String[] allTagNames, String[] anyTagNames)
		throws Exception {

		long[] classNameIds = new long[classNames.length];

		for (int i = 0; i < classNames.length; i++) {
			classNameIds[i] = PortalUtil.getClassNameId(classNames[i]);
		}

		return createAssetEntryQuery(
			groupIds, classNameIds, classTypeIds, notAllCategoryIds,
			notAnyCategoryIds, allCategoryIds, anyCategoryIds, notAllTagNames,
			notAnyTagNames, allTagNames, anyTagNames);
	}

	protected static long[] getAssetTagsIds(
			long[] groupIds, String[] assetTagNames)
		throws Exception {

		if (ArrayUtil.isEmpty(assetTagNames)) {
			return new long[0];
		}

		return AssetTagLocalServiceUtil.getTagIds(groupIds, assetTagNames);
	}

}