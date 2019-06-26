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

import com.liferay.announcements.kernel.model.AnnouncementsFlag;
import com.liferay.announcements.kernel.model.AnnouncementsFlagConstants;
import com.liferay.announcements.kernel.service.AnnouncementsFlagLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseUADAnonymizerTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Noah Sherrill
 */
@RunWith(Arquillian.class)
public class AnnouncementsFlagUADAnonymizerTest
	extends BaseUADAnonymizerTestCase<AnnouncementsFlag> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected AnnouncementsFlag addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected AnnouncementsFlag addBaseModel(
		long userId, boolean deleteAfterTestRun) {

		AnnouncementsFlag announcementsFlag =
			_announcementsFlagLocalService.addFlag(
				userId, RandomTestUtil.randomLong(),
				AnnouncementsFlagConstants.UNREAD);

		if (deleteAfterTestRun) {
			_announcementsFlags.add(announcementsFlag);
		}

		return announcementsFlag;
	}

	@Override
	protected UADAnonymizer getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		AnnouncementsFlag announcementsFlag =
			_announcementsFlagLocalService.getAnnouncementsFlag(baseModelPK);

		if (user.getUserId() != announcementsFlag.getUserId()) {
			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		AnnouncementsFlag announcementsFlag =
			_announcementsFlagLocalService.fetchAnnouncementsFlag(baseModelPK);

		if (announcementsFlag == null) {
			return true;
		}

		return false;
	}

	@Inject
	private AnnouncementsFlagLocalService _announcementsFlagLocalService;

	@DeleteAfterTestRun
	private final List<AnnouncementsFlag> _announcementsFlags =
		new ArrayList<>();

	@Inject(filter = "component.name=*.AnnouncementsFlagUADAnonymizer")
	private UADAnonymizer _uadAnonymizer;

}