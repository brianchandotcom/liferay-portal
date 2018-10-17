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

package com.liferay.portal.search.solr7.internal.highlight;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.StringQuery;
import com.liferay.portal.search.solr7.internal.SolrIndexingFixture;
import com.liferay.portal.search.test.util.highlight.BaseHighlightTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class SolrHighlightTest extends BaseHighlightTestCase {

	@Test
	public void testEllipsisSolr() throws Exception {
		String fieldName = Field.TITLE;

		addDocuments(
			value -> DocumentCreationHelpers.singleText(fieldName, value),
			Arrays.asList(
				"alpha", "alpha beta", "alpha beta alpha",
				"alpha beta gamma alpha eta theta alpha zeta eta alpha iota",
				"alpha beta gamma delta epsilon zeta eta theta iota alpha"));

		Query query = new StringQuery(fieldName.concat(":alpha"));

		assertSearch(
			fieldName, query,
			queryConfig -> queryConfig.setHighlightFragmentSize(20),
			toFullHighlights(
				"[H]alpha[/H]", "[H]alpha[/H] beta",
				"[H]alpha[/H] beta [H]alpha[/H]",
				"[H]alpha[/H] beta gamma...[H]alpha[/H] eta theta [H]alpha" +
					"[/H]...zeta eta [H]alpha[/H] iota",
				"[H]alpha[/H] beta gamma...theta iota [H]alpha[/H]"));
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return new SolrIndexingFixture();
	}

}