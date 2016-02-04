package com.liferay.portal.search.solr.internal.groupby;

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

import com.liferay.portal.search.solr.internal.SolrIndexingFixture;
import com.liferay.portal.search.unit.test.IndexingFixture;
import com.liferay.portal.search.unit.test.groupby.BaseGroupByTestCase;

import org.junit.Test;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class GroupByTest extends BaseGroupByTestCase {

	@Override
	@Test
	public void testGroupBy() throws Exception {
		super.testGroupBy();
	}

	@Override
	@Test
	public void testStartAndEnd() throws Exception {
		super.testStartAndEnd();
	}

	@Override
	@Test
	public void testStartAndSize() throws Exception {
		super.testStartAndSize();
	}

	@Override
	protected IndexingFixture createIndexingFixture(String indexName)
		throws Exception {

		return new SolrIndexingFixture();
	}

}