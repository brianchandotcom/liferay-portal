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
import com.liferay.depot.service.DepotEntryService;
import com.liferay.depot.service.DepotEntryServiceUtil;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author Alicia García
 */
public class DLFolderUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testValidateDepotFolder() throws PortalException {
		GroupLocalService groupLocalService = Mockito.mock(
			GroupLocalService.class);

		long depotGroupId = RandomTestUtil.randomLong();

		Group depotGroup = _getDepotGroup(depotGroupId);

		ReflectionTestUtil.setFieldValue(
			GroupLocalServiceUtil.class, "_service", groupLocalService);

		Mockito.when(
			groupLocalService.getGroup(Matchers.anyLong())
		).thenReturn(
			depotGroup
		);

		DepotEntryService depotEntryService = Mockito.mock(
			DepotEntryService.class);

		List<DepotEntry> depotEntries = _getGroupConnectedDepotEntries(
			depotGroupId);

		ReflectionTestUtil.setFieldValue(
			DepotEntryServiceUtil.class, "_service", depotEntryService);

		Mockito.when(
			depotEntryService.getGroupConnectedDepotEntries(
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
		GroupLocalService groupLocalService = Mockito.mock(
			GroupLocalService.class);

		long depotGroupId = RandomTestUtil.randomLong();

		Group depotGroup = _getDepotGroup(depotGroupId);

		Mockito.when(
			groupLocalService.getGroup(Matchers.anyLong())
		).thenReturn(
			depotGroup
		);

		ReflectionTestUtil.setFieldValue(
			GroupLocalServiceUtil.class, "_service", groupLocalService);

		DepotEntryService depotEntryService = Mockito.mock(
			DepotEntryService.class);

		List<DepotEntry> depotEntries = _getGroupConnectedDepotEntries(
			RandomTestUtil.randomLong());

		Mockito.when(
			depotEntryService.getGroupConnectedDepotEntries(
				Matchers.anyLong(), Matchers.anyInt(), Matchers.anyInt())
		).thenReturn(
			depotEntries
		);

		ReflectionTestUtil.setFieldValue(
			DepotEntryServiceUtil.class, "_service", depotEntryService);

		DLFolderUtil.validateDepotFolder(
			RandomTestUtil.randomLong(), depotGroup.getGroupId(),
			RandomTestUtil.randomLong());
	}

	private DepotEntry _addDepotEntry(long depotGroupId) {
		DepotEntry depotEntry = Mockito.mock(DepotEntry.class);

		Mockito.doReturn(
			depotGroupId
		).when(
			depotEntry
		).getGroupId();

		return depotEntry;
	}

	private Group _getDepotGroup(long groupId) {
		Group group = Mockito.mock(Group.class);

		Mockito.doReturn(
			groupId
		).when(
			group
		).getGroupId();

		Mockito.doReturn(
			true
		).when(
			group
		).isDepot();

		return group;
	}

	private List<DepotEntry> _getGroupConnectedDepotEntries(long depotGroupId) {
		return ListUtil.fromArray(_addDepotEntry(depotGroupId));
	}

}