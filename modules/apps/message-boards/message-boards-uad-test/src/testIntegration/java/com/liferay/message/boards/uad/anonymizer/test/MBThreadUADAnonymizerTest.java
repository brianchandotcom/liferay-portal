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

import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.message.boards.uad.test.MBThreadUADTestHelper;

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
public class MBThreadUADAnonymizerTest extends BaseUADAnonymizerTestCase<MBThread>
	implements WhenHasStatusByUserIdField {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@Override
	public MBThread addBaseModelWithStatusByUserId(long userId,
		long statusByUserId) throws Exception {
		MBThread mbThread = _mbThreadUADTestHelper.addMBThreadWithStatusByUserId(userId,
				statusByUserId);

		_mbThreads.add(mbThread);

		return mbThread;
	}

	@After
	public void tearDown() throws Exception {
		_mbThreadUADTestHelper.cleanUpDependencies(_mbThreads);
	}

	@Override
	protected MBThread addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected MBThread addBaseModel(long userId, boolean deleteAfterTestRun)
		throws Exception {
		MBThread mbThread = _mbThreadUADTestHelper.addMBThread(userId);

		if (deleteAfterTestRun) {
			_mbThreads.add(mbThread);
		}

		return mbThread;
	}

	@Override
	protected void deleteBaseModels(List<MBThread> baseModels)
		throws Exception {
		_mbThreadUADTestHelper.cleanUpDependencies(baseModels);
	}

	@Override
	protected UADAnonymizer getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {
		MBThread mbThread = _mbThreadLocalService.getMBThread(baseModelPK);

		String userName = mbThread.getUserName();
		String statusByUserName = mbThread.getStatusByUserName();

		if ((mbThread.getUserId() != user.getUserId()) &&
				!userName.equals(user.getFullName()) &&
				(mbThread.getRootMessageUserId() != user.getUserId()) &&
				(mbThread.getLastPostByUserId() != user.getUserId()) &&
				(mbThread.getStatusByUserId() != user.getUserId()) &&
				!statusByUserName.equals(user.getFullName())) {
			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_mbThreadLocalService.fetchMBThread(baseModelPK) == null) {
			return true;
		}

		return false;
	}

	@DeleteAfterTestRun
	private final List<MBThread> _mbThreads = new ArrayList<MBThread>();
	@Inject
	private MBThreadLocalService _mbThreadLocalService;
	@Inject
	private MBThreadUADTestHelper _mbThreadUADTestHelper;
	@Inject(filter = "component.name=*.MBThreadUADAnonymizer")
	private UADAnonymizer _uadAnonymizer;
}