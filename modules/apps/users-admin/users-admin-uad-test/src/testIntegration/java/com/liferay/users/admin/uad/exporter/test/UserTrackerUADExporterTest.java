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

package com.liferay.users.admin.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.UserTracker;
import com.liferay.portal.kernel.service.UserTrackerLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.test.util.BaseUADExporterTestCase;
import com.liferay.users.admin.uad.test.UserTrackerUADTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class UserTrackerUADExporterTest
	extends BaseUADExporterTestCase<UserTracker> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected UserTracker addBaseModel(long userId) throws Exception {
		UserTracker userTracker = UserTrackerUADTestUtil.addUserTracker(
			_userTrackerLocalService, userId);

		_userTrackers.add(userTracker);

		return userTracker;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "userTrackerId";
	}

	@Override
	protected UADExporter getUADExporter() {
		return _uadExporter;
	}

	@Inject(filter = "component.name=*.UserTrackerUADExporter")
	private UADExporter _uadExporter;

	@Inject
	private UserTrackerLocalService _userTrackerLocalService;

	@DeleteAfterTestRun
	private final List<UserTracker> _userTrackers = new ArrayList<>();

}