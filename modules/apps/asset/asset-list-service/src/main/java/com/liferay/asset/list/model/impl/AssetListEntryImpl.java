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

package com.liferay.asset.list.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.asset.list.service.AssetListEntryAssetEntryRelLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Pavel Savinov
 */
@ProviderType
public class AssetListEntryImpl extends AssetListEntryBaseImpl {

	public AssetListEntryImpl() {
	}

	public List<AssetEntry> getAssetEntries() {
		return getAssetEntries(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<AssetEntry> getAssetEntries(int start, int end) {
		if (Objects.equals(
				getType(), AssetListEntryTypeConstants.TYPE_MANUAL)) {

			return _getManualAssetEntries(start, end);
		}

		return _getDynamicAssetEntries(start, end);
	}

	public int getAssetEntriesCount() {
		if (Objects.equals(
				getType(), AssetListEntryTypeConstants.TYPE_MANUAL)) {

			return AssetListEntryAssetEntryRelLocalServiceUtil.
				getAssetListEntryAssetEntryRelsCount(getAssetListEntryId());
		}

		return AssetEntryLocalServiceUtil.getEntriesCount(getAssetEntryQuery());
	}

	public AssetEntryQuery getAssetEntryQuery() {
		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		UnicodeProperties properties = new UnicodeProperties(true);

		properties.fastLoad(getTypeSettings());

		_setCategoriesAndTags(
			assetEntryQuery, properties, _getAssetCategoryIds(properties),
			_getAssetTagNames(properties));

		assetEntryQuery.setGroupIds(new long[] {getGroupId()});

		boolean anyAssetType = GetterUtil.getBoolean(
			properties.getProperty("anyAssetType", null), true);

		if (!anyAssetType) {
			long[] availableClassNameIds =
				AssetRendererFactoryRegistryUtil.getClassNameIds(
					getCompanyId());

			long[] classNameIds = _getClassNameIds(
				properties, availableClassNameIds);

			assetEntryQuery.setClassNameIds(classNameIds);
		}

		long[] classTypeIds = GetterUtil.getLongValues(
			StringUtil.split(properties.getProperty("classTypeIds", null)));

		assetEntryQuery.setClassTypeIds(classTypeIds);

		String orderByColumn1 = GetterUtil.getString(
			properties.getProperty("orderByColumn1", "modifiedDate"));

		assetEntryQuery.setOrderByCol1(orderByColumn1);

		String orderByColumn2 = GetterUtil.getString(
			properties.getProperty("orderByColumn2", "title"));

		assetEntryQuery.setOrderByCol2(orderByColumn2);

		String orderByType1 = GetterUtil.getString(
			properties.getProperty("orderByType1", "DESC"));

		assetEntryQuery.setOrderByType1(orderByType1);

		String orderByType2 = GetterUtil.getString(
			properties.getProperty("orderByType2", "ASC"));

		assetEntryQuery.setOrderByType2(orderByType2);

		return assetEntryQuery;
	}

	public String getTypeLabel() {
		return AssetListEntryTypeConstants.getTypeLabel(getType());
	}

	private static long[] _getAssetCategoryIds(UnicodeProperties properties) {
		long[] assetCategoryIds = new long[0];

		for (int i = 0; true; i++) {
			String[] queryValues = StringUtil.split(
				properties.getProperty("queryValues" + i, null));

			if (ArrayUtil.isEmpty(queryValues)) {
				break;
			}

			boolean queryContains = GetterUtil.getBoolean(
				properties.getProperty("queryContains" + i, StringPool.BLANK));
			boolean queryAndOperator = GetterUtil.getBoolean(
				properties.getProperty(
					"queryAndOperator" + i, StringPool.BLANK));
			String queryName = properties.getProperty(
				"queryName" + i, StringPool.BLANK);

			if (Objects.equals(queryName, "assetCategories") && queryContains &&
				(queryAndOperator || (queryValues.length == 1))) {

				assetCategoryIds = ArrayUtil.append(
					assetCategoryIds, GetterUtil.getLongValues(queryValues));
			}
		}

		return assetCategoryIds;
	}

	private static String[] _getAssetTagNames(UnicodeProperties properties) {
		String[] allAssetTagNames = new String[0];

		for (int i = 0; true; i++) {
			String[] queryValues = StringUtil.split(
				properties.getProperty("queryValues" + i, null));

			if (ArrayUtil.isEmpty(queryValues)) {
				break;
			}

			boolean queryContains = GetterUtil.getBoolean(
				properties.getProperty("queryContains" + i, StringPool.BLANK));
			boolean queryAndOperator = GetterUtil.getBoolean(
				properties.getProperty(
					"queryAndOperator" + i, StringPool.BLANK));
			String queryName = properties.getProperty(
				"queryName" + i, StringPool.BLANK);

			if (!Objects.equals(queryName, "assetCategories") &&
				queryContains &&
				(queryAndOperator || (queryValues.length == 1))) {

				allAssetTagNames = queryValues;
			}
		}

		return allAssetTagNames;
	}

	private long[] _filterAssetCategoryIds(long[] assetCategoryIds) {
		List<Long> assetCategoryIdsList = new ArrayList<>();

		for (long assetCategoryId : assetCategoryIds) {
			AssetCategory category =
				AssetCategoryLocalServiceUtil.fetchAssetCategory(
					assetCategoryId);

			if (category == null) {
				continue;
			}

			assetCategoryIdsList.add(assetCategoryId);
		}

		return ArrayUtil.toArray(
			assetCategoryIdsList.toArray(
				new Long[assetCategoryIdsList.size()]));
	}

	private long[] _getClassNameIds(
		UnicodeProperties properties, long[] availableClassNameIds) {

		boolean anyAssetType = GetterUtil.getBoolean(
			properties.getProperty("anyAssetType", Boolean.TRUE.toString()));

		if (anyAssetType) {
			return availableClassNameIds;
		}

		long defaultClassNameId = GetterUtil.getLong(
			properties.getProperty("anyAssetType", null));

		if (defaultClassNameId > 0) {
			return new long[] {defaultClassNameId};
		}

		long[] classNameIds = GetterUtil.getLongValues(
			StringUtil.split(properties.getProperty("classNameIds", null)));

		if (ArrayUtil.isNotEmpty(classNameIds)) {
			return classNameIds;
		}

		return availableClassNameIds;
	}

	private List<AssetEntry> _getDynamicAssetEntries(int start, int end) {
		AssetEntryQuery assetEntryQuery = getAssetEntryQuery();

		assetEntryQuery.setEnd(end);
		assetEntryQuery.setStart(start);

		return AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
	}

	private List<AssetEntry> _getManualAssetEntries(int start, int end) {
		List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
			AssetListEntryAssetEntryRelLocalServiceUtil.
				getAssetListEntryAssetEntryRels(
					getAssetListEntryId(), start, end);

		Stream<AssetListEntryAssetEntryRel> stream =
			assetListEntryAssetEntryRels.stream();

		return stream.map(
			assetListEntryAssetEntryRel ->
				AssetEntryLocalServiceUtil.fetchEntry(
					assetListEntryAssetEntryRel.getAssetEntryId())
		).collect(
			Collectors.toList()
		);
	}

	private void _setCategoriesAndTags(
		AssetEntryQuery assetEntryQuery, UnicodeProperties properties,
		long[] overrideAllAssetCategoryIds, String[] overrideAllAssetTagNames) {

		long[] allAssetCategoryIds = new long[0];
		long[] anyAssetCategoryIds = new long[0];
		long[] notAllAssetCategoryIds = new long[0];
		long[] notAnyAssetCategoryIds = new long[0];

		String[] allAssetTagNames = new String[0];
		String[] anyAssetTagNames = new String[0];
		String[] notAllAssetTagNames = new String[0];
		String[] notAnyAssetTagNames = new String[0];

		for (int i = 0; true; i++) {
			String[] queryValues = StringUtil.split(
				properties.getProperty("queryValues" + i, null));

			if (ArrayUtil.isEmpty(queryValues)) {
				break;
			}

			boolean queryContains = GetterUtil.getBoolean(
				properties.getProperty("queryContains" + i, StringPool.BLANK));
			boolean queryAndOperator = GetterUtil.getBoolean(
				properties.getProperty(
					"queryAndOperator" + i, StringPool.BLANK));
			String queryName = properties.getProperty(
				"queryName" + i, StringPool.BLANK);

			if (Objects.equals(queryName, "assetCategories")) {
				long[] assetCategoryIds = GetterUtil.getLongValues(queryValues);

				if (queryContains && queryAndOperator) {
					allAssetCategoryIds = assetCategoryIds;
				}
				else if (queryContains && !queryAndOperator) {
					anyAssetCategoryIds = assetCategoryIds;
				}
				else if (!queryContains && queryAndOperator) {
					notAllAssetCategoryIds = assetCategoryIds;
				}
				else {
					notAnyAssetCategoryIds = assetCategoryIds;
				}
			}
			else {
				if (queryContains && queryAndOperator) {
					allAssetTagNames = queryValues;
				}
				else if (queryContains && !queryAndOperator) {
					anyAssetTagNames = queryValues;
				}
				else if (!queryContains && queryAndOperator) {
					notAllAssetTagNames = queryValues;
				}
				else {
					notAnyAssetTagNames = queryValues;
				}
			}
		}

		if (overrideAllAssetCategoryIds != null) {
			allAssetCategoryIds = overrideAllAssetCategoryIds;
		}

		allAssetCategoryIds = _filterAssetCategoryIds(allAssetCategoryIds);

		assetEntryQuery.setAllCategoryIds(allAssetCategoryIds);

		if (overrideAllAssetTagNames != null) {
			allAssetTagNames = overrideAllAssetTagNames;
		}

		long siteGroupId = PortalUtil.getSiteGroupId(getGroupId());

		for (String assetTagName : allAssetTagNames) {
			long[] allAssetTagIds = AssetTagLocalServiceUtil.getTagIds(
				new long[] {siteGroupId}, assetTagName);

			assetEntryQuery.addAllTagIdsArray(allAssetTagIds);
		}

		assetEntryQuery.setAnyCategoryIds(anyAssetCategoryIds);

		long[] anyAssetTagIds = AssetTagLocalServiceUtil.getTagIds(
			siteGroupId, anyAssetTagNames);

		assetEntryQuery.setAnyTagIds(anyAssetTagIds);

		assetEntryQuery.setNotAllCategoryIds(notAllAssetCategoryIds);

		for (String assetTagName : notAllAssetTagNames) {
			long[] notAllAssetTagIds = AssetTagLocalServiceUtil.getTagIds(
				new long[] {siteGroupId}, assetTagName);

			assetEntryQuery.addNotAllTagIdsArray(notAllAssetTagIds);
		}

		assetEntryQuery.setNotAnyCategoryIds(notAnyAssetCategoryIds);

		long[] notAnyAssetTagIds = AssetTagLocalServiceUtil.getTagIds(
			siteGroupId, notAnyAssetTagNames);

		assetEntryQuery.setNotAnyTagIds(notAnyAssetTagIds);
	}

}