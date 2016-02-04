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
import com.liferay.portal.search.elasticsearch.internal.cluster.TestCluster;
import com.liferay.portal.search.elasticsearch.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch.internal.util.ResourceUtil;
import com.liferay.portal.search.elasticsearch.settings.BaseIndexSettingsContributor;
import com.liferay.portal.search.elasticsearch.settings.TypeMappingsHelper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class CompanyIndexFactoryTest {

	@Before
	public void setUp() throws Exception {
		_testCluster.setUp();

		_elasticsearchFixture = _testCluster.getNode(0);
	}

	@After
	public void tearDown() throws Exception {
		_testCluster.tearDown();
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

		String field = indexOneDocument();

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
		Map<String, Object> properties = new HashMap<>();

		properties.put(
			"typeMappings.KeywordQueryDocumentType",
			"/META-INF/mappings/keyword-query-type-mappings.json");
		properties.put(
			"typeMappings.SpellCheckDocumentType",
			"/META-INF/mappings/spellcheck-type-mappings.json");

		_companyIndexFactory.activate(properties);

		createIndices();

		GetIndexResponse getIndexResponse = _elasticsearchFixture.getIndex(
			String.valueOf(_COMPANY_ID));

		ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>>
			mappings = getIndexResponse.mappings();

		Iterator<ImmutableOpenMap<String, MappingMetaData>> iterator =
			mappings.valuesIt();

		ImmutableOpenMap<String, MappingMetaData> map = iterator.next();

		Assert.assertTrue(
			map.containsKey(
				LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE));
		Assert.assertTrue(map.containsKey("KeywordQueryDocumentType"));
		Assert.assertTrue(map.containsKey("SpellCheckDocumentType"));
	}

	@Test
	public void testIndexSettingsContributor() throws Exception {
		_companyIndexFactory.addIndexSettingsContributor(
			new BaseIndexSettingsContributor(1) {

				@Override
				public void populate(Settings.Builder builder) {
					builder.put("index.number_of_replicas", "2");
					builder.put("index.number_of_shards", "3");
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
						replaceAnalyzer(mappings, "brazilian"));
				}

			});

		_companyIndexFactory.setAdditionalTypeMappings(
			replaceAnalyzer(mappings, "portuguese"));

		createIndices();

		String field = indexOneDocument();

		assertAnalyzer(field, "brazilian");
	}

	protected void assertAnalyzer(String field, String analyzer)
		throws Exception {

		FieldMappingAssert.assertAnalyzer(
			analyzer, field, LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE,
			String.valueOf(_COMPANY_ID),
			_elasticsearchFixture.getIndicesAdminClient());
	}

	protected void createIndices() throws Exception {
		AdminClient adminClient = _elasticsearchFixture.getAdminClient();

		_companyIndexFactory.createIndices(adminClient, _COMPANY_ID);
	}

	protected Settings getIndexSettings() {
		String name = String.valueOf(_COMPANY_ID);

		GetIndexResponse getIndexResponse = _elasticsearchFixture.getIndex(
			name);

		ImmutableOpenMap<String, Settings> immutableOpenMap =
			getIndexResponse.getSettings();

		return immutableOpenMap.get(name);
	}

	protected String indexOneDocument() {
		Client client = _elasticsearchFixture.getClient();

		IndexRequestBuilder indexRequestBuilder = client.prepareIndex(
			String.valueOf(_COMPANY_ID),
			LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE);

		String field = RandomTestUtil.randomString() + "_ja";

		indexRequestBuilder.setSource(field, RandomTestUtil.randomString());

		indexRequestBuilder.get();

		return field;
	}

	protected String loadAdditionalAnalyzers() throws Exception {
		return ResourceUtil.getResourceAsString(
			getClass(), "CompanyIndexFactoryTest-additionalAnalyzers.json");
	}

	protected String loadAdditionalTypeMappings() throws Exception {
		return ResourceUtil.getResourceAsString(
			getClass(), "CompanyIndexFactoryTest-additionalTypeMappings.json");
	}

	protected String replaceAnalyzer(String mappings, String analyzer) {
		return StringUtil.replace(
			mappings, "kuromoji_liferay_custom", analyzer);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private final CompanyIndexFactory _companyIndexFactory =
		new CompanyIndexFactory();
	private ElasticsearchFixture _elasticsearchFixture;
	private final TestCluster _testCluster = new TestCluster(1, this);

}