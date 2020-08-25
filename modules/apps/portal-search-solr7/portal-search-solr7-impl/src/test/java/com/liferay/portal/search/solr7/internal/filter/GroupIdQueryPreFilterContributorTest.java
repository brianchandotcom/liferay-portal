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

package com.liferay.portal.search.solr7.internal.filter;

import com.liferay.portal.search.solr7.internal.SolrIndexingFixture;
import com.liferay.portal.search.test.util.filter.groupid.BaseGroupIdQueryPreFilterContributorTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Joshua Cords
 */
public class GroupIdQueryPreFilterContributorTest
	extends BaseGroupIdQueryPreFilterContributorTestCase {

	@Ignore
	@Test
	public void testScopeEverythingWithInactiveGroups() {
	}

	@Ignore
	@Test
	public void testScopeSingleGroup() throws Exception {
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return new SolrIndexingFixture();
	}

}