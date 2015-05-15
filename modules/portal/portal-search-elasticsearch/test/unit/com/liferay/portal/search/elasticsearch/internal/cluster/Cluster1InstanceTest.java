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

package com.liferay.portal.search.elasticsearch.internal.cluster;

import com.liferay.portal.search.elasticsearch.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch.internal.connection.ElasticsearchFixture.Index;

import org.elasticsearch.indices.recovery.RecoveryState.Type;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * @author André de Oliveira
 */
public class Cluster1InstanceTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			Cluster1InstanceTest.class.getSimpleName());

		_elasticsearchFixture.setUpClass();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_elasticsearchFixture.tearDownClass();

		_elasticsearchFixture = null;
	}

	@Test
	public void test1PrimaryShardByDefault() throws Exception {
		Index index = createIndex();

		ClusterAssert.assertShards(index, Type.GATEWAY);
	}

	@Rule
	public TestName testName = new TestName();

	protected Index createIndex() {
		return _elasticsearchFixture.createIndex(testName.getMethodName());
	}

	private static ElasticsearchFixture _elasticsearchFixture;

}