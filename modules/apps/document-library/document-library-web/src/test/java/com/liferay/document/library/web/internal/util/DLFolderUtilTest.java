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

package com.liferay.document.library.web.internal.util;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryServiceUtil;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Alicia García
 */
@PrepareForTest({GroupLocalServiceUtil.class, DepotEntryServiceUtil.class})
@RunWith(PowerMockRunner.class)
public class DLFolderUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testValidateDepotFolder() throws PortalException {
		PowerMockito.mockStatic(GroupLocalServiceUtil.class);

		long depotGroupId = RandomTestUtil.randomLong();

		Group depotGroup = _getDepotGroup(depotGroupId);

		PowerMockito.when(
			GroupLocalServiceUtil.getGroup(Matchers.anyLong())
		).thenReturn(
			depotGroup
		);

		PowerMockito.mockStatic(DepotEntryServiceUtil.class);

		List<DepotEntry> depotEntries = _getGroupConnectedDepotEntries(
			depotGroupId);

		PowerMockito.when(
			DepotEntryServiceUtil.getGroupConnectedDepotEntries(
				Matchers.anyLong(), Matchers.anyInt(), Matchers.anyInt())
		).thenReturn(
			depotEntries
		);

		DLFolderUtil.validateDepotFolder(
			RandomTestUtil.randomLong(), depotGroup.getGroupId(),
			RandomTestUtil.randomLong());
	}

	@Test(expected = NoSuchFolderException.class)
	public void testValidateDepotFolderNotConnected() throws PortalException {
		PowerMockito.mockStatic(GroupLocalServiceUtil.class);

		long depotGroupId = RandomTestUtil.randomLong();

		Group depotGroup = _getDepotGroup(depotGroupId);

		PowerMockito.when(
			GroupLocalServiceUtil.getGroup(Matchers.anyLong())
		).thenReturn(
			depotGroup
		);

		PowerMockito.mockStatic(DepotEntryServiceUtil.class);

		List<DepotEntry> depotEntries = _getGroupConnectedDepotEntries(
			RandomTestUtil.randomLong());

		PowerMockito.when(
			DepotEntryServiceUtil.getGroupConnectedDepotEntries(
				Matchers.anyLong(), Matchers.anyInt(), Matchers.anyInt())
		).thenReturn(
			depotEntries
		);

		DLFolderUtil.validateDepotFolder(
			RandomTestUtil.randomLong(), depotGroup.getGroupId(),
			RandomTestUtil.randomLong());
	}

	private DepotEntry _addDepotEntry(long depotGroupId) {
		DepotEntry depotEntry = PowerMockito.mock(DepotEntry.class);

		PowerMockito.doReturn(
			depotGroupId
		).when(
			depotEntry
		).getGroupId();

		return depotEntry;
	}

	private Group _getDepotGroup(long groupId) {
		Group group = PowerMockito.mock(Group.class);

		PowerMockito.doReturn(
			true
		).when(
			group
		).isDepot();

		PowerMockito.doReturn(
			groupId
		).when(
			group
		).getGroupId();

		return group;
	}

	private List<DepotEntry> _getGroupConnectedDepotEntries(long depotGroupId) {
		return ListUtil.fromArray(_addDepotEntry(depotGroupId));
	}

}