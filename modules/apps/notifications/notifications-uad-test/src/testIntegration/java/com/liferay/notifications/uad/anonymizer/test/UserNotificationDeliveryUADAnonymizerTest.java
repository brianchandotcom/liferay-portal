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

package com.liferay.notifications.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDelivery;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.service.UserNotificationDeliveryLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
public class UserNotificationDeliveryUADAnonymizerTest
	extends BaseUADAnonymizerTestCase<UserNotificationDelivery> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected UserNotificationDelivery addBaseModel(long userId)
		throws Exception {

		return addBaseModel(userId, true);
	}

	@Override
	protected UserNotificationDelivery addBaseModel(
			long userId, boolean deleteAfterTestRun)
		throws Exception {

		UserNotificationDelivery userNotificationDelivery =
			_userNotificationDeliveryLocalService.addUserNotificationDelivery(
				userId, RandomTestUtil.randomString(), 0,
				UserNotificationDeliveryConstants.TYPE_WEBSITE,
				UserNotificationDeliveryConstants.TYPE_WEBSITE, false);

		if (deleteAfterTestRun) {
			_userNotificationDeliveries.add(userNotificationDelivery);
		}

		return userNotificationDelivery;
	}

	@Override
	protected UADAnonymizer getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		return isBaseModelDeleted(baseModelPK);
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		UserNotificationDelivery userNotificationDelivery =
			_userNotificationDeliveryLocalService.fetchUserNotificationDelivery(
				baseModelPK);

		if (userNotificationDelivery == null) {
			return true;
		}

		return false;
	}

	@Inject(filter = "component.name=*.UserNotificationDeliveryUADAnonymizer")
	private UADAnonymizer _uadAnonymizer;

	@DeleteAfterTestRun
	private final List<UserNotificationDelivery> _userNotificationDeliveries =
		new ArrayList<>();

	@Inject
	private UserNotificationDeliveryLocalService
		_userNotificationDeliveryLocalService;

}