/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentRequest;
import com.liferay.portal.search.internal.script.ScriptsImpl;
import com.liferay.portal.search.opensearch2.internal.BaseOpenSearchTestCase;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.query.OpenSearchQueryTranslatorFixture;
import com.liferay.portal.search.opensearch2.internal.script.ScriptTranslator;
import com.liferay.portal.search.opensearch2.internal.util.JsonpUtil;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.opensearch.client.opensearch.core.UpdateByQueryRequest;

/**
 * @author Dylan Rebelak
 * @author Petteri Karttunen
 */
public class UpdateByQueryDocumentRequestExecutorTest
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

		com.liferay.portal.search.opensearch2.internal.legacy.query.
			OpenSearchQueryTranslatorFixture
				lecacyOpenSearchQueryTranslatorFixture =
					new com.liferay.portal.search.opensearch2.internal.legacy.
						query.OpenSearchQueryTranslatorFixture();

		OpenSearchQueryTranslatorFixture openSearchQueryTranslatorFixture =
			new OpenSearchQueryTranslatorFixture();

		UpdateByQueryDocumentRequest updateByQueryDocumentRequest =
			new UpdateByQueryDocumentRequest(
				booleanQuery, null, new String[] {TEST_INDEX_NAME});

		updateByQueryDocumentRequest.setRefresh(refresh);

		UpdateByQueryDocumentRequestExecutorImpl
			updateByQueryDocumentRequestExecutorImpl =
				new UpdateByQueryDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			updateByQueryDocumentRequestExecutorImpl, "_legacyQueryTranslator",
			lecacyOpenSearchQueryTranslatorFixture.
				getOpenSearchQueryTranslator());
		ReflectionTestUtil.setFieldValue(
			updateByQueryDocumentRequestExecutorImpl,
			"_openSearchConnectionManager", openSearchConnectionManager);
		ReflectionTestUtil.setFieldValue(
			updateByQueryDocumentRequestExecutorImpl, "_queryTranslator",
			openSearchQueryTranslatorFixture.getOpenSearchQueryTranslator());
		ReflectionTestUtil.setFieldValue(
			updateByQueryDocumentRequestExecutorImpl, "_scripts", _scripts);
		ReflectionTestUtil.setFieldValue(
			updateByQueryDocumentRequestExecutorImpl, "_scriptTranslator",
			new ScriptTranslator());

		UpdateByQueryRequest updateByQueryRequest =
			updateByQueryDocumentRequestExecutorImpl.createUpdateByQueryRequest(
				updateByQueryDocumentRequest);

		Assert.assertArrayEquals(
			new String[] {TEST_INDEX_NAME},
			ArrayUtil.toStringArray(updateByQueryRequest.index()));

		Assert.assertEquals(
			updateByQueryDocumentRequest.isRefresh(),
			GetterUtil.getBoolean(updateByQueryRequest.refresh()));

		String queryString = JsonpUtil.toString(updateByQueryRequest);

		Assert.assertTrue(queryString.contains(_FIELD_NAME));
		Assert.assertTrue(queryString.contains("\"value\":\"true\""));
	}

	private static final String _FIELD_NAME = "testField";

	private static final Scripts _scripts = new ScriptsImpl();

}