/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.suggest.NGramHolderBuilder;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.internal.legacy.searcher.SearchRequestBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.searcher.SearchResponseBuilderFactoryImpl;
import com.liferay.portal.search.solr8.internal.connection.SolrClientManager;
import com.liferay.portal.search.solr8.internal.connection.TestSolrClientManager;
import com.liferay.portal.search.solr8.internal.document.DefaultSolrDocumentFactory;
import com.liferay.portal.search.solr8.internal.facet.FacetProcessor;
import com.liferay.portal.search.solr8.internal.query.BooleanQueryTranslatorImpl;
import com.liferay.portal.search.solr8.internal.query.DisMaxQueryTranslatorImpl;
import com.liferay.portal.search.solr8.internal.query.FuzzyQueryTranslatorImpl;
import com.liferay.portal.search.solr8.internal.query.MatchAllQueryTranslatorImpl;
import com.liferay.portal.search.solr8.internal.query.MatchQueryTranslatorImpl;
import com.liferay.portal.search.solr8.internal.query.MoreLikeThisQueryTranslatorImpl;
import com.liferay.portal.search.solr8.internal.query.MultiMatchQueryTranslatorImpl;
import com.liferay.portal.search.solr8.internal.query.NestedQueryTranslatorImpl;
import com.liferay.portal.search.solr8.internal.query.SolrQueryTranslator;
import com.liferay.portal.search.solr8.internal.query.StringQueryTranslatorImpl;
import com.liferay.portal.search.solr8.internal.query.TermQueryTranslatorImpl;
import com.liferay.portal.search.solr8.internal.query.TermRangeQueryTranslatorImpl;
import com.liferay.portal.search.solr8.internal.query.WildcardQueryTranslatorImpl;
import com.liferay.portal.search.solr8.internal.search.engine.adapter.SolrSearchEngineAdapterFixture;
import com.liferay.portal.search.solr8.internal.suggest.NGramHolderBuilderImpl;
import com.liferay.portal.search.solr8.internal.suggest.NGramQueryBuilderImpl;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.util.LocalizationImpl;

import java.nio.ByteBuffer;

import java.util.Collections;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Miguel Angelo Caldas Gallindo
 * @author André de Oliveira
 */
public class SolrIndexingFixture implements IndexingFixture {

	public SolrIndexingFixture() {
		this(Collections.<String, Object>emptyMap());
	}

	public SolrIndexingFixture(
		Map<String, Object> solrConfigurationProperties) {

		_properties = createSolrConfigurationProperties(
			solrConfigurationProperties);
	}

	@Override
	public long getCompanyId() {
		return _COMPANY_ID;
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
	public SearchEngineAdapter getSearchEngineAdapter() {
		return _searchEngineAdapter;
	}

	@Override
	public boolean isSearchEngineAvailable() {
		return SolrUnitTestRequirements.isSolrExternallyStartedByDeveloper();
	}

	public void setFacetProcessor(FacetProcessor<SolrQuery> facetProcessor) {
		_facetProcessor = facetProcessor;
	}

	@Override
	public void setUp() throws Exception {
		SolrClientManager solrClientManager = new TestSolrClientManager(
			_properties);

		_solrSearchEngineAdapterFixture = createSolrSearchEngineAdapterFixture(
			solrClientManager, _facetProcessor, _properties);

		_solrSearchEngineAdapterFixture.setUp();

		SearchEngineAdapter searchEngineAdapter =
			_solrSearchEngineAdapterFixture.getSearchEngineAdapter();

		_serviceRegistration = _bundleContext.registerService(
			NGramHolderBuilder.class, new NGramHolderBuilderImpl(), null);

		_indexSearcher = createIndexSearcher(
			searchEngineAdapter, solrClientManager);
		_indexWriter = createIndexWriter(searchEngineAdapter);
		_searchEngineAdapter = searchEngineAdapter;
	}

	@Override
	public void tearDown() throws Exception {
		if (_serviceRegistration == null) {
			return;
		}

		_serviceRegistration.unregister();
	}

	protected static SolrQueryTranslator createSolrQueryTranslator() {
		SolrQueryTranslator solrQueryTranslator = new SolrQueryTranslator();

		ReflectionTestUtil.setFieldValue(
			solrQueryTranslator, "booleanQueryTranslator",
			new BooleanQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			solrQueryTranslator, "disMaxQueryTranslator",
			new DisMaxQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			solrQueryTranslator, "fuzzyQueryTranslator",
			new FuzzyQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			solrQueryTranslator, "matchAllQueryTranslator",
			new MatchAllQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			solrQueryTranslator, "matchQueryTranslator",
			new MatchQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			solrQueryTranslator, "moreLikeThisQueryTranslator",
			new MoreLikeThisQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			solrQueryTranslator, "multiMatchQueryTranslator",
			new MultiMatchQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			solrQueryTranslator, "nestedQueryTranslator",
			new NestedQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			solrQueryTranslator, "stringQueryTranslator",
			new StringQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			solrQueryTranslator, "termQueryTranslator",
			new TermQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			solrQueryTranslator, "termRangeQueryTranslator",
			new TermRangeQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			solrQueryTranslator, "wildcardQueryTranslator",
			new WildcardQueryTranslatorImpl());

		return solrQueryTranslator;
	}

	protected static SolrSearchEngineAdapterFixture
		createSolrSearchEngineAdapterFixture(
			SolrClientManager solrClientManager,
			FacetProcessor<SolrQuery> facetProcessor,
			Map<String, Object> properties) {

		return new SolrSearchEngineAdapterFixture() {
			{
				setFacetProcessor(facetProcessor);
				setProperties(properties);
				setQueryTranslator(createSolrQueryTranslator());
				setSolrClientManager(solrClientManager);
				setSolrDocumentFactory(new DefaultSolrDocumentFactory());
			}
		};
	}

	protected Digester createDigester() {
		Digester digester = Mockito.mock(Digester.class);

		Mockito.doAnswer(
			invocation -> {
				Object[] args = invocation.getArguments();

				ByteBuffer byteBuffer = (ByteBuffer)args[1];

				return byteBuffer.array();
			}
		).when(
			digester
		).digestRaw(
			Mockito.anyString(), (ByteBuffer)Mockito.any()
		);

		return digester;
	}

	protected IndexSearcher createIndexSearcher(
		SearchEngineAdapter searchEngineAdapter,
		SolrClientManager solrClientManager) {

		SolrIndexSearcher solrIndexSearcher = new SolrIndexSearcher() {
			{
				activate(_properties);
			}
		};

		ReflectionTestUtil.setFieldValue(
			solrIndexSearcher, "_props", createProps());
		ReflectionTestUtil.setFieldValue(
			solrIndexSearcher, "_querySuggester",
			createSolrQuerySuggester(solrClientManager));
		ReflectionTestUtil.setFieldValue(
			solrIndexSearcher, "_searchEngineAdapter", searchEngineAdapter);
		ReflectionTestUtil.setFieldValue(
			solrIndexSearcher, "_searchRequestBuilderFactory",
			new SearchRequestBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			solrIndexSearcher, "_searchResponseBuilderFactory",
			new SearchResponseBuilderFactoryImpl());

		return solrIndexSearcher;
	}

	protected IndexWriter createIndexWriter(
		SearchEngineAdapter searchEngineAdapter) {

		SolrIndexWriter solrIndexWriter = new SolrIndexWriter() {
			{
				activate(_properties);
			}
		};

		ReflectionTestUtil.setFieldValue(
			solrIndexWriter, "_searchEngineAdapter", searchEngineAdapter);
		ReflectionTestUtil.setFieldValue(
			solrIndexWriter, "_spellCheckIndexWriter",
			createSolrSpellCheckIndexWriter(searchEngineAdapter));

		return solrIndexWriter;
	}

	protected Props createProps() {
		Props props = Mockito.mock(Props.class);

		Mockito.doReturn(
			"20"
		).when(
			props
		).get(
			PropsKeys.INDEX_SEARCH_LIMIT
		);

		Mockito.doReturn(
			"yyyyMMddHHmmss"
		).when(
			props
		).get(
			PropsKeys.INDEX_DATE_FORMAT_PATTERN
		);

		return props;
	}

	protected Map<String, Object> createSolrConfigurationProperties(
		Map<String, Object> solrConfigurationProperties) {

		return HashMapBuilder.<String, Object>put(
			"defaultCollection", "liferay"
		).put(
			"logExceptionsOnly", false
		).put(
			"readURL", "http://localhost:8983/solr/liferay"
		).put(
			"writeURL", "http://localhost:8983/solr/liferay"
		).putAll(
			solrConfigurationProperties
		).build();
	}

	protected SolrQuerySuggester createSolrQuerySuggester(
		SolrClientManager solrClientManager) {

		SolrQuerySuggester solrQuerySuggester = new SolrQuerySuggester() {
			{
				setLocalization(_localization);

				activate(_properties);
			}
		};

		ReflectionTestUtil.setFieldValue(
			solrQuerySuggester, "_nGramQueryBuilder",
			new NGramQueryBuilderImpl());
		ReflectionTestUtil.setFieldValue(
			solrQuerySuggester, "_solrClientManager", solrClientManager);

		return solrQuerySuggester;
	}

	protected SolrSpellCheckIndexWriter createSolrSpellCheckIndexWriter(
		SearchEngineAdapter searchEngineAdapter) {

		SolrSpellCheckIndexWriter solrSpellCheckIndexWriter =
			new SolrSpellCheckIndexWriter() {
				{
					digester = createDigester();

					activate(_properties);
				}
			};

		ReflectionTestUtil.setFieldValue(
			solrSpellCheckIndexWriter, "_searchEngineAdapter",
			searchEngineAdapter);

		return solrSpellCheckIndexWriter;
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static ServiceRegistration<NGramHolderBuilder> _serviceRegistration;

	private FacetProcessor<SolrQuery> _facetProcessor;
	private IndexSearcher _indexSearcher;
	private IndexWriter _indexWriter;
	private final Localization _localization = new LocalizationImpl();
	private final Map<String, Object> _properties;
	private SearchEngineAdapter _searchEngineAdapter;
	private SolrSearchEngineAdapterFixture _solrSearchEngineAdapterFixture;

}