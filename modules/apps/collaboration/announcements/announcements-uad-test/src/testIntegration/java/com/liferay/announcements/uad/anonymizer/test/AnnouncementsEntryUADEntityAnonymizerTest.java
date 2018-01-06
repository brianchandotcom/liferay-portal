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

package com.liferay.announcements.uad.anonymizer.test;

import com.liferay.announcements.kernel.exception.NoSuchEntryException;
import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.uad.constants.AnnouncementsUADConstants;
import com.liferay.announcements.uad.test.BaseAnnouncementsEntryUADEntityTestCase;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.entity.UADEntity;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Noah Sherrill
 */
@RunWith(Arquillian.class)
public class AnnouncementsEntryUADEntityAnonymizerTest
	extends BaseAnnouncementsEntryUADEntityTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_defaultUser = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		_user = UserTestUtil.addUser();
	}

	@Test
	public void testAutoAnonymize() throws Exception {
		AnnouncementsEntry announcementsEntry = addEntry(_user.getUserId());

		long entryId = announcementsEntry.getEntryId();

		UADEntity uadEntity = _uadEntityAggregator.getUADEntity(
			Long.toString(entryId));

		_uadEntityAnonymizer.autoAnonymize(uadEntity);

		_assertAnnouncementsEntryAutoAnonymized(entryId);
	}

	@Test
	public void testAutoAnonymizeAll() throws Exception {
		AnnouncementsEntry announcementsEntry = addEntry(
			TestPropsValues.getUserId());
		AnnouncementsEntry announcementsEntryAutoAnonymize = addEntry(
			_user.getUserId());

		_uadEntityAnonymizer.autoAnonymizeAll(_user.getUserId());

		_assertAnnouncementsEntryAutoAnonymized(
			announcementsEntryAutoAnonymize.getEntryId());

		announcementsEntry = announcementsEntryLocalService.getEntry(
			announcementsEntry.getEntryId());

		Assert.assertEquals(
			TestPropsValues.getUserId(), announcementsEntry.getUserId());
	}

	@Test
	public void testAutoAnonymizeAllNoAnnouncementsEntries() throws Exception {
		_uadEntityAnonymizer.autoAnonymizeAll(_user.getUserId());
	}

	@Test(expected = NoSuchEntryException.class)
	public void testDelete() throws Exception {
		AnnouncementsEntry announcementsEntry = addEntry(_user.getUserId());

		long entryId = announcementsEntry.getEntryId();

		UADEntity uadEntity = _uadEntityAggregator.getUADEntity(
			Long.toString(entryId));

		_uadEntityAnonymizer.delete(uadEntity);

		announcementsEntryLocalService.getEntry(entryId);
	}

	@Test
	public void testDeleteAll() throws Exception {
		AnnouncementsEntry announcementsEntry = addEntry(
			TestPropsValues.getUserId());
		addEntry(_user.getUserId());

		_uadEntityAnonymizer.deleteAll(_user.getUserId());

		announcementsEntry = announcementsEntryLocalService.getEntry(
			announcementsEntry.getEntryId());

		Assert.assertEquals(
			TestPropsValues.getUserId(), announcementsEntry.getUserId());

		List<AnnouncementsEntry> announcementsEntries =
			announcementsEntryLocalService.getUserEntries(
				_user.getUserId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			announcementsEntries.toString(), 0, announcementsEntries.size());
	}

	@Test
	public void testDeleteAllNoAnnouncementsEntries() throws Exception {
		_uadEntityAnonymizer.deleteAll(_user.getUserId());
	}

	private void _assertAnnouncementsEntryAutoAnonymized(long entryId)
		throws Exception {

		AnnouncementsEntry announcementsEntry =
			announcementsEntryLocalService.getEntry(entryId);

		Assert.assertEquals(
			_defaultUser.getUserId(), announcementsEntry.getUserId());
		Assert.assertEquals(
			_defaultUser.getFullName(), announcementsEntry.getUserName());
	}

	private User _defaultUser;

	@Inject(
		filter = "model.class.name=" + AnnouncementsUADConstants.ANNOUNCEMENTS_ENTRY
	)
	private UADEntityAggregator _uadEntityAggregator;

	@Inject(
		filter = "model.class.name=" + AnnouncementsUADConstants.ANNOUNCEMENTS_ENTRY
	)
	private UADEntityAnonymizer _uadEntityAnonymizer;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}