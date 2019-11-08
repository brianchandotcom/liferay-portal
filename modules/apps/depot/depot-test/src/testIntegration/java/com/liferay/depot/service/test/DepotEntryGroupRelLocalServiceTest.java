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

package com.liferay.depot.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.exception.NoSuchEntryGroupRelException;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class DepotEntryGroupRelLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddDepotEntryGroupRel() throws Exception {
		DepotEntry depotEntry = _addDepotEntry();

		DepotEntryGroupRel depotEntryGroupRel =
			_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				depotEntry.getDepotEntryId(), _group.getGroupId());

		Assert.assertNotNull(depotEntryGroupRel.getDepotEntryId());
		Assert.assertEquals(
			depotEntry.getDepotEntryId(), depotEntryGroupRel.getDepotEntryId());
		Assert.assertEquals(
			_group.getGroupId(), depotEntryGroupRel.getToGroupId());
	}

	@Test
	public void testAddDepotEntryGroupRelWithARepeatedDepotEntryGroupRel()
		throws Exception {

		DepotEntry depotEntry = _addDepotEntry();

		DepotEntryGroupRel originalDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				depotEntry.getDepotEntryId(), _group.getGroupId());

		DepotEntryGroupRel finalDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				depotEntry.getDepotEntryId(), _group.getGroupId());

		Assert.assertEquals(
			originalDepotEntryGroupRel.getDepotEntryGroupRelId(),
			finalDepotEntryGroupRel.getDepotEntryGroupRelId());
		Assert.assertEquals(
			originalDepotEntryGroupRel.getDepotEntryId(),
			finalDepotEntryGroupRel.getDepotEntryId());
		Assert.assertEquals(
			originalDepotEntryGroupRel.getToGroupId(),
			finalDepotEntryGroupRel.getToGroupId());
	}

	@Test
	public void testDeleteDepotEntryGroupRel() throws Exception {
		DepotEntry depotEntry = _addDepotEntry();

		DepotEntryGroupRel depotEntryGroupRel =
			_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				depotEntry.getDepotEntryId(), _group.getGroupId());

		_depotEntryGroupRelLocalService.deleteDepotEntryGroupRel(
			depotEntryGroupRel.getDepotEntryGroupRelId());

		_depotEntries.remove(depotEntry);

		try {
			_depotEntryGroupRelLocalService.getDepotEntryGroupRel(
				depotEntryGroupRel.getDepotEntryGroupRelId());

			Assert.fail();
		}
		catch (NoSuchEntryGroupRelException nsegre) {
		}
	}

	@Test
	public void testDeleteGroup() throws Exception {
		DepotEntry depotEntry = _addDepotEntry();

		Group group = _groupLocalService.addGroup(
			TestPropsValues.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			null, 0, 0,
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			GroupConstants.TYPE_SITE_OPEN, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, true, true,
			ServiceContextTestUtil.getServiceContext());

		int depotEntryGroupRelsCount =
			_depotEntryGroupRelLocalService.getDepotEntryGroupRelsCount();

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntry.getDepotEntryId(), group.getGroupId());

		Assert.assertEquals(
			depotEntryGroupRelsCount + 1,
			_depotEntryGroupRelLocalService.getDepotEntryGroupRelsCount());

		_groupLocalService.deleteGroup(group.getGroupId());

		Assert.assertEquals(
			depotEntryGroupRelsCount,
			_depotEntryGroupRelLocalService.getDepotEntryGroupRelsCount());
	}

	@Test
	public void testGetDepotEntryGroupRel() throws Exception {
		DepotEntry depotEntry = _addDepotEntry();

		DepotEntryGroupRel originalDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				depotEntry.getDepotEntryId(), _group.getGroupId());

		DepotEntryGroupRel finalDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.getDepotEntryGroupRel(
				originalDepotEntryGroupRel.getDepotEntryGroupRelId());

		Assert.assertEquals(
			originalDepotEntryGroupRel.getDepotEntryGroupRelId(),
			finalDepotEntryGroupRel.getDepotEntryGroupRelId());
		Assert.assertEquals(
			originalDepotEntryGroupRel.getDepotEntryId(),
			finalDepotEntryGroupRel.getDepotEntryId());
		Assert.assertEquals(
			originalDepotEntryGroupRel.getToGroupId(),
			finalDepotEntryGroupRel.getToGroupId());
	}

	@Test
	public void testGetDepotEntryGroupRels() throws Exception {
		DepotEntry depotEntry = _addDepotEntry();

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntry.getDepotEntryId(), _group.getGroupId());

		List<DepotEntryGroupRel> depotEntryGroupRels =
			_depotEntryGroupRelLocalService.getDepotEntryGroupRels(
				_group.getGroupId(), 0, 20);

		Assert.assertEquals(
			depotEntryGroupRels.toString(), depotEntryGroupRels.size(), 1);

		DepotEntryGroupRel depotEntryGroupRel = depotEntryGroupRels.get(0);

		Assert.assertEquals(
			depotEntry.getDepotEntryId(), depotEntryGroupRel.getDepotEntryId());
		Assert.assertEquals(
			_group.getGroupId(), depotEntryGroupRel.getToGroupId());
	}

	private DepotEntry _addDepotEntry() throws Exception {
		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext());

		_depotEntries.add(depotEntry);

		return depotEntry;
	}

	@DeleteAfterTestRun
	private final List<DepotEntry> _depotEntries = new ArrayList<>();

	@Inject
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

}