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

package com.liferay.portal.search.test.util.count;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.search.test.util.document.BaseDocumentTestCase;

import org.junit.Test;

/**
 * @author Wade Cao
 */
public abstract class BaseDocumentCountTestCase extends BaseDocumentTestCase {

	@Test
	public void testAllWordsInAllDocuments() throws Exception {
		assertCount("sixth fifth fourth third second first", 6);
	}

	@Test
	public void testOneWordInAllDocuments() throws Exception {
		assertCount("Smith", 6);
	}

	@Test
	public void testOneWordPerDocument() throws Exception {
		assertCount("first", 1);

		assertCount("second", 1);

		assertCount("third", 1);

		assertCount("fourth", 1);

		assertCount("fifth", 1);

		assertCount("sixth", 1);
	}

	protected void assertCount(String keywords, int expectedCount)
		throws Exception {

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.setQuery(getQuery(keywords));

				indexingTestHelper.search();

				indexingTestHelper.assertResultCount(expectedCount);
			});
	}

	protected Query getQuery(String keywords) {
		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		booleanQueryImpl.add(
			new MatchQuery("firstName", keywords), BooleanClauseOccur.SHOULD);
		booleanQueryImpl.add(
			new MatchQuery("lastName", keywords), BooleanClauseOccur.SHOULD);

		return booleanQueryImpl;
	}

}