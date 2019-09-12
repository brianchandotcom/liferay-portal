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

package com.liferay.portal.kernel.search;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchCountryException;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.NoSuchRegionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.ResourcedModel;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.model.WorkflowedModel;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.MultiValueFacet;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.search.hits.HitsProcessorRegistryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RegionServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.registry.collections.ServiceTrackerCollections;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 * @author Ryan Park
 * @author Raymond Augé
 */
public abstract class BaseIndexer<T> implements Indexer<T> {

	@Override
	public void delete(long companyId, String uid) throws SearchException {
		try {
			IndexWriterHelperUtil.deleteDocument(
				getSearchEngineId(), companyId, uid, _commitImmediately);
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	public void delete(T object) throws SearchException {
		if (object == null) {
			return;
		}

		try {
			doDelete(object);
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}

		if (!(object instanceof Indexer<?>)) {
			return false;
		}

		Indexer<?> indexer = (Indexer<?>)object;

		return Objects.equals(getClassName(), indexer.getClassName());
	}

	@Override
	public Document getDocument(T object) throws SearchException {
		try {
			Document document = doGetDocument(object);

			for (IndexerPostProcessor indexerPostProcessor :
					_indexerPostProcessors) {

				indexerPostProcessor.postProcessDocument(document, object);
			}

			if (document == null) {
				return null;
			}

			Map<String, Field> fields = document.getFields();

			Field groupIdField = fields.get(Field.GROUP_ID);

			if (groupIdField != null) {
				long groupId = GetterUtil.getLong(groupIdField.getValue());

				addStagingGroupKeyword(document, groupId);
			}

			return document;
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	public BooleanFilter getFacetBooleanFilter(
			String className, SearchContext searchContext)
		throws Exception {

		return null;
	}

	@Override
	public BooleanQuery getFullQuery(SearchContext searchContext)
		throws SearchException {

		try {
			searchContext.setSearchEngineId(getSearchEngineId());

			resetFullQuery(searchContext);

			String[] fullQueryEntryClassNames =
				searchContext.getFullQueryEntryClassNames();

			if (ArrayUtil.isNotEmpty(fullQueryEntryClassNames)) {
				searchContext.setAttribute(
					"relatedEntryClassNames", getSearchClassNames());
			}

			String[] entryClassNames = ArrayUtil.append(
				getSearchClassNames(), fullQueryEntryClassNames);

			searchContext.setEntryClassNames(entryClassNames);

			BooleanFilter fullQueryBooleanFilter = new BooleanFilter();

			addSearchAssetCategoryIds(fullQueryBooleanFilter, searchContext);
			addSearchAssetTagNames(fullQueryBooleanFilter, searchContext);
			addSearchFolderId(fullQueryBooleanFilter, searchContext);
			addSearchLayout(fullQueryBooleanFilter, searchContext);
			addSearchUserId(fullQueryBooleanFilter, searchContext);

			Map<String, Indexer<?>> entryClassNameIndexerMap =
				_getEntryClassNameIndexerMap(
					entryClassNames, searchContext.getSearchEngineId());

			_addPreFilters(
				fullQueryBooleanFilter, entryClassNameIndexerMap,
				searchContext);

			BooleanQuery fullQuery = createFullQuery(
				fullQueryBooleanFilter, searchContext);

			fullQuery.setQueryConfig(searchContext.getQueryConfig());

			return fullQuery;
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	public IndexerPostProcessor[] getIndexerPostProcessors() {
		return _indexerPostProcessors;
	}

	@Override
	public String[] getSearchClassNames() {
		return new String[] {getClassName()};
	}

	@Override
	public String getSearchEngineId() {
		if (_searchEngineId != null) {
			return _searchEngineId;
		}

		Class<?> clazz = getClass();

		String searchEngineId = GetterUtil.getString(
			PropsUtil.get(
				PropsKeys.INDEX_SEARCH_ENGINE_ID,
				new com.liferay.portal.kernel.configuration.Filter(
					clazz.getName())));

		if (Validator.isNotNull(searchEngineId)) {
			SearchEngine searchEngine = SearchEngineHelperUtil.getSearchEngine(
				searchEngineId);

			if (searchEngine != null) {
				_searchEngineId = searchEngineId;
			}
		}

		if (_searchEngineId == null) {
			_searchEngineId = SearchEngineHelperUtil.getDefaultSearchEngineId();
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Search engine ID for ", clazz.getName(), " is ",
					searchEngineId));
		}

		return _searchEngineId;
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             com.liferay.portal.search.sort.SortFieldBuilder
	 *             #getSortField}
	 */
	@Deprecated
	@Override
	public String getSortField(String orderByCol) {
		String sortField = doGetSortField(orderByCol);

		if (_document.isDocumentSortableTextField(sortField)) {
			return Field.getSortableFieldName(sortField);
		}

		return sortField;
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             com.liferay.portal.search.sort.SortFieldBuilder
	 *             #getSortField}
	 */
	@Deprecated
	@Override
	public String getSortField(String orderByCol, int sortType) {
		if ((sortType == Sort.DOUBLE_TYPE) || (sortType == Sort.FLOAT_TYPE) ||
			(sortType == Sort.INT_TYPE) || (sortType == Sort.LONG_TYPE)) {

			return Field.getSortableFieldName(orderByCol);
		}

		return getSortField(orderByCol);
	}

	@Override
	public Summary getSummary(
			Document document, String snippet, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws SearchException {

		try {
			Locale locale = getLocale(portletRequest);

			Summary summary = doGetSummary(
				document, locale, snippet, portletRequest, portletResponse);

			for (IndexerPostProcessor indexerPostProcessor :
					_indexerPostProcessors) {

				indexerPostProcessor.postProcessSummary(
					summary, document, locale, snippet);
			}

			return summary;
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getClassName());
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		return true;
	}

	@Override
	public boolean isCommitImmediately() {
		return _commitImmediately;
	}

	@Override
	public boolean isFilterSearch() {
		return _filterSearch;
	}

	@Override
	public boolean isIndexerEnabled() {
		if (_indexerEnabled == null) {
			String indexerEnabled = PropsUtil.get(
				PropsKeys.INDEXER_ENABLED,
				new com.liferay.portal.kernel.configuration.Filter(
					getClassName()));

			_indexerEnabled = GetterUtil.getBoolean(indexerEnabled, true);

			return _indexerEnabled;
		}

		return _indexerEnabled;
	}

	@Override
	public boolean isPermissionAware() {
		return _permissionAware;
	}

	public boolean isSelectAllLocales() {
		return _selectAllLocales;
	}

	@Override
	public boolean isStagingAware() {
		return _stagingAware;
	}

	@Override
	public boolean isVisible(long classPK, int status) throws Exception {
		return true;
	}

	/**
	 * @param      classPK
	 * @param      status
	 * @return
	 * @throws     Exception
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             RelatedEntryIndexer.isVisibleRelatedEntry(long, int)}
	 */
	@Deprecated
	@Override
	public boolean isVisibleRelatedEntry(long classPK, int status)
		throws Exception {

		return true;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #postProcessContextBooleanFilter(BooleanFilter,
	 *             SearchContext)}
	 */
	@Deprecated
	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		if (Validator.isNull(searchContext.getKeywords())) {
			addSearchTerm(searchQuery, searchContext, Field.DESCRIPTION, false);
			addSearchTerm(searchQuery, searchContext, Field.TITLE, false);
			addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);
		}
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #postProcessSearchQuery(BooleanQuery, BooleanFilter,
	 *             SearchContext)}
	 */
	@Deprecated
	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {
	}

	@Override
	public void registerIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {

		List<IndexerPostProcessor> indexerPostProcessorsList =
			ListUtil.fromArray(_indexerPostProcessors);

		indexerPostProcessorsList.add(indexerPostProcessor);

		_indexerPostProcessors = indexerPostProcessorsList.toArray(
			new IndexerPostProcessor[0]);
	}

	@Override
	public void reindex(Collection<T> collection) {
		if (IndexWriterHelperUtil.isIndexReadOnly() ||
			IndexWriterHelperUtil.isIndexReadOnly(getClassName()) ||
			!isIndexerEnabled() || collection.isEmpty()) {

			return;
		}

		for (T element : collection) {
			try {
				reindex(element);
			}
			catch (SearchException se) {
				_log.error("Unable to index object: " + element, se);
			}
		}
	}

	@Override
	public void reindex(String className, long classPK) throws SearchException {
		try {
			if (IndexWriterHelperUtil.isIndexReadOnly() ||
				IndexWriterHelperUtil.isIndexReadOnly(getClassName()) ||
				!isIndexerEnabled() || (classPK <= 0)) {

				return;
			}

			doReindex(className, classPK);
		}
		catch (NoSuchModelException nsme) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to index ", className, " ", classPK),
					nsme);
			}
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	public void reindex(String[] ids) throws SearchException {
		long companyThreadLocalCompanyId = CompanyThreadLocal.getCompanyId();

		try {
			if (IndexWriterHelperUtil.isIndexReadOnly() ||
				IndexWriterHelperUtil.isIndexReadOnly(getClassName()) ||
				!isIndexerEnabled()) {

				return;
			}

			if (ids.length > 0) {
				long companyId = GetterUtil.getLong(ids[0]);

				CompanyThreadLocal.setCompanyId(companyId);
			}

			doReindex(ids);
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
		finally {
			CompanyThreadLocal.setCompanyId(companyThreadLocalCompanyId);
		}
	}

	@Override
	public void reindex(T object) throws SearchException {
		try {
			if (IndexWriterHelperUtil.isIndexReadOnly() ||
				IndexWriterHelperUtil.isIndexReadOnly(getClassName()) ||
				!isIndexerEnabled()) {

				return;
			}

			if (object == null) {
				return;
			}

			doReindex(object);
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	public Hits search(SearchContext searchContext) throws SearchException {
		try {
			Hits hits = null;

			QueryConfig queryConfig = searchContext.getQueryConfig();

			addDefaultHighlightFieldNames(queryConfig);

			if (ArrayUtil.isEmpty(queryConfig.getSelectedFieldNames())) {
				addDefaultSelectedFieldNames(searchContext);
			}

			addFacetSelectedFieldNames(searchContext, queryConfig);

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if ((permissionChecker != null) &&
				isUseSearchResultPermissionFilter(searchContext)) {

				if (searchContext.getUserId() == 0) {
					searchContext.setUserId(permissionChecker.getUserId());
				}

				SearchResultPermissionFilter searchResultPermissionFilter =
					_searchResultPermissionFilterFactory.create(
						this::doSearch, permissionChecker);

				hits = searchResultPermissionFilter.search(searchContext);
			}
			else {
				hits = doSearch(searchContext);
			}

			processHits(searchContext, hits);

			return hits;
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	public Hits search(
			SearchContext searchContext, String... selectedFieldNames)
		throws SearchException {

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setSelectedFieldNames(selectedFieldNames);

		return search(searchContext);
	}

	@Override
	public long searchCount(SearchContext searchContext)
		throws SearchException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((permissionChecker != null) &&
			isUseSearchResultPermissionFilter(searchContext)) {

			Hits hits = search(searchContext);

			return hits.getLength();
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setHitsProcessingEnabled(false);
		queryConfig.setScoreEnabled(false);
		queryConfig.setQueryIndexingEnabled(false);
		queryConfig.setQuerySuggestionEnabled(false);

		searchContext.setSearchEngineId(getSearchEngineId());

		BooleanQuery fullQuery = getFullQuery(searchContext);

		fullQuery.setQueryConfig(queryConfig);

		return IndexSearcherHelperUtil.searchCount(searchContext, fullQuery);
	}

	public void setCommitImmediately(boolean commitImmediately) {
		_commitImmediately = commitImmediately;
	}

	@Override
	public void setIndexerEnabled(boolean indexerEnabled) {
		_indexerEnabled = indexerEnabled;
	}

	public void setSelectAllLocales(boolean selectAllLocales) {
		_selectAllLocales = selectAllLocales;
	}

	@Override
	public void unregisterIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {

		List<IndexerPostProcessor> indexerPostProcessorsList =
			ListUtil.fromArray(_indexerPostProcessors);

		indexerPostProcessorsList.remove(indexerPostProcessor);

		_indexerPostProcessors = indexerPostProcessorsList.toArray(
			new IndexerPostProcessor[0]);
	}

	/**
	 * @param      document
	 * @param      className
	 * @param      classPK
	 * @deprecated As of Judson (7.1.x), no direct replacement. Logic now
	 *             encapsulated in {@link
	 *             com.liferay.portal.search.internal.contributor.
	 *             document.AssetDocumentContrbutor}
	 */
	@Deprecated
	protected void addAssetFields(
		Document document, String className, long classPK) {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if ((assetRendererFactory == null) ||
			!assetRendererFactory.isSelectable()) {

			return;
		}

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			className, classPK);

		if (assetEntry == null) {
			return;
		}

		if (!document.hasField(Field.CREATE_DATE)) {
			document.addDate(Field.CREATE_DATE, assetEntry.getCreateDate());
		}

		if (assetEntry.getExpirationDate() != null) {
			document.addDate(
				Field.EXPIRATION_DATE, assetEntry.getExpirationDate());
		}
		else {
			document.addDate(Field.EXPIRATION_DATE, new Date(Long.MAX_VALUE));
		}

		if (!document.hasField(Field.MODIFIED_DATE)) {
			document.addDate(Field.MODIFIED_DATE, assetEntry.getModifiedDate());
		}

		document.addNumber(Field.PRIORITY, assetEntry.getPriority());

		if (assetEntry.getPublishDate() != null) {
			document.addDate(Field.PUBLISH_DATE, assetEntry.getPublishDate());
		}
		else {
			document.addDate(Field.PUBLISH_DATE, new Date(0));
		}

		document.addLocalizedKeyword(
			"localized_title",
			populateMap(assetEntry, assetEntry.getTitleMap()), true, true);
		document.addKeyword("visible", assetEntry.isVisible());
	}

	protected void addDefaultHighlightFieldNames(QueryConfig queryConfig) {
		queryConfig.addHighlightFieldNames(Field.ASSET_CATEGORY_TITLES);

		if (queryConfig.isHighlightEnabled()) {
			queryConfig.addHighlightFieldNames(
				Field.CONTENT, Field.DESCRIPTION, Field.TITLE);
		}
	}

	protected void addDefaultSelectedFieldNames(SearchContext searchContext) {
		QueryConfig queryConfig = searchContext.getQueryConfig();

		Set<String> selectedFieldNames = null;

		if (!ArrayUtil.isEmpty(getDefaultSelectedFieldNames())) {
			selectedFieldNames = SetUtil.fromArray(
				getDefaultSelectedFieldNames());

			if (searchContext.isIncludeAttachments() ||
				searchContext.isIncludeDiscussions()) {

				selectedFieldNames.add(Field.CLASS_NAME_ID);
				selectedFieldNames.add(Field.CLASS_PK);
			}
		}

		if (!ArrayUtil.isEmpty(getDefaultSelectedLocalizedFieldNames())) {
			if (selectedFieldNames == null) {
				selectedFieldNames = new HashSet<>();
			}

			if (isSelectAllLocales()) {
				addSelectedLocalizedFieldNames(
					selectedFieldNames,
					LocaleUtil.toLanguageIds(
						LanguageUtil.getSupportedLocales()));
			}
			else {
				addSelectedLocalizedFieldNames(
					selectedFieldNames,
					LocaleUtil.toLanguageId(queryConfig.getLocale()));
			}
		}

		if ((selectedFieldNames != null) && !selectedFieldNames.isEmpty()) {
			queryConfig.setSelectedFieldNames(
				selectedFieldNames.toArray(new String[0]));
		}
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	protected void addFacetClause(
			SearchContext searchContext, BooleanFilter facetBooleanFilter,
			Collection<Facet> facets)
		throws ParseException {

		BooleanQuery facetBooleanQuery = new BooleanQueryImpl();

		for (Facet facet : facets) {
			BooleanClause<Query> facetBooleanClause = facet.getFacetClause();

			if (facetBooleanClause != null) {
				facetBooleanQuery.add(
					facetBooleanClause.getClause(),
					facetBooleanClause.getBooleanClauseOccur());
			}
		}

		if (!facetBooleanQuery.hasClauses()) {
			return;
		}

		QueryFilter queryFilter = new QueryFilter(facetBooleanQuery);

		facetBooleanFilter.add(queryFilter, BooleanClauseOccur.MUST);
	}

	protected void addFacetSelectedFieldNames(
		SearchContext searchContext, QueryConfig queryConfig) {

		String[] selectedFieldNames = queryConfig.getSelectedFieldNames();

		if (ArrayUtil.isEmpty(selectedFieldNames) ||
			((selectedFieldNames.length == 1) &&
			 selectedFieldNames[0].equals(Field.ANY))) {

			return;
		}

		Set<String> selectedFieldNameSet = SetUtil.fromArray(
			selectedFieldNames);

		Map<String, Facet> facets = searchContext.getFacets();

		selectedFieldNameSet.addAll(facets.keySet());

		selectedFieldNames = selectedFieldNameSet.toArray(new String[0]);

		queryConfig.setSelectedFieldNames(selectedFieldNames);
	}

	protected void addLocalizedField(
		Document document, String field, Locale siteDefaultLocale,
		Map<Locale, String> map) {

		for (Map.Entry<Locale, String> entry : map.entrySet()) {
			Locale locale = entry.getKey();

			if (locale.equals(siteDefaultLocale)) {
				document.addText(field, entry.getValue());
			}

			String languageId = LocaleUtil.toLanguageId(locale);

			document.addText(
				LocalizationUtil.getLocalizedName(field, languageId),
				entry.getValue());
		}
	}

	protected void addSearchAssetCategoryIds(
			BooleanFilter queryBooleanFilter, SearchContext searchContext)
		throws Exception {

		MultiValueFacet multiValueFacet = new MultiValueFacet(searchContext);

		multiValueFacet.setFieldName(Field.ASSET_CATEGORY_IDS);
		multiValueFacet.setStatic(true);
		multiValueFacet.setValues(searchContext.getAssetCategoryIds());

		searchContext.addFacet(multiValueFacet);
	}

	/**
	 * @param      document
	 * @param      field
	 * @param      assetCategories
	 * @deprecated As of Judson (7.1.x), no direct replacement. Logic not
	 *             encapsulated in {@link
	 *             com.liferay.portal.search.internal.contributor.
	 *             document.AssetCategoryDocumentContrbutor}
	 */
	@Deprecated
	protected void addSearchAssetCategoryTitles(
		Document document, String field, List<AssetCategory> assetCategories) {

		Map<Locale, List<String>> assetCategoryTitles = new HashMap<>();

		Locale defaultLocale = LocaleUtil.getDefault();

		for (AssetCategory assetCategory : assetCategories) {
			Map<Locale, String> titleMap = assetCategory.getTitleMap();

			for (Map.Entry<Locale, String> entry : titleMap.entrySet()) {
				String title = entry.getValue();

				if (Validator.isNull(title)) {
					continue;
				}

				Locale locale = entry.getKey();

				List<String> titles = assetCategoryTitles.get(locale);

				if (titles == null) {
					titles = new ArrayList<>();

					assetCategoryTitles.put(locale, titles);
				}

				titles.add(StringUtil.toLowerCase(title));
			}
		}

		for (Map.Entry<Locale, List<String>> entry :
				assetCategoryTitles.entrySet()) {

			Locale locale = entry.getKey();

			List<String> titles = entry.getValue();

			String[] titlesArray = titles.toArray(new String[0]);

			if (locale.equals(defaultLocale)) {
				document.addText(field, titlesArray);
			}

			document.addText(
				field.concat(
					StringPool.UNDERLINE
				).concat(
					locale.toString()
				),
				titlesArray);
		}
	}

	protected void addSearchAssetTagNames(
			BooleanFilter queryBooleanFilter, SearchContext searchContext)
		throws Exception {

		MultiValueFacet multiValueFacet = new MultiValueFacet(searchContext);

		multiValueFacet.setFieldName(Field.ASSET_TAG_NAMES);
		multiValueFacet.setStatic(true);
		multiValueFacet.setValues(searchContext.getAssetTagNames());

		searchContext.addFacet(multiValueFacet);
	}

	protected Filter addSearchClassTypeIds(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		long[] classTypeIds = searchContext.getClassTypeIds();

		if (ArrayUtil.isEmpty(classTypeIds)) {
			return null;
		}

		TermsFilter classTypeIdsTermsFilter = new TermsFilter(
			Field.CLASS_TYPE_ID);

		classTypeIdsTermsFilter.addValues(
			ArrayUtil.toStringArray(classTypeIds));

		return contextBooleanFilter.add(
			classTypeIdsTermsFilter, BooleanClauseOccur.MUST);
	}

	protected void addSearchEntryClassNames(
			BooleanFilter queryBooleanFilter, SearchContext searchContext)
		throws Exception {
	}

	protected Map<String, Query> addSearchExpando(
			BooleanQuery searchQuery, SearchContext searchContext,
			String keywords)
		throws Exception {

		_expandoQueryContributor.contribute(
			keywords, searchQuery, getSearchClassNames(), searchContext);

		return new HashMap<>();
	}

	protected void addSearchFolderId(
			BooleanFilter queryBooleanFilter, SearchContext searchContext)
		throws Exception {

		MultiValueFacet multiValueFacet = new MultiValueFacet(searchContext);

		multiValueFacet.setFieldName(Field.TREE_PATH);
		multiValueFacet.setStatic(true);

		long[] folderIds = searchContext.getFolderIds();

		if (ArrayUtil.isNotEmpty(folderIds)) {
			folderIds = ArrayUtil.remove(folderIds, _DEFAULT_FOLDER_ID);

			multiValueFacet.setValues(folderIds);
		}

		searchContext.addFacet(multiValueFacet);
	}

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	protected void addSearchGroupId(
			BooleanFilter queryBooleanFilter, SearchContext searchContext)
		throws Exception {
	}

	protected Map<String, Query> addSearchKeywords(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		return addSearchExpando(
			searchQuery, searchContext, searchContext.getKeywords());
	}

	protected void addSearchLayout(
			BooleanFilter queryBooleanFilter, SearchContext searchContext)
		throws Exception {

		MultiValueFacet multiValueFacet = new MultiValueFacet(searchContext);

		multiValueFacet.setFieldName(Field.LAYOUT_UUID);
		multiValueFacet.setStatic(true);

		searchContext.addFacet(multiValueFacet);
	}

	protected Map<String, Query> addSearchLocalizedTerm(
			BooleanQuery searchQuery, SearchContext searchContext, String field,
			boolean like)
		throws Exception {

		Map<String, Query> queries = new HashMap<>();

		Query query = addSearchTerm(searchQuery, searchContext, field, like);

		queries.put(field, query);

		String localizedFieldName = Field.getLocalizedName(
			searchContext.getLocale(), field);

		Query localizedQuery = addSearchTerm(
			searchQuery, searchContext, localizedFieldName, like);

		queries.put(localizedFieldName, localizedQuery);

		return queries;
	}

	protected Query addSearchTerm(
			BooleanQuery searchQuery, SearchContext searchContext, String field,
			boolean like)
		throws Exception {

		if (Validator.isNull(field)) {
			return null;
		}

		String value = null;

		Serializable serializable = searchContext.getAttribute(field);

		if (serializable != null) {
			Class<?> clazz = serializable.getClass();

			if (clazz.isArray()) {
				value = StringUtil.merge((Object[])serializable);
			}
			else {
				value = GetterUtil.getString(serializable);
			}
		}
		else {
			value = GetterUtil.getString(serializable);
		}

		if (Validator.isNotNull(value) &&
			(searchContext.getFacet(field) != null)) {

			return null;
		}

		if (Validator.isNull(value)) {
			value = searchContext.getKeywords();
		}

		if (Validator.isNull(value)) {
			return null;
		}

		Query query = null;

		if (searchContext.isAndSearch()) {
			query = searchQuery.addRequiredTerm(field, value, like);
		}
		else {
			query = searchQuery.addTerm(field, value, like);
		}

		return query;
	}

	protected void addSearchUserId(
			BooleanFilter queryBooleanFilter, SearchContext searchContext)
		throws Exception {

		MultiValueFacet multiValueFacet = new MultiValueFacet(searchContext);

		multiValueFacet.setFieldName(Field.USER_ID);
		multiValueFacet.setStatic(true);

		long userId = GetterUtil.getLong(
			searchContext.getAttribute(Field.USER_ID));

		if (userId > 0) {
			multiValueFacet.setValues(new long[] {userId});
		}

		searchContext.addFacet(multiValueFacet);
	}

	protected void addSelectedLocalizedFieldNames(
		Set<String> selectedFieldNames, String... languageIds) {

		for (String defaultLocalizedSelectedFieldName :
				getDefaultSelectedLocalizedFieldNames()) {

			selectedFieldNames.add(defaultLocalizedSelectedFieldName);

			for (String languageId : languageIds) {
				String localizedFieldName = LocalizationUtil.getLocalizedName(
					defaultLocalizedSelectedFieldName, languageId);

				selectedFieldNames.add(localizedFieldName);
			}
		}
	}

	protected void addStagingGroupKeyword(Document document, long groupId) {
		if (!isStagingAware()) {
			return;
		}

		document.addKeyword(Field.STAGING_GROUP, isStagingGroup(groupId));
	}

	protected void addStatus(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		int[] statuses = GetterUtil.getIntegerValues(
			searchContext.getAttribute(Field.STATUS), null);

		if (ArrayUtil.isEmpty(statuses)) {
			int status = GetterUtil.getInteger(
				searchContext.getAttribute(Field.STATUS),
				WorkflowConstants.STATUS_APPROVED);

			statuses = new int[] {status};
		}

		if (!ArrayUtil.contains(statuses, WorkflowConstants.STATUS_ANY)) {
			TermsFilter statusesTermsFilter = new TermsFilter(Field.STATUS);

			statusesTermsFilter.addValues(ArrayUtil.toStringArray(statuses));

			contextBooleanFilter.add(
				statusesTermsFilter, BooleanClauseOccur.MUST);
		}
		else {
			contextBooleanFilter.addTerm(
				Field.STATUS, String.valueOf(WorkflowConstants.STATUS_IN_TRASH),
				BooleanClauseOccur.MUST_NOT);
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected void addTrashFields(
		Document document, TrashedModel trashedModel) {
	}

	protected BooleanQuery createFullQuery(
			BooleanFilter fullQueryBooleanFilter, SearchContext searchContext)
		throws Exception {

		BooleanQuery searchQuery = new BooleanQueryImpl();

		addSearchKeywords(searchQuery, searchContext);

		_addSearchTerms(searchQuery, fullQueryBooleanFilter, searchContext);

		doPostProcessSearchQuery(this, searchQuery, searchContext);

		Map<String, Facet> facets = searchContext.getFacets();

		BooleanFilter facetBooleanFilter = new BooleanFilter();

		addFacetClause(searchContext, facetBooleanFilter, facets.values());

		if (facetBooleanFilter.hasClauses()) {
			fullQueryBooleanFilter.add(
				facetBooleanFilter, BooleanClauseOccur.MUST);
		}

		BooleanQuery fullBooleanQuery = new BooleanQueryImpl();

		if (fullQueryBooleanFilter.hasClauses()) {
			fullBooleanQuery.setPreBooleanFilter(fullQueryBooleanFilter);
		}

		if (searchQuery.hasClauses()) {
			fullBooleanQuery.add(searchQuery, BooleanClauseOccur.MUST);
		}

		BooleanClause<Query>[] booleanClauses =
			searchContext.getBooleanClauses();

		if (booleanClauses != null) {
			for (BooleanClause<Query> booleanClause : booleanClauses) {
				fullBooleanQuery.add(
					booleanClause.getClause(),
					booleanClause.getBooleanClauseOccur());
			}
		}

		postProcessFullQuery(fullBooleanQuery, searchContext);

		for (IndexerPostProcessor indexerPostProcessor :
				_indexerPostProcessors) {

			indexerPostProcessor.postProcessFullQuery(
				fullBooleanQuery, searchContext);
		}

		return fullBooleanQuery;
	}

	protected Summary createSummary(Document document) {
		return createSummary(document, Field.TITLE, Field.CONTENT);
	}

	protected Summary createSummary(
		Document document, String titleField, String contentField) {

		String prefix = Field.SNIPPET + StringPool.UNDERLINE;

		String title = document.get(prefix + titleField, titleField);
		String content = document.get(prefix + contentField, contentField);

		return new Summary(title, content);
	}

	protected void deleteDocument(long companyId, long field1)
		throws Exception {

		deleteDocument(companyId, String.valueOf(field1));
	}

	protected void deleteDocument(long companyId, long field1, String field2)
		throws Exception {

		deleteDocument(companyId, String.valueOf(field1), field2);
	}

	protected void deleteDocument(long companyId, String field1)
		throws Exception {

		Document document = new DocumentImpl();

		document.addUID(getClassName(), field1);

		IndexWriterHelperUtil.deleteDocument(
			getSearchEngineId(), companyId, document.get(Field.UID),
			_commitImmediately);
	}

	protected void deleteDocument(long companyId, String field1, String field2)
		throws Exception {

		Document document = new DocumentImpl();

		document.addUID(getClassName(), field1, field2);

		IndexWriterHelperUtil.deleteDocument(
			getSearchEngineId(), companyId, document.get(Field.UID),
			_commitImmediately);
	}

	protected abstract void doDelete(T object) throws Exception;

	protected abstract Document doGetDocument(T object) throws Exception;

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             com.liferay.portal.search.contributor.sort.
	 *             SortFieldTranslator}
	 */
	@Deprecated
	protected String doGetSortField(String orderByCol) {
		return orderByCol;
	}

	protected abstract Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception;

	/**
	 * @deprecated As of Wilberforce (7.0.x), added strictly to support
	 *             backwards compatibility of {@link
	 *             Indexer#postProcessSearchQuery(BooleanQuery, SearchContext)}
	 */
	@Deprecated
	protected void doPostProcessSearchQuery(
			Indexer<?> indexer, BooleanQuery searchQuery,
			SearchContext searchContext)
		throws Exception {

		indexer.postProcessSearchQuery(searchQuery, searchContext);
	}

	protected abstract void doReindex(String className, long classPK)
		throws Exception;

	protected abstract void doReindex(String[] ids) throws Exception;

	protected abstract void doReindex(T object) throws Exception;

	protected Hits doSearch(SearchContext searchContext)
		throws SearchException {

		searchContext.setSearchEngineId(getSearchEngineId());

		Query fullQuery = getFullQuery(searchContext);

		if (!fullQuery.hasChildren()) {
			BooleanFilter preBooleanFilter = fullQuery.getPreBooleanFilter();

			fullQuery = new MatchAllQuery();

			fullQuery.setPreBooleanFilter(preBooleanFilter);
		}

		fullQuery.setQueryConfig(searchContext.getQueryConfig());

		return IndexSearcherHelperUtil.search(searchContext, fullQuery);
	}

	protected Document getBaseModelDocument(
		String portletId, BaseModel<?> baseModel) {

		return getBaseModelDocument(portletId, baseModel, baseModel);
	}

	protected Document getBaseModelDocument(
		String portletId, BaseModel<?> baseModel,
		BaseModel<?> workflowedBaseModel) {

		Document document = newDocument();

		String className = baseModel.getModelClassName();

		long classPK = 0;
		long resourcePrimKey = 0;

		if (baseModel instanceof ResourcedModel) {
			ResourcedModel resourcedModel = (ResourcedModel)baseModel;

			classPK = resourcedModel.getResourcePrimKey();
			resourcePrimKey = resourcedModel.getResourcePrimKey();
		}
		else {
			classPK = (Long)baseModel.getPrimaryKeyObj();
		}

		DocumentHelper documentHelper = new DocumentHelper(document);

		documentHelper.setEntryKey(className, classPK);

		document.addUID(className, classPK);

		if (resourcePrimKey > 0) {
			document.addKeyword(Field.ROOT_ENTRY_CLASS_PK, resourcePrimKey);
		}

		if (workflowedBaseModel instanceof WorkflowedModel) {
			WorkflowedModel workflowedModel =
				(WorkflowedModel)workflowedBaseModel;

			document.addKeyword(Field.STATUS, workflowedModel.getStatus());
		}

		for (DocumentContributor documentContributor :
				getDocumentContributors()) {

			documentContributor.contribute(document, baseModel);
		}

		return document;
	}

	protected String getClassName(SearchContext searchContext) {
		return getClassName();
	}

	protected String[] getDefaultSelectedFieldNames() {
		return _defaultSelectedFieldNames;
	}

	protected String[] getDefaultSelectedLocalizedFieldNames() {
		return _defaultSelectedLocalizedFieldNames;
	}

	protected List<DocumentContributor> getDocumentContributors() {
		if (_documentContributors != null) {
			return _documentContributors;
		}

		_documentContributors = ServiceTrackerCollections.openList(
			DocumentContributor.class);

		return _documentContributors;
	}

	protected String getExpandoFieldName(
		SearchContext searchContext, ExpandoBridge expandoBridge,
		String attributeName) {

		return null;
	}

	protected List<ExpandoQueryContributor> getExpandoQueryContributors() {
		return Collections.singletonList(_expandoQueryContributor);
	}

	protected Locale getLocale(PortletRequest portletRequest) {
		if (portletRequest != null) {
			return portletRequest.getLocale();
		}

		return LocaleUtil.getMostRelevantLocale();
	}

	protected Set<String> getLocalizedCountryNames(Country country) {
		Set<String> countryNames = new HashSet<>();

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			String countryName = country.getName(locale);

			countryName = StringUtil.toLowerCase(countryName);

			countryNames.add(countryName);
		}

		return countryNames;
	}

	protected Group getSiteGroup(long groupId) {
		Group group = null;

		try {
			group = GroupLocalServiceUtil.getGroup(groupId);

			if (group.isLayout()) {
				group = group.getParentGroup();
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get site group", pe);
			}
		}

		return group;
	}

	protected long getSiteGroupId(long groupId) {
		Group group = getSiteGroup(groupId);

		if (group == null) {
			return groupId;
		}

		return group.getGroupId();
	}

	protected Locale getSnippetLocale(Document document, Locale locale) {
		String prefix = Field.SNIPPET + StringPool.UNDERLINE;

		String localizedAssetCategoryTitlesName =
			prefix +
				Field.getLocalizedName(locale, Field.ASSET_CATEGORY_TITLES);
		String localizedContentName =
			prefix + Field.getLocalizedName(locale, Field.CONTENT);
		String localizedDescriptionName =
			prefix + Field.getLocalizedName(locale, Field.DESCRIPTION);
		String localizedTitleName =
			prefix + Field.getLocalizedName(locale, Field.TITLE);

		if ((document.getField(localizedAssetCategoryTitlesName) != null) ||
			(document.getField(localizedContentName) != null) ||
			(document.getField(localizedDescriptionName) != null) ||
			(document.getField(localizedTitleName) != null)) {

			return locale;
		}

		return null;
	}

	protected boolean isStagingGroup(long groupId) {
		Group group = getSiteGroup(groupId);

		if (group == null) {
			return false;
		}

		return group.isStagingGroup();
	}

	protected boolean isUseSearchResultPermissionFilter(
		SearchContext searchContext) {

		return isFilterSearch();
	}

	protected boolean isVisible(int entryStatus, int queryStatus) {
		if (((queryStatus != WorkflowConstants.STATUS_ANY) &&
			 (entryStatus == queryStatus)) ||
			(entryStatus != WorkflowConstants.STATUS_IN_TRASH)) {

			return true;
		}

		return false;
	}

	protected Document newDocument() {
		return (Document)_document.clone();
	}

	protected void populateAddresses(
			Document document, List<Address> addresses, long regionId,
			long countryId)
		throws PortalException {

		List<String> cities = new ArrayList<>();

		List<String> countries = new ArrayList<>();

		if (countryId > 0) {
			try {
				countries.addAll(
					getLocalizedCountryNames(
						CountryServiceUtil.getCountry(countryId)));
			}
			catch (NoSuchCountryException nsce) {
				if (_log.isWarnEnabled()) {
					_log.warn(nsce.getMessage());
				}
			}
		}

		List<String> regions = new ArrayList<>();

		if (regionId > 0) {
			try {
				Region region = RegionServiceUtil.getRegion(regionId);

				regions.add(StringUtil.toLowerCase(region.getName()));
			}
			catch (NoSuchRegionException nsre) {
				if (_log.isWarnEnabled()) {
					_log.warn(nsre.getMessage());
				}
			}
		}

		List<String> streets = new ArrayList<>();
		List<String> zips = new ArrayList<>();

		for (Address address : addresses) {
			cities.add(StringUtil.toLowerCase(address.getCity()));
			countries.addAll(getLocalizedCountryNames(address.getCountry()));

			Region region = address.getRegion();

			regions.add(StringUtil.toLowerCase(region.getName()));

			streets.add(StringUtil.toLowerCase(address.getStreet1()));
			streets.add(StringUtil.toLowerCase(address.getStreet2()));
			streets.add(StringUtil.toLowerCase(address.getStreet3()));
			zips.add(StringUtil.toLowerCase(address.getZip()));
		}

		document.addText("city", cities.toArray(new String[0]));
		document.addText("country", countries.toArray(new String[0]));
		document.addText("region", regions.toArray(new String[0]));
		document.addText("street", streets.toArray(new String[0]));
		document.addText("zip", zips.toArray(new String[0]));
	}

	protected Map<Locale, String> populateMap(
		AssetEntry assetEntry, Map<Locale, String> map) {

		return LocalizationUtil.populateLocalizationMap(
			map, assetEntry.getDefaultLanguageId(), assetEntry.getGroupId());
	}

	protected void postProcessFullQuery(
			BooleanQuery fullQuery, SearchContext searchContext)
		throws Exception {
	}

	protected void processHits(SearchContext searchContext, Hits hits)
		throws SearchException {

		HitsProcessorRegistryUtil.process(searchContext, hits);
	}

	protected void resetFullQuery(SearchContext searchContext) {
		searchContext.clearFullQueryEntryClassNames();

		for (RelatedEntryIndexer relatedEntryIndexer :
				RelatedEntryIndexerRegistryUtil.getRelatedEntryIndexers()) {

			relatedEntryIndexer.updateFullQuery(searchContext);
		}
	}

	protected void setDefaultSelectedFieldNames(
		String... defaultLocalizedFieldNames) {

		_defaultSelectedFieldNames = defaultLocalizedFieldNames;
	}

	protected void setDefaultSelectedLocalizedFieldNames(
		String... defaultLocalizedFieldNames) {

		_defaultSelectedLocalizedFieldNames = defaultLocalizedFieldNames;
	}

	protected void setFilterSearch(boolean filterSearch) {
		_filterSearch = filterSearch;
	}

	protected void setPermissionAware(boolean permissionAware) {
		_permissionAware = permissionAware;
	}

	protected void setStagingAware(boolean stagingAware) {
		_stagingAware = stagingAware;
	}

	private void _addPreFilters(
			BooleanFilter queryBooleanFilter,
			Map<String, Indexer<?>> entryClassNameIndexerMap,
			SearchContext searchContext)
		throws Exception {

		_preFilterContributor.contribute(
			queryBooleanFilter, entryClassNameIndexerMap, searchContext);
	}

	private void _addSearchTerms(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		postProcessSearchQuery(
			searchQuery, fullQueryBooleanFilter, searchContext);

		for (IndexerPostProcessor indexerPostProcessor :
				_indexerPostProcessors) {

			indexerPostProcessor.postProcessSearchQuery(
				searchQuery, fullQueryBooleanFilter, searchContext);
		}
	}

	private Map<String, Indexer<?>> _getEntryClassNameIndexerMap(
		String[] entryClassNames, String searchEngineId) {

		Map<String, Indexer<?>> entryClassNameIndexerMap =
			new LinkedHashMap<>();

		for (String entryClassName : entryClassNames) {
			Indexer<?> indexer = IndexerRegistryUtil.getIndexer(entryClassName);

			if (indexer == null) {
				continue;
			}

			if (!searchEngineId.equals(indexer.getSearchEngineId())) {
				continue;
			}

			entryClassNameIndexerMap.put(entryClassName, indexer);
		}

		return entryClassNameIndexerMap;
	}

	private static final long _DEFAULT_FOLDER_ID = 0L;

	private static final Log _log = LogFactoryUtil.getLog(BaseIndexer.class);

	private static volatile ExpandoQueryContributor _expandoQueryContributor =
		ServiceProxyFactory.newServiceTrackedInstance(
			ExpandoQueryContributor.class, BaseIndexer.class,
			"_expandoQueryContributor", false);
	private static volatile PreFilterContributor _preFilterContributor =
		ServiceProxyFactory.newServiceTrackedInstance(
			PreFilterContributor.class, BaseIndexer.class,
			"_preFilterContributor", false);
	private static volatile SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory =
			ServiceProxyFactory.newServiceTrackedInstance(
				SearchResultPermissionFilterFactory.class, BaseIndexer.class,
				"_searchResultPermissionFilterFactory", false);

	private boolean _commitImmediately;
	private String[] _defaultSelectedFieldNames;
	private String[] _defaultSelectedLocalizedFieldNames;
	private final Document _document = new DocumentImpl();
	private List<DocumentContributor> _documentContributors;
	private boolean _filterSearch;
	private Boolean _indexerEnabled;
	private IndexerPostProcessor[] _indexerPostProcessors =
		new IndexerPostProcessor[0];
	private boolean _permissionAware;
	private String _searchEngineId;
	private boolean _selectAllLocales;
	private boolean _stagingAware = true;

}