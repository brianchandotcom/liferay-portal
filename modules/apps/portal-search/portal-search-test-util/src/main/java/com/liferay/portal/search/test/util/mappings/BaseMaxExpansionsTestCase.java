/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.mappings;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.internal.analysis.SimpleKeywordTokenizer;
import com.liferay.portal.search.internal.analysis.TitleFieldQueryBuilder;

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

		assertSearch("Prefi", MAX_EXPANSIONS);
		assertSearchCount("Prefi", MAX_EXPANSIONS);
	}

	@Test
	public void testPrefixWithNumberSuffix() throws Exception {
		addDocuments("Prefix#");

		assertSearch("Prefi", MAX_EXPANSIONS);
		assertSearchCount("Prefi", MAX_EXPANSIONS);
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

		assertSearch("Prefi", MAX_EXPANSIONS);
		assertSearchCount("Prefi", MAX_EXPANSIONS);
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

				activate(
					HashMapBuilder.<String, Object>put(
						"maxExpansions", MAX_EXPANSIONS
					).build());
			}
		};
	}

	@Override
	protected String getField() {
		return Field.TITLE;
	}

	protected static final int MAX_EXPANSIONS = 5;

	private static final int _TOTAL_DOCUMENTS = 10;

}