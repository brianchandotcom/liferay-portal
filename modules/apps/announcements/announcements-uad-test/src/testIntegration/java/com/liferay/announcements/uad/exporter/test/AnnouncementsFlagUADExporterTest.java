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

package com.liferay.announcements.uad.exporter.test;

import com.liferay.announcements.kernel.model.AnnouncementsFlag;
import com.liferay.announcements.uad.test.AnnouncementsFlagUADTestHelper;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
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
			_announcementsFlagUADTestHelper.addAnnouncementsFlag(userId);

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

	@DeleteAfterTestRun
	private final List<AnnouncementsFlag> _announcementsFlags =
		new ArrayList<>();

	@Inject
	private AnnouncementsFlagUADTestHelper _announcementsFlagUADTestHelper;

	@Inject(filter = "component.name=*.AnnouncementsFlagUADExporter")
	private UADExporter _uadExporter;

}