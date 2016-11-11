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

package com.liferay.portal.search.elasticsearch.internal.index;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch.index.IndexNameBuilder;
import com.liferay.portal.search.elasticsearch.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch.internal.connection.IndexName;
import com.liferay.portal.search.elasticsearch.internal.document.SingleFieldFixture;
import com.liferay.portal.search.elasticsearch.internal.util.ResourceUtil;
import com.liferay.portal.search.elasticsearch.settings.BaseIndexSettingsContributor;
import com.liferay.portal.search.elasticsearch.settings.IndexSettingsHelper;
import com.liferay.portal.search.elasticsearch.settings.TypeMappingsHelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * @author André de Oliveira
 */
public class CompanyIndexFactoryTest {

	@Before
	public void setUp() throws Exception {
		_companyIndexFactory = createCompanyIndexFactory();

		_elasticsearchFixture = new ElasticsearchFixture(
			CompanyIndexFactoryTest.class.getSimpleName());

		_elasticsearchFixture.setUp();

		_singleFieldFixture = new SingleFieldFixture(
			_elasticsearchFixture.getClient(),
			new IndexName(getTestIndexName()),
			LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE);
	}

	@After
	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
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
		_companyIndexFactory.setAdditionalIndexConfigurations(
			loadAdditionalAnalyzers());
		_companyIndexFactory.setAdditionalTypeMappings(
			loadAdditionalTypeMappings());

		createIndices();

		String field = RandomTestUtil.randomString() + "_ja";

		indexOneDocument(field);

		assertAnalyzer(field, "kuromoji_liferay_custom");
	}

	@Test
	public void testCreateIndicesWithBlankStrings() throws Exception {
		Map<String, Object> properties = new HashMap<>();

		properties.put("additionalIndexConfigurations", StringPool.BLANK);
		properties.put("additionalTypeMappings", StringPool.SPACE);

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
		_companyIndexFactory.activate(
			new HashMap<String, Object>() {
				{
					put(
						"typeMappings.KeywordQueryDocumentType",
						"/META-INF/mappings/keyword-query-type-mappings.json");
					put(
						"typeMappings.SpellCheckDocumentType",
						"/META-INF/mappings/spellcheck-type-mappings.json");
				}
			});

		createIndices();

		assertIndicesExist(
			LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE,
			"KeywordQueryDocumentType", "SpellCheckDocumentType");
	}

	@Test
	public void testIndexSettingsContributor() throws Exception {
		_companyIndexFactory.addIndexSettingsContributor(
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

		_companyIndexFactory.addIndexSettingsContributor(
			new BaseIndexSettingsContributor(1) {

				@Override
				public void contribute(TypeMappingsHelper typeMappingsHelper) {
					typeMappingsHelper.addTypeMappings(
						getTestIndexName(),
						replaceAnalyzer(mappings, "brazilian"));
				}

			});

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
		_companyIndexFactory.activate(
			Collections.<String, Object>singletonMap(
				"typeMappings.SpellCheckDocumentType",
				"/META-INF/mappings/spellcheck-type-mappings.json"));

		_companyIndexFactory.setAdditionalIndexConfigurations(
			loadAdditionalAnalyzers());
		_companyIndexFactory.setOverrideTypeMappings(
			loadOverrideTypeMappings());

		createIndices();

		assertIndicesExist(
			LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE,
			"SpellCheckDocumentType");
	}

	@Test
	public void testOverrideTypeMappingsIgnoreOtherContributions()
		throws Exception {

		final String mappings = replaceAnalyzer(
			loadAdditionalTypeMappings(), RandomTestUtil.randomString());

		_companyIndexFactory.addIndexSettingsContributor(
			new BaseIndexSettingsContributor(1) {

				@Override
				public void contribute(TypeMappingsHelper typeMappingsHelper) {
					typeMappingsHelper.addTypeMappings(
						getTestIndexName(), mappings);
				}

			});

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

	protected void assertAnalyzer(String field, String analyzer)
		throws Exception {

		FieldMappingAssert.assertAnalyzer(
			analyzer, field, LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE,
			getTestIndexName(), _elasticsearchFixture.getIndicesAdminClient());
	}

	protected void assertIndicesExist(String... indexNames) {
		GetIndexResponse getIndexResponse = _elasticsearchFixture.getIndex(
			getTestIndexName());

		ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>>
			mappings = getIndexResponse.mappings();

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

	protected CompanyIndexFactory createCompanyIndexFactory() {
		CompanyIndexFactory companyIndexFactory = new CompanyIndexFactory();

		companyIndexFactory.indexNameBuilder = new TestIndexNameBuilder();

		return companyIndexFactory;
	}

	protected void createIndices() throws Exception {
		AdminClient adminClient = _elasticsearchFixture.getAdminClient();

		_companyIndexFactory.createIndices(
			adminClient, RandomTestUtil.randomLong());
	}

	protected Settings getIndexSettings() {
		String name = getTestIndexName();

		GetIndexResponse getIndexResponse = _elasticsearchFixture.getIndex(
			name);

		ImmutableOpenMap<String, Settings> immutableOpenMap =
			getIndexResponse.getSettings();

		return immutableOpenMap.get(name);
	}

	protected String getTestIndexName() {
		IndexName indexName = new IndexName(testName.getMethodName());

		return indexName.getName();
	}

	protected void indexOneDocument(String field) {
		_singleFieldFixture.setField(field);

		_singleFieldFixture.indexDocument(RandomTestUtil.randomString());
	}

	protected String loadAdditionalAnalyzers() throws Exception {
		return ResourceUtil.getResourceAsString(
			getClass(), "CompanyIndexFactoryTest-additionalAnalyzers.json");
	}

	protected String loadAdditionalTypeMappings() throws Exception {
		return ResourceUtil.getResourceAsString(
			getClass(), "CompanyIndexFactoryTest-additionalTypeMappings.json");
	}

	protected String loadOverrideTypeMappings() throws Exception {
		return ResourceUtil.getResourceAsString(
			getClass(), "CompanyIndexFactoryTest-overrideTypeMappings.json");
	}

	protected String replaceAnalyzer(String mappings, String analyzer) {
		return StringUtil.replace(
			mappings, "kuromoji_liferay_custom", analyzer);
	}

	protected class TestIndexNameBuilder implements IndexNameBuilder {

		@Override
		public String getIndexName(long companyId) {
			return getTestIndexName();
		}

	}

	private CompanyIndexFactory _companyIndexFactory;
	private ElasticsearchFixture _elasticsearchFixture;
	private SingleFieldFixture _singleFieldFixture;

}