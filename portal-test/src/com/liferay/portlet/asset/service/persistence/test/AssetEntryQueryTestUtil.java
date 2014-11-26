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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;

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
			long groupId, String className, long[] notAllCategories,
			long[] notAnyCategories, long[] allCategories, long[] anyCategories)
		throws Exception {

		return createAssetEntryQuery(
			new long[] {groupId}, className, notAllCategories, notAnyCategories,
			allCategories, anyCategories);
	}

	public static AssetEntryQuery createAssetEntryQuery(
			long groupId, String className, String[] notAllTags,
			String[] notAnyTags, String[] allTags, String[] anyTags)
		throws Exception {

		return createAssetEntryQuery(
			new long[] {groupId}, className, notAllTags, notAnyTags, allTags,
			anyTags);
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
			long[] notAllCategories, long[] notAnyCategories,
			long[] allCategories, long[] anyCategories, String[] notAllTags,
			String[] notAnyTags, String[] allTags, String[] anyTags)
		throws Exception {

		return createAssetEntryQuery(
			new long[] {groupId}, classNames, classTypeIds, notAllCategories,
			notAnyCategories, allCategories, anyCategories, notAllTags,
			notAnyTags, allTags, anyTags);
	}

	public static AssetEntryQuery createAssetEntryQuery(
			long[] groupIds, long[] classNameIds, long[] classTypeIds,
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
			for (String assetTagName : notAllTags) {
				long[] notAllAssetTagIds = AssetTagLocalServiceUtil.getTagIds(
					groupIds, assetTagName);

				assetEntryQuery.addNotAllTagIdsArray(notAllAssetTagIds);
			}
		}

		if (ArrayUtil.isNotEmpty(notAnyTags)) {
			assetEntryQuery.setNotAnyTagIds(
				getAssetTagsIds(groupIds, notAnyTags));
		}

		if (ArrayUtil.isNotEmpty(anyTags)) {
			assetEntryQuery.setAnyTagIds(getAssetTagsIds(groupIds, anyTags));
		}

		if (ArrayUtil.isNotEmpty(allTags)) {
			for (String assetTagName : allTags) {
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
			long[] groupIds, String className, long[] notAllCategories,
			long[] notAnyCategories, long[] allCategories, long[] anyCategories)
		throws Exception {

		return createAssetEntryQuery(
			groupIds, new String[] {className}, null, notAllCategories,
			notAnyCategories, allCategories, anyCategories, null, null, null,
			null);
	}

	public static AssetEntryQuery createAssetEntryQuery(
			long[] groupIds, String className, String[] notAllTags,
			String[] notAnyTags, String[] allTags, String[] anyTags)
		throws Exception {

		return createAssetEntryQuery(
			groupIds, new String[] {className}, null, null, null, null, null,
			notAllTags, notAnyTags, allTags, anyTags);
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
			long[] notAllCategories, long[] notAnyCategories,
			long[] allCategories, long[] anyCategories, String[] notAllTags,
			String[] notAnyTags, String[] allTags, String[] anyTags)
		throws Exception {

		long[] classNameIds = new long[classNames.length];

		for (int i = 0; i < classNames.length; i++) {
			classNameIds[i] = PortalUtil.getClassNameId(classNames[i]);
		}

		return createAssetEntryQuery(
			groupIds, classNameIds, classTypeIds, notAllCategories,
			notAnyCategories, allCategories, anyCategories, notAllTags,
			notAnyTags, allTags, anyTags);
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