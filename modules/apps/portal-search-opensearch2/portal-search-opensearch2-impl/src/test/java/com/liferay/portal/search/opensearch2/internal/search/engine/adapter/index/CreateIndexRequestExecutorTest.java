/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.index;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.opensearch2.internal.BaseOpenSearchTestCase;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class CreateIndexRequestExecutorTest extends BaseOpenSearchTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Test
	public void testIndexRequestTranslation() {
		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			TEST_INDEX_NAME);

		createIndexRequest.setSource(
			StringBundler.concat(
				"{\n", "    \"settings\": {\n", "        \"analysis\": {\n",
				"            \"analyzer\": {\n",
				"                \"content\": {\n",
				"                    \"tokenizer\": \"whitespace\",\n",
				"                    \"type\": \"custom\"\n",
				"                }\n", "            }\n", "        }\n",
				"    }\n", "}"));

		CreateIndexRequestExecutorImpl createIndexRequestExecutorImpl =
			new CreateIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			createIndexRequestExecutorImpl, "_jsonFactory",
			new JSONFactoryImpl());

		ReflectionTestUtil.setFieldValue(
			createIndexRequestExecutorImpl, "_openSearchConnectionManager",
			openSearchConnectionManager);

		org.opensearch.client.opensearch.indices.CreateIndexRequest
			elasticsearchCreateIndexRequest =
				createIndexRequestExecutorImpl.createCreateIndexRequest(
					createIndexRequest);

		Assert.assertEquals(
			TEST_INDEX_NAME, elasticsearchCreateIndexRequest.index());
	}

}