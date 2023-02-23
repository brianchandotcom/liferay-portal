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

package com.liferay.portal.search.test.util.filter.tags;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.search.internal.spi.model.query.contributor.AssetTagNamesQueryPreFilterContributor;
import com.liferay.portal.search.spi.model.query.contributor.QueryPreFilterContributor;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * @author Wade Cao
 */
public abstract class BaseAssetTagNamesQueryPreFilterContributorTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testAssetTagNamesQueryPreFilter() throws Exception {
		String[] tagNames = {"tagNameOne", "tagNameThree tagNameTwo"};

		addDocuments(tagNames);

		assertSearch(tagNames[0], Arrays.asList(tagNames[0]));

		assertSearch(tagNames[1], Arrays.asList(tagNames[1]));
	}

	protected void addDocuments(String... values) throws Exception {
		addDocuments(
			value -> DocumentCreationHelpers.singleText(FIELD, value), values);
	}

	protected void assertSearch(String queryString, List<String> expectedValues)
		throws Exception {

		assertSearch(
			indexingTestHelper -> {
				QueryPreFilterContributor contributor =
					new AssetTagNamesQueryPreFilterContributor();

				BooleanFilter booleanFilter = new BooleanFilter();

				SearchContext searchContext =
					indexingTestHelper.getSearchContext();

				searchContext.setAssetTagNames(new String[] {queryString});

				contributor.contribute(booleanFilter, searchContext);

				indexingTestHelper.setFilter(booleanFilter);

				indexingTestHelper.search();

				indexingTestHelper.assertValues(FIELD, expectedValues);
			});
	}

	protected static final String FIELD = Field.ASSET_TAG_NAMES;

}