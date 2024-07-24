/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.spring.hibernate.SpringHibernateThreadLocalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.service.builder.test.model.IndexEntry;
import com.liferay.portal.tools.service.builder.test.service.IndexEntryLocalService;
import com.liferay.portal.tools.service.builder.test.service.IndexEntryLocalServiceUtil;
import com.liferay.portal.tools.service.builder.test.service.IndexEntryLocalServiceWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class IndexEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(IndexEntryTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistrations.add(
			bundleContext.registerService(
				ModelDocumentContributor.class,
				new IndexEntryModelDocumentContributor(),
				HashMapDictionaryBuilder.<String, Object>put(
					"indexer.class.name", IndexEntry.class.getName()
				).build()));

		_serviceRegistrations.add(
			bundleContext.registerService(
				ModelSearchConfigurator.class,
				new IndexEntryModelSearchConfigurator(),
				new HashMapDictionary<>()));

		_serviceRegistrations.add(
			bundleContext.registerService(
				ServiceWrapper.class, new TestIndexEntryLocalServiceWrapper(),
				new HashMapDictionary<>()));
	}

	@AfterClass
	public static void tearDownClass() {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	@Test
	public void testReindexWithBufferAndWithoutTransaction() throws Throwable {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.portal.search.configuration." +
						"IndexerRegistryConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"buffered", true
					).build())) {

			_testReindex(Callable::call);
		}
	}

	@Test
	public void testReindexWithBufferAndWithTransaction() throws Throwable {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.portal.search.configuration." +
						"IndexerRegistryConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"buffered", true
					).build())) {

			_testReindex(
				callable -> TransactionInvokerUtil.invoke(
					_transactionConfig, callable));
		}
	}

	@Test
	public void testReindexWithoutBufferAndWithoutTransaction()
		throws Throwable {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.portal.search.configuration." +
						"IndexerRegistryConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"buffered", false
					).build())) {

			_testReindex(Callable::call);
		}
	}

	@Test
	public void testReindexWithoutBufferAndWithTransaction() throws Throwable {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.portal.search.configuration." +
						"IndexerRegistryConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"buffered", false
					).build())) {

			_testReindex(
				callable -> TransactionInvokerUtil.invoke(
					_transactionConfig, callable));
		}
	}

	private void _testReindex(
			UnsafeConsumer<Callable<Object>, Throwable> unsafeConsumer)
		throws Throwable {

		long companyId = RandomTestUtil.nextLong();

		_searchEngineHelper.initialize(companyId);

		try {
			SearchRequestBuilder searchRequestBuilder =
				_searchRequestBuilderFactory.builder();

			searchRequestBuilder.emptySearchEnabled(true);
			searchRequestBuilder.modelIndexerClasses(IndexEntry.class);
			searchRequestBuilder.companyId(companyId);

			SearchRequest searchRequest = searchRequestBuilder.build();

			SearchResponse searchResponse = _searcher.search(searchRequest);

			Assert.assertEquals(0, searchResponse.getTotalHits());

			TestIndexEntryLocalServiceWrapper.reset();

			List<Boolean> booleans =
				TestIndexEntryLocalServiceWrapper.getBooleans();

			Assert.assertTrue(booleans.isEmpty());

			unsafeConsumer.accept(
				() -> {
					_indexEntry = _indexEntryLocalService.addIndexEntry(
						companyId, RandomTestUtil.randomString());

					return null;
				});

			booleans = TestIndexEntryLocalServiceWrapper.getBooleans();

			Assert.assertEquals(booleans.toString(), 1, booleans.size());
			Assert.assertEquals(Boolean.FALSE, booleans.get(0));

			searchResponse = _searcher.search(searchRequest);

			Assert.assertEquals(1, searchResponse.getTotalHits());

			List<Document> documents = searchResponse.getDocuments();

			Document document = documents.get(0);

			Assert.assertEquals(
				Collections.emptyList(), document.getLongs("keywords"));
			Assert.assertEquals(
				_indexEntry.getName(), document.getString("name"));

			TestIndexEntryLocalServiceWrapper.reset();

			booleans = TestIndexEntryLocalServiceWrapper.getBooleans();

			Assert.assertTrue(booleans.isEmpty());

			long keywordsEntryId = RandomTestUtil.nextLong();

			unsafeConsumer.accept(
				() -> {
					_indexEntryLocalService.addKeywordsEntry(
						keywordsEntryId, _indexEntry);

					return null;
				});

			booleans = TestIndexEntryLocalServiceWrapper.getBooleans();

			Assert.assertEquals(booleans.toString(), 1, booleans.size());
			Assert.assertEquals(Boolean.FALSE, booleans.get(0));

			searchResponse = _searcher.search(searchRequest);

			Assert.assertEquals(1, searchResponse.getTotalHits());

			documents = searchResponse.getDocuments();

			document = documents.get(0);

			Assert.assertEquals(
				Collections.singletonList(keywordsEntryId),
				document.getLongs("keywords"));
			Assert.assertEquals(
				_indexEntry.getName(), document.getString("name"));
		}
		finally {
			_searchEngineHelper.removeCompany(companyId);
		}
	}

	private static final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();
	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@DeleteAfterTestRun
	private IndexEntry _indexEntry;

	@Inject
	private IndexEntryLocalService _indexEntryLocalService;

	@Inject
	private IndexWriterHelper _indexWriterHelper;

	@Inject
	private SearchEngineHelper _searchEngineHelper;

	@Inject
	private Searcher _searcher;

	@Inject
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	private static class IndexEntryModelDocumentContributor
		implements ModelDocumentContributor<IndexEntry> {

		@Override
		public void contribute(
			com.liferay.portal.kernel.search.Document document,
			IndexEntry indexEntry) {

			document.addKeyword("name", indexEntry.getName());
			document.addKeyword(
				"keywords",
				IndexEntryLocalServiceUtil.getKeywordsEntryPrimaryKeys(
					indexEntry.getIndexEntryId()));
		}

	}

	private static class IndexEntryModelIndexerWriterContributor
		implements ModelIndexerWriterContributor<IndexEntry> {

		@Override
		public void customize(
			BatchIndexingActionable batchIndexingActionable,
			ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {
		}

		@Override
		public BatchIndexingActionable getBatchIndexingActionable() {
			return null;
		}

		@Override
		public long getCompanyId(IndexEntry indexEntry) {
			return indexEntry.getCompanyId();
		}

	}

	private static class IndexEntryModelSearchConfigurator
		implements ModelSearchConfigurator<IndexEntry> {

		@Override
		public String getClassName() {
			return IndexEntry.class.getName();
		}

		@Override
		public ModelIndexerWriterContributor<IndexEntry>
			getModelIndexerWriterContributor() {

			return new IndexEntryModelIndexerWriterContributor();
		}

		@Override
		public boolean isPermissionAware() {
			return false;
		}

		@Override
		public boolean isStagingAware() {
			return false;
		}

	}

	private static class TestIndexEntryLocalServiceWrapper
		extends IndexEntryLocalServiceWrapper {

		public static List<Boolean> getBooleans() {
			return _booleans;
		}

		public static void reset() {
			_booleans.clear();
		}

		@Override
		public long[] getKeywordsEntryPrimaryKeys(long indexEntryId) {
			_booleans.add(
				SpringHibernateThreadLocalUtil.isCurrentTransactionReadOnly());

			return super.getKeywordsEntryPrimaryKeys(indexEntryId);
		}

		private static List<Boolean> _booleans = new ArrayList<>();

	}

}