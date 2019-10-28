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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.exception.RequiredSegmentsEntryException;
import com.liferay.segments.exception.SegmentsEntryKeyException;
import com.liferay.segments.exception.SegmentsEntryNameException;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryRelLocalService;
import com.liferay.segments.service.SegmentsEntryRoleLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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

		_groups.add(_group);

		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);
	}

	@Test
	public void testAddSegmentsEntry() throws PortalException {
		String segmentsEntryKey = RandomTestUtil.randomString();
		String name = RandomTestUtil.randomString();
		String description = RandomTestUtil.randomString();
		String criteria = CriteriaSerializer.serialize(new Criteria());
		String type = RandomTestUtil.randomString();

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), segmentsEntryKey, name, description, criteria,
			type);

		Assert.assertEquals(
			StringUtil.toUpperCase(segmentsEntryKey.trim()),
			segmentsEntry.getSegmentsEntryKey());
		Assert.assertEquals(
			name, segmentsEntry.getName(LocaleUtil.getDefault()));
		Assert.assertEquals(
			description, segmentsEntry.getDescription(LocaleUtil.getDefault()));
		Assert.assertTrue(segmentsEntry.isActive());
		Assert.assertEquals(criteria, segmentsEntry.getCriteria());
		Assert.assertEquals(type, segmentsEntry.getType());

		Assert.assertEquals(
			1,
			_segmentsEntryLocalService.getSegmentsEntriesCount(
				_group.getGroupId(), false));
	}

	@Test(expected = SegmentsEntryKeyException.class)
	public void testAddSegmentsEntryWithExistingKey() throws PortalException {
		String segmentsEntryKey = RandomTestUtil.randomString();

		SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), segmentsEntryKey);
		SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), segmentsEntryKey);
	}

	@Test(expected = SegmentsEntryKeyException.class)
	public void testAddSegmentsEntryWithExistingKeyInAncestorGroup()
		throws Exception {

		String segmentsEntryKey = RandomTestUtil.randomString();

		SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), segmentsEntryKey);

		Group childGroup = GroupTestUtil.addGroup(_group.getGroupId());

		_groups.add(0, childGroup);

		SegmentsTestUtil.addSegmentsEntry(
			childGroup.getGroupId(), segmentsEntryKey);
	}

	@Test(expected = SegmentsEntryNameException.class)
	public void testAddSegmentsEntryWithoutName() throws Exception {
		SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK,
			CriteriaSerializer.serialize(new Criteria()),
			RandomTestUtil.randomString());
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

	@Test(
		expected = RequiredSegmentsEntryException.MustNotDeleteSegmentsEntryReferencedBySegmentsExperiences.class
	)
	public void testDeleteSegmentsEntryReferencedBySegmentsExperiences()
		throws PortalException {

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		_segmentsExperienceLocalService.addSegmentsExperience(
			segmentsEntry.getSegmentsEntryId(), 0, 0,
			RandomTestUtil.randomLocaleStringMap(), RandomTestUtil.randomInt(),
			false,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsEntryLocalService.deleteSegmentsEntry(
			segmentsEntry.getSegmentsEntryId());
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

		_groups.add(0, childGroup);

		int segmentsEntriesCount =
			_segmentsEntryLocalService.getSegmentsEntriesCount(
				childGroup.getGroupId(), true);

		Assert.assertTrue(segmentsEntriesCount > 0);

		segmentsEntriesCount =
			_segmentsEntryLocalService.getSegmentsEntriesCount(
				childGroup.getGroupId(), false);

		Assert.assertEquals(0, segmentsEntriesCount);
	}

	@Test
	public void testGetSegmentsEntriesWithIncludeAncestorSegmentsEntries()
		throws Exception {

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		Group childGroup = GroupTestUtil.addGroup(_group.getGroupId());

		_groups.add(0, childGroup);

		List<SegmentsEntry> segmentsEntries =
			_segmentsEntryLocalService.getSegmentsEntries(
				childGroup.getGroupId(), true, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertTrue(segmentsEntries.contains(segmentsEntry));

		segmentsEntries = _segmentsEntryLocalService.getSegmentsEntries(
			childGroup.getGroupId(), false, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertFalse(segmentsEntries.contains(segmentsEntry));
	}

	@Test
	public void testSearchSegmentsEntries() throws PortalException {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		BaseModelSearchResult<SegmentsEntry> baseModelSearchResult =
			_segmentsEntryLocalService.searchSegmentsEntries(
				segmentsEntry.getCompanyId(), segmentsEntry.getGroupId(),
				segmentsEntry.getNameCurrentValue(), true, 0, 1, null);

		List<SegmentsEntry> segmentsEntries =
			baseModelSearchResult.getBaseModels();

		Assert.assertEquals(
			segmentsEntries.toString(), 1, segmentsEntries.size());
		Assert.assertEquals(segmentsEntry, segmentsEntries.get(0));
	}

	@Test
	public void testSearchSegmentsEntriesWithRoleIds() throws PortalException {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		_segmentsEntryRoleLocalService.addSegmentsEntryRole(
			_role.getRoleId(), segmentsEntry.getSegmentsEntryId(),
			ServiceContextTestUtil.getServiceContext());

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("roleIds", new long[] {_role.getRoleId()});

		BaseModelSearchResult<SegmentsEntry> baseModelSearchResult =
			_segmentsEntryLocalService.searchSegmentsEntries(
				segmentsEntry.getCompanyId(), segmentsEntry.getGroupId(), null,
				true, params, 0, 1, null);

		List<SegmentsEntry> segmentsEntries =
			baseModelSearchResult.getBaseModels();

		Assert.assertEquals(
			segmentsEntries.toString(), 1, segmentsEntries.size());
		Assert.assertEquals(segmentsEntry, segmentsEntries.get(0));
	}

	@Test
	public void testUpdateSegmentsEntry() throws PortalException {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		String segmentsEntryKey = RandomTestUtil.randomString();

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.getDefault(), RandomTestUtil.randomString()
		).build();

		Map<Locale, String> descriptionMap = HashMapBuilder.put(
			LocaleUtil.getDefault(), RandomTestUtil.randomString()
		).build();

		String criteria = RandomTestUtil.randomString();

		SegmentsEntry updatedSegmentsEntry =
			_segmentsEntryLocalService.updateSegmentsEntry(
				segmentsEntry.getSegmentsEntryId(), segmentsEntryKey, nameMap,
				descriptionMap, false, criteria,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(
			StringUtil.toUpperCase(segmentsEntryKey.trim()),
			updatedSegmentsEntry.getSegmentsEntryKey());
		Assert.assertEquals(
			nameMap.get(LocaleUtil.getDefault()),
			updatedSegmentsEntry.getName(LocaleUtil.getDefault()));
		Assert.assertEquals(
			descriptionMap.get(LocaleUtil.getDefault()),
			updatedSegmentsEntry.getDescription(LocaleUtil.getDefault()));
		Assert.assertFalse(updatedSegmentsEntry.isActive());
		Assert.assertEquals(criteria, updatedSegmentsEntry.getCriteria());

		Assert.assertEquals(
			1,
			_segmentsEntryLocalService.getSegmentsEntriesCount(
				_group.getGroupId(), false));
	}

	@Test(expected = SegmentsEntryKeyException.class)
	public void testUpdateSegmentsEntryWithExistingKey()
		throws PortalException {

		String segmentsEntryKey = RandomTestUtil.randomString();

		SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), segmentsEntryKey);

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), RandomTestUtil.randomString());

		_segmentsEntryLocalService.updateSegmentsEntry(
			segmentsEntry.getSegmentsEntryId(), segmentsEntryKey,
			segmentsEntry.getNameMap(), segmentsEntry.getDescriptionMap(),
			segmentsEntry.isActive(), segmentsEntry.getCriteria(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private Group _group;

	@DeleteAfterTestRun
	private final List<Group> _groups = new ArrayList<>();

	@DeleteAfterTestRun
	private Role _role;

	@Inject
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Inject
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

	@Inject
	private SegmentsEntryRoleLocalService _segmentsEntryRoleLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}