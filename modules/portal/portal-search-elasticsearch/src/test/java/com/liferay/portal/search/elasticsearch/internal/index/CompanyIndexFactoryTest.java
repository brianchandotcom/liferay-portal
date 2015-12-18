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
import com.liferay.portal.search.elasticsearch.internal.cluster.TestCluster;
import com.liferay.portal.search.elasticsearch.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch.settings.BaseIndexSettingsContributor;

import java.util.HashMap;

import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;

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
	public void testActivate() throws Exception {
		HashMap<String, Object> properties = new HashMap<>();

		_companyIndexFactory.activate(properties);
	}

	@Test
	public void testAdditionalIndexConfigurations() throws Exception {
		_companyIndexFactory.setAdditionalIndexConfigurations(
			new String[] {
				"index.number_of_replicas: 1", "index.number_of_shards: 2"
			});

		Settings settings = createIndex();

		Assert.assertEquals("1", settings.get("index.number_of_replicas"));
		Assert.assertEquals("2", settings.get("index.number_of_shards"));
	}

	@Test
	public void testDefaults() throws Exception {
		Settings settings = createIndex();

		Assert.assertEquals("0", settings.get("index.number_of_replicas"));
		Assert.assertEquals("1", settings.get("index.number_of_shards"));
	}

	@Test
	public void testIndexSettingsContributor() throws Exception {
		_companyIndexFactory.addIndexSettingsContributor(
			new BaseIndexSettingsContributor(1) {

				@Override
				public void populate(Builder builder) {
					builder.put("index.number_of_replicas", "2");
					builder.put("index.number_of_shards", "3");
				}

			});
		_companyIndexFactory.setAdditionalIndexConfigurations(
			new String[] {
				"index.number_of_replicas: 0", "index.number_of_shards: 0"
			});

		Settings settings = createIndex();

		Assert.assertEquals("2", settings.get("index.number_of_replicas"));
		Assert.assertEquals("3", settings.get("index.number_of_shards"));
	}

	protected Settings createIndex() throws Exception {
		AdminClient adminClient = _elasticsearchFixture.getAdminClient();

		_companyIndexFactory.createIndices(adminClient, _COMPANY_ID);

		return getIndexSettings();
	}

	protected Settings getIndexSettings() {
		String name = String.valueOf(_COMPANY_ID);

		GetIndexResponse getIndexResponse = _elasticsearchFixture.getIndex(
			name);

		ImmutableOpenMap<String, Settings> immutableOpenMap =
			getIndexResponse.getSettings();

		return immutableOpenMap.get(name);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private final CompanyIndexFactory _companyIndexFactory =
		new CompanyIndexFactory();
	private ElasticsearchFixture _elasticsearchFixture;
	private final TestCluster _testCluster = new TestCluster(1, this);

}