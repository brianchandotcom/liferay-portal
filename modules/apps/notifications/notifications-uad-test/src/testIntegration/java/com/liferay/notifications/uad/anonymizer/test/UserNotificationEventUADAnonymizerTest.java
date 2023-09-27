/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notifications.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
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
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class UserNotificationEventUADAnonymizerTest
	extends BaseUADAnonymizerTestCase<UserNotificationEvent> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected UserNotificationEvent addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected UserNotificationEvent addBaseModel(
			long userId, boolean deleteAfterTestRun)
		throws Exception {

		UserNotificationEvent userNotificationEvent =
			_userNotificationEventLocalService.addUserNotificationEvent(
				userId, RandomTestUtil.randomString(), 0,
				UserNotificationDeliveryConstants.TYPE_WEBSITE, 0,
				RandomTestUtil.randomString(), false,
				ServiceContextTestUtil.getServiceContext());

		if (deleteAfterTestRun) {
			_userNotificationEvents.add(userNotificationEvent);
		}

		return userNotificationEvent;
	}

	@Override
	protected UADAnonymizer<UserNotificationEvent> getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		return isBaseModelDeleted(baseModelPK);
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		UserNotificationEvent userNotificationEvent =
			_userNotificationEventLocalService.fetchUserNotificationEvent(
				baseModelPK);

		if (userNotificationEvent == null) {
			return true;
		}

		return false;
	}

	@Inject(
		filter = "component.name=com.liferay.notifications.uad.anonymizer.UserNotificationEventUADAnonymizer"
	)
	private UADAnonymizer<UserNotificationEvent> _uadAnonymizer;

	@Inject
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

	@DeleteAfterTestRun
	private final List<UserNotificationEvent> _userNotificationEvents =
		new ArrayList<>();

}