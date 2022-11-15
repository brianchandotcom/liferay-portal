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