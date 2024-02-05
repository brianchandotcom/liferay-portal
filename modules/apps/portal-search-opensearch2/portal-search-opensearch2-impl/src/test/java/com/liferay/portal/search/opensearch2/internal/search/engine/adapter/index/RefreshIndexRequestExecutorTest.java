/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.index;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.engine.adapter.index.RefreshIndexRequest;
import com.liferay.portal.search.opensearch2.internal.BaseOpenSearchTestCase;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.opensearch.client.opensearch.indices.RefreshRequest;

/**
 * @author Michael C. Han
 */
public class RefreshIndexRequestExecutorTest extends BaseOpenSearchTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Test
	public void testIndexRequestTranslation() {
		RefreshIndexRequest refreshIndexRequest = new RefreshIndexRequest(
			TEST_INDEX_NAME);

		RefreshIndexRequestExecutorImpl refreshIndexRequestExecutorImpl =
			new RefreshIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			refreshIndexRequestExecutorImpl,
			"_indexRequestShardFailureTranslator",
			new IndexRequestShardFailureTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			refreshIndexRequestExecutorImpl, "_openSearchConnectionManager",
			openSearchConnectionManager);

		RefreshRequest refreshRequest =
			refreshIndexRequestExecutorImpl.createRefreshRequest(
				refreshIndexRequest);

		Assert.assertArrayEquals(
			new String[] {TEST_INDEX_NAME},
			ArrayUtil.toStringArray(refreshRequest.index()));
	}

}