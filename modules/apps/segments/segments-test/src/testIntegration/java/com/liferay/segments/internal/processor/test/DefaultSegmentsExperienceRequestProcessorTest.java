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

package com.liferay.segments.internal.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.processor.SegmentsExperienceRequestProcessor;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class DefaultSegmentsExperienceRequestProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetSegmentsExperienceIds() throws Exception {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		long classNameId = _classNameLocalService.getClassNameId(
			Layout.class.getName());

		Layout layout = _addLayout();

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.addSegmentsExperience(
				segmentsEntry.getSegmentsEntryId(), classNameId,
				layout.getPlid(), RandomTestUtil.randomLocaleStringMap(), 0,
				true, new UnicodeProperties(true),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		long[] segmentsExperienceIds =
			_segmentsExperienceRequestProcessor.getSegmentsExperienceIds(
				new MockHttpServletRequest(), new MockHttpServletResponse(),
				_group.getGroupId(), classNameId, layout.getPlid(),
				new long[0]);

		Assert.assertEquals(
			Arrays.toString(segmentsExperienceIds), 1,
			segmentsExperienceIds.length);
		Assert.assertEquals(
			segmentsExperience.getSegmentsExperienceId(),
			segmentsExperienceIds[0]);
	}

	@Test
	public void testGetSegmentsExperienceIdsWithoutSegmentsExperienceIds()
		throws Exception {

		Layout layout = _addLayout();

		long[] segmentsExperienceIds =
			_segmentsExperienceRequestProcessor.getSegmentsExperienceIds(
				new MockHttpServletRequest(), new MockHttpServletResponse(),
				_group.getGroupId(),
				_classNameLocalService.getClassNameId(Layout.class.getName()),
				layout.getPlid(), new long[0]);

		Assert.assertEquals(
			Arrays.toString(segmentsExperienceIds), 0,
			segmentsExperienceIds.length);
	}

	@Test
	public void testGetSegmentsExperienceIdsWithoutSegmentsExperienceIdsAndWithoutSegmentEntryIds()
		throws Exception {

		Layout layout = _addLayout();

		long[] segmentsExperienceIds =
			_segmentsExperienceRequestProcessor.getSegmentsExperienceIds(
				new MockHttpServletRequest(), new MockHttpServletResponse(),
				_group.getGroupId(),
				_classNameLocalService.getClassNameId(Layout.class.getName()),
				layout.getPlid(), new long[0], new long[0]);

		Assert.assertEquals(
			Arrays.toString(segmentsExperienceIds), 0,
			segmentsExperienceIds.length);
	}

	@Test
	public void testGetSegmentsExperienceIdsWithSegmentEntryIds()
		throws Exception {

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		long classNameId = _classNameLocalService.getClassNameId(
			Layout.class.getName());

		Layout layout = _addLayout();

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.addSegmentsExperience(
				segmentsEntry.getSegmentsEntryId(), classNameId,
				layout.getPlid(), RandomTestUtil.randomLocaleStringMap(), 0,
				true, new UnicodeProperties(true),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		long[] segmentsExperienceIds =
			_segmentsExperienceRequestProcessor.getSegmentsExperienceIds(
				new MockHttpServletRequest(), new MockHttpServletResponse(),
				_group.getGroupId(), classNameId, layout.getPlid(), new long[0],
				new long[] {segmentsEntry.getSegmentsEntryId()});

		Assert.assertEquals(
			Arrays.toString(segmentsExperienceIds), 1,
			segmentsExperienceIds.length);
		Assert.assertEquals(
			segmentsExperience.getSegmentsExperienceId(),
			segmentsExperienceIds[0]);
	}

	private Layout _addLayout() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		return _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, LayoutConstants.TYPE_CONTENT, false,
			StringPool.BLANK, serviceContext);
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Inject(
		filter = "component.name=*.DefaultSegmentsExperienceRequestProcessor"
	)
	private SegmentsExperienceRequestProcessor
		_segmentsExperienceRequestProcessor;

}