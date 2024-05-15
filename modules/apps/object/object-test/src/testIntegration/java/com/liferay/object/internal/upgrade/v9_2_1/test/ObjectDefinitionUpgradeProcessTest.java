/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v9_2_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Thalles Montenegro
 */
@RunWith(Arquillian.class)
public class ObjectDefinitionUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpgradeProcess() throws Exception {
		Company company1 = CompanyTestUtil.addCompany();

		User user1 = UserTestUtil.addUser(company1);

		ObjectDefinition objectDefinition1 = _publishCustomObjectDefinition(
			user1.getUserId());

		_userLocalService.deleteUser(user1);

		Company company2 = CompanyTestUtil.addCompany();

		User user2 = UserTestUtil.addUser(company2);

		ObjectDefinition objectDefinition2 = _publishCustomObjectDefinition(
			user2.getUserId());

		_userLocalService.deleteUser(user2);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();
		}

		long defaultServiceAccountUserId =
			_userLocalService.getUserIdByScreenName(
				company1.getCompanyId(), "default-service-account");

		_assertObjectDefinitionUserId(
			defaultServiceAccountUserId,
			objectDefinition1.getObjectDefinitionId());

		defaultServiceAccountUserId = _userLocalService.getUserIdByScreenName(
			company2.getCompanyId(), "default-service-account");

		_assertObjectDefinitionUserId(
			defaultServiceAccountUserId,
			objectDefinition2.getObjectDefinitionId());
	}

	private void _assertObjectDefinitionUserId(
			long expectedUserId, long objectDefinitionId)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectDefinitionId);

		Assert.assertEquals(expectedUserId, objectDefinition.getUserId());
	}

	private ObjectDefinition _publishCustomObjectDefinition(long userId)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				userId, 0, false, false, false, false,
				LocalizedMapUtil.getLocalizedMap("Test"), "Test", null, null,
				LocalizedMapUtil.getLocalizedMap("Tests"), true,
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(), StringUtil.randomId())));

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			userId, objectDefinition.getObjectDefinitionId());
	}

	private static final String _CLASS_NAME =
		"com.liferay.object.internal.upgrade.v9_2_1." +
			"ObjectDefinitionUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.object.internal.upgrade.registry.ObjectServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private UserLocalService _userLocalService;

}