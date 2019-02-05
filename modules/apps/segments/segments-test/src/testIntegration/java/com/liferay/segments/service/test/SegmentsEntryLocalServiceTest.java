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
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.exception.SegmentsEntryKeyException;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryRelLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.HashMap;
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
public class SegmentsEntryLocalServiceTest {

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
	public void testAddSegmentsEntry() throws PortalException {
		String name = RandomTestUtil.randomString();
		String description = RandomTestUtil.randomString();
		String criteria = RandomTestUtil.randomString();
		String key = RandomTestUtil.randomString();
		String type = RandomTestUtil.randomString();

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), name, description, criteria, key, type);

		Assert.assertEquals(
			name, segmentsEntry.getName(LocaleUtil.getDefault()));
		Assert.assertEquals(
			description, segmentsEntry.getDescription(LocaleUtil.getDefault()));
		Assert.assertTrue(segmentsEntry.isActive());
		Assert.assertEquals(criteria, segmentsEntry.getCriteria());
		Assert.assertEquals(
			FriendlyURLNormalizerUtil.normalize(key), segmentsEntry.getKey());
		Assert.assertEquals(type, segmentsEntry.getType());

		Assert.assertEquals(
			1,
			_segmentsEntryLocalService.getSegmentsEntriesCount(
				_group.getGroupId(), false));
	}

	@Test(expected = SegmentsEntryKeyException.class)
	public void testAddSegmentsEntryWithExistingKey() throws PortalException {
		String key = RandomTestUtil.randomString();

		SegmentsTestUtil.addSegmentsEntry(_group.getGroupId(), key);
		SegmentsTestUtil.addSegmentsEntry(_group.getGroupId(), key);
	}

	@Test(expected = SegmentsEntryKeyException.class)
	public void testAddSegmentsEntryWithExistingKeyInAncestorGroup()
		throws Exception {

		String key = RandomTestUtil.randomString();

		SegmentsTestUtil.addSegmentsEntry(_group.getGroupId(), key);

		Group childGroup = GroupTestUtil.addGroup(_group.getGroupId());

		SegmentsTestUtil.addSegmentsEntry(childGroup.getGroupId(), key);
	}

	@Test
	public void testDeleteSegmentsEntry() throws PortalException {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		_segmentsEntryLocalService.deleteSegmentsEntry(
			segmentsEntry.getSegmentsEntryId());

		Assert.assertEquals(
			0,
			_segmentsEntryLocalService.getSegmentsEntriesCount(
				_group.getGroupId(), false));
	}

	@Test
	public void testDeleteSegmentsEntryByGroupId() throws PortalException {
		int count = 5;

		for (int i = 0; i < count; i++) {
			SegmentsTestUtil.addSegmentsEntry(_group.getGroupId());
		}

		Assert.assertEquals(
			count,
			_segmentsEntryLocalService.getSegmentsEntriesCount(
				_group.getGroupId(), false));

		_segmentsEntryLocalService.deleteSegmentsEntries(_group.getGroupId());

		Assert.assertEquals(
			0,
			_segmentsEntryLocalService.getSegmentsEntriesCount(
				_group.getGroupId(), false));
	}

	@Test
	public void testDeleteSegmentsEntryWithSegmentsEntryRels()
		throws PortalException {

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		int count = 5;

		for (int i = 0; i < count; i++) {
			_segmentsEntryRelLocalService.addSegmentsEntryRel(
				segmentsEntry.getSegmentsEntryId(), 0, i, serviceContext);
		}

		List<SegmentsEntryRel> segmentsEntryRels =
			_segmentsEntryRelLocalService.getSegmentsEntryRels(
				segmentsEntry.getSegmentsEntryId());

		Assert.assertEquals(
			segmentsEntryRels.toString(), count, segmentsEntryRels.size());

		_segmentsEntryLocalService.deleteSegmentsEntry(segmentsEntry);

		segmentsEntryRels = _segmentsEntryRelLocalService.getSegmentsEntryRels(
			segmentsEntry.getSegmentsEntryId());

		Assert.assertEquals(
			segmentsEntryRels.toString(), 0, segmentsEntryRels.size());
	}

	@Test
	public void testGetSegmentsEntriesCountWithIncludeAncestorSegmentsEntries()
		throws Exception {

		SegmentsTestUtil.addSegmentsEntry(_group.getGroupId());

		Group childGroup = GroupTestUtil.addGroup(_group.getGroupId());

		int segmentsEntriesCount =
			_segmentsEntryLocalService.getSegmentsEntriesCount(
				childGroup.getGroupId(), true);

		Assert.assertTrue(segmentsEntriesCount > 0);

		segmentsEntriesCount =
			_segmentsEntryLocalService.getSegmentsEntriesCount(
				childGroup.getGroupId(), false);

		Assert.assertEquals(0, segmentsEntriesCount);

		_groupLocalService.deleteGroup(childGroup);
	}

	@Test
	public void testGetSegmentsEntriesWithIncludeAncestorSegmentsEntries()
		throws Exception {

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		Group childGroup = GroupTestUtil.addGroup(_group.getGroupId());

		List<SegmentsEntry> segmentsEntries =
			_segmentsEntryLocalService.getSegmentsEntries(
				childGroup.getGroupId(), true, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertTrue(segmentsEntries.contains(segmentsEntry));

		segmentsEntries = _segmentsEntryLocalService.getSegmentsEntries(
			childGroup.getGroupId(), false, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertFalse(segmentsEntries.contains(segmentsEntry));

		_groupLocalService.deleteGroup(childGroup);
	}

	@Test
	public void testUpdateSegmentsEntry() throws PortalException {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(
			LocaleUtil.getDefault(), RandomTestUtil.randomString());

		String criteria = RandomTestUtil.randomString();
		String key = RandomTestUtil.randomString();

		SegmentsEntry updatedSegmentsEntry =
			_segmentsEntryLocalService.updateSegmentsEntry(
				segmentsEntry.getSegmentsEntryId(), nameMap, descriptionMap,
				false, criteria, key,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(
			nameMap.get(LocaleUtil.getDefault()),
			updatedSegmentsEntry.getName(LocaleUtil.getDefault()));
		Assert.assertEquals(
			descriptionMap.get(LocaleUtil.getDefault()),
			updatedSegmentsEntry.getDescription(LocaleUtil.getDefault()));
		Assert.assertFalse(updatedSegmentsEntry.isActive());
		Assert.assertEquals(criteria, updatedSegmentsEntry.getCriteria());
		Assert.assertEquals(
			FriendlyURLNormalizerUtil.normalize(key),
			updatedSegmentsEntry.getKey());

		Assert.assertEquals(
			1,
			_segmentsEntryLocalService.getSegmentsEntriesCount(
				_group.getGroupId(), false));
	}

	@Test(expected = SegmentsEntryKeyException.class)
	public void testUpdateSegmentsEntryWithExistingKey()
		throws PortalException {

		String key1 = RandomTestUtil.randomString();

		SegmentsTestUtil.addSegmentsEntry(_group.getGroupId(), key1);

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), RandomTestUtil.randomString());

		_segmentsEntryLocalService.updateSegmentsEntry(
			segmentsEntry.getSegmentsEntryId(), segmentsEntry.getNameMap(),
			segmentsEntry.getDescriptionMap(), segmentsEntry.isActive(),
			segmentsEntry.getCriteria(), key1,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Inject
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

}