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

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.indexer.IndexerPermissionPostFilter;
import com.liferay.portal.search.indexer.IndexerQueryBuilder;
import com.liferay.portal.search.indexer.IndexerSearcher;
import com.liferay.portal.search.indexer.IndexerSummaryBuilder;
import com.liferay.portal.search.indexer.IndexerWriter;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Michael C. Han
 */
public class DefaultIndexer<T extends BaseModel<?>> implements Indexer<T> {

	public DefaultIndexer(
		ModelSearchSettings modelSearchSettings,
		IndexerDocumentBuilder indexerDocumentBuilder,
		IndexerSearcher indexerSearcher, IndexerWriter<T> indexerWriter,
		IndexerPermissionPostFilter indexerPermissionPostFilter,
		IndexerQueryBuilder indexerQueryBuilder,
		IndexerSummaryBuilder indexerSummaryBuilder,
		IndexerPostProcessorsHolder indexerPostProcessorsHolder) {

		_modelSearchSettings = modelSearchSettings;
		_indexerDocumentBuilder = indexerDocumentBuilder;
		_indexerSearcher = indexerSearcher;
		_indexerWriter = indexerWriter;
		_indexerPermissionPostFilter = indexerPermissionPostFilter;
		_indexerQueryBuilder = indexerQueryBuilder;
		_indexerSummaryBuilder = indexerSummaryBuilder;
		_indexerPostProcessorsHolder = indexerPostProcessorsHolder;
	}

	@Override
	public void delete(long companyId, String uid) throws SearchException {
		_indexerWriter.delete(companyId, uid);
	}

	@Override
	public void delete(T baseModel) throws SearchException {
		_indexerWriter.delete(baseModel);
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
	public String getClassName() {
		return _modelSearchSettings.getClassName();
	}

	@Override
	public String[] getClassNames() {
		return getSearchClassNames();
	}

	@Override
	public Document getDocument(T baseModel) throws SearchException {
		return _indexerDocumentBuilder.getDocument(baseModel);
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

		return _indexerQueryBuilder.getQuery(searchContext);
	}

	@Override
	public IndexerPostProcessor[] getIndexerPostProcessors() {
		Stream<IndexerPostProcessor> stream =
			_indexerPostProcessorsHolder.stream();

		return stream.toArray(IndexerPostProcessor[]::new);
	}

	@Override
	public String getPortletId() {
		return StringPool.BLANK;
	}

	@Override
	public String[] getSearchClassNames() {
		return _modelSearchSettings.getSearchClassNames();
	}

	@Override
	public String getSearchEngineId() {
		return SearchEngineHelper.SYSTEM_ENGINE_ID;
	}

	@Override
	public String getSortField(String orderByCol) {
		return StringPool.BLANK;
	}

	@Override
	public String getSortField(String orderByCol, int sortType) {
		return StringPool.BLANK;
	}

	@Override
	public Summary getSummary(Document document, Locale locale, String snippet)
		throws SearchException {

		return getSummary(document, snippet, null, null);
	}

	@Override
	public Summary getSummary(
			Document document, String snippet, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws SearchException {

		return _indexerSummaryBuilder.getSummary(
			document, snippet, _getLocale(portletRequest));
	}

	@Override
	public int hashCode() {
		String[] searchClassNames = getSearchClassNames();

		StringBundler sb = new StringBundler(searchClassNames.length);

		for (String searchClassName : searchClassNames) {
			sb.append(searchClassName);
		}

		return HashUtil.hash(0, sb.toString());
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		return _indexerPermissionPostFilter.hasPermission(
			permissionChecker, entryClassPK);
	}

	@Override
	public boolean isCommitImmediately() {
		return _modelSearchSettings.isCommitImmediately();
	}

	@Override
	public boolean isFilterSearch() {
		return isPermissionAware();
	}

	@Override
	public boolean isIndexerEnabled() {
		return _indexerWriter.isEnabled();
	}

	@Override
	public boolean isPermissionAware() {
		return _indexerPermissionPostFilter.isPermissionAware();
	}

	@Override
	public boolean isStagingAware() {
		return _modelSearchSettings.isStagingAware();
	}

	@Override
	public boolean isVisible(long classPK, int status) throws Exception {
		return _indexerPermissionPostFilter.isVisible(classPK, status);
	}

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
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {
	}

	@Override
	public void registerIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {

		_indexerPostProcessorsHolder.addIndexerPostProcessor(
			indexerPostProcessor);
	}

	@Override
	public void reindex(Collection<T> objects) throws SearchException {
		_indexerWriter.reindex(objects);
	}

	@Override
	public void reindex(String className, long classPK) throws SearchException {
		_indexerWriter.reindex(classPK);
	}

	@Override
	public void reindex(String[] ids) throws SearchException {
		_indexerWriter.reindex(ids);
	}

	@Override
	public void reindex(T baseModel) throws SearchException {
		_indexerWriter.reindex(baseModel);
	}

	@Override
	public Hits search(SearchContext searchContext) throws SearchException {
		return _indexerSearcher.search(searchContext);
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

		return _indexerSearcher.searchCount(searchContext);
	}

	@Override
	public void setIndexerEnabled(boolean indexerEnabled) {
		_indexerWriter.setEnabled(indexerEnabled);
	}

	@Override
	public void unregisterIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {

		_indexerPostProcessorsHolder.removeIndexerPostProcessor(
			indexerPostProcessor);
	}

	private Locale _getLocale(PortletRequest portletRequest) {
		if (portletRequest != null) {
			return portletRequest.getLocale();
		}

		return LocaleUtil.getMostRelevantLocale();
	}

	private final IndexerDocumentBuilder _indexerDocumentBuilder;
	private final IndexerPermissionPostFilter _indexerPermissionPostFilter;
	private final IndexerPostProcessorsHolder _indexerPostProcessorsHolder;
	private final IndexerQueryBuilder _indexerQueryBuilder;
	private final IndexerSearcher _indexerSearcher;
	private final IndexerSummaryBuilder _indexerSummaryBuilder;
	private final IndexerWriter<T> _indexerWriter;
	private final ModelSearchSettings _modelSearchSettings;

}