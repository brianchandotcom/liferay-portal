/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index;

import co.elastic.clients.elasticsearch.indices.PutIndicesSettingsRequest;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.engine.adapter.index.UpdateIndexSettingsIndexRequest;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class UpdateIndexSettingsIndexRequestExecutorTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			UpdateIndexSettingsIndexRequestExecutorTest.class.getSimpleName());

		_elasticsearchFixture.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Test
	public void testIndexRequestTranslation() {
		UpdateIndexSettingsIndexRequest updateIndexSettingsIndexRequest =
			new UpdateIndexSettingsIndexRequest(_INDEX_NAME);

		updateIndexSettingsIndexRequest.setSettings(
			StringBundler.concat(
				"{\n", "    \"analysis\": {\n", "        \"analyzer\": {\n",
				"            \"content\": {\n",
				"                \"tokenizer\": \"whitespace\",\n",
				"                \"type\": \"custom\"\n", "            }\n",
				"        }\n", "    }\n", "}"));

		UpdateIndexSettingsIndexRequestExecutor
			updateIndexSettingsIndexRequestExecutor =
				new UpdateIndexSettingsIndexRequestExecutor(
					_elasticsearchFixture);

		PutIndicesSettingsRequest putIndicesSettingsRequest =
			updateIndexSettingsIndexRequestExecutor.
				createPutIndicesSettingsRequest(
					updateIndexSettingsIndexRequest);

		List<String> indices = putIndicesSettingsRequest.index();

		Assert.assertEquals(String.join(", ", indices), 1, indices.size());
		Assert.assertEquals(_INDEX_NAME, indices.get(0));
	}

	private static final String _INDEX_NAME = "test_request_index";

	private ElasticsearchFixture _elasticsearchFixture;

}