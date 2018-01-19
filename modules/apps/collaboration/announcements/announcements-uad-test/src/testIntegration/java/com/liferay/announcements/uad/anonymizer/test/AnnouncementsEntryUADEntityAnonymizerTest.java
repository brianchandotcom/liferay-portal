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

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.kernel.service.AnnouncementsEntryLocalService;
import com.liferay.announcements.uad.constants.AnnouncementsUADConstants;
import com.liferay.announcements.uad.test.AnnouncementsEntryUADEntityTestHelper;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.test.util.BaseUADEntityAnonymizerTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Noah Sherrill
 */
@RunWith(Arquillian.class)
public class AnnouncementsEntryUADEntityAnonymizerTest
	extends BaseUADEntityAnonymizerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected Object addDataObject(long userId) throws Exception {
		AnnouncementsEntry announcementsEntry =
			_announcementsEntryUADEntityTestHelper.addAnnouncementsEntry(
				userId);

		_announcementsEntries.add(announcementsEntry);

		return announcementsEntry;
	}

	@Override
	protected long getDataObjectId(Object dataObject) {
		AnnouncementsEntry announcementsEntry = (AnnouncementsEntry)dataObject;

		return announcementsEntry.getEntryId();
	}

	@Override
	protected String getUADRegistryKey() {
		return AnnouncementsUADConstants.ANNOUNCEMENTS_ENTRY;
	}

	@Override
	protected boolean isDataObjectAutoAnonymized(long dataObjectId, User user)
		throws Exception {

		AnnouncementsEntry announcementsEntry =
			_announcementsEntryLocalService.getEntry(dataObjectId);

		if ((user.getUserId() != announcementsEntry.getUserId()) &&
			!StringUtil.equals(
				user.getFullName(), announcementsEntry.getUserName())) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isDataObjectDeleted(long dataObjectId) {
		if (_announcementsEntryLocalService.fetchAnnouncementsEntry(
				dataObjectId) == null) {

			return true;
		}

		return false;
	}

	@DeleteAfterTestRun
	private final List<AnnouncementsEntry> _announcementsEntries =
		new ArrayList<>();

	@Inject
	private AnnouncementsEntryLocalService _announcementsEntryLocalService;

	@Inject
	private AnnouncementsEntryUADEntityTestHelper
		_announcementsEntryUADEntityTestHelper;

}