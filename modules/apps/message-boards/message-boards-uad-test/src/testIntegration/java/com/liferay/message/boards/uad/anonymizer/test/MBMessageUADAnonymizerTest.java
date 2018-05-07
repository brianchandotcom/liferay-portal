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

package com.liferay.message.boards.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.uad.test.MBMessageUADTestHelper;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseUADAnonymizerTestCase;
import com.liferay.user.associated.data.test.util.WhenHasStatusByUserIdField;

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
public class MBMessageUADAnonymizerTest extends BaseUADAnonymizerTestCase<MBMessage>
	implements WhenHasStatusByUserIdField {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@Override
	public MBMessage addBaseModelWithStatusByUserId(long userId,
		long statusByUserId) throws Exception {
		MBMessage mbMessage = _mbMessageUADTestHelper.addMBMessageWithStatusByUserId(userId,
				statusByUserId);

		_mbMessages.add(mbMessage);

		return mbMessage;
	}

	@After
	public void tearDown() throws Exception {
		_mbMessageUADTestHelper.cleanUpDependencies(_mbMessages);
	}

	@Override
	protected MBMessage addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected MBMessage addBaseModel(long userId, boolean deleteAfterTestRun)
		throws Exception {
		MBMessage mbMessage = _mbMessageUADTestHelper.addMBMessage(userId);

		if (deleteAfterTestRun) {
			_mbMessages.add(mbMessage);
		}

		return mbMessage;
	}

	@Override
	protected void deleteBaseModels(List<MBMessage> baseModels)
		throws Exception {
		_mbMessageUADTestHelper.cleanUpDependencies(baseModels);
	}

	@Override
	protected UADAnonymizer getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {
		MBMessage mbMessage = _mbMessageLocalService.getMBMessage(baseModelPK);

		String userName = mbMessage.getUserName();
		String statusByUserName = mbMessage.getStatusByUserName();

		if ((mbMessage.getUserId() != user.getUserId()) &&
				!userName.equals(user.getFullName()) &&
				(mbMessage.getStatusByUserId() != user.getUserId()) &&
				!statusByUserName.equals(user.getFullName())) {
			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_mbMessageLocalService.fetchMBMessage(baseModelPK) == null) {
			return true;
		}

		return false;
	}

	@DeleteAfterTestRun
	private final List<MBMessage> _mbMessages = new ArrayList<MBMessage>();
	@Inject
	private MBMessageLocalService _mbMessageLocalService;
	@Inject
	private MBMessageUADTestHelper _mbMessageUADTestHelper;
	@Inject(filter = "component.name=*.MBMessageUADAnonymizer")
	private UADAnonymizer _uadAnonymizer;
}