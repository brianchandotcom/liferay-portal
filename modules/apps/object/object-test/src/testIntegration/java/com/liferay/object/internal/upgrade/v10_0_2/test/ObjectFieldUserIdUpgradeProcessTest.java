/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v10_0_2.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Costa
 */
@RunWith(Arquillian.class)
public class ObjectFieldUserIdUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_user = UserTestUtil.addUser(TestPropsValues.getCompanyId());

		_objectDefinition = ObjectDefinitionTestUtil.addCustomObjectDefinition(
			ObjectDefinitionTestUtil.getRandomName(), _user.getUserId());

		_objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				_user.getUserId(), _objectDefinition.getObjectDefinitionId());

		_objectField = _objectFieldLocalService.getObjectField(
			_objectDefinition.getTitleObjectFieldId());
	}

	@After
	public void tearDown() throws Exception {
		_objectDefinitionLocalService.deleteObjectDefinition(_objectDefinition);
	}

	@Test
	public void testUpgrade() throws Exception {
		long userId = _user.getUserId();

		_userLocalService.deleteUser(_user);

		Assert.assertNull(_userLocalService.fetchUser(userId));

		Assert.assertEquals(userId, _objectField.getUserId());

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.object.internal.upgrade.v10_0_2." +
				"ObjectFieldUserIdUpgradeProcess");

		upgradeProcess.upgrade();

		User defaultServiceAccountUser =
			_userLocalService.fetchUserByScreenName(
				TestPropsValues.getCompanyId(), "default-service-account");
		_objectField = _objectFieldLocalService.getObjectField(
			_objectField.getObjectFieldId());

		Assert.assertEquals(
			defaultServiceAccountUser.getUserId(), _objectField.getUserId());
	}

	private static ObjectDefinition _objectDefinition;

	@Inject
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;

	private static ObjectField _objectField;

	@Inject
	private static ObjectFieldLocalService _objectFieldLocalService;

	@Inject(
		filter = "component.name=com.liferay.object.internal.upgrade.registry.ObjectServiceUpgradeStepRegistrator"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private static User _user;

	@Inject
	private static UserLocalService _userLocalService;

}