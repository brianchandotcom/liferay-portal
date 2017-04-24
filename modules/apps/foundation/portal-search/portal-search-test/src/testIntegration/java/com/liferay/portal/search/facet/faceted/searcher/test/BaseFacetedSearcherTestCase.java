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

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcher;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcherManager;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.search.test.internal.util.UserSearchFixture;
import com.liferay.portal.search.test.util.AssertUtils;
import com.liferay.portal.search.test.util.TermCollectorUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;

/**
 * @author André de Oliveira
 */
public abstract class BaseFacetedSearcherTestCase {

	@Before
	public void setUp() throws Exception {
		WorkflowThreadLocal.setEnabled(false);

		setUpFacetedSearcherManager();
		setUpUserSearchFixture();
	}

	@After
	public void tearDown() throws Exception {
		userSearchFixture.tearDown();
	}

	protected void assertFrequencies(
		String fieldName, SearchContext searchContext,
		Map<String, Integer> expected) {

		Map<String, Facet> facets = searchContext.getFacets();

		Facet facet = facets.get(fieldName);

		FacetCollector facetCollector = facet.getFacetCollector();

		AssertUtils.assertEquals(
			searchContext.getKeywords(), expected,
			TermCollectorUtil.toMap(facetCollector.getTermCollectors()));
	}

	protected void assertTags(
		String keywords, Hits hits, Map<String, String> expected) {

		AssertUtils.assertEquals(
			keywords, expected, userSearchFixture.toMap(hits.toList()));
	}

	protected FacetedSearcher createFacetedSearcher() {
		return _facetedSearcherManager.createFacetedSearcher();
	}

	protected Hits search(SearchContext searchContext) throws Exception {
		FacetedSearcher facetedSearcher = createFacetedSearcher();

		return facetedSearcher.search(searchContext);
	}

	protected void setUpFacetedSearcherManager() {
		Registry registry = RegistryUtil.getRegistry();

		_facetedSearcherManager = registry.getService(
			FacetedSearcherManager.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture.setUp();

		_assetTags = userSearchFixture.getAssetTags();
		_groups = userSearchFixture.getGroups();
		_users = userSearchFixture.getUsers();
	}

	protected Map<String, String> toMap(User user, String... tags) {
		return userSearchFixture.toMap(user, tags);
	}

	protected final UserSearchFixture userSearchFixture =
		new UserSearchFixture();

	@DeleteAfterTestRun
	private List<AssetTag> _assetTags;

	private FacetedSearcherManager _facetedSearcherManager;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<User> _users;

}