/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.index;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.opensearch2.internal.BaseOpenSearchTestCase;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.configuration.OpenSearchConfigurationWrapper;
import com.liferay.portal.search.opensearch2.internal.configuration.OpenSearchConfigurationWrapperImpl;
import com.liferay.portal.search.opensearch2.internal.connection.IndexName;
import com.liferay.portal.search.opensearch2.internal.document.SingleFieldFixture;
import com.liferay.portal.search.opensearch2.internal.query.QueryFactories;
import com.liferay.portal.search.opensearch2.internal.util.ResourceUtil;
import com.liferay.portal.search.spi.index.configuration.contributor.IndexConfigurationContributor;
import com.liferay.portal.search.spi.index.configuration.contributor.helper.IndexSettingsHelper;
import com.liferay.portal.search.spi.index.configuration.contributor.helper.TypeMappingsHelper;
import com.liferay.portal.search.spi.index.listener.CompanyIndexListener;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hamcrest.CoreMatchers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import org.mockito.Mockito;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.mapping.Property;
import org.opensearch.client.opensearch._types.mapping.TypeMapping;
import org.opensearch.client.opensearch.indices.ExistsRequest;
import org.opensearch.client.opensearch.indices.GetIndexResponse;
import org.opensearch.client.opensearch.indices.IndexSettings;
import org.opensearch.client.opensearch.indices.IndexState;
import org.opensearch.client.opensearch.indices.OpenSearchIndicesClient;
import org.opensearch.client.opensearch.ingest.OpenSearchIngestClient;
import org.opensearch.client.opensearch.ingest.Processor;
import org.opensearch.client.opensearch.ingest.PutPipelineRequest;
import org.opensearch.client.opensearch.ingest.SetProcessor;
import org.opensearch.client.transport.endpoints.BooleanResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author André de Oliveira
 * @author Petteri Karttunen
 */
public class CompanyIndexFactoryTest extends BaseOpenSearchTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_putTimestampPipeline(
			openSearchConnectionManager.getOpenSearchClient());
	}

	@Before
	public void setUp() throws Exception {
		_companyIndexFactoryFixture = new CompanyIndexFactoryFixture(
			testName.getMethodName(), openSearchConnectionManager);

		_companyIndexFactory =
			_companyIndexFactoryFixture.getCompanyIndexFactory();

		IndexHelper indexHelper = _companyIndexFactoryFixture.getIndexHelper();

		Mockito.reset(_openSearchConfigurationWrapper);

		ReflectionTestUtil.setFieldValue(
			indexHelper, "_openSearchConfigurationWrapper",
			_openSearchConfigurationWrapper);

		ReflectionTestUtil.setFieldValue(
			_companyIndexFactory, "_openSearchConfigurationWrapper",
			_openSearchConfigurationWrapper);

		Mockito.when(
			_openSearchConfigurationWrapper.indexMaxResultWindow()
		).thenReturn(
			10000
		);

		_singleFieldFixture = new SingleFieldFixture(
			openSearchConnectionManager.getOpenSearchClient(),
			new IndexName(_companyIndexFactoryFixture.getIndexName()));

		_singleFieldFixture.setQueryBuilderFactory(QueryFactories.MATCH);
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
			_openSearchConfigurationWrapper.additionalIndexConfigurations()
		).thenReturn(
			JSONUtil.put(
				"index",
				JSONUtil.put(
					"number_of_replicas", 1
				).put(
					"number_of_shards", 2
				)
			).toString()
		);
		createIndices();

		_assertIndexSettings(1, 2);

		deleteIndices();
	}

	@Test
	public void testAdditionalTypeMappings() throws Exception {
		Mockito.when(
			_openSearchConfigurationWrapper.additionalTypeMappings()
		).thenReturn(
			loadAdditionalTypeMappings()
		);

		_assertAdditionalTypeMappings();
	}

	@Test
	public void testAdditionalTypeMappingsWithLegacyRootType()
		throws Exception {

		Mockito.when(
			_openSearchConfigurationWrapper.additionalTypeMappings()
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
			_companyIndexFactoryFixture.getIndexHelper(),
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
		deleteIndices();
	}

	@Test
	public void testCreateIndicesWithBlankStrings() throws Exception {
		Mockito.when(
			_openSearchConfigurationWrapper.additionalIndexConfigurations()
		).thenReturn(
			StringPool.BLANK
		);

		Mockito.when(
			_openSearchConfigurationWrapper.additionalTypeMappings()
		).thenReturn(
			StringPool.SPACE
		);

		Mockito.when(
			_openSearchConfigurationWrapper.indexNumberOfReplicas()
		).thenReturn(
			StringPool.BLANK
		);

		Mockito.when(
			_openSearchConfigurationWrapper.indexNumberOfShards()
		).thenReturn(
			StringPool.SPACE
		);

		createIndices();
		deleteIndices();
	}

	@Test
	public void testCreateIndicesWithEmptyConfiguration() throws Exception {
		createIndices();
		deleteIndices();
	}

	@Test
	public void testDefaultIndexSettings() throws Exception {
		createIndices();

		_assertIndexSettings(0, 1);

		deleteIndices();
	}

	@Test
	public void testDefaultIndices() throws Exception {
		createIndices();

		_assertMappings(Field.COMPANY_ID, Field.ENTRY_CLASS_NAME);

		deleteIndices();
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
			_openSearchConfigurationWrapper.additionalIndexConfigurations()
		).thenReturn(
			JSONUtil.put(
				"index",
				JSONUtil.put(
					"number_of_replicas", 0
				).put(
					"number_of_shards", 0
				)
			).toString()
		);

		createIndices();

		_assertIndexSettings(2, 3);

		deleteIndices();
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
							_replaceAnalyzer("brazilian", mappings));
					}

					@Override
					public void contributeSettings(
						IndexSettingsHelper indexSettingsHelper) {
					}

				},
				null));

		Mockito.when(
			_openSearchConfigurationWrapper.additionalTypeMappings()
		).thenReturn(
			_replaceAnalyzer("portuguese", mappings)
		);

		createIndices();

		String field = RandomTestUtil.randomString() + "_ja";

		_indexOneDocument(field);

		assertAnalyzer("brazilian", field);

		deleteIndices();
	}

	@Test
	public void testIndexConfigurations() throws Exception {
		Mockito.when(
			_openSearchConfigurationWrapper.indexNumberOfReplicas()
		).thenReturn(
			"0"
		);

		Mockito.when(
			_openSearchConfigurationWrapper.indexNumberOfShards()
		).thenReturn(
			"3"
		);

		createIndices();

		_assertIndexSettings(0, 3);

		deleteIndices();
	}

	@Test
	public void testOptionalDefaultTemplateIsAlwaysAfterContributedTemplates()
		throws Exception {

		Mockito.when(
			_openSearchConfigurationWrapper.additionalTypeMappings()
		).thenReturn(
			loadAdditionalTypeMappings()
		);

		createIndices();

		_indexOneDocument("match_additional_mapping");
		_indexOneDocument("match_catch_all");

		assertType("match_additional_mapping", "keyword");
		assertType("match_catch_all", "text");

		deleteIndices();
	}

	@Test
	public void testOverrideLegacyTypeMappings() throws Exception {
		Mockito.when(
			_openSearchConfigurationWrapper.additionalIndexConfigurations()
		).thenReturn(
			_loadAdditionalAnalyzers()
		);

		Mockito.when(
			_openSearchConfigurationWrapper.overrideTypeMappings()
		).thenReturn(
			_loadOverrideLegacyTypeMappings()
		);

		createIndices();

		String field1 = "title";

		_indexOneDocument(field1);

		assertAnalyzer("kuromoji_liferay_custom", field1);

		String field2 = "description";

		_indexOneDocument(field2);

		_assertNoAnalyzer(field2);

		deleteIndices();
	}

	@Test
	public void testOverrideTypeMappings() throws Exception {
		Mockito.when(
			_openSearchConfigurationWrapper.additionalIndexConfigurations()
		).thenReturn(
			_loadAdditionalAnalyzers()
		);

		Mockito.when(
			_openSearchConfigurationWrapper.overrideTypeMappings()
		).thenReturn(
			_loadOverrideTypeMappings()
		);

		createIndices();

		String field1 = "title";

		_indexOneDocument(field1);

		assertAnalyzer("kuromoji_liferay_custom", field1);

		String field2 = "description";

		_indexOneDocument(field2);

		_assertNoAnalyzer(field2);

		deleteIndices();
	}

	@Test
	public void testOverrideTypeMappingsHonorDefaultIndices() throws Exception {
		Mockito.when(
			_openSearchConfigurationWrapper.additionalIndexConfigurations()
		).thenReturn(
			_loadAdditionalAnalyzers()
		);

		Mockito.when(
			_openSearchConfigurationWrapper.overrideTypeMappings()
		).thenReturn(
			_loadOverrideTypeMappings()
		);

		createIndices();

		_assertMappings(Field.TITLE);

		deleteIndices();
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

	protected void assertAnalyzer(String analyzer, String field)
		throws Exception {

		OpenSearchClient openSearchClient =
			openSearchConnectionManager.getOpenSearchClient();

		FieldMappingAssert.assertAnalyzer(
			analyzer, field, _companyIndexFactoryFixture.getIndexName(),
			openSearchClient.indices());
	}

	protected void assertType(String field, String type) throws Exception {
		OpenSearchClient openSearchClient =
			openSearchConnectionManager.getOpenSearchClient();

		FieldMappingAssert.assertType(
			type, field, _companyIndexFactoryFixture.getIndexName(),
			openSearchClient.indices());
	}

	protected void createIndices() throws Exception {
		OpenSearchClient openSearchClient =
			openSearchConnectionManager.getOpenSearchClient();

		OpenSearchIndicesClient openSearchIndicesClient =
			openSearchClient.indices();

		_companyIndexFactory.createIndices(
			RandomTestUtil.randomLong(), openSearchIndicesClient);
	}

	protected void deleteIndices() {
		OpenSearchClient openSearchClient =
			openSearchConnectionManager.getOpenSearchClient();

		OpenSearchIndicesClient openSearchIndicesClient =
			openSearchClient.indices();

		_companyIndexFactory.deleteIndices(
			RandomTestUtil.randomLong(), openSearchIndicesClient);
	}

	protected boolean hasIndex(String indexName) {
		OpenSearchClient openSearchClient =
			openSearchConnectionManager.getOpenSearchClient();

		OpenSearchIndicesClient openSearchIndicesClient =
			openSearchClient.indices();

		try {
			BooleanResponse booleanResponse = openSearchIndicesClient.exists(
				ExistsRequest.of(
					existRequest -> existRequest.index(indexName)));

			return booleanResponse.value();
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

	private static void _putTimestampPipeline(OpenSearchClient openSearchClient)
		throws Exception {

		Processor.Builder processorBuilder = new Processor.Builder();

		processorBuilder.set(
			SetProcessor.of(
				setProcessor -> setProcessor.field(
					"_source.timestamp"
				).value(
					JsonData.of("{{{_ingest.timestamp}}}")
				)));

		PutPipelineRequest.Builder putPipelineRequestBuilder =
			new PutPipelineRequest.Builder();

		putPipelineRequestBuilder.id("timestamp");
		putPipelineRequestBuilder.description("Adds timestamp to documents");
		putPipelineRequestBuilder.processors(processorBuilder.build());

		OpenSearchIngestClient ingestClient = openSearchClient.ingest();

		ingestClient.putPipeline(putPipelineRequestBuilder.build());
	}

	private void _assertAdditionalTypeMappings() throws Exception {
		Mockito.when(
			_openSearchConfigurationWrapper.additionalIndexConfigurations()
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

		assertAnalyzer("english", intactFieldName);

		String replacedFieldName = RandomTestUtil.randomString() + "_ja";

		_indexOneDocument(replacedFieldName);

		assertAnalyzer("kuromoji_liferay_custom", replacedFieldName);

		deleteIndices();
	}

	private void _assertHasIndex(String indexName) {
		Assert.assertTrue(
			"Index " + indexName + " does not exist", hasIndex(indexName));
	}

	private void _assertIndexSettings(
		int numberOfReplicas, int numberOfShards) {

		IndexSettings indexSettings1 = _getIndexSettings();

		IndexSettings indexSettings2 = indexSettings1.index();

		Assert.assertEquals(
			String.valueOf(numberOfReplicas),
			indexSettings2.numberOfReplicas());
		Assert.assertEquals(
			String.valueOf(numberOfShards), indexSettings2.numberOfShards());
	}

	private void _assertMappings(String... fieldNames) {
		String indexName = _companyIndexFactoryFixture.getIndexName();

		GetIndexResponse getIndexResponse = getIndex(indexName);

		IndexState indexState = getIndexResponse.get(indexName);

		TypeMapping typeMapping = indexState.mappings();

		Map<String, Property> properties = typeMapping.properties();

		Set<String> keySet = properties.keySet();

		Assert.assertThat(keySet, CoreMatchers.hasItems(fieldNames));
	}

	private void _assertNoAnalyzer(String field) throws Exception {
		assertAnalyzer(null, field);
	}

	private void _assertNoIndex(String indexName) {
		Assert.assertFalse(
			"Index " + indexName + " exists", hasIndex(indexName));
	}

	private IndexSettings _getIndexSettings() {
		String indexName = _companyIndexFactoryFixture.getIndexName();

		GetIndexResponse getIndexResponse = getIndex(indexName);

		IndexState indexState = getIndexResponse.get(indexName);

		return indexState.settings();
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

	private String _replaceAnalyzer(String analyzer, String mappings) {
		return StringUtil.replace(
			mappings, "kuromoji_liferay_custom", analyzer);
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	private CompanyIndexFactory _companyIndexFactory;
	private CompanyIndexFactoryFixture _companyIndexFactoryFixture;
	private final OpenSearchConfigurationWrapper
		_openSearchConfigurationWrapper = Mockito.mock(
			OpenSearchConfigurationWrapperImpl.class);
	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();
	private SingleFieldFixture _singleFieldFixture;

}