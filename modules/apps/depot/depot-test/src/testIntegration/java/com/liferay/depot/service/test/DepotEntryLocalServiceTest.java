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
import com.liferay.depot.constants.DepotEntryConstants;
import com.liferay.depot.exception.DepotEntryNameException;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class DepotEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddDepotEntry() throws Exception {
		DepotEntry depotEntry = _addDepotEntry("name", "description");

		Group group = _groupLocalService.getGroup(depotEntry.getGroupId());

		Assert.assertEquals(DepotEntry.class.getName(), group.getClassName());
		Assert.assertEquals(depotEntry.getDepotEntryId(), group.getClassPK());
		Assert.assertEquals(
			"description", group.getDescription(LocaleUtil.getDefault()));
		Assert.assertEquals("name", group.getName(LocaleUtil.getDefault()));
		Assert.assertEquals(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, group.getParentGroupId());
		Assert.assertEquals(
			DepotEntryConstants.GROUP_TYPE_DEPOT, group.getType());
		Assert.assertFalse(group.isSite());

		try {
			_addDepotEntry(null, null);

			Assert.fail();
		}
		catch (DepotEntryNameException dene) {
		}
	}

	@Test(expected = NoSuchGroupException.class)
	public void testDeleteDepotEntry() throws Exception {
		DepotEntry depotEntry = _addDepotEntry("name", "description", false);

		_depotEntryLocalService.deleteDepotEntry(depotEntry);

		_groupLocalService.getGroup(depotEntry.getGroupId());
	}

	@Test
	public void testUpdateDepotEntry() throws Exception {
		DepotEntry depotEntry = _addDepotEntry("name", "description");

		try {
			_depotEntryLocalService.updateDepotEntry(
				depotEntry.getDepotEntryId(), new HashMap<>(), new HashMap<>(),
				ServiceContextTestUtil.getServiceContext());

			Assert.fail();
		}
		catch (DepotEntryNameException dene) {
		}

		_depotEntryLocalService.updateDepotEntry(
			depotEntry.getDepotEntryId(),
			new HashMap<Locale, String>() {
				{
					put(LocaleUtil.getDefault(), "newName");
				}
			},
			new HashMap<Locale, String>() {
				{
					put(LocaleUtil.getDefault(), "newDescription");
				}
			},
			ServiceContextTestUtil.getServiceContext());

		Group group = _groupLocalService.getGroup(depotEntry.getGroupId());

		Assert.assertEquals(
			"newDescription", group.getDescription(LocaleUtil.getDefault()));
		Assert.assertEquals("newName", group.getName(LocaleUtil.getDefault()));
	}

	private DepotEntry _addDepotEntry(String name, String description)
		throws Exception {

		return _addDepotEntry(name, description, true);
	}

	private DepotEntry _addDepotEntry(
			String name, String description, boolean deleteAfterTestRun)
		throws Exception {

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			new HashMap<Locale, String>() {
				{
					put(LocaleUtil.getDefault(), name);
				}
			},
			new HashMap<Locale, String>() {
				{
					put(LocaleUtil.getDefault(), description);
				}
			},
			ServiceContextTestUtil.getServiceContext());

		if (deleteAfterTestRun) {
			_depotEntries.add(depotEntry);
		}

		return depotEntry;
	}

	@DeleteAfterTestRun
	private final List<DepotEntry> _depotEntries = new ArrayList<>();

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

}