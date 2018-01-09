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

package com.liferay.announcements.uad.entity;

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author Noah Sherrill
 */
public class AnnouncementsEntryUADEntityTest {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		_announcementsEntryUADEntity = new AnnouncementsEntryUADEntity(
			_userId, _uadEntityId, _announcementsEntry);
	}

	@Test
	public void testGetAnnouncementsEntry() throws Exception {
		Assert.assertEquals(
			_announcementsEntry,
			_announcementsEntryUADEntity.getAnnouncementsEntry());
	}

	@Test
	public void testGetUADEntityId() throws Exception {
		Assert.assertEquals(
			_uadEntityId, _announcementsEntryUADEntity.getUADEntityId());
	}

	@Test
	public void testGetUADRegistryKey() throws Exception {
		Assert.assertEquals(
			AnnouncementsEntry.class.getName(),
			_announcementsEntryUADEntity.getUADRegistryKey());
	}

	@Test
	public void testGetUserId() throws Exception {
		Assert.assertEquals(_userId, _announcementsEntryUADEntity.getUserId());
	}

	private static final String _uadEntityId = RandomTestUtil.randomString();
	private static final long _userId = RandomTestUtil.randomLong();

	@Mock
	private AnnouncementsEntry _announcementsEntry;

	private AnnouncementsEntryUADEntity _announcementsEntryUADEntity;

}