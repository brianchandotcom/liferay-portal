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

		SegmentsEntry segmentsEntry = _addSegmentsEntry(
			name, description, criteria, key, type);

		Assert.assertEquals(
			1,
			_segmentsEntryLocalService.getSegmentsEntriesCount(
				_group.getGroupId()));
		Assert.assertTrue(segmentsEntry.isActive());
		Assert.assertEquals(
			name, segmentsEntry.getName(LocaleUtil.getDefault()));
		Assert.assertEquals(
			description, segmentsEntry.getDescription(LocaleUtil.getDefault()));
		Assert.assertEquals(criteria, segmentsEntry.getCriteria());
		Assert.assertEquals(
			FriendlyURLNormalizerUtil.normalize(key), segmentsEntry.getKey());
		Assert.assertEquals(type, segmentsEntry.getType());
	}

	@Test(expected = SegmentsEntryKeyException.class)
	public void testAddSegmentsEntryWithExistingKey() throws PortalException {
		String key = RandomTestUtil.randomString();

		_addSegmentsEntry(key);
		_addSegmentsEntry(key);
	}

	@Test
	public void testDeleteSegmentsEntry() throws PortalException {
		SegmentsEntry segmentsEntry = _addSegmentsEntry();

		_segmentsEntryLocalService.deleteSegmentsEntry(
			segmentsEntry.getSegmentsEntryId());

		Assert.assertEquals(
			0,
			_segmentsEntryLocalService.getSegmentsEntriesCount(
				_group.getGroupId()));
	}

	@Test
	public void testDeleteSegmentsEntryByGroupId() throws PortalException {
		int count = 5;

		for (int i = 0; i < count; i++) {
			_addSegmentsEntry();
		}

		Assert.assertEquals(
			count,
			_segmentsEntryLocalService.getSegmentsEntriesCount(
				_group.getGroupId()));

		_segmentsEntryLocalService.deleteSegmentsEntries(_group.getGroupId());

		Assert.assertEquals(
			0,
			_segmentsEntryLocalService.getSegmentsEntriesCount(
				_group.getGroupId()));
	}

	@Test
	public void testDeleteSegmentsEntryWithRels() throws PortalException {
		SegmentsEntry segmentsEntry = _addSegmentsEntry();

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
	public void testUpdateSegmentsEntry() throws PortalException {
		SegmentsEntry segmentsEntry = _addSegmentsEntry();

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
			1,
			_segmentsEntryLocalService.getSegmentsEntriesCount(
				_group.getGroupId()));
		Assert.assertFalse(updatedSegmentsEntry.isActive());
		Assert.assertEquals(
			nameMap.get(LocaleUtil.getDefault()),
			updatedSegmentsEntry.getName(LocaleUtil.getDefault()));
		Assert.assertEquals(
			descriptionMap.get(LocaleUtil.getDefault()),
			updatedSegmentsEntry.getDescription(LocaleUtil.getDefault()));
		Assert.assertEquals(criteria, updatedSegmentsEntry.getCriteria());
		Assert.assertEquals(
			FriendlyURLNormalizerUtil.normalize(key),
			updatedSegmentsEntry.getKey());
	}

	@Test(expected = SegmentsEntryKeyException.class)
	public void testUpdateSegmentsEntryWithExistingKey()
		throws PortalException {

		String key1 = RandomTestUtil.randomString();

		_addSegmentsEntry(key1);

		SegmentsEntry segmentsEntry = _addSegmentsEntry(
			RandomTestUtil.randomString());

		_segmentsEntryLocalService.updateSegmentsEntry(
			segmentsEntry.getSegmentsEntryId(), segmentsEntry.getNameMap(),
			segmentsEntry.getDescriptionMap(), segmentsEntry.isActive(),
			segmentsEntry.getCriteria(), key1,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private SegmentsEntry _addSegmentsEntry() throws PortalException {
		return _addSegmentsEntry(RandomTestUtil.randomString());
	}

	private SegmentsEntry _addSegmentsEntry(String key) throws PortalException {
		return _addSegmentsEntry(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), key, RandomTestUtil.randomString());
	}

	private SegmentsEntry _addSegmentsEntry(
			final String name, final String description, String criteria,
			String key, String type)
		throws PortalException {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getDefault(), name);

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.getDefault(), description);

		return _segmentsEntryLocalService.addSegmentsEntry(
			nameMap, descriptionMap, true, criteria, key, type,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Inject
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

}