/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.document;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.internal.document.DocumentBuilderImpl;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.hits.FieldsTranslator;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.opensearch.client.json.JsonData;

/**
 * @author André de Oliveira
 */
public class DocumentFieldsTranslatorTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Test
	public void testDocumentSourceMapWithMultiValueField() {
		FieldsTranslator fieldsTranslator = new FieldsTranslator(null);

		DocumentBuilder documentBuilder = new DocumentBuilderImpl();

		String fieldName = RandomTestUtil.randomString();

		List<String> list1 = Arrays.asList(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		fieldsTranslator.translateFields(
			documentBuilder,
			Collections.singletonMap(fieldName, JsonData.of(list1)));

		Document document = documentBuilder.build();

		List<String> list2 = document.getStrings(fieldName);

		Assert.assertEquals(list1.toString(), list2.toString());
	}

}