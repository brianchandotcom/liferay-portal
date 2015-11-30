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

package com.liferay.portal.search;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.FacetedSearcher;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.MultiValueFacet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Andrew Betts
 */
@Sync
public class FacetedSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		WorkflowThreadLocal.setEnabled(false);

		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();
	}

	@Test
	public void testSearchByFacet() throws Exception {
		String tag = "enterprise. open-source for life";

		addUser(_group1, tag);

		SearchContext searchContext = getSearchContext(tag);

		MultiValueFacet multiValueFacet = new MultiValueFacet(searchContext);

		multiValueFacet.setFieldName(Field.ASSET_TAG_NAMES);
		multiValueFacet.setStatic(false);

		searchContext.addFacet(multiValueFacet);

		FacetedSearcher facetedSearcher = new FacetedSearcher();

		facetedSearcher.search(searchContext);

		Map<String, Facet> facets = searchContext.getFacets();

		Facet facet = facets.get(Field.ASSET_TAG_NAMES);

		FacetCollector facetCollector = facet.getFacetCollector();

		List<TermCollector> termCollectors = facetCollector.getTermCollectors();

		Assert.assertEquals(1, termCollectors.size());

		TermCollector termCollector = termCollectors.get(0);

		Assert.assertEquals(tag, termCollector.getTerm());
		Assert.assertEquals(1, termCollector.getFrequency());
	}

	@Test
	public void testSearchByKeywords() throws Exception {
		String tag = RandomTestUtil.randomString();

		addUser(_group1, tag);

		assertSearch(tag, 1);
	}

	@Test
	public void testSearchByKeywordsIgnoresInactiveSites() throws Exception {
		addUser(_group1, "Liferay " + RandomTestUtil.randomString());
		addUser(_group2, "Liferay " + RandomTestUtil.randomString());

		assertSearch("Liferay", 2);

		deactivate(_group1);

		assertSearch("Liferay", 1);

		deactivate(_group2);

		assertSearch("Liferay", 0);
	}

	protected static void addUser(Group group, String tag) throws Exception {
		ServiceContext serviceContext = getServiceContext(group, tag);

		User user = UserTestUtil.addUser(group.getGroupId());

		UserTestUtil.updateUser(user, serviceContext);
	}

	protected static void assertSearch(String tag, int count) throws Exception {
		FacetedSearcher indexer = new FacetedSearcher();

		Hits hits = indexer.search(getSearchContext(tag));

		Assert.assertEquals(count, hits.getLength());
	}

	protected static void deactivate(Group group) {
		group.setActive(false);

		GroupLocalServiceUtil.updateGroup(group);
	}

	protected static SearchContext getSearchContext(String keywords)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setKeywords(keywords);

		return searchContext;
	}

	protected static ServiceContext getServiceContext(Group group, String tag)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setAssetTagNames(new String[] {tag});

		return serviceContext;
	}

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

}