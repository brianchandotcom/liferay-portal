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
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.internal.facet.tag.AssetTagNamesFacet;
import com.liferay.portal.search.test.util.indexing.QueryContributor;
import com.liferay.portal.search.test.util.indexing.QueryContributors;

import java.util.Arrays;
import java.util.List;

/**
 * @author André de Oliveira
 */
public abstract class BaseAssetTagNamesFacetTestCase extends BaseFacetTestCase {

	protected void assertFacet(
			QueryContributor queryContributor, List<String> expectedTerms)
		throws Exception {

		assertFacet(
			searchContext -> initFacet(new AssetTagNamesFacet(searchContext)),
			queryContributor, expectedTerms);
	}

	@Override
	protected String getField() {
		return Field.ASSET_TAG_NAMES;
	}

	protected void testCaseInsensitiveSearchCaseSensitiveGrouping()
		throws Exception {

		addDocument("tag");
		addDocument("tAg");
		addDocument("TAG");
		addDocument(RandomTestUtil.randomString());

		assertFacet(
			QueryContributors.mustMatch(getField(), "tag"),
			Arrays.asList("TAG=1", "tAg=1", "tag=1"));
	}

	protected void testKeysWithSpaces() throws Exception {
		addDocument("Green-Blue Tag");
		addDocument("Green-Blue Tag", "Red Tag");
		addDocument("Tag");

		assertFacet(
			QueryContributors.dummy(),
			Arrays.asList("Green-Blue Tag=2", "Red Tag=1", "Tag=1"));
	}

}