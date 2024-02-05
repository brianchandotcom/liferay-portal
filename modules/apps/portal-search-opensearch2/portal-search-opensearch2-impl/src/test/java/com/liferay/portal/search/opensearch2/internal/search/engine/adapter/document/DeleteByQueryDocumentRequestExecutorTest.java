/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.opensearch2.internal.BaseOpenSearchTestCase;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.query.OpenSearchQueryTranslatorFixture;
import com.liferay.portal.search.opensearch2.internal.util.JsonpUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.opensearch.client.opensearch.core.DeleteByQueryRequest;

/**
 * @author Dylan Rebelak
 */
public class DeleteByQueryDocumentRequestExecutorTest
	extends BaseOpenSearchTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Test
	public void testDocumentRequestTranslationWithNoRefresh() {
		doTestDocumentRequestTranslation(false);
	}

	@Test
	public void testDocumentRequestTranslationWithRefresh() {
		doTestDocumentRequestTranslation(true);
	}

	protected void doTestDocumentRequestTranslation(boolean refresh) {
		BooleanQuery booleanQuery = new BooleanQueryImpl();

		booleanQuery.addExactTerm(_FIELD_NAME, true);

		DeleteByQueryDocumentRequest deleteByQueryDocumentRequest =
			new DeleteByQueryDocumentRequest(
				booleanQuery, new String[] {TEST_INDEX_NAME});

		deleteByQueryDocumentRequest.setRefresh(refresh);

		com.liferay.portal.search.opensearch2.internal.legacy.query.
			OpenSearchQueryTranslatorFixture
				legacyOpenSearchQueryTranslatorFixture =
					new com.liferay.portal.search.opensearch2.internal.legacy.
						query.OpenSearchQueryTranslatorFixture();

		OpenSearchQueryTranslatorFixture openSearchQueryTranslatorFixture =
			new OpenSearchQueryTranslatorFixture();

		DeleteByQueryDocumentRequestExecutorImpl
			deleteByQueryDocumentRequestExecutorImpl =
				new DeleteByQueryDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			deleteByQueryDocumentRequestExecutorImpl, "_legacyQueryTranslator",
			legacyOpenSearchQueryTranslatorFixture.
				getOpenSearchQueryTranslator());
		ReflectionTestUtil.setFieldValue(
			deleteByQueryDocumentRequestExecutorImpl,
			"_openSearchConnectionManager", openSearchConnectionManager);
		ReflectionTestUtil.setFieldValue(
			deleteByQueryDocumentRequestExecutorImpl, "_queryTranslator",
			openSearchQueryTranslatorFixture.getOpenSearchQueryTranslator());

		DeleteByQueryRequest deleteByQueryRequest =
			deleteByQueryDocumentRequestExecutorImpl.createDeleteByQueryRequest(
				deleteByQueryDocumentRequest);

		Assert.assertArrayEquals(
			new String[] {TEST_INDEX_NAME},
			ArrayUtil.toStringArray(deleteByQueryRequest.index()));

		Assert.assertEquals(
			deleteByQueryDocumentRequest.isRefresh(),
			deleteByQueryRequest.refresh());

		String queryString = JsonpUtil.toString(deleteByQueryRequest);

		Assert.assertTrue(queryString.contains(_FIELD_NAME));
		Assert.assertTrue(queryString.contains("\"value\":\"true\""));
	}

	private static final String _FIELD_NAME = "testField";

}