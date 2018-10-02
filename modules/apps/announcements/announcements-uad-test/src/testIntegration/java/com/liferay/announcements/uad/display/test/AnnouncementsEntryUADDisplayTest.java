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

package com.liferay.announcements.uad.display.test;

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.kernel.service.AnnouncementsEntryLocalService;
import com.liferay.announcements.uad.test.AnnouncementsEntryUADTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.test.util.BaseUADDisplayTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Noah Sherrill
 */
@RunWith(Arquillian.class)
public class AnnouncementsEntryUADDisplayTest
	extends BaseUADDisplayTestCase<AnnouncementsEntry> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected AnnouncementsEntry addBaseModel(long userId) throws Exception {
		AnnouncementsEntry announcementsEntry =
			AnnouncementsEntryUADTestUtil.addAnnouncementsEntry(
				_announcementsEntryLocalService, _classNameLocalService,
				userId);

		_announcementsEntries.add(announcementsEntry);

		return announcementsEntry;
	}

	@Override
	protected UADDisplay<AnnouncementsEntry> getUADDisplay() {
		return _uadDisplay;
	}

	@DeleteAfterTestRun
	private final List<AnnouncementsEntry> _announcementsEntries =
		new ArrayList<>();

	@Inject
	private AnnouncementsEntryLocalService _announcementsEntryLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject(filter = "component.name=*.AnnouncementsEntryUADDisplay")
	private UADDisplay<AnnouncementsEntry> _uadDisplay;

}