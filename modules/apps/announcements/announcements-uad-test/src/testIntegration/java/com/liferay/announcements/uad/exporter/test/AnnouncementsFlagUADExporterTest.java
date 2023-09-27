/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.uad.exporter.test;

import com.liferay.announcements.kernel.model.AnnouncementsFlag;
import com.liferay.announcements.kernel.model.AnnouncementsFlagConstants;
import com.liferay.announcements.kernel.service.AnnouncementsFlagLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.test.util.BaseUADExporterTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Noah Sherrill
 */
@RunWith(Arquillian.class)
public class AnnouncementsFlagUADExporterTest
	extends BaseUADExporterTestCase<AnnouncementsFlag> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected AnnouncementsFlag addBaseModel(long userId) throws Exception {
		AnnouncementsFlag announcementsFlag =
			_announcementsFlagLocalService.addFlag(
				userId, RandomTestUtil.randomLong(),
				AnnouncementsFlagConstants.UNREAD);

		_announcementsFlags.add(announcementsFlag);

		return announcementsFlag;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "flagId";
	}

	@Override
	protected UADExporter<AnnouncementsFlag> getUADExporter() {
		return _uadExporter;
	}

	@Inject
	private AnnouncementsFlagLocalService _announcementsFlagLocalService;

	@DeleteAfterTestRun
	private final List<AnnouncementsFlag> _announcementsFlags =
		new ArrayList<>();

	@Inject(
		filter = "component.name=com.liferay.announcements.uad.exporter.AnnouncementsFlagUADExporter"
	)
	private UADExporter<AnnouncementsFlag> _uadExporter;

}