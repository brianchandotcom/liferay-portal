/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.internal.configuration.AssetListConfiguration;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRelModel;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRelModel;
import com.liferay.asset.list.service.AssetListEntryAssetEntryRelLocalService;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalService;
import com.liferay.asset.list.util.comparator.AssetListEntrySegmentsEntryRelPriorityComparator;
import com.liferay.asset.util.AssetHelper;
import com.liferay.asset.util.AssetRendererFactoryClassProvider;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.util.DLFileEntryTypeUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.petra.function.transform.TransformUtil;
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
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.segments.constants.SegmentsEntryConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 */
@Component(
	configurationPid = "com.liferay.asset.list.internal.configuration.AssetListConfiguration",
	service = AssetListAssetEntryProvider.class
)
public class AssetListAssetEntryProviderImpl
	implements AssetListAssetEntryProvider {

	@Override
	public List<AssetEntry> getAssetEntries(
		AssetListEntry assetListEntry, long[] segmentsEntryIds,
		long[][] assetCategoryIds, String[][] assetTagNames, String keywords,
		String userId, int start, int end) {

		if (Objects.equals(
				assetListEntry.getType(),
				AssetListEntryTypeConstants.TYPE_MANUAL)) {

			return _getManualAssetEntries(
				assetListEntry, segmentsEntryIds, assetCategoryIds,
				assetTagNames, keywords, start, end);
		}

		return _getDynamicAssetEntries(
			assetListEntry, segmentsEntryIds, assetCategoryIds, assetTagNames,
			keywords, userId, start, end);
	}

	@Override
	public int getAssetEntriesCount(
		AssetListEntry assetListEntry, long[] segmentsEntryIds,
		long[][] assetCategoryIds, String[][] assetTagNames, String keywords,
		String userId) {

		if (Objects.equals(
				assetListEntry.getType(),
				AssetListEntryTypeConstants.TYPE_MANUAL)) {

			return _getManualAssetEntriesCount(
				assetListEntry, segmentsEntryIds, assetCategoryIds,
				assetTagNames, keywords);
		}

		return _getDynamicAssetEntriesCount(
			assetListEntry, segmentsEntryIds, assetCategoryIds, assetTagNames,
			keywords, userId);
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
	protected void activate(
			BundleContext bundleContext, Map<String, Object> properties)
		throws ConfigurationException {

		_assetListConfiguration = ConfigurableUtil.createConfigurable(
			AssetListConfiguration.class, properties);
	}

	protected AssetEntryQuery getAssetEntryQuery(
		AssetListEntry assetListEntry, long segmentsEntryId, String userId) {

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.create(
			true
		).fastLoad(
			assetListEntry.getTypeSettings(segmentsEntryId)
		).build();

		return _createAssetEntryQuery(assetListEntry, unicodeProperties);
	}

	private AssetEntryQuery _createAssetEntryQuery(
		AssetListEntry assetListEntry, UnicodeProperties unicodeProperties) {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		_setCategoriesAndTagsAndKeywords(
			assetEntryQuery, unicodeProperties,
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
				assetListEntry.getCompanyId(), true);
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
					DLFileEntryTypeUtil.getDDMStructures(dlFileEntryType);

				if (!ddmStructures.isEmpty()) {
					DDMStructure ddmStructure = ddmStructures.get(0);

					assetEntryQuery.setAttribute(
						"ddmStructureFieldName",
						_ddmIndexer.encodeName(
							ddmStructure.getStructureId(),
							_getFieldReference(
								ddmStructure, ddmStructureFieldName),
							LocaleUtil.getSiteDefault()));
				}
			}
			else {
				long ddmStructureId = classTypeIds[0];

				assetEntryQuery.setAttribute(
					"ddmStructureFieldName",
					_ddmIndexer.encodeName(
						ddmStructureId,
						_getFieldReference(
							ddmStructureId, ddmStructureFieldName),
						LocaleUtil.getSiteDefault()));
			}

			assetEntryQuery.setAttribute(
				"ddmStructureFieldValue", ddmStructureFieldValue);
		}

		String orderByColumn1 = GetterUtil.getString(
			unicodeProperties.getProperty("orderByColumn1", "priority"));

		assetEntryQuery.setOrderByCol1(orderByColumn1);

		String orderByColumn2 = GetterUtil.getString(
			unicodeProperties.getProperty("orderByColumn2", "modifiedDate"));

		assetEntryQuery.setOrderByCol2(orderByColumn2);

		assetEntryQuery.setOrderByType1(
			GetterUtil.getString(
				unicodeProperties.getProperty("orderByType1", "ASC")));
		assetEntryQuery.setOrderByType2(
			GetterUtil.getString(
				unicodeProperties.getProperty("orderByType2", "ASC")));

		return assetEntryQuery;
	}

	private List<AssetEntry> _dynamicSearch(
		long companyId, long[][] assetCategoryIds,
		List<AssetEntryQuery> assetEntryQueries, String[][] assetTagNames,
		String keywords) {

		try {
			if (ListUtil.isEmpty(assetEntryQueries)) {
				return Collections.emptyList();
			}

			AssetEntryQuery assetEntryQuery = assetEntryQueries.get(0);

			if (assetEntryQueries.size() == 1) {
				Hits hits = _assetHelper.search(
					_getDynamicSearchContext(
						companyId, assetCategoryIds, assetEntryQuery,
						assetTagNames, keywords),
					assetEntryQuery, assetEntryQuery.getStart(),
					assetEntryQuery.getEnd());

				return _assetHelper.getAssetEntries(hits);
			}

			SearchHits searchHits = _assetHelper.search(
				_getDynamicSearchContext(
					companyId, assetCategoryIds, assetEntryQueries.get(0),
					assetTagNames, keywords),
				assetEntryQueries, assetEntryQuery.getStart(),
				assetEntryQuery.getEnd());

			return _assetHelper.getAssetEntries(searchHits);
		}
		catch (Exception exception) {
			_log.error("Unable to get asset entries", exception);
		}

		return Collections.emptyList();
	}

	private int _dynamicSearchCount(
		long companyId, long[][] assetCategoryIds,
		List<AssetEntryQuery> assetEntryQueries, String[][] assetTagNames,
		String keywords) {

		try {
			if (ListUtil.isEmpty(assetEntryQueries)) {
				return 0;
			}

			AssetEntryQuery assetEntryQuery = assetEntryQueries.get(0);

			if (assetEntryQueries.size() == 1) {
				Long count = _assetHelper.searchCount(
					_getDynamicSearchContext(
						companyId, assetCategoryIds, assetEntryQuery,
						assetTagNames, keywords),
					assetEntryQuery);

				return count.intValue();
			}

			Long count = _assetHelper.searchCount(
				_getDynamicSearchContext(
					companyId, assetCategoryIds, assetEntryQuery, assetTagNames,
					keywords),
				assetEntryQueries, assetEntryQuery.getStart(),
				assetEntryQuery.getEnd());

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
		AssetListEntry assetListEntry, long[] segmentsEntryIds) {

		if (_assetListConfiguration.combineAssetsFromAllSegmentsManual()) {
			List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
				new ArrayList<>();

			segmentsEntryIds = _sortSegmentsByPriority(
				assetListEntry,
				_getCombinedSegmentsEntryIds(assetListEntry, segmentsEntryIds));

			for (long segmentId : segmentsEntryIds) {
				assetListEntryAssetEntryRels.addAll(
					ListUtil.sort(
						_assetListEntryAssetEntryRelLocalService.
							getAssetListEntryAssetEntryRels(
								assetListEntry.getAssetListEntryId(),
								new long[] {segmentId}, QueryUtil.ALL_POS,
								QueryUtil.ALL_POS),
						Comparator.comparing(
							AssetListEntryAssetEntryRelModel::getPosition)));
			}

			return assetListEntryAssetEntryRels;
		}

		return _assetListEntryAssetEntryRelLocalService.
			getAssetListEntryAssetEntryRels(
				assetListEntry.getAssetListEntryId(),
				new long[] {
					_getFirstSegmentsEntryId(assetListEntry, segmentsEntryIds)
				},
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);
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

	private BooleanClause[] _getAssetTagNamesBooleanClauses(
		String[][] assetTagNames) {

		if (ArrayUtil.isEmpty(assetTagNames)) {
			return new BooleanClause[0];
		}

		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		BooleanFilter assetTagNamesBooleanFilter = new BooleanFilter();

		for (String[] assetTagArrayNames : assetTagNames) {
			TermsFilter assetTagIdTermsFilter = new TermsFilter(
				Field.ASSET_TAG_NAMES);

			assetTagIdTermsFilter.addValues(
				ArrayUtil.toStringArray(assetTagArrayNames));

			assetTagNamesBooleanFilter.add(
				assetTagIdTermsFilter, BooleanClauseOccur.MUST);
		}

		booleanQueryImpl.setPreBooleanFilter(assetTagNamesBooleanFilter);

		return new BooleanClause[] {
			BooleanClauseFactoryUtil.create(
				booleanQueryImpl, BooleanClauseOccur.MUST.getName())
		};
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
			availableClassTypeIds = TransformUtil.transformToLongArray(
				classTypeReader.getAvailableClassTypes(
					_portal.getSharedContentSiteGroupIds(
						assetListEntry.getCompanyId(),
						assetListEntry.getGroupId(),
						assetListEntry.getUserId()),
					LocaleUtil.getDefault()),
				ClassType::getClassTypeId);
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

	private long[] _getCombinedSegmentsEntryIds(
		AssetListEntry assetListEntry, long[] segmentEntryIds) {

		if ((segmentEntryIds.length > 1) &&
			ArrayUtil.contains(
				segmentEntryIds, SegmentsEntryConstants.ID_DEFAULT)) {

			segmentEntryIds = ArrayUtil.remove(
				segmentEntryIds, SegmentsEntryConstants.ID_DEFAULT);
		}

		long[] combinedSegmentsEntryIds = TransformUtil.transformToLongArray(
			ListUtil.sort(
				TransformUtil.transformToList(
					segmentEntryIds,
					segmentsEntryId ->
						_assetListEntrySegmentsEntryRelLocalService.
							fetchAssetListEntrySegmentsEntryRel(
								assetListEntry.getAssetListEntryId(),
								segmentsEntryId)),
				Comparator.comparing(
					AssetListEntrySegmentsEntryRel::getPriority)),
			AssetListEntrySegmentsEntryRelModel::getSegmentsEntryId);

		if (combinedSegmentsEntryIds.length == 0) {
			combinedSegmentsEntryIds = new long[] {
				SegmentsEntryConstants.ID_DEFAULT
			};
		}

		return combinedSegmentsEntryIds;
	}

	private List<AssetEntry> _getDynamicAssetEntries(
		AssetListEntry assetListEntry, long[] segmentsEntryIds,
		long[][] assetCategoryIds, String[][] assetTagNames, String keywords,
		String userId, int start, int end) {

		if (!_assetListConfiguration.combineAssetsFromAllSegmentsDynamic()) {
			AssetEntryQuery assetEntryQuery = getAssetEntryQuery(
				assetListEntry,
				_getFirstSegmentsEntryId(assetListEntry, segmentsEntryIds),
				userId);

			assetEntryQuery.setEnd(end);
			assetEntryQuery.setStart(start);

			return _dynamicSearch(
				assetListEntry.getCompanyId(), assetCategoryIds,
				Collections.singletonList(assetEntryQuery), assetTagNames,
				keywords);
		}

		return _dynamicSearch(
			assetListEntry.getCompanyId(), assetCategoryIds,
			TransformUtil.transformToList(
				_getCombinedSegmentsEntryIds(assetListEntry, segmentsEntryIds),
				segmentsEntryId -> getAssetEntryQuery(
					assetListEntry, segmentsEntryId, userId)),
			assetTagNames, keywords);
	}

	private int _getDynamicAssetEntriesCount(
		AssetListEntry assetListEntry, long[] segmentsEntryIds,
		long[][] assetCategoryIds, String[][] assetTagNames, String keywords,
		String userId) {

		if (!_assetListConfiguration.combineAssetsFromAllSegmentsDynamic()) {
			AssetEntryQuery assetEntryQuery = getAssetEntryQuery(
				assetListEntry,
				_getFirstSegmentsEntryId(assetListEntry, segmentsEntryIds),
				userId);

			return _dynamicSearchCount(
				assetListEntry.getCompanyId(), assetCategoryIds,
				Collections.singletonList(assetEntryQuery), assetTagNames,
				keywords);
		}

		return _dynamicSearchCount(
			assetListEntry.getCompanyId(), assetCategoryIds,
			TransformUtil.transformToList(
				_getCombinedSegmentsEntryIds(assetListEntry, segmentsEntryIds),
				segmentsEntryId -> getAssetEntryQuery(
					assetListEntry, segmentsEntryId, userId)),
			assetTagNames, keywords);
	}

	private SearchContext _getDynamicSearchContext(
		long companyId, long[][] assetCategoryIds,
		AssetEntryQuery assetEntryQuery, String[][] assetTagNames,
		String keywords) {

		SearchContext searchContext = new SearchContext();

		searchContext.setBooleanClauses(
			ArrayUtil.append(
				_getAssetCategoryIdsBooleanClauses(assetCategoryIds),
				_getAssetTagNamesBooleanClauses(assetTagNames)));
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
				_log.debug(portalException);
			}

			return fieldName;
		}
	}

	private String _getFieldReference(long ddmStructureId, String fieldName) {
		try {
			DDMStructure ddmStructure =
				_ddmStructureLocalService.getDDMStructure(ddmStructureId);

			return ddmStructure.getFieldProperty(fieldName, "fieldReference");
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return fieldName;
		}
	}

	private long _getFirstSegmentsEntryId(
		AssetListEntry assetListEntry, long[] segmentsEntryIds) {

		if (segmentsEntryIds.length == 0) {
			return SegmentsEntryConstants.ID_DEFAULT;
		}

		if (segmentsEntryIds.length == 1) {
			return segmentsEntryIds[0];
		}

		List<AssetListEntrySegmentsEntryRel> assetListEntrySegmentsEntryRels =
			_assetListEntrySegmentsEntryRelLocalService.
				getAssetListEntrySegmentsEntryRels(
					assetListEntry.getAssetListEntryId(), segmentsEntryIds, 0,
					1, new AssetListEntrySegmentsEntryRelPriorityComparator());

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			assetListEntrySegmentsEntryRels.get(0);

		return assetListEntrySegmentsEntryRel.getSegmentsEntryId();
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
		long[][] assetCategoryIds, String[][] assetTagNames, String keywords,
		int start, int end) {

		List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
			_getAssetListEntryAssetEntryRels(assetListEntry, segmentsEntryIds);

		if (ListUtil.isEmpty(assetListEntryAssetEntryRels)) {
			return Collections.emptyList();
		}

		List<Long> assetEntryIds = ListUtil.toList(
			assetListEntryAssetEntryRels,
			AssetListEntryAssetEntryRelModel::getAssetEntryId);

		try {
			Hits hits = _assetHelper.search(
				_getManualSearchContext(
					assetCategoryIds, assetEntryIds, assetTagNames,
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
		long[][] assetCategoryIds, String[][] assetTagNames, String keywords) {

		List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
			_getAssetListEntryAssetEntryRels(assetListEntry, segmentsEntryIds);

		if (ListUtil.isEmpty(assetListEntryAssetEntryRels)) {
			return 0;
		}

		List<Long> assetEntryIds = ListUtil.toList(
			assetListEntryAssetEntryRels,
			AssetListEntryAssetEntryRelModel::getAssetEntryId);

		try {
			Long count = _assetHelper.searchCount(
				_getManualSearchContext(
					assetCategoryIds, assetEntryIds, assetTagNames,
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
					assetListEntry.getCompanyId(), true));
		}

		return assetEntryQuery;
	}

	private SearchContext _getManualSearchContext(
		long[][] assetCategoryIds, List<Long> assetEntryIds,
		String[][] assetTagNames, long companyId, String keywords) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(
			Field.ASSET_ENTRY_IDS, ArrayUtil.toLongArray(assetEntryIds));
		searchContext.setBooleanClauses(
			ArrayUtil.append(
				_getAssetTagNamesBooleanClauses(assetTagNames),
				_getAssetCategoryIdsBooleanClauses(assetCategoryIds)));
		searchContext.setCompanyId(companyId);
		searchContext.setKeywords(keywords);

		return searchContext;
	}

	private void _setCategoriesAndTagsAndKeywords(
		AssetEntryQuery assetEntryQuery, UnicodeProperties unicodeProperties,
		long[] overrideAllAssetCategoryIds, String[] overrideAllAssetTagNames,
		String[] overrideAllKeywords) {

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

		long[] groupIds = GetterUtil.getLongValues(
			StringUtil.split(unicodeProperties.getProperty("groupIds", null)));

		for (String assetTagName : allAssetTagNames) {
			long[] allAssetTagIds = _assetTagLocalService.getTagIds(
				groupIds, assetTagName);

			assetEntryQuery.addAllTagIdsArray(allAssetTagIds);
		}

		anyAssetCategoryIds = _filterAssetCategoryIds(anyAssetCategoryIds);

		assetEntryQuery.setAnyCategoryIds(anyAssetCategoryIds);

		assetEntryQuery.setAnyKeywords(anyKeywords);

		long[] anyAssetTagIds = _assetTagLocalService.getTagIds(
			groupIds, anyAssetTagNames);

		assetEntryQuery.setAnyTagIds(anyAssetTagIds);

		assetEntryQuery.setNotAllCategoryIds(notAllAssetCategoryIds);
		assetEntryQuery.setNotAllKeywords(notAllKeywords);

		for (String assetTagName : notAllAssetTagNames) {
			long[] notAllAssetTagIds = _assetTagLocalService.getTagIds(
				groupIds, assetTagName);

			assetEntryQuery.addNotAllTagIdsArray(notAllAssetTagIds);
		}

		assetEntryQuery.setNotAnyCategoryIds(notAnyAssetCategoryIds);
		assetEntryQuery.setNotAnyKeywords(notAnyKeywords);

		long[] notAnyAssetTagIds = _assetTagLocalService.getTagIds(
			groupIds, notAnyAssetTagNames);

		assetEntryQuery.setNotAnyTagIds(notAnyAssetTagIds);
	}

	private long[] _sortSegmentsByPriority(
		AssetListEntry assetListEntry, long[] segmentsEntryIds) {

		return TransformUtil.transformToLongArray(
			ListUtil.sort(
				TransformUtil.transformToList(
					segmentsEntryIds,
					segmentsEntryId ->
						_assetListEntrySegmentsEntryRelLocalService.
							fetchAssetListEntrySegmentsEntryRel(
								assetListEntry.getAssetListEntryId(),
								segmentsEntryId)),
				Comparator.comparing(
					AssetListEntrySegmentsEntryRel::getPriority)),
			AssetListEntrySegmentsEntryRel::getSegmentsEntryId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetListAssetEntryProviderImpl.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetHelper _assetHelper;

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
	private DDMIndexer _ddmIndexer;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private Portal _portal;

}