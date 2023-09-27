/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.SystemEventLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
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
public class SystemEventUADAnonymizerTest
	extends BaseUADAnonymizerTestCase<SystemEvent> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected SystemEvent addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected SystemEvent addBaseModel(long userId, boolean deleteAfterTestRun)
		throws Exception {

		SystemEvent systemEvent = _systemEventLocalService.addSystemEvent(
			userId, TestPropsValues.getGroupId(), Group.class.getName(),
			RandomTestUtil.nextLong(), PortalUUIDUtil.generate(),
			StringPool.BLANK, SystemEventConstants.TYPE_DELETE,
			StringPool.BLANK);

		if (deleteAfterTestRun) {
			_systemEvents.add(systemEvent);
		}

		return systemEvent;
	}

	@Override
	protected UADAnonymizer<SystemEvent> getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		SystemEvent systemEvent = _systemEventLocalService.getSystemEvent(
			baseModelPK);

		String userName = systemEvent.getUserName();

		if ((systemEvent.getUserId() != user.getUserId()) &&
			!userName.equals(user.getFullName())) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_systemEventLocalService.fetchSystemEvent(baseModelPK) == null) {
			return true;
		}

		return false;
	}

	@Inject
	private SystemEventLocalService _systemEventLocalService;

	@DeleteAfterTestRun
	private final List<SystemEvent> _systemEvents = new ArrayList<>();

	@Inject(
		filter = "component.name=com.liferay.portal.uad.anonymizer.SystemEventUADAnonymizer"
	)
	private UADAnonymizer<SystemEvent> _uadAnonymizer;

}