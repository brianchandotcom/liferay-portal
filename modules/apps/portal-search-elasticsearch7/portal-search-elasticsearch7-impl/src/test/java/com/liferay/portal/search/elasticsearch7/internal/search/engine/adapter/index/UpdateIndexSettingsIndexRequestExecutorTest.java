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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.engine.adapter.index.UpdateIndexSettingsIndexRequest;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;

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

		_indicesOptionsTranslator = new IndicesOptionsTranslatorImpl();
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

		UpdateIndexSettingsIndexRequestExecutorImpl
			updateIndexSettingsIndexRequestExecutorImpl =
				new UpdateIndexSettingsIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			updateIndexSettingsIndexRequestExecutorImpl,
			"_elasticsearchClientResolver", _elasticsearchFixture);
		ReflectionTestUtil.setFieldValue(
			updateIndexSettingsIndexRequestExecutorImpl,
			"_indicesOptionsTranslator", _indicesOptionsTranslator);

		UpdateSettingsRequest updateSettingsRequest =
			updateIndexSettingsIndexRequestExecutorImpl.
				createUpdateSettingsRequest(updateIndexSettingsIndexRequest);

		String[] indices = updateSettingsRequest.indices();

		Assert.assertEquals(Arrays.toString(indices), 1, indices.length);
		Assert.assertEquals(_INDEX_NAME, indices[0]);
	}

	private static final String _INDEX_NAME = "test_request_index";

	private ElasticsearchFixture _elasticsearchFixture;
	private IndicesOptionsTranslator _indicesOptionsTranslator;

}