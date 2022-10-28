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

package com.liferay.portal.search.solr8.internal.sort;

import com.liferay.portal.search.solr8.internal.SolrIndexingFixture;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.search.test.util.sort.BaseDocumentSortTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public class SolrDocumentSortTest extends BaseDocumentSortTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Ignore("LPS-83586")
	@Override
	@Test
	public void testLongSort() throws Exception {
	}

	@Ignore("LPS-83586")
	@Override
	@Test
	public void testLongSortIgnoresScores() throws Exception {
	}

	@Override
	protected IndexingFixture createIndexingFixture() {
		return new SolrIndexingFixture();
	}

}