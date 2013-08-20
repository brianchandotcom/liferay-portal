/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;

/**
 * @author Eudaldo Alonso
 */
public class AssetEntryQueryTestUtil {

	public static AssetEntryQuery createAssetEntryQuery(
			long groupId, long[] classNameIds)
		throws Exception {

		return createAssetEntryQuery(
			groupId, classNameIds, null, null, null, null, null, null, null,
			null, null);
	}

	public static AssetEntryQuery createAssetEntryQuery(
			long groupId, long[] classNameIds, long[] classTypeIds,
			long[] notAllCategories, long[] notAnyCategories,
			long[] allCategories, long[] anyCategories, String[] notAllTags,
			String[] notAnyTags, String[] allTags, String[] anyTags)
		throws Exception {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		// Class name IDs

		assetEntryQuery.setClassNameIds(classNameIds);

		// Class type IDs

		if (Validator.isNotNull(classTypeIds)) {
			assetEntryQuery.setClassTypeIds(classTypeIds);
		}

		// Categories

		if (Validator.isNotNull(notAllCategories)) {
			assetEntryQuery.setNotAllCategoryIds(notAllCategories);
		}

		if (Validator.isNotNull(notAnyCategories)) {
			assetEntryQuery.setNotAnyCategoryIds(notAnyCategories);
		}

		if (Validator.isNotNull(anyCategories)) {
			assetEntryQuery.setAnyCategoryIds(anyCategories);
		}

		if (Validator.isNotNull(allCategories)) {
			assetEntryQuery.setAllCategoryIds(allCategories);
		}

		// Tags

		if (ArrayUtil.isNotEmpty(notAllTags)) {
			assetEntryQuery.setNotAllTagIds(
				getAssetTagsIds(groupId, notAllTags));
		}

		if (ArrayUtil.isNotEmpty(notAnyTags)) {
			assetEntryQuery.setNotAnyTagIds(
				getAssetTagsIds(groupId, notAnyTags));
		}

		if (ArrayUtil.isNotEmpty(anyTags)) {
			assetEntryQuery.setAnyTagIds(getAssetTagsIds(groupId, anyTags));
		}

		if (ArrayUtil.isNotEmpty(allTags)) {
			assetEntryQuery.setAllTagIds(getAssetTagsIds(groupId, allTags));
		}

		// Group IDs

		assetEntryQuery.setGroupIds(new long[] {groupId});

		return assetEntryQuery;
	}

	public static AssetEntryQuery createAssetEntryQuery(
			long groupId, String className, long[] notAllCategories,
			long[] notAnyCategories, long[] allCategories, long[] anyCategories)
		throws Exception {

		return createAssetEntryQuery(
			groupId, new String[] {className}, null, notAllCategories,
			notAnyCategories, allCategories, anyCategories, null, null, null,
			null);
	}

	public static AssetEntryQuery createAssetEntryQuery(
			long groupId, String className, String[] notAllTags,
			String[] notAnyTags, String[] allTags, String[] anyTags)
		throws Exception {

		return createAssetEntryQuery(
			groupId, new String[] {className}, null, null, null, null, null,
			notAllTags, notAnyTags, allTags, anyTags);
	}

	public static AssetEntryQuery createAssetEntryQuery(
			long groupId, String[] classNames)
		throws Exception {

		return createAssetEntryQuery(
			groupId, classNames, null, null, null, null, null, null, null, null,
			null);
	}

	public static AssetEntryQuery createAssetEntryQuery(
			long groupId, String[] classNames, long[] classTypeIds)
		throws Exception {

		return createAssetEntryQuery(
			groupId, classNames, classTypeIds, null, null, null, null, null,
			null, null, null);
	}

	public static AssetEntryQuery createAssetEntryQuery(
			long groupId, String[] classNames, long[] classTypeIds,
			long[] notAllCategories, long[] notAnyCategories,
			long[] allCategories, long[] anyCategories, String[] notAllTags,
			String[] notAnyTags, String[] allTags, String[] anyTags)
		throws Exception {

		long[] classNameIds = new long[classNames.length];

		for (int i = 0; i < classNames.length; i++) {
			classNameIds[i] = PortalUtil.getClassNameId(classNames[i]);
		}

		return createAssetEntryQuery(
			groupId, classNameIds, classTypeIds, notAllCategories,
			notAnyCategories, allCategories, anyCategories, notAllTags,
			notAnyTags, allTags, anyTags);
	}

	protected static long[] getAssetTagsIds(
			long groupId, String[] assetTagNames)
		throws Exception {

		if (ArrayUtil.isEmpty(assetTagNames)) {
			return new long[0];
		}

		return AssetTagLocalServiceUtil.getTagIds(groupId, assetTagNames);
	}

}