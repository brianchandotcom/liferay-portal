/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.filter;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author André de Oliveira
 * @author Eric Yan
 */
public abstract class BaseTermFilterTestCase extends BaseIndexingTestCase {

	@Test
	public void testBasicSearch() throws Exception {
		index("One");

		assertTermFilter("One", "One");

		assertTermFilter("one", "");
	}

	@Test
	public void testLuceneSpecialCharacters() throws Exception {
		String value = "One\\+-!():^[]\"{}~*?|&/Two";

		index(value);

		assertTermFilter(value, value);
	}

	@Test
	public void testSolrSpecialCharacters() throws Exception {
		String value = "One\\+-!():^[]\"{}~*?|&/; Two";

		index(value);

		assertTermFilter(value, value);
	}

	@Test
	public void testSpaces() throws Exception {
		index("One Two");

		assertTermFilter("One Two", "One Two");

		assertTermFilter("One", "");
		assertTermFilter("Two", "");
	}

	protected void assertTermFilter(String filterValue, String expectedValue)
		throws Exception {

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.setFilter(
					new TermFilter(_FIELD, filterValue));

				indexingTestHelper.search();

				indexingTestHelper.assertValues(
					_FIELD, Arrays.asList(expectedValue));
			});
	}

	protected void index(String value) throws Exception {
		addDocument(DocumentCreationHelpers.singleKeyword(_FIELD, value));
	}

	private static final String _FIELD = Field.FOLDER_ID;

}