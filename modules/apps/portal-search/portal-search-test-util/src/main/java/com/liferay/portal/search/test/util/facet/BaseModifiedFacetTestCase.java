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

package com.liferay.portal.search.test.util.facet;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.ModifiedFacet;
import com.liferay.portal.search.test.util.indexing.QueryContributors;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * @author Bryan Engler
 */
public abstract class BaseModifiedFacetTestCase extends BaseFacetTestCase {

	@Test
	public void testRange() throws Exception {
		addDocument("20170102000000");
		addDocument("20170104000000");
		addDocument("20170106000000");

		assertFacet(
			"[20170101000000 TO 20170105000000]",
			Arrays.asList("[20170101000000 TO 20170105000000]=2"));
	}

	protected void assertFacet(String customRange, List<String> expectedTerms)
		throws Exception {

		assertFacet(
			searchContext -> createFacet(searchContext, customRange),
			QueryContributors.dummy(), expectedTerms);
	}

	protected Facet createFacet(
		SearchContext searchContext, String customRange) {

		Facet facet = initFacet(new ModifiedFacet(searchContext));

		searchContext.setAttribute(facet.getFieldId(), customRange);

		return facet;
	}

	@Override
	protected String getField() {
		return Field.MODIFIED_DATE;
	}

}