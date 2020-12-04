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

package com.liferay.segments.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsWebKeys;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class SegmentsEntryRetrieverTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser(_group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setRequest(new MockHttpServletRequest());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testGetSegmentsEntryIds() throws Exception {
		SegmentsEntry segmentsEntry = _addSegmentsEntry(_user);

		long[] segmentsEntryIds = _segmentsEntryRetriever.getSegmentsEntryIds(
			_group.getGroupId(), _user.getUserId(), null);

		Assert.assertEquals(
			Arrays.toString(segmentsEntryIds), 2, segmentsEntryIds.length);

		Assert.assertEquals(
			segmentsEntry.getSegmentsEntryId(), segmentsEntryIds[0]);

		Assert.assertEquals(
			SegmentsEntryConstants.ID_DEFAULT, segmentsEntryIds[1]);
	}

	@Test
	public void testGetSegmentsEntryIdsWithCachedSegmentsEntry() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		HttpServletRequest httpServletRequest = serviceContext.getRequest();

		httpServletRequest.setAttribute(
			SegmentsWebKeys.SEGMENTS_ENTRY_IDS, new long[] {1234567L});

		long[] segmentsEntryIds = _segmentsEntryRetriever.getSegmentsEntryIds(
			_group.getGroupId(), _user.getUserId(), null);

		Assert.assertEquals(
			Arrays.toString(segmentsEntryIds), 2, segmentsEntryIds.length);

		Assert.assertEquals(1234567L, segmentsEntryIds[0]);

		Assert.assertEquals(
			SegmentsEntryConstants.ID_DEFAULT, segmentsEntryIds[1]);
	}

	@Test
	public void testGetSegmentsEntryIdsWithoutSegmentsEntry() throws Exception {
		long[] segmentsEntryIds = _segmentsEntryRetriever.getSegmentsEntryIds(
			_group.getGroupId(), _user.getUserId(), null);

		Assert.assertEquals(
			Arrays.toString(segmentsEntryIds), 1, segmentsEntryIds.length);

		Assert.assertEquals(
			SegmentsEntryConstants.ID_DEFAULT, segmentsEntryIds[0]);
	}

	private SegmentsEntry _addSegmentsEntry(User user) throws Exception {
		Criteria criteria = new Criteria();

		_userSegmentsCriteriaContributor.contribute(
			criteria, String.format("(firstName eq '%s')", user.getFirstName()),
			Criteria.Conjunction.AND);

		return SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), CriteriaSerializer.serialize(criteria),
			User.class.getName());
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SegmentsEntryRetriever _segmentsEntryRetriever;

	@DeleteAfterTestRun
	private User _user;

	@Inject(
		filter = "segments.criteria.contributor.key=user",
		type = SegmentsCriteriaContributor.class
	)
	private SegmentsCriteriaContributor _userSegmentsCriteriaContributor;

}