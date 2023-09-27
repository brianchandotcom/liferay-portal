/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.portal.kernel.model.UserTracker;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.test.util.BaseUADExporterTestCase;

import com.liferay.users.admin.uad.test.UserTrackerUADTestHelper;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;

import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@RunWith(Arquillian.class)
public class UserTrackerUADExporterTest extends BaseUADExporterTestCase<UserTracker> {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		_userTrackerUADTestHelper.cleanUpDependencies(_userTrackers);
	}

	@Override
	protected UserTracker addBaseModel(long userId) throws Exception {
		UserTracker userTracker = _userTrackerUADTestHelper.addUserTracker(userId);

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

	@DeleteAfterTestRun
	private final List<UserTracker> _userTrackers = new ArrayList<UserTracker>();
	@Inject
	private UserTrackerUADTestHelper _userTrackerUADTestHelper;
	@Inject(filter = "component.name=*.UserTrackerUADExporter")
	private UADExporter _uadExporter;
}