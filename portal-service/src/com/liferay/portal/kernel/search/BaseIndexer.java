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

package com.liferay.portal.kernel.search;

import com.liferay.portal.NoSuchCountryException;
import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchRegionException;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.facet.AssetEntriesFacet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.MultiValueFacet;
import com.liferay.portal.kernel.search.facet.ScopeFacet;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.AttachedModel;
import com.liferay.portal.model.AuditedModel;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.Country;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupedModel;
import com.liferay.portal.model.Region;
import com.liferay.portal.model.ResourcedModel;
import com.liferay.portal.model.User;
import com.liferay.portal.model.WorkflowedModel;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CountryServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RegionServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.util.DDMIndexerUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 * @author Ryan Park
 * @author Raymond Augé
 */
public abstract class BaseIndexer implements Indexer {

	public static final int INDEX_FILTER_SEARCH_LIMIT = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.INDEX_FILTER_SEARCH_LIMIT));

	public BaseIndexer() {
		_document = new DocumentImpl();
	}

	@Override
	public void addRelatedEntryFields(Document document, Object obj)
		throws Exception {
	}

	@Override
	public void delete(long companyId, String uid) throws SearchException {
		try {
			SearchEngineUtil.deleteDocument(
				getSearchEngineId(), companyId, uid);
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	public void delete(Object obj) throws SearchException {
		try {
			doDelete(obj);
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	public Document getDocument(Object obj) throws SearchException {
		try {
			Document document = doGetDocument(obj);

			for (IndexerPostProcessor indexerPostProcessor :
					_indexerPostProcessors) {

				indexerPostProcessor.postProcessDocument(document, obj);
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
	public BooleanQuery getFacetQuery(
			String className, SearchContext searchContext)
		throws Exception {

		BooleanQuery facetQuery = BooleanQueryFactoryUtil.create(searchContext);

		facetQuery.addExactTerm(Field.ENTRY_CLASS_NAME, className);

		if (searchContext.getUserId() > 0) {
			SearchPermissionChecker searchPermissionChecker =
				SearchEngineUtil.getSearchPermissionChecker();

			facetQuery =
				(BooleanQuery)searchPermissionChecker.getPermissionQuery(
					searchContext.getCompanyId(), searchContext.getGroupIds(),
					searchContext.getUserId(), className, facetQuery,
					searchContext);
		}

		return facetQuery;
	}

	@Override
	public BooleanQuery getFullQuery(SearchContext searchContext)
		throws SearchException {

		try {
			searchContext.setSearchEngineId(getSearchEngineId());

			String[] entryClassNames = getClassNames();

			if (searchContext.isIncludeAttachments()) {
				entryClassNames = ArrayUtil.append(
					entryClassNames, DLFileEntry.class.getName());
			}

			if (searchContext.isIncludeDiscussions()) {
				entryClassNames = ArrayUtil.append(
					entryClassNames, MBMessage.class.getName());

				searchContext.setAttribute("discussion", true);
			}

			searchContext.setEntryClassNames(entryClassNames);

			if (searchContext.isIncludeAttachments() ||
				searchContext.isIncludeDiscussions()) {

				searchContext.setAttribute(
					"relatedEntryClassNames", getClassNames());
			}

			BooleanQuery contextQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			addSearchAssetCategoryIds(contextQuery, searchContext);
			addSearchAssetTagNames(contextQuery, searchContext);
			addSearchEntryClassNames(contextQuery, searchContext);
			addSearchFolderId(contextQuery, searchContext);
			addSearchGroupId(contextQuery, searchContext);
			addSearchLayout(contextQuery, searchContext);
			addSearchUserId(contextQuery, searchContext);

			BooleanQuery fullQuery = createFullQuery(
				contextQuery, searchContext);

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
	public String getSearchEngineId() {
		if (_searchEngineId != null) {
			return _searchEngineId;
		}

		Class<?> clazz = getClass();

		String searchEngineId = GetterUtil.getString(
			PropsUtil.get(
				PropsKeys.INDEX_SEARCH_ENGINE_ID, new Filter(clazz.getName())));

		if (Validator.isNotNull(searchEngineId)) {
			SearchEngine searchEngine = SearchEngineUtil.getSearchEngine(
				searchEngineId);

			if (searchEngine != null) {
				_searchEngineId = searchEngineId;
			}
		}

		if (_searchEngineId == null) {
			_searchEngineId = SearchEngineUtil.getDefaultSearchEngineId();
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Search engine ID for " + clazz.getName() + " is " +
					searchEngineId);
		}

		return _searchEngineId;
	}

	@Override
	public String getSortField(String orderByCol) {
		String sortField = doGetSortField(orderByCol);

		if (_document.isDocumentSortableTextField(sortField)) {
			return DocumentImpl.getSortableFieldName(sortField);
		}

		return sortField;
	}

	@Override
	public Summary getSummary(
			Document document, Locale locale, String snippet,
			PortletURL portletURL)
		throws SearchException {

		try {
			Summary summary = doGetSummary(
				document, locale, snippet, portletURL);

			for (IndexerPostProcessor indexerPostProcessor :
					_indexerPostProcessors) {

				indexerPostProcessor.postProcessSummary(
					summary, document, locale, snippet, portletURL);
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
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		return true;
	}

	@Override
	public boolean isFilterSearch() {
		return _filterSearch;
	}

	public boolean isIndexerEnabled() {
		return _indexerEnabled;
	}

	@Override
	public boolean isPermissionAware() {
		return _permissionAware;
	}

	@Override
	public boolean isStagingAware() {
		return _stagingAware;
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		String keywords = searchContext.getKeywords();

		if (Validator.isNull(keywords)) {
			addSearchTerm(searchQuery, searchContext, Field.DESCRIPTION, false);
			addSearchTerm(searchQuery, searchContext, Field.TITLE, false);
			addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);
		}
	}

	@Override
	public void registerIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {

		List<IndexerPostProcessor> indexerPostProcessorsList =
			ListUtil.fromArray(_indexerPostProcessors);

		indexerPostProcessorsList.add(indexerPostProcessor);

		_indexerPostProcessors = indexerPostProcessorsList.toArray(
			new IndexerPostProcessor[indexerPostProcessorsList.size()]);
	}

	@Override
	public void reindex(Object obj) throws SearchException {
		try {
			if (SearchEngineUtil.isIndexReadOnly() || !isIndexerEnabled()) {
				return;
			}

			doReindex(obj);
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	public void reindex(String className, long classPK) throws SearchException {
		try {
			if (SearchEngineUtil.isIndexReadOnly() || !isIndexerEnabled()) {
				return;
			}

			doReindex(className, classPK);
		}
		catch (NoSuchModelException nsme) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to index " + className + " " + classPK);
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
		try {
			if (SearchEngineUtil.isIndexReadOnly() || !isIndexerEnabled()) {
				return;
			}

			doReindex(ids);
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	public void reindexDDMStructures(List<Long> ddmStructureIds)
		throws SearchException {

		try {
			if (SearchEngineUtil.isIndexReadOnly() || !isIndexerEnabled()) {
				return;
			}

			doReindexDDMStructures(ddmStructureIds);
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
			searchContext.setSearchEngineId(getSearchEngineId());

			BooleanQuery fullQuery = getFullQuery(searchContext);

			fullQuery.setQueryConfig(searchContext.getQueryConfig());

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			int end = searchContext.getEnd();
			int start = searchContext.getStart();

			if (isFilterSearch() && (permissionChecker != null)) {
				searchContext.setEnd(end + INDEX_FILTER_SEARCH_LIMIT);
				searchContext.setStart(0);
			}

			Hits hits = SearchEngineUtil.search(searchContext, fullQuery);

			searchContext.setEnd(end);
			searchContext.setStart(start);

			if (isFilterSearch() && (permissionChecker != null)) {
				hits = filterSearch(hits, permissionChecker, searchContext);
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
	public void unregisterIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {

		List<IndexerPostProcessor> indexerPostProcessorsList =
			ListUtil.fromArray(_indexerPostProcessors);

		indexerPostProcessorsList.remove(indexerPostProcessor);

		_indexerPostProcessors = indexerPostProcessorsList.toArray(
			new IndexerPostProcessor[indexerPostProcessorsList.size()]);
	}

	protected void addAssetFields(
			Document document, String className, long classPK)
		throws SystemException {

		AssetRendererFactory assetRendererFactory =
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

		RatingsStats ratingsStats = RatingsStatsLocalServiceUtil.getStats(
			className, classPK);

		document.addNumber(Field.RATINGS, ratingsStats.getAverageScore());

		document.addNumber(Field.VIEW_COUNT, assetEntry.getViewCount());

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			assetEntry.getTitle(), true);

		document.addLocalizedKeyword("localized_title", titleMap, true);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #addSearchLocalizedTerm(BooleanQuery, SearchContext, String,
	 *             boolean)}
	 */
	protected void addLocalizedSearchTerm(
			BooleanQuery searchQuery, SearchContext searchContext, String field,
			boolean like)
		throws Exception {

		addSearchLocalizedTerm(searchQuery, searchContext, field, like);
	}

	protected void addRelatedClassNames(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		String[] relatedEntryClassNames = (String[])searchContext.getAttribute(
			"relatedEntryClassNames");

		if ((relatedEntryClassNames == null) ||
			(relatedEntryClassNames.length == 0)) {

			return;
		}

		BooleanQuery relatedQueries = BooleanQueryFactoryUtil.create(
			searchContext);

		for (String relatedEntryClassName : relatedEntryClassNames) {
			Indexer indexer = IndexerRegistryUtil.getIndexer(
				relatedEntryClassName);

			if (indexer == null) {
				continue;
			}

			BooleanQuery relatedQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			indexer.postProcessContextQuery(relatedQuery, searchContext);

			relatedQuery.addRequiredTerm(
				Field.CLASS_NAME_ID,
				PortalUtil.getClassNameId(relatedEntryClassName));

			relatedQueries.add(relatedQuery, BooleanClauseOccur.SHOULD);
		}

		contextQuery.add(relatedQueries, BooleanClauseOccur.MUST);
	}

	protected void addSearchArrayQuery(
			BooleanQuery searchQuery, SearchContext searchContext, String field)
		throws Exception {

		if (Validator.isNull(field)) {
			return;
		}

		Object fieldValues = searchContext.getAttribute(field);

		if (fieldValues == null) {
			return;
		}

		BooleanQuery fieldQuery = null;

		if (fieldValues instanceof int[]) {
			int[] fieldValuesArray = (int[])fieldValues;

			if (fieldValuesArray.length == 0) {
				return;
			}

			fieldQuery = BooleanQueryFactoryUtil.create(searchContext);

			for (int fieldValue : fieldValuesArray) {
				fieldQuery.addTerm(field, fieldValue);
			}
		}
		else if (fieldValues instanceof Integer[]) {
			Integer[] fieldValuesArray = (Integer[])fieldValues;

			if (fieldValuesArray.length == 0) {
				return;
			}

			fieldQuery = BooleanQueryFactoryUtil.create(searchContext);

			for (Integer fieldValue : fieldValuesArray) {
				fieldQuery.addTerm(field, fieldValue);
			}
		}
		else if (fieldValues instanceof long[]) {
			long[] fieldValuesArray = (long[])fieldValues;

			if (fieldValuesArray.length == 0) {
				return;
			}

			fieldQuery = BooleanQueryFactoryUtil.create(searchContext);

			for (long fieldValue : fieldValuesArray) {
				fieldQuery.addTerm(field, fieldValue);
			}
		}
		else if (fieldValues instanceof Long[]) {
			Long[] fieldValuesArray = (Long[])fieldValues;

			if (fieldValuesArray.length == 0) {
				return;
			}

			fieldQuery = BooleanQueryFactoryUtil.create(searchContext);

			for (Long fieldValue : fieldValuesArray) {
				fieldQuery.addTerm(field, fieldValue);
			}
		}
		else if (fieldValues instanceof short[]) {
			short[] fieldValuesArray = (short[])fieldValues;

			if (fieldValuesArray.length == 0) {
				return;
			}

			fieldQuery = BooleanQueryFactoryUtil.create(searchContext);

			for (short fieldValue : fieldValuesArray) {
				fieldQuery.addTerm(field, fieldValue);
			}
		}
		else if (fieldValues instanceof Short[]) {
			Short[] fieldValuesArray = (Short[])fieldValues;

			if (fieldValuesArray.length == 0) {
				return;
			}

			fieldQuery = BooleanQueryFactoryUtil.create(searchContext);

			for (Short fieldValue : fieldValuesArray) {
				fieldQuery.addTerm(field, fieldValue);
			}
		}

		if (fieldQuery != null) {
			if (searchContext.isAndSearch()) {
				searchQuery.add(fieldQuery, BooleanClauseOccur.MUST);
			}
			else {
				searchQuery.add(fieldQuery, BooleanClauseOccur.SHOULD);
			}
		}
	}

	protected void addSearchAssetCategoryIds(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		MultiValueFacet multiValueFacet = new MultiValueFacet(searchContext);

		multiValueFacet.setFieldName(Field.ASSET_CATEGORY_IDS);
		multiValueFacet.setStatic(true);
		multiValueFacet.setValues(searchContext.getAssetCategoryIds());

		searchContext.addFacet(multiValueFacet);
	}

	protected void addSearchAssetCategoryTitles(
		Document document, String field, List<AssetCategory> assetCategories) {

		Map<Locale, List<String>> assetCategoryTitles =
			new HashMap<Locale, List<String>>();

		Locale defaultLocale = LocaleUtil.getDefault();

		for (AssetCategory assetCategory : assetCategories) {
			Map<Locale, String> titleMap = assetCategory.getTitleMap();

			for (Map.Entry<Locale, String> entry : titleMap.entrySet()) {
				Locale locale = entry.getKey();
				String title = entry.getValue();

				if (Validator.isNull(title)) {
					continue;
				}

				List<String> titles = assetCategoryTitles.get(locale);

				if (titles == null) {
					titles = new ArrayList<String>();

					assetCategoryTitles.put(locale, titles);
				}

				titles.add(title);
			}
		}

		for (Map.Entry<Locale, List<String>> entry :
				assetCategoryTitles.entrySet()) {

			Locale locale = entry.getKey();
			List<String> titles = entry.getValue();

			String[] titlesArray = titles.toArray(new String[0]);

			if (locale.equals(defaultLocale)) {
				document.addKeyword(field, titlesArray);
			}

			document.addKeyword(
				field.concat(StringPool.UNDERLINE).concat(locale.toString()),
				titlesArray);
		}
	}

	protected void addSearchAssetTagNames(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		MultiValueFacet multiValueFacet = new MultiValueFacet(searchContext);

		multiValueFacet.setFieldName(Field.ASSET_TAG_NAMES);
		multiValueFacet.setStatic(true);
		multiValueFacet.setValues(searchContext.getAssetTagNames());

		searchContext.addFacet(multiValueFacet);
	}

	protected void addSearchClassTypeIds(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		long[] classTypeIds = searchContext.getClassTypeIds();

		if ((classTypeIds == null) || (classTypeIds.length <= 0)) {
			return;
		}

		BooleanQuery classTypeIdsQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		for (long classTypeId : classTypeIds) {
			classTypeIdsQuery.addTerm(Field.CLASS_TYPE_ID, classTypeId);
		}

		contextQuery.add(classTypeIdsQuery, BooleanClauseOccur.MUST);
	}

	protected void addSearchDDMStruture(
			BooleanQuery searchQuery, SearchContext searchContext,
			DDMStructure ddmStructure)
		throws Exception {

		Set<String> fieldNames = ddmStructure.getFieldNames();

		for (String fieldName : fieldNames) {
			String name = DDMIndexerUtil.encodeName(
				ddmStructure.getStructureId(), fieldName,
				searchContext.getLocale());

			addSearchTerm(searchQuery, searchContext, name, false);
		}
	}

	protected void addSearchEntryClassNames(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		Facet facet = new AssetEntriesFacet(searchContext);

		facet.setStatic(true);

		searchContext.addFacet(facet);
	}

	protected void addSearchExpando(
			BooleanQuery searchQuery, SearchContext searchContext,
			String keywords)
		throws Exception {

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			searchContext.getCompanyId(), getClassName(searchContext));

		Set<String> attributeNames = SetUtil.fromEnumeration(
			expandoBridge.getAttributeNames());

		for (String attributeName : attributeNames) {
			UnicodeProperties properties = expandoBridge.getAttributeProperties(
				attributeName);

			int indexType = GetterUtil.getInteger(
				properties.getProperty(ExpandoColumnConstants.INDEX_TYPE));

			if (indexType != ExpandoColumnConstants.INDEX_TYPE_NONE) {
				String fieldName = ExpandoBridgeIndexerUtil.encodeFieldName(
					attributeName);

				if (Validator.isNotNull(keywords)) {
					if (searchContext.isAndSearch()) {
						searchQuery.addRequiredTerm(fieldName, keywords);
					}
					else {
						searchQuery.addTerm(fieldName, keywords);
					}
				}
			}
		}
	}

	protected void addSearchFolderId(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		MultiValueFacet multiValueFacet = new MultiValueFacet(searchContext);

		multiValueFacet.setFieldName(Field.FOLDER_ID);
		multiValueFacet.setStatic(true);
		multiValueFacet.setValues(searchContext.getFolderIds());

		searchContext.addFacet(multiValueFacet);
	}

	protected void addSearchGroupId(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		Facet facet = new ScopeFacet(searchContext);

		facet.setStatic(true);

		searchContext.addFacet(facet);
	}

	protected void addSearchKeywords(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		String keywords = searchContext.getKeywords();

		if (Validator.isNull(keywords)) {
			return;
		}

		searchQuery.addTerms(Field.KEYWORDS, keywords, searchContext.isLike());

		addSearchExpando(searchQuery, searchContext, keywords);
	}

	protected void addSearchLayout(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		MultiValueFacet multiValueFacet = new MultiValueFacet(searchContext);

		multiValueFacet.setFieldName(Field.LAYOUT_UUID);
		multiValueFacet.setStatic(true);

		searchContext.addFacet(multiValueFacet);
	}

	protected void addSearchLocalizedDDMStructure(
			BooleanQuery searchQuery, SearchContext searchContext,
			DDMStructure ddmStructure)
		throws Exception {

		Set<String> fieldNames = ddmStructure.getFieldNames();

		for (String fieldName : fieldNames) {
			String name = DDMIndexerUtil.encodeName(
				ddmStructure.getStructureId(), fieldName);

			addSearchLocalizedTerm(searchQuery, searchContext, name, false);
		}
	}

	protected void addSearchLocalizedTerm(
			BooleanQuery searchQuery, SearchContext searchContext, String field,
			boolean like)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, field, like);
		addSearchTerm(
			searchQuery, searchContext,
			DocumentImpl.getLocalizedName(searchContext.getLocale(), field),
			like);
	}

	protected void addSearchTerm(
			BooleanQuery searchQuery, SearchContext searchContext, String field,
			boolean like)
		throws Exception {

		if (Validator.isNull(field)) {
			return;
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

			return;
		}

		if (Validator.isNull(value)) {
			value = searchContext.getKeywords();
		}

		if (Validator.isNull(value)) {
			return;
		}

		if (searchContext.isAndSearch()) {
			searchQuery.addRequiredTerm(field, value, like);
		}
		else {
			searchQuery.addTerm(field, value, like);
		}
	}

	protected void addSearchUserId(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		MultiValueFacet multiValueFacet = new MultiValueFacet(searchContext);

		multiValueFacet.setFieldName(Field.USER_ID);
		multiValueFacet.setStatic(true);

		searchContext.addFacet(multiValueFacet);
	}

	protected void addStagingGroupKeyword(Document document, long groupId)
		throws Exception {

		if (!isStagingAware()) {
			return;
		}

		boolean stagingGroup = false;

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isLayout()) {
			group = GroupLocalServiceUtil.getGroup(group.getParentGroupId());
		}

		if (group.isStagingGroup()) {
			stagingGroup = true;
		}

		document.addKeyword(Field.STAGING_GROUP, stagingGroup);
	}

	protected void addTrashFields(
			Document document, String className, long classPK, Date removedDate,
			String removedByUserName, String type)
		throws SystemException {

		TrashEntry trashEntry = TrashEntryLocalServiceUtil.fetchEntry(
			className, classPK);

		if (removedDate == null) {
			if (trashEntry != null) {
				removedDate = trashEntry.getCreateDate();
			}
			else {
				removedDate = new Date();
			}
		}

		document.addDate(Field.REMOVED_DATE, removedDate);

		if (removedByUserName == null) {
			if (trashEntry != null) {
				removedByUserName = trashEntry.getUserName();
			}
			else {
				ServiceContext serviceContext =
					ServiceContextThreadLocal.getServiceContext();

				if (serviceContext != null) {
					try {
						User user = UserLocalServiceUtil.getUser(
							serviceContext.getUserId());

						removedByUserName = user.getFullName();
					}
					catch (PortalException pe) {
					}
				}
			}
		}

		if (Validator.isNotNull(removedByUserName)) {
			document.addKeyword(
				Field.REMOVED_BY_USER_NAME, removedByUserName, true);
		}

		if (type == null) {
			if (trashEntry != null) {
				TrashHandler trashHandler =
					TrashHandlerRegistryUtil.getTrashHandler(
						trashEntry.getClassName());

				try {
					TrashRenderer trashRenderer = trashHandler.getTrashRenderer(
						trashEntry.getClassPK());

					type = trashRenderer.getType();
				}
				catch (PortalException pe) {
				}
			}
		}

		if (Validator.isNotNull(type)) {
			document.addKeyword(Field.TYPE, type, true);
		}
	}

	protected BooleanQuery createFullQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		BooleanQuery searchQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		addSearchKeywords(searchQuery, searchContext);
		postProcessSearchQuery(searchQuery, searchContext);

		for (IndexerPostProcessor indexerPostProcessor :
				_indexerPostProcessors) {

			indexerPostProcessor.postProcessSearchQuery(
				searchQuery, searchContext);
		}

		Map<String, Facet> facets = searchContext.getFacets();

		for (Facet facet : facets.values()) {
			BooleanClause facetClause = facet.getFacetClause();

			if (facetClause != null) {
				contextQuery.add(
					facetClause.getQuery(),
					facetClause.getBooleanClauseOccur());
			}
		}

		BooleanQuery fullQuery = BooleanQueryFactoryUtil.create(searchContext);

		fullQuery.add(contextQuery, BooleanClauseOccur.MUST);

		if (searchQuery.hasClauses()) {
			fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
		}

		BooleanClause[] booleanClauses = searchContext.getBooleanClauses();

		if (booleanClauses != null) {
			for (BooleanClause booleanClause : booleanClauses) {
				fullQuery.add(
					booleanClause.getQuery(),
					booleanClause.getBooleanClauseOccur());
			}
		}

		postProcessFullQuery(fullQuery, searchContext);

		for (IndexerPostProcessor indexerPostProcessor :
				_indexerPostProcessors) {

			indexerPostProcessor.postProcessFullQuery(fullQuery, searchContext);
		}

		return fullQuery;
	}

	protected Summary createLocalizedSummary(Document document, Locale locale) {
		return createLocalizedSummary(
			document, locale, Field.TITLE, Field.CONTENT);
	}

	protected Summary createLocalizedSummary(
		Document document, Locale locale, String titleField,
		String contentField) {

		Locale snippetLocale = getSnippetLocale(document, locale);

		String prefix = Field.SNIPPET + StringPool.UNDERLINE;

		String title = document.get(
			snippetLocale, prefix + titleField, titleField);

		String content = document.get(
			snippetLocale, prefix + contentField, contentField);

		return new Summary(snippetLocale, title, content, null);
	}

	protected Summary createSummary(Document document) {
		return createSummary(document, Field.TITLE, Field.CONTENT);
	}

	protected Summary createSummary(
		Document document, String titleField, String contentField) {

		String prefix = Field.SNIPPET + StringPool.UNDERLINE;

		String title = document.get(prefix + titleField, titleField);
		String content = document.get(prefix + contentField, contentField);

		return new Summary(title, content, null);
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

		document.addUID(getPortletId(), field1);

		SearchEngineUtil.deleteDocument(
			getSearchEngineId(), companyId, document.get(Field.UID));
	}

	protected void deleteDocument(long companyId, String field1, String field2)
		throws Exception {

		Document document = new DocumentImpl();

		document.addUID(getPortletId(), field1, field2);

		SearchEngineUtil.deleteDocument(
			getSearchEngineId(), companyId, document.get(Field.UID));
	}

	protected abstract void doDelete(Object obj) throws Exception;

	protected abstract Document doGetDocument(Object obj) throws Exception;

	protected String doGetSortField(String orderByCol) {
		return orderByCol;
	}

	protected abstract Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletURL portletURL)
		throws Exception;

	protected abstract void doReindex(Object obj) throws Exception;

	protected abstract void doReindex(String className, long classPK)
		throws Exception;

	protected abstract void doReindex(String[] ids) throws Exception;

	protected void doReindexDDMStructures(List<Long> structureIds)
		throws Exception {
	}

	protected Hits filterSearch(
		Hits hits, PermissionChecker permissionChecker,
		SearchContext searchContext) {

		List<Document> docs = new ArrayList<Document>();
		List<Float> scores = new ArrayList<Float>();

		int start = searchContext.getStart();
		int end = searchContext.getEnd();

		String paginationType = GetterUtil.getString(
			searchContext.getAttribute("paginationType"), "more");

		boolean hasMore = false;

		Document[] documents = hits.getDocs();

		int excludeDocsSize = 0;

		for (int i = 0; i < documents.length; i++) {
			try {
				Document document = documents[i];

				String entryClassName = document.get(Field.ENTRY_CLASS_NAME);
				long entryClassPK = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				Indexer indexer = IndexerRegistryUtil.getIndexer(
					entryClassName);

				if ((indexer.isFilterSearch() &&
					 indexer.hasPermission(
						 permissionChecker, entryClassName, entryClassPK,
						 ActionKeys.VIEW)) ||
					!indexer.isFilterSearch() ||
					!indexer.isPermissionAware()) {

					docs.add(document);
					scores.add(hits.score(i));
				}
				else {
					excludeDocsSize++;
				}
			}
			catch (Exception e) {
				excludeDocsSize++;
			}

			if (paginationType.equals("more") && (end > 0) &&
				(end < documents.length) && (docs.size() >= end)) {

				hasMore = true;

				break;
			}
		}

		int length = docs.size();

		if (hasMore) {
			length = documents.length - excludeDocsSize;
		}

		hits.setLength(length);

		if ((start != QueryUtil.ALL_POS) && (end != QueryUtil.ALL_POS)) {
			if (end > length) {
				end = length;
			}

			docs = docs.subList(start, end);
		}

		hits.setDocs(docs.toArray(new Document[docs.size()]));
		hits.setScores(scores.toArray(new Float[docs.size()]));

		hits.setSearchTime(
			(float)(System.currentTimeMillis() - hits.getStart()) /
				Time.SECOND);

		return hits;
	}

	protected Document getBaseModelDocument(
			String portletId, BaseModel<?> baseModel)
		throws SystemException {

		return getBaseModelDocument(portletId, baseModel, baseModel);
	}

	protected Document getBaseModelDocument(
			String portletId, BaseModel<?> baseModel,
			BaseModel<?> workflowedBaseModel)
		throws SystemException {

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

		document.addUID(portletId, classPK);

		List<AssetCategory> assetCategories =
			AssetCategoryLocalServiceUtil.getCategories(className, classPK);

		long[] assetCategoryIds = StringUtil.split(
			ListUtil.toString(
				assetCategories, AssetCategory.CATEGORY_ID_ACCESSOR),
			0L);

		document.addKeyword(Field.ASSET_CATEGORY_IDS, assetCategoryIds);

		addSearchAssetCategoryTitles(
			document, Field.ASSET_CATEGORY_TITLES, assetCategories);

		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
			className, classPK);

		document.addText(Field.ASSET_TAG_NAMES, assetTagNames);

		List<AssetTag> assetTags = AssetTagLocalServiceUtil.getTags(
			className, classPK);

		long[] assetTagsIds = StringUtil.split(
			ListUtil.toString(assetTags, AssetTag.TAG_ID_ACCESSOR), 0L);

		document.addKeyword(Field.ASSET_TAG_IDS, assetTagsIds);

		document.addKeyword(Field.ENTRY_CLASS_NAME, className);
		document.addKeyword(Field.ENTRY_CLASS_PK, classPK);
		document.addKeyword(Field.PORTLET_ID, portletId);

		if (resourcePrimKey > 0) {
			document.addKeyword(Field.ROOT_ENTRY_CLASS_PK, resourcePrimKey);
		}

		if (baseModel instanceof AttachedModel) {
			AttachedModel attachedModel = (AttachedModel)baseModel;

			document.addKeyword(
				Field.CLASS_NAME_ID, attachedModel.getClassNameId());
			document.addKeyword(Field.CLASS_PK, attachedModel.getClassPK());
		}

		if (baseModel instanceof AuditedModel) {
			AuditedModel auditedModel = (AuditedModel)baseModel;

			document.addKeyword(Field.COMPANY_ID, auditedModel.getCompanyId());
			document.addDate(Field.CREATE_DATE, auditedModel.getCreateDate());
			document.addDate(
				Field.MODIFIED_DATE, auditedModel.getModifiedDate());
			document.addKeyword(Field.USER_ID, auditedModel.getUserId());

			String userName = PortalUtil.getUserName(
				auditedModel.getUserId(), auditedModel.getUserName());

			document.addKeyword(Field.USER_NAME, userName, true);
		}

		GroupedModel groupedModel = null;

		if (baseModel instanceof GroupedModel) {
			groupedModel = (GroupedModel)baseModel;

			document.addKeyword(
				Field.GROUP_ID, getSiteGroupId(groupedModel.getGroupId()));
			document.addKeyword(
				Field.SCOPE_GROUP_ID, groupedModel.getGroupId());
		}

		if (workflowedBaseModel instanceof WorkflowedModel) {
			WorkflowedModel workflowedModel =
				(WorkflowedModel)workflowedBaseModel;

			document.addKeyword(Field.STATUS, workflowedModel.getStatus());

			if ((groupedModel != null) && workflowedModel.isInTrash()) {
				addTrashFields(document, className, classPK, null, null, null);
			}
		}

		addAssetFields(document, className, classPK);

		ExpandoBridgeIndexerUtil.addAttributes(
			document, baseModel.getExpandoBridge());

		return document;
	}

	protected String getClassName(SearchContext searchContext) {
		String[] classNames = getClassNames();

		return classNames[0];
	}

	protected Set<String> getLocalizedCountryNames(Country country) {
		Set<String> countryNames = new HashSet<String>();

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			String countryName = country.getName(locale);

			countryName = countryName.toLowerCase();

			countryNames.add(countryName);
		}

		return countryNames;
	}

	/**
	 * @deprecated As of 6.2 renamed to {@link #getSiteGroupId(long)}
	 */
	protected long getParentGroupId(long groupId) {
		return getSiteGroupId(groupId);
	}

	protected abstract String getPortletId(SearchContext searchContext);

	protected long getSiteGroupId(long groupId) {
		long siteGroupId = groupId;

		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			if (group.isLayout()) {
				siteGroupId = group.getParentGroupId();
			}
		}
		catch (Exception e) {
		}

		return siteGroupId;
	}

	protected Locale getSnippetLocale(Document document, Locale locale) {
		String prefix = Field.SNIPPET + StringPool.UNDERLINE;

		String localizedContentName =
			prefix + DocumentImpl.getLocalizedName(locale, Field.CONTENT);
		String localizedDescriptionName =
			prefix + DocumentImpl.getLocalizedName(locale, Field.DESCRIPTION);
		String localizedTitleName =
			prefix + DocumentImpl.getLocalizedName(locale, Field.TITLE);

		if ((document.getField(localizedContentName) != null) ||
			(document.getField(localizedDescriptionName) != null) ||
			(document.getField(localizedTitleName) != null)) {

			return locale;
		}

		return null;
	}

	protected Document newDocument() {
		return (Document)_document.clone();
	}

	protected void populateAddresses(
			Document document, List<Address> addresses, long regionId,
			long countryId)
		throws PortalException, SystemException {

		List<String> cities = new ArrayList<String>();

		List<String> countries = new ArrayList<String>();

		if (countryId > 0) {
			try {
				Country country = CountryServiceUtil.getCountry(countryId);

				countries.addAll(getLocalizedCountryNames(country));
			}
			catch (NoSuchCountryException nsce) {
				if (_log.isWarnEnabled()) {
					_log.warn(nsce.getMessage());
				}
			}
		}

		List<String> regions = new ArrayList<String>();

		if (regionId > 0) {
			try {
				Region region = RegionServiceUtil.getRegion(regionId);

				regions.add(region.getName().toLowerCase());
			}
			catch (NoSuchRegionException nsre) {
				if (_log.isWarnEnabled()) {
					_log.warn(nsre.getMessage());
				}
			}
		}

		List<String> streets = new ArrayList<String>();
		List<String> zips = new ArrayList<String>();

		for (Address address : addresses) {
			cities.add(address.getCity().toLowerCase());
			countries.addAll(getLocalizedCountryNames(address.getCountry()));
			regions.add(address.getRegion().getName().toLowerCase());
			streets.add(address.getStreet1().toLowerCase());
			streets.add(address.getStreet2().toLowerCase());
			streets.add(address.getStreet3().toLowerCase());
			zips.add(address.getZip().toLowerCase());
		}

		document.addText("city", cities.toArray(new String[cities.size()]));
		document.addText(
			"country", countries.toArray(new String[countries.size()]));
		document.addText("region", regions.toArray(new String[regions.size()]));
		document.addText("street", streets.toArray(new String[streets.size()]));
		document.addText("zip", zips.toArray(new String[zips.size()]));
	}

	protected void postProcessFullQuery(
			BooleanQuery fullQuery, SearchContext searchContext)
		throws Exception {
	}

	protected void processHits(SearchContext searchContext, Hits hits)
		throws SearchException {

		HitsProcessor hitsProcessor =
			HitsProcessorRegistryUtil.getDefaultHitsProcessor();

		if (hitsProcessor != null) {
			hitsProcessor.process(searchContext, hits);
		}
	}

	protected void setFilterSearch(boolean filterSearch) {
		_filterSearch = filterSearch;
	}

	protected void setIndexerEnabled(boolean indexerEnabled) {
		_indexerEnabled = indexerEnabled;
	}

	protected void setPermissionAware(boolean permissionAware) {
		_permissionAware = permissionAware;
	}

	protected void setSortableTextFields(String[] sortableTextFields) {
		_document.setSortableTextFields(sortableTextFields);
	}

	protected void setStagingAware(boolean stagingAware) {
		_stagingAware = stagingAware;
	}

	private static Log _log = LogFactoryUtil.getLog(BaseIndexer.class);

	private Document _document;
	private boolean _filterSearch;
	private boolean _indexerEnabled = true;
	private IndexerPostProcessor[] _indexerPostProcessors =
		new IndexerPostProcessor[0];
	private boolean _permissionAware;
	private String _searchEngineId;
	private boolean _stagingAware = true;

}