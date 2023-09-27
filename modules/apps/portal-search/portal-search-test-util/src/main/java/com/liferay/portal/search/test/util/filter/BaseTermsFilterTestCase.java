/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.filter;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author André de Oliveira
 */
public abstract class BaseTermsFilterTestCase extends BaseIndexingTestCase {

	@Test
	public void testBasicSearch() throws Exception {
		index("One");
		index("Two");
		index("Three");

		assertTermsFilter(new String[] {"Two", "Three"});
	}

	@Test
	public void testLuceneSpecialCharacters() throws Exception {
		index("One\\+-!():^[]\"{}~*?|&/Two");
		index("Three");

		assertTermsFilter(
			new String[] {"One\\+-!():^[]\"{}~*?|&/Two", "Three"});
	}

	@Test
	public void testSolrSpecialCharacters() throws Exception {
		index("One\\+-!():^[]\"{}~*?|&/; Two");
		index("Three");

		assertTermsFilter(
			new String[] {"One\\+-!():^[]\"{}~*?|&/; Two", "Three"});
	}

	@Test
	public void testSpaces() throws Exception {
		index("One Two");
		index("Three");

		assertTermsFilter(new String[] {"One Two", "Three"});
	}

	protected void assertTermsFilter(String[] values) throws Exception {
		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.setFilter(
					new TermsFilter(_FIELD) {
						{
							addValues(values);
						}
					});

				indexingTestHelper.search();

				indexingTestHelper.assertValues(_FIELD, Arrays.asList(values));
			});
	}

	protected void index(String value) throws Exception {
		addDocument(DocumentCreationHelpers.singleKeyword(_FIELD, value));
	}

	private static final String _FIELD = Field.FOLDER_ID;

}