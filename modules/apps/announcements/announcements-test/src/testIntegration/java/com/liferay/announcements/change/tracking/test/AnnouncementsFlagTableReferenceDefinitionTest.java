/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.change.tracking.test;

import com.liferay.announcements.kernel.exception.EntryDisplayDateException;
import com.liferay.announcements.kernel.exception.EntryExpirationDateException;
import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.kernel.model.AnnouncementsFlagConstants;
import com.liferay.announcements.kernel.service.AnnouncementsEntryLocalService;
import com.liferay.announcements.kernel.service.AnnouncementsFlagLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseTableReferenceDefinitionTestCase;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author David Truong
 */
@RunWith(Arquillian.class)
public class AnnouncementsFlagTableReferenceDefinitionTest
	extends BaseTableReferenceDefinitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		User user = TestPropsValues.getUser();

		_announcementsEntry = _announcementsEntryLocalService.addEntry(
			TestPropsValues.getUserId(), group.getClassNameId(),
			group.getGroupId(), StringUtil.randomString(),
			StringUtil.randomString(), "http://localhost", "general",
			_portal.getDate(
				1, 1, 1990, 1, 1, user.getTimeZone(),
				EntryDisplayDateException.class),
			_portal.getDate(
				1, 1, 3000, 1, 1, user.getTimeZone(),
				EntryExpirationDateException.class),
			1, false);
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return _announcementsFlagLocalService.addFlag(
			TestPropsValues.getUserId(), _announcementsEntry.getEntryId(),
			AnnouncementsFlagConstants.HIDDEN);
	}

	@DeleteAfterTestRun
	private AnnouncementsEntry _announcementsEntry;

	@Inject
	private AnnouncementsEntryLocalService _announcementsEntryLocalService;

	@Inject
	private AnnouncementsFlagLocalService _announcementsFlagLocalService;

	@Inject
	private Portal _portal;

}