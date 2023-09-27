/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.uad.test.MBMessageUADTestUtil;
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
public class MBMessageUADAnonymizerTest
	extends BaseHasAssetEntryUADAnonymizerTestCase<MBMessage>
	implements WhenHasStatusByUserIdField<MBMessage> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public MBMessage addBaseModelWithStatusByUserId(
			long userId, long statusByUserId)
		throws Exception {

		MBMessage mbMessage =
			MBMessageUADTestUtil.addMBMessageWithStatusByUserId(
				_mbCategoryLocalService, _mbMessageLocalService, userId,
				statusByUserId);

		_mbMessages.add(mbMessage);

		return mbMessage;
	}

	@After
	public void tearDown() throws Exception {
		MBMessageUADTestUtil.cleanUpDependencies(
			_mbCategoryLocalService, _mbMessages);
	}

	@Override
	protected MBMessage addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected MBMessage addBaseModel(long userId, boolean deleteAfterTestRun)
		throws Exception {

		MBMessage mbMessage = MBMessageUADTestUtil.addMBMessage(
			_mbCategoryLocalService, _mbMessageLocalService, userId);

		if (deleteAfterTestRun) {
			_mbMessages.add(mbMessage);
		}

		return mbMessage;
	}

	@Override
	protected void deleteBaseModels(List<MBMessage> baseModels)
		throws Exception {

		MBMessageUADTestUtil.cleanUpDependencies(
			_mbCategoryLocalService, baseModels);
	}

	@Override
	protected UADAnonymizer<MBMessage> getUADAnonymizer() {
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
			!statusByUserName.equals(user.getFullName()) &&
			isAssetEntryAutoAnonymized(
				MBMessage.class.getName(), mbMessage.getMessageId(), user)) {

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

	@Inject
	private MBCategoryLocalService _mbCategoryLocalService;

	@Inject
	private MBMessageLocalService _mbMessageLocalService;

	@DeleteAfterTestRun
	private final List<MBMessage> _mbMessages = new ArrayList<>();

	@Inject(
		filter = "component.name=com.liferay.message.boards.uad.anonymizer.MBMessageUADAnonymizer"
	)
	private UADAnonymizer<MBMessage> _uadAnonymizer;

}