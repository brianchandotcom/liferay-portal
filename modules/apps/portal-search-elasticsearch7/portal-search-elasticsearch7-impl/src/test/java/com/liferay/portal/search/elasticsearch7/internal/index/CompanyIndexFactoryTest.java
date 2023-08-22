/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch7.internal.connection.IndexName;
import com.liferay.portal.search.elasticsearch7.internal.document.SingleFieldFixture;
import com.liferay.portal.search.elasticsearch7.internal.query.QueryBuilderFactories;
import com.liferay.portal.search.elasticsearch7.internal.util.ResourceUtil;
import com.liferay.portal.search.spi.index.configuration.contributor.IndexConfigurationContributor;
import com.liferay.portal.search.spi.index.configuration.contributor.helper.IndexSettingsHelper;
import com.liferay.portal.search.spi.index.configuration.contributor.helper.TypeMappingsHelper;
import com.liferay.portal.search.spi.index.listener.CompanyIndexListener;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.ingest.PutPipelineRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.IngestClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.xcontent.XContentType;

import org.hamcrest.CoreMatchers;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author André de Oliveira
 */
public class CompanyIndexFactoryTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			CompanyIndexFactoryTest.class.getSimpleName());

		_elasticsearchFixture.setUp();

		_putTimestampPipeline(_elasticsearchFixture.getRestHighLevelClient());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Before
	public void setUp() throws Exception {
		_companyIndexFactoryFixture = new CompanyIndexFactoryFixture(
			_elasticsearchFixture, testName.getMethodName());

		_companyIndexFactory =
			_companyIndexFactoryFixture.getCompanyIndexFactory();

		CompanyIndexFactoryHelper companyIndexFactoryHelper =
			_companyIndexFactoryFixture.getCompanyIndexFactoryHelper();

		Mockito.reset(_elasticsearchConfigurationWrapper);

		ReflectionTestUtil.setFieldValue(
			companyIndexFactoryHelper, "_elasticsearchConfigurationWrapper",
			_elasticsearchConfigurationWrapper);

		ReflectionTestUtil.setFieldValue(
			_companyIndexFactory, "_companyIndexFactoryHelper",
			companyIndexFactoryHelper);
		ReflectionTestUtil.setFieldValue(
			_companyIndexFactory, "_elasticsearchConfigurationWrapper",
			_elasticsearchConfigurationWrapper);

		Mockito.when(
			_elasticsearchConfigurationWrapper.indexMaxResultWindow()
		).thenReturn(
			10000
		);

		_singleFieldFixture = new SingleFieldFixture(
			_elasticsearchFixture.getRestHighLevelClient(),
			new IndexName(_companyIndexFactoryFixture.getIndexName()));

		_singleFieldFixture.setQueryBuilderFactory(QueryBuilderFactories.MATCH);
	}

	@After
	public void tearDown() {
		_companyIndexFactoryFixture.tearDown();

		if (_serviceRegistrations.isEmpty()) {
			return;
		}

		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();
	}

	@Test
	public void testAdditionalIndexConfigurations() throws Exception {
		Mockito.when(
			_elasticsearchConfigurationWrapper.additionalIndexConfigurations()
		).thenReturn(
			"index.number_of_replicas: 1\nindex.number_of_shards: 2"
		);

		createIndices();

		Settings settings = _getIndexSettings();

		Assert.assertEquals("1", settings.get("index.number_of_replicas"));
		Assert.assertEquals("2", settings.get("index.number_of_shards"));
	}

	@Test
	public void testAdditionalTypeMappings() throws Exception {
		Mockito.when(
			_elasticsearchConfigurationWrapper.additionalTypeMappings()
		).thenReturn(
			loadAdditionalTypeMappings()
		);

		_assertAdditionalTypeMappings();
	}

	@Test
	public void testAdditionalTypeMappingsWithLegacyRootType()
		throws Exception {

		Mockito.when(
			_elasticsearchConfigurationWrapper.additionalTypeMappings()
		).thenReturn(
			_loadAdditionalTypeMappingsWithLegacyRootType()
		);

		_assertAdditionalTypeMappings();
	}

	@Test
	public void testAddMultipleIndexConfigurationContributors()
		throws Exception {

		_serviceRegistrations.add(
			_bundleContext.registerService(
				IndexConfigurationContributor.class,
				new TestIndexConfigurationContributor(), null));

		_serviceRegistrations.add(
			_bundleContext.registerService(
				IndexConfigurationContributor.class,
				new TestIndexConfigurationContributor(), null));
	}

	@Test
	public void testCompanyIndexListeners() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_companyIndexFactoryFixture, "_indexName", "other");

		ReflectionTestUtil.setFieldValue(
			_companyIndexFactoryFixture.getCompanyIndexFactoryHelper(),
			"_companyIndexListenerServiceTrackerList",
			ServiceTrackerListFactory.open(
				_bundleContext, CompanyIndexListener.class, null,
				new ServiceTrackerCustomizer
					<CompanyIndexListener, CompanyIndexListener>() {

					@Override
					public CompanyIndexListener addingService(
						ServiceReference<CompanyIndexListener>
							serviceReference) {

						return null;
					}

					@Override
					public void modifiedService(
						ServiceReference<CompanyIndexListener> serviceReference,
						CompanyIndexListener companyIndexListener) {
					}

					@Override
					public void removedService(
						ServiceReference<CompanyIndexListener> serviceReference,
						CompanyIndexListener companyIndexListener) {
					}

				}));

		addCompanyIndexListener(
			new CompanyIndexListener() {

				@Override
				public void onAfterCreate(String indexName) {
					_companyIndexFactoryFixture.createIndices();
				}

				@Override
				public void onBeforeDelete(String indexName) {
					_companyIndexFactoryFixture.deleteIndices();
				}

			});

		createIndices();

		_assertHasIndex(_companyIndexFactoryFixture.getIndexName());

		deleteIndices();

		_assertNoIndex(_companyIndexFactoryFixture.getIndexName());
	}

	@Test
	public void testCompanyIndexListenersThrowsException() throws Exception {
		addCompanyIndexListener(
			new CompanyIndexListener() {

				@Override
				public void onAfterCreate(String indexName) {
					throw new RuntimeException();
				}

				@Override
				public void onBeforeDelete(String indexName) {
					throw new RuntimeException();
				}

			});

		createIndices();
	}

	@Test
	public void testCreateIndicesWithBlankStrings() throws Exception {
		Mockito.when(
			_elasticsearchConfigurationWrapper.additionalIndexConfigurations()
		).thenReturn(
			StringPool.BLANK
		);

		Mockito.when(
			_elasticsearchConfigurationWrapper.additionalTypeMappings()
		).thenReturn(
			StringPool.SPACE
		);

		Mockito.when(
			_elasticsearchConfigurationWrapper.indexNumberOfReplicas()
		).thenReturn(
			StringPool.BLANK
		);

		Mockito.when(
			_elasticsearchConfigurationWrapper.indexNumberOfShards()
		).thenReturn(
			StringPool.SPACE
		);

		createIndices();
	}

	@Test
	public void testCreateIndicesWithEmptyConfiguration() throws Exception {
		createIndices();
	}

	@Test
	public void testDefaultIndexSettings() throws Exception {
		createIndices();

		Settings settings = _getIndexSettings();

		Assert.assertEquals("0", settings.get("index.number_of_replicas"));
		Assert.assertEquals("1", settings.get("index.number_of_shards"));
	}

	@Test
	public void testDefaultIndices() throws Exception {
		createIndices();

		_assertMappings(Field.COMPANY_ID, Field.ENTRY_CLASS_NAME);
	}

	@Test
	public void testExecuteCompanyIndexListenerOnBeforeDelete()
		throws Exception {

		CompanyIndexListener companyIndexListener = Mockito.mock(
			CompanyIndexListener.class);

		addCompanyIndexListener(companyIndexListener);

		createIndices();

		deleteIndices();

		Mockito.verify(
			companyIndexListener, Mockito.times(1)
		).onBeforeDelete(
			Mockito.anyString()
		);
	}

	@Test
	public void testIndexConfigurationContributor() throws Exception {
		_serviceRegistrations.add(
			_bundleContext.registerService(
				IndexConfigurationContributor.class,
				new IndexConfigurationContributor() {

					@Override
					public void contributeMappings(
						String indexName,
						TypeMappingsHelper typeMappingsHelper) {
					}

					@Override
					public void contributeSettings(
						IndexSettingsHelper indexSettingsHelper) {

						indexSettingsHelper.put(
							"index.number_of_replicas", "2");
						indexSettingsHelper.put("index.number_of_shards", "3");
					}

				},
				null));

		Mockito.when(
			_elasticsearchConfigurationWrapper.additionalIndexConfigurations()
		).thenReturn(
			"index.number_of_replicas: 0\nindex.number_of_shards: 0"
		);

		createIndices();

		Settings settings = _getIndexSettings();

		Assert.assertEquals("2", settings.get("index.number_of_replicas"));
		Assert.assertEquals("3", settings.get("index.number_of_shards"));
	}

	@Test
	public void testIndexConfigurationContributorTypeMappings()
		throws Exception {

		String mappings = loadAdditionalTypeMappings();

		_serviceRegistrations.add(
			_bundleContext.registerService(
				IndexConfigurationContributor.class,
				new IndexConfigurationContributor() {

					@Override
					public void contributeMappings(
						String indexName,
						TypeMappingsHelper typeMappingsHelper) {

						typeMappingsHelper.putTypeMappings(
							_replaceAnalyzer(mappings, "brazilian"));
					}

					@Override
					public void contributeSettings(
						IndexSettingsHelper indexSettingsHelper) {
					}

				},
				null));

		Mockito.when(
			_elasticsearchConfigurationWrapper.additionalTypeMappings()
		).thenReturn(
			_replaceAnalyzer(mappings, "portuguese")
		);

		createIndices();

		String field = RandomTestUtil.randomString() + "_ja";

		_indexOneDocument(field);

		assertAnalyzer(field, "brazilian");
	}

	@Test
	public void testIndexConfigurations() throws Exception {
		Mockito.when(
			_elasticsearchConfigurationWrapper.indexNumberOfReplicas()
		).thenReturn(
			"1"
		);

		Mockito.when(
			_elasticsearchConfigurationWrapper.indexNumberOfShards()
		).thenReturn(
			"2"
		);

		createIndices();

		Settings settings = _getIndexSettings();

		Assert.assertEquals("1", settings.get("index.number_of_replicas"));
		Assert.assertEquals("2", settings.get("index.number_of_shards"));
	}

	@Test
	public void testOptionalDefaultTemplateIsAlwaysAfterContributedTemplates()
		throws Exception {

		Mockito.when(
			_elasticsearchConfigurationWrapper.additionalTypeMappings()
		).thenReturn(
			loadAdditionalTypeMappings()
		);

		createIndices();

		_indexOneDocument("match_additional_mapping");
		_indexOneDocument("match_catch_all");

		assertType("match_additional_mapping", "keyword");
		assertType("match_catch_all", "text");
	}

	@Test
	public void testOverrideLegacyTypeMappings() throws Exception {
		Mockito.when(
			_elasticsearchConfigurationWrapper.additionalIndexConfigurations()
		).thenReturn(
			_loadAdditionalAnalyzers()
		);

		Mockito.when(
			_elasticsearchConfigurationWrapper.overrideTypeMappings()
		).thenReturn(
			_loadOverrideLegacyTypeMappings()
		);

		createIndices();

		String field1 = "title";

		_indexOneDocument(field1);

		assertAnalyzer(field1, "kuromoji_liferay_custom");

		String field2 = "description";

		_indexOneDocument(field2);

		_assertNoAnalyzer(field2);
	}

	@Test
	public void testOverrideTypeMappings() throws Exception {
		Mockito.when(
			_elasticsearchConfigurationWrapper.additionalIndexConfigurations()
		).thenReturn(
			_loadAdditionalAnalyzers()
		);

		Mockito.when(
			_elasticsearchConfigurationWrapper.overrideTypeMappings()
		).thenReturn(
			_loadOverrideTypeMappings()
		);

		createIndices();

		String field1 = "title";

		_indexOneDocument(field1);

		assertAnalyzer(field1, "kuromoji_liferay_custom");

		String field2 = "description";

		_indexOneDocument(field2);

		_assertNoAnalyzer(field2);
	}

	@Test
	public void testOverrideTypeMappingsHonorDefaultIndices() throws Exception {
		Mockito.when(
			_elasticsearchConfigurationWrapper.additionalIndexConfigurations()
		).thenReturn(
			_loadAdditionalAnalyzers()
		);

		Mockito.when(
			_elasticsearchConfigurationWrapper.overrideTypeMappings()
		).thenReturn(
			_loadOverrideTypeMappings()
		);

		createIndices();

		_assertMappings(Field.TITLE);
	}

	@Test
	public void testRemoveIndexConfigurationContributor() {
		ServiceRegistration<IndexConfigurationContributor> serviceRegistration =
			_bundleContext.registerService(
				IndexConfigurationContributor.class,
				new TestIndexConfigurationContributor(), null);

		serviceRegistration.unregister();
	}

	@Rule
	public TestName testName = new TestName();

	protected void addCompanyIndexListener(
		CompanyIndexListener companyIndexListener) {

		_serviceRegistrations.add(
			_bundleContext.registerService(
				CompanyIndexListener.class, companyIndexListener, null));
	}

	protected void assertAnalyzer(String field, String analyzer)
		throws Exception {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchFixture.getRestHighLevelClient();

		FieldMappingAssert.assertAnalyzer(
			analyzer, field, _companyIndexFactoryFixture.getIndexName(),
			restHighLevelClient.indices());
	}

	protected void assertType(String field, String type) throws Exception {
		RestHighLevelClient restHighLevelClient =
			_elasticsearchFixture.getRestHighLevelClient();

		FieldMappingAssert.assertType(
			type, field, _companyIndexFactoryFixture.getIndexName(),
			restHighLevelClient.indices());
	}

	protected void createIndices() throws Exception {
		RestHighLevelClient restHighLevelClient =
			_elasticsearchFixture.getRestHighLevelClient();

		IndicesClient indicesClient = restHighLevelClient.indices();

		_companyIndexFactory.createIndices(
			indicesClient, RandomTestUtil.randomLong());
	}

	protected void deleteIndices() {
		RestHighLevelClient restHighLevelClient =
			_elasticsearchFixture.getRestHighLevelClient();

		IndicesClient indicesClient = restHighLevelClient.indices();

		_companyIndexFactory.deleteIndices(
			indicesClient, RandomTestUtil.randomLong());
	}

	protected boolean hasIndex(String indexName) {
		RestHighLevelClient restHighLevelClient =
			_elasticsearchFixture.getRestHighLevelClient();

		IndicesClient indicesClient = restHighLevelClient.indices();

		GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);

		try {
			return indicesClient.exists(
				getIndexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected String loadAdditionalTypeMappings() {
		try {
			return ResourceUtil.getResourceAsString(
				getClass(),
				"CompanyIndexFactoryTest-additionalTypeMappings.json");
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	protected static class TestIndexConfigurationContributor
		implements IndexConfigurationContributor {

		@Override
		public void contributeMappings(
			String indexName, TypeMappingsHelper typeMappingsHelper) {
		}

		@Override
		public void contributeSettings(
			IndexSettingsHelper indexSettingsHelper) {
		}

	}

	private static void _putTimestampPipeline(
			RestHighLevelClient restHighLevelClient)
		throws Exception {

		IngestClient ingestClient = restHighLevelClient.ingest();

		String source = JSONUtil.put(
			"description", "Adds timestamp to documents"
		).put(
			"processors",
			JSONUtil.put(
				JSONUtil.put(
					"set",
					JSONUtil.put(
						"field", "_source.timestamp"
					).put(
						"value", "{{{_ingest.timestamp}}}"
					)))
		).toString();

		PutPipelineRequest putPipelineRequest = new PutPipelineRequest(
			"timestamp",
			new BytesArray(source.getBytes(StandardCharsets.UTF_8)),
			XContentType.JSON);

		ingestClient.putPipeline(putPipelineRequest, RequestOptions.DEFAULT);
	}

	private void _assertAdditionalTypeMappings() throws Exception {
		Mockito.when(
			_elasticsearchConfigurationWrapper.additionalIndexConfigurations()
		).thenReturn(
			_loadAdditionalAnalyzers()
		);

		createIndices();

		String contributedKeywordFieldName = "orderStatus";

		assertType(contributedKeywordFieldName, "keyword");

		String contributedTextFieldName = "productDescription";

		assertType(contributedTextFieldName, "text");

		String liferayKeywordFieldName = "status";

		assertType(liferayKeywordFieldName, "keyword");

		String liferayTextFieldName = "subtitle";

		assertType(liferayTextFieldName, "text");

		String intactFieldName = RandomTestUtil.randomString() + "_en";

		_indexOneDocument(intactFieldName);

		assertAnalyzer(intactFieldName, "english");

		String replacedFieldName = RandomTestUtil.randomString() + "_ja";

		_indexOneDocument(replacedFieldName);

		assertAnalyzer(replacedFieldName, "kuromoji_liferay_custom");
	}

	private void _assertHasIndex(String indexName) {
		Assert.assertTrue(
			"Index " + indexName + " does not exist", hasIndex(indexName));
	}

	private void _assertMappings(String... fieldNames) {
		String indexName = _companyIndexFactoryFixture.getIndexName();

		GetIndexResponse getIndexResponse = _elasticsearchFixture.getIndex(
			indexName);

		Map<String, MappingMetadata> mappings = getIndexResponse.getMappings();

		MappingMetadata mappingMetadata = mappings.get(indexName);

		Map<String, Object> map = _getPropertiesMap(mappingMetadata);

		Set<String> set = map.keySet();

		Assert.assertThat(set, CoreMatchers.hasItems(fieldNames));
	}

	private void _assertNoAnalyzer(String field) throws Exception {
		assertAnalyzer(field, null);
	}

	private void _assertNoIndex(String indexName) {
		Assert.assertFalse(
			"Index " + indexName + " exists", hasIndex(indexName));
	}

	private Settings _getIndexSettings() {
		String name = _companyIndexFactoryFixture.getIndexName();

		GetIndexResponse getIndexResponse = _elasticsearchFixture.getIndex(
			name);

		Map<String, Settings> map = getIndexResponse.getSettings();

		return map.get(name);
	}

	private Map<String, Object> _getPropertiesMap(
		MappingMetadata mappingMetadata) {

		Map<String, Object> map = mappingMetadata.getSourceAsMap();

		return (Map<String, Object>)map.get("properties");
	}

	private void _indexOneDocument(String field) {
		_indexOneDocument(field, RandomTestUtil.randomString());
	}

	private void _indexOneDocument(String field, String value) {
		_singleFieldFixture.setField(field);

		_singleFieldFixture.indexDocument(value);
	}

	private String _loadAdditionalAnalyzers() throws Exception {
		return ResourceUtil.getResourceAsString(
			getClass(), "CompanyIndexFactoryTest-additionalAnalyzers.json");
	}

	private String _loadAdditionalTypeMappingsWithLegacyRootType() {
		try {
			return ResourceUtil.getResourceAsString(
				getClass(),
				"CompanyIndexFactoryTest-additionalTypeMappings-with-legacy-" +
					"root-type.json");
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private String _loadOverrideLegacyTypeMappings() throws Exception {
		return ResourceUtil.getResourceAsString(
			getClass(),
			"CompanyIndexFactoryTest-overrideLegacyTypeMappings.json");
	}

	private String _loadOverrideTypeMappings() throws Exception {
		return ResourceUtil.getResourceAsString(
			getClass(), "CompanyIndexFactoryTest-overrideTypeMappings.json");
	}

	private String _replaceAnalyzer(String mappings, String analyzer) {
		return StringUtil.replace(
			mappings, "kuromoji_liferay_custom", analyzer);
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static ElasticsearchFixture _elasticsearchFixture;

	private CompanyIndexFactory _companyIndexFactory;
	private CompanyIndexFactoryFixture _companyIndexFactoryFixture;
	private final ElasticsearchConfigurationWrapper
		_elasticsearchConfigurationWrapper = Mockito.mock(
			ElasticsearchConfigurationWrapper.class);
	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();
	private SingleFieldFixture _singleFieldFixture;

}