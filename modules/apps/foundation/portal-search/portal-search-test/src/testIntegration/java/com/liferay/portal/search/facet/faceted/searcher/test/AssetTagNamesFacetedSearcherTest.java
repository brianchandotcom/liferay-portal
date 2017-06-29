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

package com.liferay.portal.search.facet.faceted.searcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.search.facet.tag.AssetTagNamesFacetFactory;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.Collections;
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrew Betts
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
@Sync
public class AssetTagNamesFacetedSearcherTest
	extends BaseFacetedSearcherTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		Registry registry = RegistryUtil.getRegistry();

		_assetTagNamesFacetFactory = registry.getService(
			AssetTagNamesFacetFactory.class);
	}

	@Test
	public void testSearchByFacet() throws Exception {
		String tag = "enterprise. open-source for life";

		addUser(tag);

		SearchContext searchContext = getSearchContext(tag);

		Facet facet = _assetTagNamesFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		search(searchContext);

		Map<String, Integer> frequencies = Collections.singletonMap(tag, 1);

		assertFrequencies(facet.getFieldName(), searchContext, frequencies);
	}

	@Test
	public void testSearchQuoted() throws Exception {
		String[] assetTagNames = {"Enterprise", "Open Source", "For   Life"};

		User user = addUser(assetTagNames);

		Map<String, String> expected = userSearchFixture.toMap(
			user, assetTagNames);

		assertTags("\"Enterprise\"", expected);
		assertTags("\"Open\"", expected);
		assertTags("\"Source\"", expected);
		assertTags("\"Open Source\"", expected);
		assertTags("\"For   Life\"", expected);
	}

	protected User addUser(String... assetTagNames) throws Exception {
		Group group = userSearchFixture.addGroup();

		return addUser(group, assetTagNames);
	}

	protected void assertTags(String keywords, Map<String, String> expected)
		throws Exception {

		SearchContext searchContext = getSearchContext(keywords);

		Hits hits = search(searchContext);

		assertTags(keywords, hits, expected);
	}

	private AssetTagNamesFacetFactory _assetTagNamesFacetFactory;

}