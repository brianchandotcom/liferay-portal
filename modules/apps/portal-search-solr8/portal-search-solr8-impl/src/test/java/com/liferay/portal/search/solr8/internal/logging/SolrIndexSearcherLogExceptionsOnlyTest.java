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

package com.liferay.portal.search.solr8.internal.logging;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.solr8.internal.SolrIndexSearcher;
import com.liferay.portal.search.solr8.internal.SolrIndexingFixture;
import com.liferay.portal.search.solr8.internal.SolrUnitTestRequirements;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.search.test.util.logging.ExpectedLog;
import com.liferay.portal.search.test.util.logging.ExpectedLogMethodTestRule;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Bryan Engler
 */
public class SolrIndexSearcherLogExceptionsOnlyTest
	extends BaseIndexingTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			ExpectedLogMethodTestRule.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		Assume.assumeTrue(
			SolrUnitTestRequirements.isSolrExternallyStartedByDeveloper());
	}

	@ExpectedLog(
		expectedClass = SolrIndexSearcher.class,
		expectedLevel = ExpectedLog.Level.WARNING,
		expectedLog = "Cannot parse '+f^eld:text'"
	)
	@Test
	public void testExceptionOnlyLoggedWhenQueryMalformedSearch() {
		search(createSearchContext(), getMalformedQuery());
	}

	@ExpectedLog(
		expectedClass = SolrIndexSearcher.class,
		expectedLevel = ExpectedLog.Level.WARNING,
		expectedLog = "Cannot parse '+f^eld:text'"
	)
	@Test
	public void testExceptionOnlyLoggedWhenQueryMalformedSearchCount() {
		searchCount(createSearchContext(), getMalformedQuery());
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return new SolrIndexingFixture(
			HashMapBuilder.<String, Object>put(
				"logExceptionsOnly", true
			).build());
	}

	protected Query getMalformedQuery() {
		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		booleanQueryImpl.add(
			new TermQueryImpl("f^eld", "text"), BooleanClauseOccur.MUST);

		return booleanQueryImpl;
	}

}