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