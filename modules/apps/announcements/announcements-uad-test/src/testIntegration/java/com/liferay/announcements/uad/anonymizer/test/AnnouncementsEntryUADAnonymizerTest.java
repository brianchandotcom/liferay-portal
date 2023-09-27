/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.uad.anonymizer.test;

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.kernel.service.AnnouncementsEntryLocalService;
import com.liferay.announcements.uad.test.AnnouncementsEntryUADTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.util.StringUtil;
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
public class AnnouncementsEntryUADAnonymizerTest
	extends BaseUADAnonymizerTestCase<AnnouncementsEntry> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected AnnouncementsEntry addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected AnnouncementsEntry addBaseModel(
			long userId, boolean deleteAfterTestRun)
		throws Exception {

		AnnouncementsEntry announcementsEntry =
			AnnouncementsEntryUADTestUtil.addAnnouncementsEntry(
				_announcementsEntryLocalService, _classNameLocalService,
				userId);

		if (deleteAfterTestRun) {
			_announcementsEntries.add(announcementsEntry);
		}

		return announcementsEntry;
	}

	@Override
	protected UADAnonymizer<AnnouncementsEntry> getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		AnnouncementsEntry announcementsEntry =
			_announcementsEntryLocalService.getEntry(baseModelPK);

		if ((user.getUserId() != announcementsEntry.getUserId()) &&
			!StringUtil.equals(
				user.getFullName(), announcementsEntry.getUserName())) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		AnnouncementsEntry announcementsEntry =
			_announcementsEntryLocalService.fetchAnnouncementsEntry(
				baseModelPK);

		if (announcementsEntry == null) {
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
	private ClassNameLocalService _classNameLocalService;

	@Inject(
		filter = "component.name=com.liferay.announcements.uad.anonymizer.AnnouncementsEntryUADAnonymizer"
	)
	private UADAnonymizer<AnnouncementsEntry> _uadAnonymizer;

}