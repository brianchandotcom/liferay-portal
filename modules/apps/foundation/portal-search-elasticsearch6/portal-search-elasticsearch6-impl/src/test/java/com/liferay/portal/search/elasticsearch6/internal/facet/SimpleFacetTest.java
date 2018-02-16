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

package com.liferay.portal.search.elasticsearch6.internal.facet;

import com.liferay.portal.search.elasticsearch6.internal.ElasticsearchIndexingFixture;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch6.internal.connection.LiferayIndexCreator;
import com.liferay.portal.search.elasticsearch6.internal.count.ElasticsearchCountTest;
import com.liferay.portal.search.test.util.facet.BaseSimpleFacetTestCase;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;

import org.junit.Test;

/**
 * @author Bryan Engler
 */
public class SimpleFacetTest extends BaseSimpleFacetTestCase {

	@Override
	@Test
	public void testFrequencyThreshold() throws Exception {
		super.testFrequencyThreshold();
	}

	@Override
	@Test
	public void testMaxTerms() throws Exception {
		super.testMaxTerms();
	}

	@Override
	@Test
	public void testMaxTermsNegative() throws Exception {
		super.testMaxTermsNegative();
	}

	@Override
	@Test
	public void testMaxTermsZero() throws Exception {
		super.testMaxTermsZero();
	}

	@Override
	@Test
	public void testUnmatchedAreIgnored() throws Exception {
		super.testUnmatchedAreIgnored();
	}

	@Override
	protected IndexingFixture createIndexingFixture() {
		ElasticsearchFixture elasticsearchFixture = new ElasticsearchFixture(
			ElasticsearchCountTest.class.getSimpleName());

		return new ElasticsearchIndexingFixture(
			elasticsearchFixture, BaseIndexingTestCase.COMPANY_ID,
			new LiferayIndexCreator(elasticsearchFixture));
	}

}