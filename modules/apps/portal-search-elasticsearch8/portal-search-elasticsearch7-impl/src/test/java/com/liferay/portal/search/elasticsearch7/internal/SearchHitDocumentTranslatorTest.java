/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.search.SearchHit;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Joshua Cords
 */
public class SearchHitDocumentTranslatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDocumentWithIgnoredField() {
		SearchHit searchHit = SearchHit.createFromMap(
			HashMapBuilder.<String, Object>put(
				"document_fields",
				() -> HashMapBuilder.put(
					"_ignored",
					() -> new DocumentField(
						"_ignored", Arrays.asList("value"),
						Arrays.asList("fieldName"))
				).build()
			).build());

		SearchHitDocumentTranslator searchHitDocumentTranslator =
			new SearchHitDocumentTranslatorImpl();

		searchHitDocumentTranslator.translate(searchHit);
	}

}