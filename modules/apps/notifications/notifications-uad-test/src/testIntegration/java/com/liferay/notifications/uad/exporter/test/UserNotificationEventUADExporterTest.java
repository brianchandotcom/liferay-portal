/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notifications.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
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
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class UserNotificationEventUADExporterTest
	extends BaseUADExporterTestCase<UserNotificationEvent> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected UserNotificationEvent addBaseModel(long userId) throws Exception {
		UserNotificationEvent userNotificationEvent =
			_userNotificationEventLocalService.addUserNotificationEvent(
				userId, RandomTestUtil.randomString(), 0,
				UserNotificationDeliveryConstants.TYPE_WEBSITE, 0,
				RandomTestUtil.randomString(), false,
				ServiceContextTestUtil.getServiceContext());

		_userNotificationEvents.add(userNotificationEvent);

		return userNotificationEvent;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "userNotificationEventId";
	}

	@Override
	protected UADExporter<UserNotificationEvent> getUADExporter() {
		return _uadExporter;
	}

	@Inject(
		filter = "component.name=com.liferay.notifications.uad.exporter.UserNotificationEventUADExporter"
	)
	private UADExporter<UserNotificationEvent> _uadExporter;

	@Inject
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

	@DeleteAfterTestRun
	private final List<UserNotificationEvent> _userNotificationEvents =
		new ArrayList<>();

}