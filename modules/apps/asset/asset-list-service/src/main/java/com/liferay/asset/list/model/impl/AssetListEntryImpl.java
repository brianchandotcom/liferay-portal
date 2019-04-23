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
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.service.AssetListEntryAssetEntryRelLocalServiceUtil;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalServiceUtil;
import com.liferay.asset.util.AssetHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.segments.constants.SegmentsConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Pavel Savinov
 */
@ProviderType
public class AssetListEntryImpl extends AssetListEntryBaseImpl {

	@Override
	public List<AssetEntry> getAssetEntries(long segmentsEntryId) {
		return getAssetEntries(
			segmentsEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<AssetEntry> getAssetEntries(
		long segmentsEntryId, int start, int end) {

		if (Objects.equals(
				getType(), AssetListEntryTypeConstants.TYPE_MANUAL)) {

			return _getManualAssetEntries(segmentsEntryId, start, end);
		}

		return _getDynamicAssetEntries(segmentsEntryId, start, end);
	}

	@Override
	public List<AssetEntry> getAssetEntries(long[] segmentsEntryIds) {
		return getAssetEntries(_getFirstSegmentsEntryId(segmentsEntryIds));
	}

	@Override
	public List<AssetEntry> getAssetEntries(
		long[] segmentsEntryIds, int start, int end) {

		return getAssetEntries(
			_getFirstSegmentsEntryId(segmentsEntryIds), start, end);
	}

	@Override
	public int getAssetEntriesCount(long segmentsEntryId) {
		if (Objects.equals(
				getType(), AssetListEntryTypeConstants.TYPE_MANUAL)) {

			return AssetListEntryAssetEntryRelLocalServiceUtil.
				getAssetListEntryAssetEntryRelsCount(
					getAssetListEntryId(), segmentsEntryId);
		}

		return AssetEntryLocalServiceUtil.getEntriesCount(
			getAssetEntryQuery(segmentsEntryId));
	}

	@Override
	public int getAssetEntriesCount(long[] segmentsEntryIds) {
		return getAssetEntriesCount(_getFirstSegmentsEntryId(segmentsEntryIds));
	}

	@Override
	public AssetEntryQuery getAssetEntryQuery(long segmentsEntryId) {
		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		UnicodeProperties properties = new UnicodeProperties(true);

		properties.fastLoad(getTypeSettings(segmentsEntryId));

		_setCategoriesAndTags(
			assetEntryQuery, properties, _getAssetCategoryIds(properties),
			_getAssetTagNames(properties));

		long[] groupIds = GetterUtil.getLongValues(
			StringUtil.split(
				properties.getProperty("groupIds", StringPool.BLANK)));

		if (ArrayUtil.isEmpty(groupIds)) {
			groupIds = new long[] {getGroupId()};
		}

		assetEntryQuery.setGroupIds(groupIds);

		boolean anyAssetType = GetterUtil.getBoolean(
			properties.getProperty("anyAssetType", null), true);

		long[] availableClassNameIds =
			AssetRendererFactoryRegistryUtil.getClassNameIds(getCompanyId());

		if (!anyAssetType) {
			long[] classNameIds = _getClassNameIds(
				properties, availableClassNameIds);

			assetEntryQuery.setClassNameIds(classNameIds);

			long[] classTypeIds = {};

			for (long classNameId : classNameIds) {
				String className = PortalUtil.getClassName(classNameId);

				classTypeIds = ArrayUtil.append(
					classTypeIds, _getClassTypeIds(properties, className));
			}

			assetEntryQuery.setClassTypeIds(classTypeIds);
		}
		else {
			assetEntryQuery.setClassNameIds(availableClassNameIds);
		}

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

	@Override
	public AssetEntryQuery getAssetEntryQuery(long[] segmentsEntryIds) {
		return getAssetEntryQuery(_getFirstSegmentsEntryId(segmentsEntryIds));
	}

	@Override
	public String getTypeLabel() {
		return AssetListEntryTypeConstants.getTypeLabel(getType());
	}

	@Override
	public String getTypeSettings(long segmentsEntryId) {
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			AssetListEntrySegmentsEntryRelLocalServiceUtil.
				fetchAssetListEntrySegmentsEntryRel(
					getAssetListEntryId(), segmentsEntryId);

		if (assetListEntrySegmentsEntryRel != null) {
			return assetListEntrySegmentsEntryRel.getTypeSettings();
		}

		return null;
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

	private long[] _getClassTypeIds(
		UnicodeProperties properties, String className) {

		long[] availableClassTypeIds = null;

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if (assetRendererFactory != null) {
			ClassTypeReader classTypeReader =
				assetRendererFactory.getClassTypeReader();

			List<ClassType> classTypes = classTypeReader.getAvailableClassTypes(
				new long[] {getGroupId()}, LocaleUtil.getDefault());

			Stream<ClassType> stream = classTypes.stream();

			availableClassTypeIds = stream.mapToLong(
				classType -> classType.getClassTypeId()
			).toArray();
		}

		boolean anyAssetType = GetterUtil.getBoolean(
			properties.getProperty(
				"anyClassType" + className, Boolean.TRUE.toString()));

		if (anyAssetType) {
			return availableClassTypeIds;
		}

		long anyClassTypeId = GetterUtil.getLong(
			properties.getProperty("anyClassType" + className, null), -1);

		if (anyClassTypeId > -1) {
			return new long[] {anyClassTypeId};
		}

		long[] classTypeIds = StringUtil.split(
			properties.getProperty("classTypeIds" + className, null), 0L);

		if (classTypeIds != null) {
			return classTypeIds;
		}

		return availableClassTypeIds;
	}

	private List<AssetEntry> _getDynamicAssetEntries(
		long segmentsEntryId, int start, int end) {

		AssetEntryQuery assetEntryQuery = getAssetEntryQuery(segmentsEntryId);

		assetEntryQuery.setEnd(end);
		assetEntryQuery.setStart(start);

		return _search(getCompanyId(), assetEntryQuery);
	}

	private long _getFirstSegmentsEntryId(long[] segmentsEntryIds) {
		LongStream stream = Arrays.stream(segmentsEntryIds);

		return stream.filter(
			segmentsEntryId -> {
				AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
					AssetListEntrySegmentsEntryRelLocalServiceUtil.
						fetchAssetListEntrySegmentsEntryRel(
							getAssetListEntryId(), segmentsEntryId);

				return assetListEntrySegmentsEntryRel != null;
			}
		).findFirst(
		).orElse(
			SegmentsConstants.SEGMENTS_ENTRY_ID_DEFAULT
		);
	}

	private List<AssetEntry> _getManualAssetEntries(
		long segmentsEntryId, int start, int end) {

		List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
			AssetListEntryAssetEntryRelLocalServiceUtil.
				getAssetListEntryAssetEntryRels(
					getAssetListEntryId(), segmentsEntryId, start, end);

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

	private List<AssetEntry> _search(
		long companyId, AssetEntryQuery assetEntryQuery) {

		SearchContext searchContext = new SearchContext();

		searchContext.setClassTypeIds(assetEntryQuery.getClassTypeIds());
		searchContext.setCompanyId(companyId);
		searchContext.setEnd(assetEntryQuery.getEnd());
		searchContext.setStart(assetEntryQuery.getStart());

		AssetHelper assetHelper = _serviceTracker.getService();

		try {
			Hits hits = assetHelper.search(
				searchContext, assetEntryQuery, assetEntryQuery.getStart(),
				assetEntryQuery.getEnd());

			return assetHelper.getAssetEntries(hits);
		}
		catch (Exception e) {
			_log.error("Unable to get asset entries", e);
		}

		return Collections.emptyList();
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

	private static final Log _log = LogFactoryUtil.getLog(
		AssetListEntryImpl.class);

	private static final ServiceTracker<AssetHelper, AssetHelper>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AssetHelper.class);

		ServiceTracker<AssetHelper, AssetHelper> serviceTracker =
			new ServiceTracker<>(
				bundle.getBundleContext(), AssetHelper.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}