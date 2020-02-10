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

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch7.internal.connection.IndexName;
import com.liferay.portal.search.elasticsearch7.internal.document.SingleFieldFixture;
import com.liferay.portal.search.elasticsearch7.internal.query.QueryBuilderFactories;
import com.liferay.portal.search.elasticsearch7.internal.settings.BaseIndexSettingsContributor;
import com.liferay.portal.search.elasticsearch7.internal.settings.IndexSettingsContributorHelper;
import com.liferay.portal.search.elasticsearch7.internal.util.ResourceUtil;
import com.liferay.portal.search.elasticsearch7.settings.IndexSettingsContributor;
import com.liferay.portal.search.elasticsearch7.settings.IndexSettingsHelper;
import com.liferay.portal.search.elasticsearch7.settings.TypeMappingsHelper;
import com.liferay.portal.search.spi.model.index.contributor.IndexContributor;

import java.io.IOException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * @author André de Oliveira
 */
public class CompanyIndexFactoryTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			CompanyIndexFactoryTest.class.getSimpleName());

		_elasticsearchFixture.setUp();
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

		_indexSettingsContributors = ReflectionTestUtil.getFieldValue(
			(IndexSettingsContributorHelper)ReflectionTestUtil.getFieldValue(
				_companyIndexFactory, "_indexSettingsContributorHelper"),
			"_indexSettingsContributors");

		_singleFieldFixture = new SingleFieldFixture(
			_elasticsearchFixture.getRestHighLevelClient(),
			new IndexName(_companyIndexFactoryFixture.getIndexName()),
			LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE);

		_singleFieldFixture.setQueryBuilderFactory(QueryBuilderFactories.MATCH);
	}

	@Test
	public void testAdditionalIndexConfigurations() throws Exception {
		_companyIndexFactory.setAdditionalIndexConfigurations(
			"index.number_of_replicas: 1\nindex.number_of_shards: 2");

		createIndices();

		Settings settings = getIndexSettings();

		Assert.assertEquals("1", settings.get("index.number_of_replicas"));
		Assert.assertEquals("2", settings.get("index.number_of_shards"));
	}

	@Test
	public void testAdditionalTypeMappings() throws Exception {
		_companyIndexFactory.setAdditionalTypeMappings(
			loadAdditionalTypeMappings());

		assertAdditionalTypeMappings();
	}

	@Test
	public void testAdditionalTypeMappingsFromContributor() throws Exception {
		addIndexSettingsContributor(loadAdditionalTypeMappings());

		assertAdditionalTypeMappings();
	}

	@Test
	public void testAdditionalTypeMappingsWithRootType() throws Exception {
		_companyIndexFactory.setAdditionalTypeMappings(
			loadAdditionalTypeMappingsWithRootType());

		assertAdditionalTypeMappings();
	}

	@Test
	public void testAdditionalTypeMappingsWithRootTypeFromContributor()
		throws Exception {

		addIndexSettingsContributor(loadAdditionalTypeMappingsWithRootType());

		assertAdditionalTypeMappings();
	}

	@Test
	public void testCreateIndicesWithBlankStrings() throws Exception {
		Map<String, Object> properties = HashMapBuilder.<String, Object>put(
			"additionalIndexConfigurations", StringPool.BLANK
		).put(
			"additionalTypeMappings", StringPool.SPACE
		).put(
			"indexNumberOfReplicas", StringPool.BLANK
		).put(
			"indexNumberOfShards", StringPool.SPACE
		).build();

		_companyIndexFactory.activate(properties);

		createIndices();
	}

	@Test
	public void testCreateIndicesWithEmptyConfiguration() throws Exception {
		_companyIndexFactory.activate(new HashMap<String, Object>());

		createIndices();
	}

	@Test
	public void testDefaultIndexSettings() throws Exception {
		createIndices();

		Settings settings = getIndexSettings();

		Assert.assertEquals("0", settings.get("index.number_of_replicas"));
		Assert.assertEquals("1", settings.get("index.number_of_shards"));
	}

	@Test
	public void testDefaultIndices() throws Exception {
		_companyIndexFactory.activate(Collections.emptyMap());

		createIndices();

		assertIndicesExist(LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE);
	}

	@Test
	public void testIndexConfigurations() throws Exception {
		_companyIndexFactory.setIndexNumberOfReplicas("1");
		_companyIndexFactory.setIndexNumberOfShards("2");

		createIndices();

		Settings settings = getIndexSettings();

		Assert.assertEquals("1", settings.get("index.number_of_replicas"));
		Assert.assertEquals("2", settings.get("index.number_of_shards"));
	}

	@Test
	public void testIndexContributors() throws Exception {
		CompanyIndexFactoryFixture companyIndexFactoryFixture =
			new CompanyIndexFactoryFixture(_elasticsearchFixture, "other");

		addIndexContributor(
			new IndexContributor() {

				@Override
				public void onAfterCreate(String indexName) {
					companyIndexFactoryFixture.createIndices();
				}

				@Override
				public void onBeforeRemove(String indexName) {
					companyIndexFactoryFixture.deleteIndices();
				}

			});

		createIndices();

		assertHasIndex(companyIndexFactoryFixture.getIndexName());

		deleteIndices();

		assertNoIndex(companyIndexFactoryFixture.getIndexName());
	}

	@Test
	public void testIndexContributorsThrowsException() throws Exception {
		addIndexContributor(
			new IndexContributor() {

				@Override
				public void onAfterCreate(String indexName) {
					throw new RuntimeException();
				}

				@Override
				public void onBeforeRemove(String indexName) {
					throw new RuntimeException();
				}

			});

		createIndices();
	}

	@Test
	public void testIndexSettingsContributor() throws Exception {
		_indexSettingsContributors.add(
			new BaseIndexSettingsContributor(1) {

				@Override
				public void populate(IndexSettingsHelper indexSettingsHelper) {
					indexSettingsHelper.put("index.number_of_replicas", "2");
					indexSettingsHelper.put("index.number_of_shards", "3");
				}

			});
		_companyIndexFactory.setAdditionalIndexConfigurations(
			"index.number_of_replicas: 0\nindex.number_of_shards: 0");

		createIndices();

		Settings settings = getIndexSettings();

		Assert.assertEquals("2", settings.get("index.number_of_replicas"));
		Assert.assertEquals("3", settings.get("index.number_of_shards"));
	}

	@Test
	public void testIndexSettingsContributorTypeMappings() throws Exception {
		final String mappings = loadAdditionalTypeMappings();

		addIndexSettingsContributor(replaceAnalyzer(mappings, "brazilian"));

		_companyIndexFactory.setAdditionalTypeMappings(
			replaceAnalyzer(mappings, "portuguese"));

		createIndices();

		String field = RandomTestUtil.randomString() + "_ja";

		indexOneDocument(field);

		assertAnalyzer(field, "brazilian");
	}

	@Test
	public void testOverrideTypeMappings() throws Exception {
		_companyIndexFactory.setAdditionalIndexConfigurations(
			loadAdditionalAnalyzers());
		_companyIndexFactory.setOverrideTypeMappings(
			loadOverrideTypeMappings());

		createIndices();

		String field = "title";

		indexOneDocument(field);

		assertAnalyzer(field, "kuromoji_liferay_custom");

		String field2 = "description";

		indexOneDocument(field2);

		assertNoAnalyzer(field2);
	}

	@Test
	public void testOverrideTypeMappingsHonorDefaultIndices() throws Exception {
		_companyIndexFactory.activate(Collections.emptyMap());

		_companyIndexFactory.setAdditionalIndexConfigurations(
			loadAdditionalAnalyzers());
		_companyIndexFactory.setOverrideTypeMappings(
			loadOverrideTypeMappings());

		createIndices();

		assertIndicesExist(LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE);
	}

	@Test
	public void testOverrideTypeMappingsIgnoreOtherContributions()
		throws Exception {

		String mappings = replaceAnalyzer(
			loadAdditionalTypeMappings(), RandomTestUtil.randomString());

		addIndexSettingsContributor(mappings);

		_companyIndexFactory.setAdditionalIndexConfigurations(
			loadAdditionalAnalyzers());
		_companyIndexFactory.setAdditionalTypeMappings(mappings);
		_companyIndexFactory.setOverrideTypeMappings(
			loadOverrideTypeMappings());

		createIndices();

		String field = RandomTestUtil.randomString() + "_ja";

		indexOneDocument(field);

		assertNoAnalyzer(field);
	}

	@Rule
	public TestName testName = new TestName();

	protected void addIndexContributor(IndexContributor indexContributor) {
		_companyIndexFactory.addIndexContributor(indexContributor);
	}

	protected void addIndexSettingsContributor(String mappings) {
		_indexSettingsContributors.add(
			new BaseIndexSettingsContributor(1) {

				@Override
				public void contribute(
					String indexName, TypeMappingsHelper typeMappingsHelper) {

					typeMappingsHelper.addTypeMappings(indexName, mappings);
				}

			});
	}

	protected void assertAdditionalTypeMappings() throws Exception {
		_companyIndexFactory.setAdditionalIndexConfigurations(
			loadAdditionalAnalyzers());

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

		indexOneDocument(intactFieldName);

		assertAnalyzer(intactFieldName, "english");

		String replacedFieldName = RandomTestUtil.randomString() + "_ja";

		indexOneDocument(replacedFieldName);

		assertAnalyzer(replacedFieldName, "kuromoji_liferay_custom");
	}

	protected void assertAnalyzer(String field, String analyzer)
		throws Exception {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchFixture.getRestHighLevelClient();

		FieldMappingAssert.assertAnalyzer(
			analyzer, field, LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE,
			_companyIndexFactoryFixture.getIndexName(),
			restHighLevelClient.indices());
	}

	protected void assertHasIndex(String indexName) {
		Assert.assertTrue(
			"Index " + indexName + " does not exist", hasIndex(indexName));
	}

	protected void assertIndicesExist(String... indexNames) {
		GetIndexResponse getIndexResponse = _elasticsearchFixture.getIndex(
			_companyIndexFactoryFixture.getIndexName());

		ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>>
			mappings = getIndexResponse.getMappings();

		Iterator<ImmutableOpenMap<String, MappingMetaData>> iterator =
			mappings.valuesIt();

		ImmutableOpenMap<String, MappingMetaData> map = iterator.next();

		for (String indexName : indexNames) {
			Assert.assertTrue(indexName, map.containsKey(indexName));
		}
	}

	protected void assertNoAnalyzer(String field) throws Exception {
		assertAnalyzer(field, null);
	}

	protected void assertNoIndex(String indexName) {
		Assert.assertFalse(
			"Index " + indexName + " exists", hasIndex(indexName));
	}

	protected void assertType(String field, String type) throws Exception {
		RestHighLevelClient restHighLevelClient =
			_elasticsearchFixture.getRestHighLevelClient();

		FieldMappingAssert.assertType(
			type, field, LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE,
			_companyIndexFactoryFixture.getIndexName(),
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

	protected Settings getIndexSettings() {
		String name = _companyIndexFactoryFixture.getIndexName();

		GetIndexResponse getIndexResponse = _elasticsearchFixture.getIndex(
			name);

		ImmutableOpenMap<String, Settings> immutableOpenMap =
			getIndexResponse.getSettings();

		return immutableOpenMap.get(name);
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

	protected void indexOneDocument(String field) {
		indexOneDocument(field, RandomTestUtil.randomString());
	}

	protected void indexOneDocument(String field, String value) {
		_singleFieldFixture.setField(field);

		_singleFieldFixture.indexDocument(value);
	}

	protected String loadAdditionalAnalyzers() throws Exception {
		return ResourceUtil.getResourceAsString(
			getClass(), "CompanyIndexFactoryTest-additionalAnalyzers.json");
	}

	protected String loadAdditionalTypeMappings() throws Exception {
		return ResourceUtil.getResourceAsString(
			getClass(), "CompanyIndexFactoryTest-additionalTypeMappings.json");
	}

	protected String loadAdditionalTypeMappingsWithRootType() throws Exception {
		return ResourceUtil.getResourceAsString(
			getClass(),
			"CompanyIndexFactoryTest-additionalTypeMappings-with-root-type." +
				"json");
	}

	protected String loadOverrideTypeMappings() throws Exception {
		return ResourceUtil.getResourceAsString(
			getClass(), "CompanyIndexFactoryTest-overrideTypeMappings.json");
	}

	protected String replaceAnalyzer(String mappings, String analyzer) {
		return StringUtil.replace(
			mappings, "kuromoji_liferay_custom", analyzer);
	}

	private static ElasticsearchFixture _elasticsearchFixture;

	private CompanyIndexFactory _companyIndexFactory;
	private CompanyIndexFactoryFixture _companyIndexFactoryFixture;
	private Set<IndexSettingsContributor> _indexSettingsContributors;
	private SingleFieldFixture _singleFieldFixture;

}