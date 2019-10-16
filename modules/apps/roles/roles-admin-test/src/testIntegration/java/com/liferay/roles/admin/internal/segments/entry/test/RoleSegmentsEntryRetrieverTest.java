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

package com.liferay.roles.admin.internal.segments.entry.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.roles.admin.segments.entry.RoleSegmentsEntryManager;
import com.liferay.roles.admin.segments.entry.RoleSegmentsEntryRetriever;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class RoleSegmentsEntryRetrieverTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext());
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testComponentRegistered() {
		Assert.assertNotNull(_roleSegmentsEntryRetriever);
	}

	@Test
	public void testSearch() throws Exception {
		_segmentsEntries.add(
			SegmentsTestUtil.addSegmentsEntry(TestPropsValues.getGroupId()));

		_assertSearch(null, 0);

		_addRoleSegmentsEntry();

		_assertSearch(null, 1);
	}

	@Test
	public void testSearchByKeyword() throws Exception {
		String keywords = RandomTestUtil.randomString();

		_segmentsEntries.add(
			SegmentsTestUtil.addSegmentsEntry(
				TestPropsValues.getGroupId(), keywords + 1));

		_assertSearch(keywords, 0);

		_addRoleSegmentsEntry();

		_assertSearch(keywords, 0);

		_addRoleSegmentsEntry(keywords + 2);

		_assertSearch(keywords, 1);
	}

	private void _addRoleSegmentsEntry() throws Exception {
		_addRoleSegmentsEntry(RandomTestUtil.randomString());
	}

	private void _addRoleSegmentsEntry(String name) throws Exception {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			TestPropsValues.getGroupId(), RandomTestUtil.randomString(), name,
			RandomTestUtil.randomString(),
			CriteriaSerializer.serialize(new Criteria()),
			RandomTestUtil.randomString());

		_segmentsEntries.add(segmentsEntry);

		_roleSegmentsEntryManager.addRoleSegmentsEntries(
			_role.getRoleId(), new long[] {segmentsEntry.getSegmentsEntryId()});
	}

	private void _assertSearch(String keywords, int expected) throws Exception {
		BaseModelSearchResult<SegmentsEntry> baseModelSearchResult =
			_roleSegmentsEntryRetriever.searchRoleSegmentsEntries(
				_role.getRoleId(), keywords, 0, 10, null, false);

		Assert.assertEquals(expected, baseModelSearchResult.getLength());
	}

	@DeleteAfterTestRun
	private Role _role;

	@Inject
	private RoleSegmentsEntryManager _roleSegmentsEntryManager;

	@Inject(blocking = false)
	private RoleSegmentsEntryRetriever _roleSegmentsEntryRetriever;

	@DeleteAfterTestRun
	private final List<SegmentsEntry> _segmentsEntries = new ArrayList<>();

}