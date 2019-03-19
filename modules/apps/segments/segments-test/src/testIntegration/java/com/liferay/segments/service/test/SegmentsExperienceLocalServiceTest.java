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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
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
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.segments.exception.DefaultSegmentsExperienceException;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

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
public class SegmentsExperienceLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_classNameId = _classNameLocalService.getClassNameId(
			Layout.class.getName());

		Layout layout = LayoutTestUtil.addLayout(_group);

		layout.setType(LayoutConstants.TYPE_CONTENT);

		layout = _layoutLocalService.updateLayout(layout);

		_classPK = layout.getPlid();

		_segmentsExperienceLocalService.deleteSegmentsExperiences(
			_group.getGroupId(), _classNameId, _classPK);
	}

	@Test
	public void testAddDefaultSegmentsExperience() throws PortalException {
		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.addDefaultSegmentsExperience(
				_group.getGroupId(), _classNameId, _classPK);

		Map<Locale, String> nameMap = ResourceBundleUtil.getLocalizationMap(
			_resourceBundleLoader, "default-experience-name");

		Assert.assertEquals(nameMap, segmentsExperience.getNameMap());

		SegmentsEntry defaultSegmentsEntry =
			_segmentsEntryLocalService.getDefaultSegmentsEntry(
				_group.getGroupId());

		Assert.assertEquals(
			defaultSegmentsEntry.getSegmentsEntryId(),
			segmentsExperience.getSegmentsEntryId());
	}

	@Test(expected = DefaultSegmentsExperienceException.class)
	public void testAddDefaultSegmentsExperienceWithExistingDefaultExperience()
		throws PortalException {

		_segmentsExperienceLocalService.addDefaultSegmentsExperience(
			_group.getGroupId(), _classNameId, _classPK);

		SegmentsEntry defaultSegmentsEntry =
			_segmentsEntryLocalService.getDefaultSegmentsEntry(
				_group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_segmentsExperienceLocalService.addSegmentsExperience(
			defaultSegmentsEntry.getSegmentsEntryId(), _classNameId, _classPK,
			RandomTestUtil.randomLocaleStringMap(), RandomTestUtil.randomInt(),
			RandomTestUtil.randomBoolean(), serviceContext);
	}

	@Test
	public void testAddSegmentsExperience() throws Exception {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());
		Map<Locale, String> nameMap = RandomTestUtil.randomLocaleStringMap();
		int priority = RandomTestUtil.randomInt();
		boolean active = RandomTestUtil.randomBoolean();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.addSegmentsExperience(
				segmentsEntry.getSegmentsEntryId(), _classNameId, _classPK,
				nameMap, priority, active, serviceContext);

		Assert.assertEquals(
			1,
			_segmentsExperienceLocalService.getSegmentsExperiencesCount(
				_group.getGroupId(), _classNameId, _classPK, active));

		Assert.assertEquals(
			segmentsEntry.getSegmentsEntryId(),
			segmentsExperience.getSegmentsEntryId());
		Assert.assertEquals(_classNameId, segmentsExperience.getClassNameId());
		Assert.assertEquals(_classPK, segmentsExperience.getClassPK());
		Assert.assertEquals(nameMap, segmentsExperience.getNameMap());
		Assert.assertEquals(active, segmentsExperience.isActive());
	}

	@Test
	public void testAddSegmentsExperiencesWithTheSameSegmentsEntry()
		throws Exception {

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		for (int i = 0; i < 2; i++) {
			_addSegmentsExperience(segmentsEntry);
		}

		List<SegmentsExperience> segmentsExperiences =
			_segmentsExperienceLocalService.getSegmentsExperiences(
				_group.getGroupId(), _classNameId, _classPK, true,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			segmentsExperiences.toString(), 2, segmentsExperiences.size());
	}

	@Test
	public void testDeleteSegmentsExperience() throws Exception {
		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		_segmentsExperienceLocalService.deleteSegmentsExperience(
			segmentsExperience.getSegmentsExperienceId());

		Assert.assertNull(
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId()));
	}

	@Test
	public void testUpdateSegmentsExperience() throws Exception {
		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());
		Map<Locale, String> nameMap = RandomTestUtil.randomLocaleStringMap();
		boolean active = RandomTestUtil.randomBoolean();

		SegmentsExperience updatedSegmentsExperience =
			_segmentsExperienceLocalService.updateSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsEntry.getSegmentsEntryId(), nameMap, active);

		Assert.assertEquals(
			segmentsEntry.getSegmentsEntryId(),
			updatedSegmentsExperience.getSegmentsEntryId());
		Assert.assertEquals(nameMap, updatedSegmentsExperience.getNameMap());
		Assert.assertEquals(active, updatedSegmentsExperience.isActive());
	}

	private SegmentsExperience _addSegmentsExperience() throws Exception {
		return _addSegmentsExperience(
			SegmentsTestUtil.addSegmentsEntry(_group.getGroupId()));
	}

	private SegmentsExperience _addSegmentsExperience(
			SegmentsEntry segmentsEntry)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		return _segmentsExperienceLocalService.addSegmentsExperience(
			segmentsEntry.getSegmentsEntryId(), _classNameId, _classPK,
			RandomTestUtil.randomLocaleStringMap(), RandomTestUtil.randomInt(),
			true, serviceContext);
	}

	private long _classNameId;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private long _classPK;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject(filter = "bundle.symbolic.name=com.liferay.segments.lang")
	private ResourceBundleLoader _resourceBundleLoader;

	@Inject
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}