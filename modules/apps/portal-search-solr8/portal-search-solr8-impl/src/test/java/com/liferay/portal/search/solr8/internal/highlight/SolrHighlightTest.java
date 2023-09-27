/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.highlight;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.StringQuery;
import com.liferay.portal.search.solr8.internal.SolrIndexingFixture;
import com.liferay.portal.search.test.util.highlight.BaseHighlightTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class SolrHighlightTest extends BaseHighlightTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testEllipsisSolr() throws Exception {
		String fieldName = Field.TITLE;

		addDocuments(
			value -> DocumentCreationHelpers.singleText(fieldName, value),
			"alpha", "alpha beta", "alpha beta alpha",
			"alpha beta gamma alpha eta theta alpha zeta eta alpha iota",
			"alpha beta gamma delta epsilon zeta eta theta iota alpha");

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