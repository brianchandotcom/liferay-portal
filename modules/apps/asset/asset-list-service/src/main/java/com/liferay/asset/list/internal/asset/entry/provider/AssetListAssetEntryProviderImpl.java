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

package com.liferay.asset.list.internal.asset.entry.provider;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.list.asset.entry.provider.AssetListAssetEntryProvider;
import com.liferay.asset.list.asset.entry.query.processor.AssetListAssetEntryQueryProcessor;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.internal.configuration.AssetListConfiguration;
import com.liferay.asset.list.internal.dynamic.data.mapping.util.DDMIndexerUtil;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRelModel;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.service.AssetListEntryAssetEntryRelLocalService;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalService;
import com.liferay.asset.util.AssetHelper;
import com.liferay.asset.util.AssetRendererFactoryClassProvider;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsEntryConstants;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Sarai Díaz
 */
@Component(
	configurationPid = "com.liferay.asset.list.internal.configuration.AssetListConfiguration",
	immediate = true, service = AssetListAssetEntryProvider.class
)
public class AssetListAssetEntryProviderImpl
	implements AssetListAssetEntryProvider {

	@Override
	public List<AssetEntry> getAssetEntries(
		AssetListEntry assetListEntry, long segmentsEntryId) {

		return getAssetEntries(
			assetListEntry, segmentsEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	@Override
	public List<AssetEntry> getAssetEntries(
		AssetListEntry assetListEntry, long segmentsEntryId, int start,
		int end) {

		return getAssetEntries(
			assetListEntry, new long[] {segmentsEntryId}, start, end);
	}

	@Override
	public List<AssetEntry> getAssetEntries(
		AssetListEntry assetListEntry, long[] segmentsEntryIds) {

		return getAssetEntries(
			assetListEntry, segmentsEntryIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	@Override
	public List<AssetEntry> getAssetEntries(
		AssetListEntry assetListEntry, long[] segmentsEntryIds, int start,
		int end) {

		return getAssetEntries(
			assetListEntry, segmentsEntryIds, StringPool.BLANK, start, end);
	}

	@Override
	public List<AssetEntry> getAssetEntries(
		AssetListEntry assetListEntry, long[] segmentsEntryIds,
		long[][] assetCategoryIds, String userId, int start, int end) {

		return getAssetEntries(
			assetListEntry, segmentsEntryIds, assetCategoryIds,
			StringPool.BLANK, userId, start, end);
	}

	@Override
	public List<AssetEntry> getAssetEntries(
		AssetListEntry assetListEntry, long[] segmentsEntryIds,
		long[][] assetCategoryIds, String keywords, String userId, int start,
		int end) {

		if (Objects.equals(
				assetListEntry.getType(),
				AssetListEntryTypeConstants.TYPE_MANUAL)) {

			return _getManualAssetEntries(
				assetListEntry, segmentsEntryIds, assetCategoryIds, keywords,
				start, end);
		}

		return _getDynamicAssetEntries(
			assetListEntry, segmentsEntryIds, assetCategoryIds, keywords,
			userId, start, end);
	}

	@Override
	public List<AssetEntry> getAssetEntries(
		AssetListEntry assetListEntry, long[] segmentsEntryIds, String userId) {

		return getAssetEntries(
			assetListEntry, segmentsEntryIds, userId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	@Override
	public List<AssetEntry> getAssetEntries(
		AssetListEntry assetListEntry, long[] segmentsEntryIds, String userId,
		int start, int end) {

		return getAssetEntries(
			assetListEntry, segmentsEntryIds, new long[0][], userId, start,
			end);
	}

	@Override
	public int getAssetEntriesCount(
		AssetListEntry assetListEntry, long segmentsEntryId) {

		return getAssetEntriesCount(
			assetListEntry, new long[] {segmentsEntryId});
	}

	@Override
	public int getAssetEntriesCount(
		AssetListEntry assetListEntry, long[] segmentsEntryIds) {

		return getAssetEntriesCount(
			assetListEntry, segmentsEntryIds, StringPool.BLANK);
	}

	@Override
	public int getAssetEntriesCount(
		AssetListEntry assetListEntry, long[] segmentsEntryIds,
		long[][] assetCategoryIds, String userId) {

		return getAssetEntriesCount(
			assetListEntry, segmentsEntryIds, assetCategoryIds,
			StringPool.BLANK, userId);
	}

	@Override
	public int getAssetEntriesCount(
		AssetListEntry assetListEntry, long[] segmentsEntryIds,
		long[][] assetCategoryIds, String keywords, String userId) {

		if (Objects.equals(
				assetListEntry.getType(),
				AssetListEntryTypeConstants.TYPE_MANUAL)) {

			return _getManualAssetEntriesCount(
				assetListEntry, segmentsEntryIds, assetCategoryIds, keywords);
		}

		return _getDynamicAssetEntriesCount(
			assetListEntry, segmentsEntryIds, assetCategoryIds, keywords,
			userId);
	}

	@Override
	public int getAssetEntriesCount(
		AssetListEntry assetListEntry, long[] segmentsEntryIds, String userId) {

		return getAssetEntriesCount(
			assetListEntry, segmentsEntryIds, new long[0][], userId);
	}

	@Override
	public AssetEntryQuery getAssetEntryQuery(
		AssetListEntry assetListEntry, long segmentsEntryId) {

		return getAssetEntryQuery(
			assetListEntry, segmentsEntryId, StringPool.BLANK);
	}

	@Override
	public AssetEntryQuery getAssetEntryQuery(
		AssetListEntry assetListEntry, long[] segmentsEntryIds) {

		return getAssetEntryQuery(
			assetListEntry, segmentsEntryIds, StringPool.BLANK);
	}

	@Override
	public AssetEntryQuery getAssetEntryQuery(
		AssetListEntry assetListEntry, long[] segmentsEntryIds, String userId) {

		return getAssetEntryQuery(
			assetListEntry,
			_getFirstSegmentsEntryId(assetListEntry, segmentsEntryIds), userId);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties)
		throws ConfigurationException {

		_assetListConfiguration = ConfigurableUtil.createConfigurable(
			AssetListConfiguration.class, properties);
	}

	protected AssetEntryQuery getAssetEntryQuery(
		AssetListEntry assetListEntry, long segmentsEntryId, String userId) {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.create(
			true
		).fastLoad(
			assetListEntry.getTypeSettings(segmentsEntryId)
		).build();

		_setCategoriesAndTagsAndKeywords(
			assetListEntry, assetEntryQuery, unicodeProperties,
			_getAssetCategoryIds(unicodeProperties),
			_getAssetTagNames(unicodeProperties),
			_getKeywords(unicodeProperties));

		long[] groupIds = GetterUtil.getLongValues(
			StringUtil.split(
				unicodeProperties.getProperty("groupIds", StringPool.BLANK)));

		if (ArrayUtil.isEmpty(groupIds)) {
			groupIds = new long[] {assetListEntry.getGroupId()};
		}

		assetEntryQuery.setGroupIds(groupIds);

		boolean anyAssetType = GetterUtil.getBoolean(
			unicodeProperties.getProperty("anyAssetType", null), true);
		long[] availableClassNameIds =
			AssetRendererFactoryRegistryUtil.getClassNameIds(
				assetListEntry.getCompanyId());
		long[] classTypeIds = {};

		if (!anyAssetType) {
			long[] classNameIds = _getClassNameIds(
				unicodeProperties, availableClassNameIds);

			assetEntryQuery.setClassNameIds(classNameIds);

			for (long classNameId : classNameIds) {
				classTypeIds = ArrayUtil.append(
					classTypeIds,
					_getClassTypeIds(
						assetListEntry, unicodeProperties,
						_portal.getClassName(classNameId)));
			}

			assetEntryQuery.setClassTypeIds(classTypeIds);
		}
		else {
			assetEntryQuery.setClassNameIds(availableClassNameIds);
		}

		String ddmStructureFieldName = unicodeProperties.getProperty(
			"ddmStructureFieldName");

		String ddmStructureFieldValue = unicodeProperties.getProperty(
			"ddmStructureFieldValue");

		if (Validator.isNotNull(ddmStructureFieldName) &&
			Validator.isNotNull(ddmStructureFieldValue) &&
			(classTypeIds.length == 1)) {

			DLFileEntryType dlFileEntryType =
				_dlFileEntryTypeLocalService.fetchFileEntryType(
					classTypeIds[0]);

			if (dlFileEntryType != null) {
				List<DDMStructure> ddmStructures =
					dlFileEntryType.getDDMStructures();

				if (!ddmStructures.isEmpty()) {
					DDMStructure ddmStructure = ddmStructures.get(0);

					assetEntryQuery.setAttribute(
						"ddmStructureFieldName",
						DDMIndexerUtil.encodeName(
							ddmStructure.getStructureId(),
							_getFieldReference(
								ddmStructure, ddmStructureFieldName),
							LocaleUtil.getMostRelevantLocale()));

					assetEntryQuery.setAttribute(
						"ddmStructureFieldValue", ddmStructureFieldValue);
				}
			}
		}

		String orderByColumn1 = GetterUtil.getString(
			unicodeProperties.getProperty("orderByColumn1", "modifiedDate"));

		assetEntryQuery.setOrderByCol1(orderByColumn1);

		String orderByColumn2 = GetterUtil.getString(
			unicodeProperties.getProperty("orderByColumn2", "title"));

		assetEntryQuery.setOrderByCol2(orderByColumn2);

		String orderByType1 = GetterUtil.getString(
			unicodeProperties.getProperty("orderByType1", "DESC"));

		assetEntryQuery.setOrderByType1(orderByType1);

		String orderByType2 = GetterUtil.getString(
			unicodeProperties.getProperty("orderByType2", "ASC"));

		assetEntryQuery.setOrderByType2(orderByType2);

		_processAssetEntryQuery(
			assetListEntry.getCompanyId(), userId, unicodeProperties,
			assetEntryQuery);

		return assetEntryQuery;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setAssetListAssetEntryQueryProcessor(
		AssetListAssetEntryQueryProcessor assetListAssetEntryQueryProcessor) {

		_assetListAssetEntryQueryProcessors.add(
			assetListAssetEntryQueryProcessor);
	}

	protected void unsetAssetListAssetEntryQueryProcessor(
		AssetListAssetEntryQueryProcessor assetListAssetEntryQueryProcessor) {

		_assetListAssetEntryQueryProcessors.remove(
			assetListAssetEntryQueryProcessor);
	}

	private List<AssetEntry> _dynamicSearch(
		long companyId, long[][] assetCategoryIds,
		AssetEntryQuery assetEntryQuery, String keywords) {

		try {
			Hits hits = _assetHelper.search(
				_getDynamicSearchContext(
					companyId, assetCategoryIds, assetEntryQuery, keywords),
				assetEntryQuery, assetEntryQuery.getStart(),
				assetEntryQuery.getEnd());

			return _assetHelper.getAssetEntries(hits);
		}
		catch (Exception exception) {
			_log.error("Unable to get asset entries", exception);
		}

		return Collections.emptyList();
	}

	private int _dynamicSearchCount(
		long companyId, long[][] assetCategoryIds,
		AssetEntryQuery assetEntryQuery, String keywords) {

		try {
			Long count = _assetHelper.searchCount(
				_getDynamicSearchContext(
					companyId, assetCategoryIds, assetEntryQuery, keywords),
				assetEntryQuery);

			return count.intValue();
		}
		catch (Exception exception) {
			_log.error("Unable to get asset entries count", exception);
		}

		return 0;
	}

	private long[] _filterAssetCategoryIds(long[] assetCategoryIds) {
		List<Long> assetCategoryIdsList = new ArrayList<>();

		for (long assetCategoryId : assetCategoryIds) {
			AssetCategory category =
				_assetCategoryLocalService.fetchAssetCategory(assetCategoryId);

			if (category == null) {
				continue;
			}

			assetCategoryIdsList.add(assetCategoryId);
		}

		return ArrayUtil.toArray(assetCategoryIdsList.toArray(new Long[0]));
	}

	private long[] _getAssetCategoryIds(UnicodeProperties unicodeProperties) {
		long[] assetCategoryIds = new long[0];

		for (int i = 0; true; i++) {
			String[] queryValues = StringUtil.split(
				unicodeProperties.getProperty("queryValues" + i, null));

			if (ArrayUtil.isEmpty(queryValues)) {
				break;
			}

			boolean queryContains = GetterUtil.getBoolean(
				unicodeProperties.getProperty(
					"queryContains" + i, StringPool.BLANK));
			boolean queryAndOperator = GetterUtil.getBoolean(
				unicodeProperties.getProperty(
					"queryAndOperator" + i, StringPool.BLANK));
			String queryName = unicodeProperties.getProperty(
				"queryName" + i, StringPool.BLANK);

			if (Objects.equals(queryName, "assetCategories") && queryContains &&
				(queryAndOperator || (queryValues.length == 1))) {

				assetCategoryIds = ArrayUtil.append(
					assetCategoryIds, GetterUtil.getLongValues(queryValues));
			}
		}

		return assetCategoryIds;
	}

	private BooleanClause[] _getAssetCategoryIdsBooleanClauses(
		long[][] assetCategoryIds) {

		if (ArrayUtil.isEmpty(assetCategoryIds)) {
			return new BooleanClause[0];
		}

		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		BooleanFilter assetCategoryIdsBooleanFilter = new BooleanFilter();

		for (long[] assetCategoryArrayIds : assetCategoryIds) {
			TermsFilter assetCategoryIdTermsFilter = new TermsFilter(
				Field.ASSET_CATEGORY_IDS);

			assetCategoryIdTermsFilter.addValues(
				ArrayUtil.toStringArray(assetCategoryArrayIds));

			assetCategoryIdsBooleanFilter.add(
				assetCategoryIdTermsFilter, BooleanClauseOccur.MUST);
		}

		booleanQueryImpl.setPreBooleanFilter(assetCategoryIdsBooleanFilter);

		return new BooleanClause[] {
			BooleanClauseFactoryUtil.create(
				booleanQueryImpl, BooleanClauseOccur.MUST.getName())
		};
	}

	private List<AssetListEntryAssetEntryRel> _getAssetListEntryAssetEntryRels(
		AssetListEntry assetListEntry, long[] segmentsEntryIds, int start,
		int end) {

		if (_assetListConfiguration.combineAssetsFromAllSegmentsManual()) {
			return _assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRels(
					assetListEntry.getAssetListEntryId(),
					_getCombinedSegmentsEntryIds(segmentsEntryIds), start, end);
		}

		return _assetListEntryAssetEntryRelLocalService.
			getAssetListEntryAssetEntryRels(
				assetListEntry.getAssetListEntryId(),
				new long[] {
					_getFirstSegmentsEntryId(assetListEntry, segmentsEntryIds)
				},
				start, end);
	}

	private String[] _getAssetTagNames(UnicodeProperties unicodeProperties) {
		List<String> allAssetTagNames = new ArrayList<>();

		for (int i = 0; true; i++) {
			String[] queryValues = StringUtil.split(
				unicodeProperties.getProperty("queryValues" + i, null));

			if (ArrayUtil.isEmpty(queryValues)) {
				break;
			}

			boolean queryContains = GetterUtil.getBoolean(
				unicodeProperties.getProperty(
					"queryContains" + i, StringPool.BLANK));
			boolean queryAndOperator = GetterUtil.getBoolean(
				unicodeProperties.getProperty(
					"queryAndOperator" + i, StringPool.BLANK));
			String queryName = unicodeProperties.getProperty(
				"queryName" + i, StringPool.BLANK);

			if (Objects.equals(queryName, "assetTags") && queryContains &&
				(queryAndOperator || (queryValues.length == 1))) {

				Collections.addAll(allAssetTagNames, queryValues);
			}
		}

		return allAssetTagNames.toArray(new String[0]);
	}

	private long[] _getClassNameIds(
		UnicodeProperties unicodeProperties, long[] availableClassNameIds) {

		boolean anyAssetType = GetterUtil.getBoolean(
			unicodeProperties.getProperty(
				"anyAssetType", Boolean.TRUE.toString()));

		if (anyAssetType) {
			return availableClassNameIds;
		}

		long defaultClassNameId = GetterUtil.getLong(
			unicodeProperties.getProperty("anyAssetType", null));

		if (defaultClassNameId > 0) {
			return new long[] {defaultClassNameId};
		}

		long[] classNameIds = GetterUtil.getLongValues(
			StringUtil.split(
				unicodeProperties.getProperty("classNameIds", null)));

		if (ArrayUtil.isNotEmpty(classNameIds)) {
			return classNameIds;
		}

		return availableClassNameIds;
	}

	private long[] _getClassTypeIds(
		AssetListEntry assetListEntry, UnicodeProperties unicodeProperties,
		String className) {

		long[] availableClassTypeIds = {};

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if (assetRendererFactory == null) {
			return availableClassTypeIds;
		}

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		try {
			List<ClassType> classTypes = classTypeReader.getAvailableClassTypes(
				_portal.getSharedContentSiteGroupIds(
					assetListEntry.getCompanyId(), assetListEntry.getGroupId(),
					assetListEntry.getUserId()),
				LocaleUtil.getDefault());

			Stream<ClassType> stream = classTypes.stream();

			availableClassTypeIds = stream.mapToLong(
				ClassType::getClassTypeId
			).toArray();
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to get class types for class name " + className,
				portalException);
		}

		Class<? extends AssetRendererFactory> clazz =
			_assetRendererFactoryClassProvider.getClass(assetRendererFactory);

		boolean anyAssetType = GetterUtil.getBoolean(
			unicodeProperties.getProperty(
				"anyClassType" + clazz.getSimpleName(),
				Boolean.TRUE.toString()));

		if (anyAssetType) {
			return availableClassTypeIds;
		}

		long anyClassTypeId = GetterUtil.getLong(
			unicodeProperties.getProperty(
				"anyClassType" + clazz.getSimpleName(), null),
			-1);

		if (anyClassTypeId > -1) {
			return new long[] {anyClassTypeId};
		}

		long[] classTypeIds = StringUtil.split(
			unicodeProperties.getProperty(
				"classTypeIds" + clazz.getSimpleName(), null),
			0L);

		if (classTypeIds != null) {
			return classTypeIds;
		}

		return availableClassTypeIds;
	}

	private long[] _getCombinedSegmentsEntryIds(long[] segmentEntryIds) {
		if ((segmentEntryIds.length > 1) &&
			ArrayUtil.contains(
				segmentEntryIds, SegmentsEntryConstants.ID_DEFAULT)) {

			return ArrayUtil.remove(
				segmentEntryIds, SegmentsEntryConstants.ID_DEFAULT);
		}

		return segmentEntryIds;
	}

	private List<AssetEntry> _getDynamicAssetEntries(
		AssetListEntry assetListEntry, long[] segmentsEntryIds,
		long[][] assetCategoryIds, String keywords, String userId, int start,
		int end) {

		if (!_assetListConfiguration.combineAssetsFromAllSegmentsDynamic()) {
			AssetEntryQuery assetEntryQuery = getAssetEntryQuery(
				assetListEntry,
				_getFirstSegmentsEntryId(assetListEntry, segmentsEntryIds),
				userId);

			assetEntryQuery.setEnd(end);
			assetEntryQuery.setStart(start);

			return _dynamicSearch(
				assetListEntry.getCompanyId(), assetCategoryIds,
				assetEntryQuery, keywords);
		}

		List<AssetEntry> dynamicAssetEntries = new ArrayList<>();

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS)) {
			for (long segmentsEntryId :
					_getCombinedSegmentsEntryIds(segmentsEntryIds)) {

				AssetEntryQuery assetEntryQuery = getAssetEntryQuery(
					assetListEntry, segmentsEntryId, userId);

				List<AssetEntry> assetEntries = _dynamicSearch(
					assetListEntry.getCompanyId(), assetCategoryIds,
					assetEntryQuery, keywords);

				dynamicAssetEntries.addAll(assetEntries);
			}

			return dynamicAssetEntries;
		}

		int count = 0;
		int remaining = Math.max(0, end - start);
		int subtotal = 0;

		for (long segmentsEntryId :
				_getCombinedSegmentsEntryIds(segmentsEntryIds)) {

			AssetEntryQuery assetEntryQuery = getAssetEntryQuery(
				assetListEntry, segmentsEntryId, userId);

			count = _dynamicSearchCount(
				assetListEntry.getCompanyId(), assetCategoryIds,
				assetEntryQuery, keywords);

			if ((subtotal + count) < start) {
				subtotal = +count;

				continue;
			}

			List<AssetEntry> assetEntries = _dynamicSearch(
				assetListEntry.getCompanyId(), assetCategoryIds,
				assetEntryQuery, keywords);

			count = assetEntries.size();

			List<AssetEntry> assetEntriesSublist = assetEntries.subList(
				Math.max(start - subtotal, 0), Math.min(remaining, count));

			dynamicAssetEntries.addAll(assetEntriesSublist);

			remaining -= assetEntriesSublist.size();

			subtotal += count;

			if (remaining <= 0) {
				break;
			}
		}

		return dynamicAssetEntries;
	}

	private int _getDynamicAssetEntriesCount(
		AssetListEntry assetListEntry, long[] segmentsEntryIds,
		long[][] assetCategoryIds, String keywords, String userId) {

		if (_assetListConfiguration.combineAssetsFromAllSegmentsDynamic()) {
			int totalCount = 0;

			for (long segmentsEntryId :
					_getCombinedSegmentsEntryIds(segmentsEntryIds)) {

				AssetEntryQuery assetEntryQuery = getAssetEntryQuery(
					assetListEntry, segmentsEntryId, userId);

				totalCount += _dynamicSearchCount(
					assetListEntry.getCompanyId(), assetCategoryIds,
					assetEntryQuery, keywords);
			}

			return totalCount;
		}

		AssetEntryQuery assetEntryQuery = getAssetEntryQuery(
			assetListEntry,
			_getFirstSegmentsEntryId(assetListEntry, segmentsEntryIds), userId);

		return _dynamicSearchCount(
			assetListEntry.getCompanyId(), assetCategoryIds, assetEntryQuery,
			keywords);
	}

	private SearchContext _getDynamicSearchContext(
		long companyId, long[][] assetCategoryIds,
		AssetEntryQuery assetEntryQuery, String keywords) {

		SearchContext searchContext = new SearchContext();

		String ddmStructureFieldName = GetterUtil.getString(
			assetEntryQuery.getAttribute("ddmStructureFieldName"));
		Serializable ddmStructureFieldValue = assetEntryQuery.getAttribute(
			"ddmStructureFieldValue");

		if (Validator.isNotNull(ddmStructureFieldName) &&
			Validator.isNotNull(ddmStructureFieldValue)) {

			searchContext.setAttribute(
				"ddmStructureFieldName", ddmStructureFieldName);
			searchContext.setAttribute(
				"ddmStructureFieldValue", ddmStructureFieldValue);
		}

		searchContext.setBooleanClauses(
			_getAssetCategoryIdsBooleanClauses(assetCategoryIds));
		searchContext.setClassTypeIds(assetEntryQuery.getClassTypeIds());
		searchContext.setCompanyId(companyId);
		searchContext.setEnd(assetEntryQuery.getEnd());
		searchContext.setKeywords(keywords);
		searchContext.setStart(assetEntryQuery.getStart());

		return searchContext;
	}

	private String _getFieldReference(
		DDMStructure ddmStructure, String fieldName) {

		try {
			return ddmStructure.getFieldProperty(fieldName, "fieldReference");
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			return fieldName;
		}
	}

	private long _getFirstSegmentsEntryId(
		AssetListEntry assetListEntry, long[] segmentsEntryIds) {

		LongStream longStream = Arrays.stream(segmentsEntryIds);

		return longStream.filter(
			segmentsEntryId -> {
				if (segmentsEntryId == SegmentsEntryConstants.ID_DEFAULT) {
					return false;
				}

				AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
					_assetListEntrySegmentsEntryRelLocalService.
						fetchAssetListEntrySegmentsEntryRel(
							assetListEntry.getAssetListEntryId(),
							segmentsEntryId);

				return assetListEntrySegmentsEntryRel != null;
			}
		).findFirst(
		).orElse(
			SegmentsEntryConstants.ID_DEFAULT
		);
	}

	private String[] _getKeywords(UnicodeProperties unicodeProperties) {
		String[] allKeywords = new String[0];

		for (int i = 0; true; i++) {
			String[] queryValues = StringUtil.split(
				unicodeProperties.getProperty("queryValues" + i, null));

			if (ArrayUtil.isEmpty(queryValues)) {
				break;
			}

			boolean queryContains = GetterUtil.getBoolean(
				unicodeProperties.getProperty(
					"queryContains" + i, StringPool.BLANK));
			boolean queryAndOperator = GetterUtil.getBoolean(
				unicodeProperties.getProperty(
					"queryAndOperator" + i, StringPool.BLANK));
			String queryName = unicodeProperties.getProperty(
				"queryName" + i, StringPool.BLANK);

			if (Objects.equals(queryName, "keywords") && queryContains &&
				(queryAndOperator || (queryValues.length == 1))) {

				allKeywords = queryValues;
			}
		}

		return allKeywords;
	}

	private List<AssetEntry> _getManualAssetEntries(
		AssetListEntry assetListEntry, long[] segmentsEntryIds,
		long[][] assetCategoryIds, String keywords, int start, int end) {

		List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
			_getAssetListEntryAssetEntryRels(
				assetListEntry, segmentsEntryIds, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		if (ListUtil.isEmpty(assetListEntryAssetEntryRels)) {
			return Collections.emptyList();
		}

		List<Long> assetEntryIds = ListUtil.toList(
			assetListEntryAssetEntryRels,
			AssetListEntryAssetEntryRelModel::getAssetEntryId);

		try {
			Hits hits = _assetHelper.search(
				_getManualSearchContext(
					assetCategoryIds, assetEntryIds,
					assetListEntry.getCompanyId(), keywords),
				_getManualAssetEntryQuery(assetListEntry), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

			List<AssetEntry> assetEntries = _assetHelper.getAssetEntries(hits);

			ListUtil.sort(
				assetEntries,
				Comparator.comparing(
					assetEntry -> assetEntryIds.indexOf(
						assetEntry.getEntryId())));

			return ListUtil.subList(assetEntries, start, end);
		}
		catch (Exception exception) {
			_log.error("Unable to get asset entries", exception);
		}

		return Collections.emptyList();
	}

	private int _getManualAssetEntriesCount(
		AssetListEntry assetListEntry, long[] segmentsEntryIds,
		long[][] assetCategoryIds, String keywords) {

		List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
			_getAssetListEntryAssetEntryRels(
				assetListEntry, segmentsEntryIds, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		if (ListUtil.isEmpty(assetListEntryAssetEntryRels)) {
			return 0;
		}

		List<Long> assetEntryIds = ListUtil.toList(
			assetListEntryAssetEntryRels,
			AssetListEntryAssetEntryRelModel::getAssetEntryId);

		try {
			Long count = _assetHelper.searchCount(
				_getManualSearchContext(
					assetCategoryIds, assetEntryIds,
					assetListEntry.getCompanyId(), keywords),
				_getManualAssetEntryQuery(assetListEntry));

			return count.intValue();
		}
		catch (Exception exception) {
			_log.error("Unable to get asset entries count", exception);
		}

		return 0;
	}

	private AssetEntryQuery _getManualAssetEntryQuery(
		AssetListEntry assetListEntry) {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		if (Validator.isNotNull(assetListEntry.getAssetEntryType()) &&
			!Objects.equals(
				assetListEntry.getAssetEntryType(),
				AssetEntry.class.getName())) {

			assetEntryQuery.setClassName(assetListEntry.getAssetEntryType());

			long classTypeId = GetterUtil.getLong(
				assetListEntry.getAssetEntrySubtype());

			if (classTypeId > 0) {
				assetEntryQuery.setClassTypeIds(new long[] {classTypeId});
			}
		}
		else {
			assetEntryQuery.setClassNameIds(
				AssetRendererFactoryRegistryUtil.getClassNameIds(
					assetListEntry.getCompanyId()));
		}

		return assetEntryQuery;
	}

	private SearchContext _getManualSearchContext(
		long[][] assetCategoryIds, List<Long> assetEntryIds, long companyId,
		String keywords) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(
			Field.ASSET_ENTRY_IDS, ArrayUtil.toLongArray(assetEntryIds));

		searchContext.setBooleanClauses(
			_getAssetCategoryIdsBooleanClauses(assetCategoryIds));
		searchContext.setCompanyId(companyId);
		searchContext.setKeywords(keywords);

		return searchContext;
	}

	private void _processAssetEntryQuery(
		long companyId, String userId, UnicodeProperties unicodeProperties,
		AssetEntryQuery assetEntryQuery) {

		for (AssetListAssetEntryQueryProcessor
				assetListAssetEntryQueryProcessor :
					_assetListAssetEntryQueryProcessors) {

			assetListAssetEntryQueryProcessor.processAssetEntryQuery(
				companyId, userId, unicodeProperties, assetEntryQuery);
		}
	}

	private void _setCategoriesAndTagsAndKeywords(
		AssetListEntry assetListEntry, AssetEntryQuery assetEntryQuery,
		UnicodeProperties unicodeProperties, long[] overrideAllAssetCategoryIds,
		String[] overrideAllAssetTagNames, String[] overrideAllKeywords) {

		long[] allAssetCategoryIds = new long[0];
		long[] anyAssetCategoryIds = new long[0];
		long[] notAllAssetCategoryIds = new long[0];
		long[] notAnyAssetCategoryIds = new long[0];

		String[] allAssetTagNames = new String[0];
		String[] anyAssetTagNames = new String[0];
		String[] notAllAssetTagNames = new String[0];
		String[] notAnyAssetTagNames = new String[0];

		String[] allKeywords = new String[0];
		String[] anyKeywords = new String[0];
		String[] notAllKeywords = new String[0];
		String[] notAnyKeywords = new String[0];

		for (int i = 0; true; i++) {
			String[] queryValues = StringUtil.split(
				unicodeProperties.getProperty("queryValues" + i, null));

			if (ArrayUtil.isEmpty(queryValues)) {
				break;
			}

			boolean queryContains = GetterUtil.getBoolean(
				unicodeProperties.getProperty(
					"queryContains" + i, StringPool.BLANK));
			boolean queryAndOperator = GetterUtil.getBoolean(
				unicodeProperties.getProperty(
					"queryAndOperator" + i, StringPool.BLANK));
			String queryName = unicodeProperties.getProperty(
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
			else if (Objects.equals(queryName, "keywords")) {
				if (queryContains && queryAndOperator) {
					allKeywords = queryValues;
				}
				else if (queryContains && !queryAndOperator) {
					anyKeywords = queryValues;
				}
				else if (!queryContains && queryAndOperator) {
					notAllKeywords = queryValues;
				}
				else {
					notAnyKeywords = queryValues;
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

		if (overrideAllKeywords != null) {
			allKeywords = overrideAllKeywords;
		}

		assetEntryQuery.setAllKeywords(allKeywords);

		if (overrideAllAssetTagNames != null) {
			allAssetTagNames = overrideAllAssetTagNames;
		}

		long siteGroupId = _portal.getSiteGroupId(assetListEntry.getGroupId());

		for (String assetTagName : allAssetTagNames) {
			long[] allAssetTagIds = _assetTagLocalService.getTagIds(
				new long[] {siteGroupId}, assetTagName);

			assetEntryQuery.addAllTagIdsArray(allAssetTagIds);
		}

		anyAssetCategoryIds = _filterAssetCategoryIds(anyAssetCategoryIds);

		assetEntryQuery.setAnyCategoryIds(anyAssetCategoryIds);

		assetEntryQuery.setAnyKeywords(anyKeywords);

		long[] anyAssetTagIds = _assetTagLocalService.getTagIds(
			siteGroupId, anyAssetTagNames);

		assetEntryQuery.setAnyTagIds(anyAssetTagIds);

		assetEntryQuery.setNotAllCategoryIds(notAllAssetCategoryIds);
		assetEntryQuery.setNotAllKeywords(notAllKeywords);

		for (String assetTagName : notAllAssetTagNames) {
			long[] notAllAssetTagIds = _assetTagLocalService.getTagIds(
				new long[] {siteGroupId}, assetTagName);

			assetEntryQuery.addNotAllTagIdsArray(notAllAssetTagIds);
		}

		assetEntryQuery.setNotAnyCategoryIds(notAnyAssetCategoryIds);
		assetEntryQuery.setNotAnyKeywords(notAnyKeywords);

		long[] notAnyAssetTagIds = _assetTagLocalService.getTagIds(
			siteGroupId, notAnyAssetTagNames);

		assetEntryQuery.setNotAnyTagIds(notAnyAssetTagIds);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetListAssetEntryProviderImpl.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetHelper _assetHelper;

	private final List<AssetListAssetEntryQueryProcessor>
		_assetListAssetEntryQueryProcessors = new CopyOnWriteArrayList<>();
	private volatile AssetListConfiguration _assetListConfiguration;

	@Reference
	private AssetListEntryAssetEntryRelLocalService
		_assetListEntryAssetEntryRelLocalService;

	@Reference
	private AssetListEntrySegmentsEntryRelLocalService
		_assetListEntrySegmentsEntryRelLocalService;

	@Reference
	private AssetRendererFactoryClassProvider
		_assetRendererFactoryClassProvider;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private Portal _portal;

}