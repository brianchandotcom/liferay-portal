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

package com.liferay.notifications.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.notifications.uad.test.UserNotificationDeliveryUADTestHelper;
import com.liferay.portal.kernel.model.UserNotificationDelivery;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.test.util.BaseUADExporterTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class UserNotificationDeliveryUADExporterTest
	extends BaseUADExporterTestCase<UserNotificationDelivery> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		_userNotificationDeliveryUADTestHelper.cleanUpDependencies(
			_userNotificationDeliveries);
	}

	@Override
	protected UserNotificationDelivery addBaseModel(long userId)
		throws Exception {

		UserNotificationDelivery userNotificationDelivery =
			_userNotificationDeliveryUADTestHelper.addUserNotificationDelivery(
				userId);

		_userNotificationDeliveries.add(userNotificationDelivery);

		return userNotificationDelivery;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "userNotificationDeliveryId";
	}

	@Override
	protected UADExporter getUADExporter() {
		return _uadExporter;
	}

	@Inject(filter = "component.name=*.UserNotificationDeliveryUADExporter")
	private UADExporter _uadExporter;

	@DeleteAfterTestRun
	private final List<UserNotificationDelivery> _userNotificationDeliveries =
		new ArrayList<>();

	@Inject
	private UserNotificationDeliveryUADTestHelper
		_userNotificationDeliveryUADTestHelper;

}