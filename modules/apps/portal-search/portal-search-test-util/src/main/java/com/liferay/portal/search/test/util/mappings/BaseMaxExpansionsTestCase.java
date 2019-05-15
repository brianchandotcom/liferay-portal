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

package com.liferay.portal.search.test.util.mappings;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.internal.analysis.SimpleKeywordTokenizer;
import com.liferay.portal.search.internal.analysis.TitleFieldQueryBuilder;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * @author Bryan Engler
 */
public abstract class BaseMaxExpansionsTestCase
	extends BaseFieldQueryBuilderTestCase {

	@Test
	public void testPrefixWithDashNumberSuffix() throws Exception {
		addDocuments("Prefix-#");

		assertSearch("Prefi", _TOTAL_DOCUMENTS);
		assertSearchCount("Prefi", _TOTAL_DOCUMENTS);
	}

	@Test
	public void testPrefixWithDotNumberSuffix() throws Exception {
		addDocuments("Prefix.#");

		assertSearch("Prefi", _TOTAL_DOCUMENTS);
		assertSearchCount("Prefi", _TOTAL_DOCUMENTS);
	}

	@Test
	public void testPrefixWithNoSuffix() throws Exception {
		addDocuments("Prefix");

		assertSearch("Prefi", _TOTAL_DOCUMENTS);
		assertSearchCount("Prefi", _TOTAL_DOCUMENTS);
	}

	@Test
	public void testPrefixWithNumberSpaceNumberSuffix() throws Exception {
		addDocuments("Prefix# #");

		assertSearch("Prefi", _MAX_EXPANSIONS);
		assertSearchCount("Prefi", _MAX_EXPANSIONS);
	}

	@Test
	public void testPrefixWithNumberSuffix() throws Exception {
		addDocuments("Prefix#");

		assertSearch("Prefi", _MAX_EXPANSIONS);
		assertSearchCount("Prefi", _MAX_EXPANSIONS);
	}

	@Test
	public void testPrefixWithSpaceNumberSuffix() throws Exception {
		addDocuments("Prefix #");

		assertSearch("Prefi", _TOTAL_DOCUMENTS);
		assertSearchCount("Prefi", _TOTAL_DOCUMENTS);
	}

	@Test
	public void testPrefixWithUnderscoreNumberSuffix() throws Exception {
		addDocuments("Prefix_#");

		assertSearch("Prefi", _MAX_EXPANSIONS);
		assertSearchCount("Prefi", _MAX_EXPANSIONS);
	}

	protected void addDocuments(String pattern) throws Exception {
		for (int i = 1; i <= _TOTAL_DOCUMENTS; i++) {
			addDocument(
				StringUtil.replace(pattern, CharPool.POUND, String.valueOf(i)));
		}
	}

	@Override
	protected FieldQueryBuilder createFieldQueryBuilder() {
		return new TitleFieldQueryBuilder() {
			{
				keywordTokenizer = new SimpleKeywordTokenizer();

				Map<String, Object> properties = new HashMap<>();

				properties.put("maxExpansions", _MAX_EXPANSIONS);

				activate(properties);
			}
		};
	}

	@Override
	protected String getField() {
		return Field.TITLE;
	}

	private static final int _MAX_EXPANSIONS = 60;

	private static final int _TOTAL_DOCUMENTS = 65;

}