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

package com.liferay.portal.search.engine.swapping.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexSearcherHelper;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.search.suggest.Suggester;
import com.liferay.portal.kernel.search.suggest.SuggesterResults;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentConfigurationDTO;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class SearchEngineSwappingTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testSearchEngineSwapping() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(SearchEngineSwappingTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ComponentConfigurationDTO componentConfigurationDTO =
			_getElasticsearchSearchEngineComponentConfigurationDTO(
				bundleContext);

		// Assert ElasticsearchSearchEngine is on duty

		Assert.assertEquals(
			ComponentConfigurationDTO.ACTIVE, componentConfigurationDTO.state);

		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine();

		Assert.assertEquals("Elasticsearch", searchEngine.getVendor());

		// Register mock SeachEngine to swap out ElasticsearchSearchEngine

		MockIndexSearcher mockIndexSearcher = new MockIndexSearcher();

		MockIndexWriter mockIndexWriter = new MockIndexWriter();

		SearchEngine mockSearchEngine = new MockSearchEngine(
			mockIndexSearcher, mockIndexWriter);

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(
				SearchEngine.class, mockSearchEngine,
				MapUtil.singletonDictionary("service.ranking", 1));

		try {
			searchEngine = _searchEngineHelper.getSearchEngine();

			Assert.assertEquals("MockSearchEngine", searchEngine.getVendor());

			componentConfigurationDTO =
				_getElasticsearchSearchEngineComponentConfigurationDTO(
					bundleContext);

			// Assert search request went to MockIndexSearcher

			Assert.assertSame(
				MockIndexSearcher.DUMMY_HITS,
				_indexSearcherHelper.search(new SearchContext(), null));

			Document document = ProxyFactory.newDummyInstance(Document.class);

			// Assert index request went to MockIndexWriter

			_indexWriterHelper.addDocument(
				CompanyConstants.SYSTEM, document, true);

			Assert.assertSame(document, mockIndexWriter._document);

			// Assert ElasticsearchSearchEngine is off duty

			Assert.assertEquals(
				ComponentConfigurationDTO.SATISFIED,
				componentConfigurationDTO.state);
		}
		finally {
			serviceRegistration.unregister();
		}

		componentConfigurationDTO =
			_getElasticsearchSearchEngineComponentConfigurationDTO(
				bundleContext);

		// Assert ElasticsearchSearchEngine is back on duty

		Assert.assertEquals(
			ComponentConfigurationDTO.ACTIVE, componentConfigurationDTO.state);

		searchEngine = _searchEngineHelper.getSearchEngine();

		Assert.assertEquals("Elasticsearch", searchEngine.getVendor());
	}

	private ComponentConfigurationDTO
			_getElasticsearchSearchEngineComponentConfigurationDTO(
				BundleContext bundleContext)
		throws Exception {

		Collection<ServiceReference<SearchEngine>> serviceReferences =
			bundleContext.getServiceReferences(
				SearchEngine.class, "(search.engine.impl=Elasticsearch)");

		Assert.assertEquals(
			serviceReferences.toString(), 1, serviceReferences.size());

		Iterator<ServiceReference<SearchEngine>> iterator1 =
			serviceReferences.iterator();

		ServiceReference<SearchEngine> serviceReference = iterator1.next();

		Collection<ComponentConfigurationDTO> componentConfigurationDTOs =
			_serviceComponentRuntime.getComponentConfigurationDTOs(
				_serviceComponentRuntime.getComponentDescriptionDTO(
					serviceReference.getBundle(),
					(String)serviceReference.getProperty("component.name")));

		Assert.assertEquals(
			componentConfigurationDTOs.toString(), 1,
			componentConfigurationDTOs.size());

		Iterator<ComponentConfigurationDTO> iterator2 =
			componentConfigurationDTOs.iterator();

		return iterator2.next();
	}

	@Inject
	private IndexSearcherHelper _indexSearcherHelper;

	@Inject
	private IndexWriterHelper _indexWriterHelper;

	@Inject
	private SearchEngineHelper _searchEngineHelper;

	@Inject
	private ServiceComponentRuntime _serviceComponentRuntime;

	private static class MockIndexSearcher implements IndexSearcher {

		public static final Hits DUMMY_HITS = ProxyFactory.newDummyInstance(
			Hits.class);

		@Override
		public String getQueryString(SearchContext searchContext, Query query) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Hits search(SearchContext searchContext, Query query) {
			return DUMMY_HITS;
		}

		@Override
		public long searchCount(SearchContext searchContext, Query query) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String spellCheckKeywords(SearchContext searchContext) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Map<String, List<String>> spellCheckKeywords(
			SearchContext searchContext, int max) {

			throw new UnsupportedOperationException();
		}

		@Override
		public SuggesterResults suggest(
			SearchContext searchContext, Suggester suggester) {

			throw new UnsupportedOperationException();
		}

		@Override
		public String[] suggestKeywordQueries(
			SearchContext searchContext, int max) {

			throw new UnsupportedOperationException();
		}

	}

	private static class MockIndexWriter implements IndexWriter {

		@Override
		public void addDocument(
			SearchContext searchContext, Document document) {

			_document = document;
		}

		@Override
		public void addDocuments(
			SearchContext searchContext, Collection<Document> documents) {

			throw new UnsupportedOperationException();
		}

		@Override
		public void clearQuerySuggestionDictionaryIndexes(
			SearchContext searchContext) {

			throw new UnsupportedOperationException();
		}

		@Override
		public void clearSpellCheckerDictionaryIndexes(
			SearchContext searchContext) {

			throw new UnsupportedOperationException();
		}

		@Override
		public void commit(SearchContext searchContext) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void deleteDocument(SearchContext searchContext, String uid) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void deleteDocuments(
			SearchContext searchContext, Collection<String> uids) {

			throw new UnsupportedOperationException();
		}

		@Override
		public void deleteEntityDocuments(
			SearchContext searchContext, String className) {

			throw new UnsupportedOperationException();
		}

		@Override
		public void indexKeyword(
			SearchContext searchContext, float weight, String keywordType) {

			throw new UnsupportedOperationException();
		}

		@Override
		public void indexQuerySuggestionDictionaries(
			SearchContext searchContext) {

			throw new UnsupportedOperationException();
		}

		@Override
		public void indexQuerySuggestionDictionary(
			SearchContext searchContext) {

			throw new UnsupportedOperationException();
		}

		@Override
		public void indexSpellCheckerDictionaries(SearchContext searchContext) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void indexSpellCheckerDictionary(SearchContext searchContext) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void partiallyUpdateDocument(
			SearchContext searchContext, Document document) {

			throw new UnsupportedOperationException();
		}

		@Override
		public void partiallyUpdateDocuments(
			SearchContext searchContext, Collection<Document> documents) {

			throw new UnsupportedOperationException();
		}

		@Override
		public void updateDocument(
			SearchContext searchContext, Document document) {

			throw new UnsupportedOperationException();
		}

		@Override
		public void updateDocuments(
			SearchContext searchContext, Collection<Document> documents) {

			throw new UnsupportedOperationException();
		}

		private Document _document;

	}

	private static class MockSearchEngine implements SearchEngine {

		@Override
		public String backup(long companyId, String backupName) {
			throw new UnsupportedOperationException();
		}

		@Override
		public IndexSearcher getIndexSearcher() {
			return _indexSearcher;
		}

		@Override
		public IndexWriter getIndexWriter() {
			return _indexWriter;
		}

		@Override
		public String getVendor() {
			return "MockSearchEngine";
		}

		@Override
		public void initialize(long companyId) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void removeBackup(long companyId, String backupName) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void removeCompany(long companyId) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void restore(long companyId, String backupName) {
			throw new UnsupportedOperationException();
		}

		private MockSearchEngine(
			IndexSearcher indexSearcher, IndexWriter indexWriter) {

			_indexSearcher = indexSearcher;
			_indexWriter = indexWriter;
		}

		private final IndexSearcher _indexSearcher;
		private final IndexWriter _indexWriter;

	}

}