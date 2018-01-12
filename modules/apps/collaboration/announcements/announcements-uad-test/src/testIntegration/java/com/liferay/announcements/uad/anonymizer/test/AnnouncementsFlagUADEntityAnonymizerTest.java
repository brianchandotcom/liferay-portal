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

import com.liferay.announcements.kernel.exception.NoSuchFlagException;
import com.liferay.announcements.kernel.model.AnnouncementsFlag;
import com.liferay.announcements.uad.constants.AnnouncementsUADConstants;
import com.liferay.announcements.uad.test.BaseAnnouncementsFlagUADEntityTestCase;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
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
public class AnnouncementsFlagUADEntityAnonymizerTest
	extends BaseAnnouncementsFlagUADEntityTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_defaultUser = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		_user = UserTestUtil.addUser();
	}

	@Test
	public void testAutoAnonymize() throws Exception {
		AnnouncementsFlag announcementsFlag = addAnnouncementsFlag(
			_user.getUserId());

		long flagId = announcementsFlag.getFlagId();

		UADEntity uadEntity = _uadEntityAggregator.getUADEntity(
			Long.toString(flagId));

		_uadEntityAnonymizer.autoAnonymize(uadEntity);

		_assertAnnouncementsFlagAutoAnonymized(flagId);
	}

	@Test
	public void testAutoAnonymizeAll() throws Exception {
		AnnouncementsFlag announcementsFlag = addAnnouncementsFlag(
			TestPropsValues.getUserId());
		AnnouncementsFlag announcementsFlagAutoAnonymize = addAnnouncementsFlag(
			_user.getUserId());

		_uadEntityAnonymizer.autoAnonymizeAll(_user.getUserId());

		_assertAnnouncementsFlagAutoAnonymized(
			announcementsFlagAutoAnonymize.getFlagId());

		announcementsFlag = announcementsFlagLocalService.getAnnouncementsFlag(
			announcementsFlag.getFlagId());

		Assert.assertEquals(
			TestPropsValues.getUserId(), announcementsFlag.getUserId());
	}

	@Test
	public void testAutoAnonymizeAllNoAnnouncementsFlags() throws Exception {
		_uadEntityAnonymizer.autoAnonymizeAll(_user.getUserId());
	}

	@Test(expected = NoSuchFlagException.class)
	public void testDelete() throws Exception {
		AnnouncementsFlag announcementsFlag = addAnnouncementsFlag(
			_user.getUserId());

		long flagId = announcementsFlag.getFlagId();

		UADEntity uadEntity = _uadEntityAggregator.getUADEntity(
			Long.toString(flagId));

		_uadEntityAnonymizer.delete(uadEntity);

		announcementsFlagLocalService.getAnnouncementsFlag(flagId);
	}

	@Test
	public void testDeleteAll() throws Exception {
		AnnouncementsFlag announcementsFlag = addAnnouncementsFlag(
			TestPropsValues.getUserId());
		AnnouncementsFlag announcementsFlagDelete = addAnnouncementsFlag(
			_user.getUserId());

		_uadEntityAnonymizer.deleteAll(_user.getUserId());

		announcementsFlag = announcementsFlagLocalService.getAnnouncementsFlag(
			announcementsFlag.getFlagId());

		Assert.assertEquals(
			TestPropsValues.getUserId(), announcementsFlag.getUserId());

		announcementsFlagDelete =
			announcementsFlagLocalService.fetchAnnouncementsFlag(
				announcementsFlagDelete.getFlagId());

		Assert.assertNull(announcementsFlagDelete);
	}

	@Test
	public void testDeleteAllNoAnnouncementsFlags() throws Exception {
		_uadEntityAnonymizer.deleteAll(_user.getUserId());
	}

	private void _assertAnnouncementsFlagAutoAnonymized(long flagId)
		throws Exception {

		AnnouncementsFlag announcementsFlag =
			announcementsFlagLocalService.getAnnouncementsFlag(flagId);

		Assert.assertEquals(
			_defaultUser.getUserId(), announcementsFlag.getUserId());
	}

	private User _defaultUser;

	@Inject(
		filter = "model.class.name=" + AnnouncementsUADConstants.ANNOUNCEMENTS_FLAG
	)
	private UADEntityAggregator _uadEntityAggregator;

	@Inject(
		filter = "model.class.name=" + AnnouncementsUADConstants.ANNOUNCEMENTS_FLAG
	)
	private UADEntityAnonymizer _uadEntityAnonymizer;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}