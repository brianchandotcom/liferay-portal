/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.index;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.index.UpdateIndexSettingsIndexRequest;
import com.liferay.portal.search.opensearch2.internal.BaseOpenSearchTestCase;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.opensearch.client.opensearch.indices.PutIndicesSettingsRequest;

/**
 * @author Michael C. Han
 */
public class UpdateIndexSettingsIndexRequestExecutorTest
	extends BaseOpenSearchTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Test
	public void testIndexRequestTranslation() {
		UpdateIndexSettingsIndexRequest updateIndexSettingsIndexRequest =
			new UpdateIndexSettingsIndexRequest(TEST_INDEX_NAME);

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
			"_openSearchConnectionManager", openSearchConnectionManager);

		PutIndicesSettingsRequest putIndicesSettingsRequest =
			updateIndexSettingsIndexRequestExecutorImpl.
				createPutIndicesSettingsRequest(
					updateIndexSettingsIndexRequest);

		List<String> indices = putIndicesSettingsRequest.index();

		Assert.assertEquals(String.join(", ", indices), 1, indices.size());
		Assert.assertEquals(TEST_INDEX_NAME, indices.get(0));
	}

}