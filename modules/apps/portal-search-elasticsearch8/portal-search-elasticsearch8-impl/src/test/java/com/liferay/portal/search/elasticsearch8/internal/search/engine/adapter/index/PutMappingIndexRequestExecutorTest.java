/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch.indices.PutMappingRequest;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.engine.adapter.index.PutMappingIndexRequest;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Dylan Rebelak
 */
public class PutMappingIndexRequestExecutorTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			PutMappingIndexRequestExecutorTest.class.getSimpleName());

		_elasticsearchFixture.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Ignore
	@Test
	public void testIndexRequestTranslation() throws Exception {
		PutMappingIndexRequest putMappingIndexRequest =
			new PutMappingIndexRequest(new String[] {_INDEX_NAME}, _FIELD_NAME);

		PutMappingIndexRequestExecutor putMappingIndexRequestExecutor =
			new PutMappingIndexRequestExecutor(_elasticsearchFixture);

		PutMappingRequest putMappingRequest =
			putMappingIndexRequestExecutor.createPutMappingRequest(
				putMappingIndexRequest);

		Assert.assertArrayEquals(
			new String[] {_INDEX_NAME},
			ArrayUtil.toStringArray(putMappingRequest.index()));

		Map<String, Property> properties = putMappingRequest.properties();

		Assert.assertNotNull(properties.get(_FIELD_NAME));
	}

	private static final String _FIELD_NAME = "testField";

	private static final String _INDEX_NAME = "test_request_index";

	private ElasticsearchFixture _elasticsearchFixture;

}