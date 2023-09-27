/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.message.boards.uad.test.MBThreadUADTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseHasAssetEntryUADAnonymizerTestCase;
import com.liferay.user.associated.data.test.util.WhenHasStatusByUserIdField;

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
public class MBThreadUADAnonymizerTest
	extends BaseHasAssetEntryUADAnonymizerTestCase<MBThread>
	implements WhenHasStatusByUserIdField<MBThread> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public MBThread addBaseModelWithStatusByUserId(
			long userId, long statusByUserId)
		throws Exception {

		MBThread mbThread = MBThreadUADTestUtil.addMBThreadWithStatusByUserId(
			_mbCategoryLocalService, _mbMessageLocalService,
			_mbThreadLocalService, userId, statusByUserId);

		_mbThreads.add(mbThread);

		return mbThread;
	}

	@After
	public void tearDown() throws Exception {
		MBThreadUADTestUtil.cleanUpDependencies(
			_mbCategoryLocalService, _mbThreads);
	}

	@Override
	protected MBThread addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected MBThread addBaseModel(long userId, boolean deleteAfterTestRun)
		throws Exception {

		MBThread mbThread = MBThreadUADTestUtil.addMBThread(
			_mbCategoryLocalService, _mbMessageLocalService,
			_mbThreadLocalService, userId);

		if (deleteAfterTestRun) {
			_mbThreads.add(mbThread);
		}

		return mbThread;
	}

	@Override
	protected void deleteBaseModels(List<MBThread> baseModels)
		throws Exception {

		MBThreadUADTestUtil.cleanUpDependencies(
			_mbCategoryLocalService, baseModels);
	}

	@Override
	protected UADAnonymizer<MBThread> getUADAnonymizer() {
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
			!statusByUserName.equals(user.getFullName()) &&
			isAssetEntryAutoAnonymized(
				MBThread.class.getName(), mbThread.getThreadId(), user)) {

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

	@Inject
	private MBCategoryLocalService _mbCategoryLocalService;

	@Inject
	private MBMessageLocalService _mbMessageLocalService;

	@Inject
	private MBThreadLocalService _mbThreadLocalService;

	@DeleteAfterTestRun
	private final List<MBThread> _mbThreads = new ArrayList<>();

	@Inject(
		filter = "component.name=com.liferay.message.boards.uad.anonymizer.MBThreadUADAnonymizer"
	)
	private UADAnonymizer<MBThread> _uadAnonymizer;

}