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

package com.liferay.segments.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryRelLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Arques
 */
@RunWith(Arquillian.class)
public class SegmentsEntryRelLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddSegmentsEntryRel() throws PortalException {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		int classNameId = RandomTestUtil.randomInt();
		int classPK = RandomTestUtil.randomInt();

		SegmentsEntryRel segmentsEntryRel =
			_segmentsEntryRelLocalService.addSegmentsEntryRel(
				segmentsEntry.getSegmentsEntryId(), classNameId, classPK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertNotNull(segmentsEntryRel);
		Assert.assertEquals(
			segmentsEntry.getSegmentsEntryId(),
			segmentsEntryRel.getSegmentsEntryId());
		Assert.assertEquals(classNameId, segmentsEntryRel.getClassNameId());
		Assert.assertEquals(classPK, segmentsEntryRel.getClassPK());
	}

	@Test
	public void testDeleteSegmentsEntryRel() throws PortalException {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		int classNameId = RandomTestUtil.randomInt();
		int classPK = RandomTestUtil.randomInt();

		_segmentsEntryRelLocalService.addSegmentsEntryRel(
			segmentsEntry.getSegmentsEntryId(), classNameId, classPK,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsEntryRelLocalService.deleteSegmentsEntryRel(
			segmentsEntry.getSegmentsEntryId(), classNameId, classPK);

		Assert.assertEquals(
			0,
			_segmentsEntryRelLocalService.getSegmentsEntryRelsCount(
				segmentsEntry.getSegmentsEntryId()));
	}

	@Test
	public void testDeleteSegmentsEntryRelsByClassNameIdAndClassPK()
		throws PortalException {

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		int classNameId = RandomTestUtil.randomInt();
		int classPK = RandomTestUtil.randomInt();

		_segmentsEntryRelLocalService.addSegmentsEntryRel(
			segmentsEntry.getSegmentsEntryId(), classNameId, classPK,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsEntryRelLocalService.deleteSegmentsEntryRels(
			classNameId, classPK);

		Assert.assertEquals(
			0,
			_segmentsEntryRelLocalService.getSegmentsEntryRelsCount(
				segmentsEntry.getSegmentsEntryId()));
	}

	@Test
	public void testDeleteSegmentsEntryRelsBySegmentsEntryId()
		throws PortalException {

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		_segmentsEntryRelLocalService.addSegmentsEntryRel(
			segmentsEntry.getSegmentsEntryId(), RandomTestUtil.randomInt(),
			RandomTestUtil.randomInt(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsEntryRelLocalService.deleteSegmentsEntryRels(
			segmentsEntry.getSegmentsEntryId());

		Assert.assertEquals(
			0,
			_segmentsEntryRelLocalService.getSegmentsEntryRelsCount(
				segmentsEntry.getSegmentsEntryId()));
	}

	@Test
	public void testHasSegmentsEntryRel() throws PortalException {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		int classNameId = RandomTestUtil.randomInt();
		int classPK = RandomTestUtil.randomInt();

		_segmentsEntryRelLocalService.addSegmentsEntryRel(
			segmentsEntry.getSegmentsEntryId(), classNameId, classPK,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertTrue(
			_segmentsEntryRelLocalService.hasSegmentsEntryRel(
				segmentsEntry.getSegmentsEntryId(), classNameId, classPK));
	}

	@Test
	public void testHasSegmentsEntryRelWithNoSegmentsEntryRels()
		throws PortalException {

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		Assert.assertFalse(
			_segmentsEntryRelLocalService.hasSegmentsEntryRel(
				segmentsEntry.getSegmentsEntryId(), RandomTestUtil.randomInt(),
				RandomTestUtil.randomInt()));
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Inject
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

}