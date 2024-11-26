/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.internal.upgrade.v1_3_2.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.list.type.entry.util.ListTypeEntryUtil;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.Collections;

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
public class ListTypeEntryUserIdUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		String listTypeEntryKey = RandomTestUtil.randomString();
		_user = UserTestUtil.addUser(TestPropsValues.getCompanyId());

		_listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				null, _user.getUserId(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				false,
				Collections.singletonList(
					ListTypeEntryUtil.createListTypeEntry(listTypeEntryKey)));

		_listTypeEntry = _listTypeEntryLocalService.getListTypeEntry(
			_listTypeDefinition.getListTypeDefinitionId(), listTypeEntryKey);
	}

	@After
	public void tearDown() throws Exception {
		_listTypeDefinitionLocalService.deleteListTypeDefinition(
			_listTypeDefinition);
	}

	@Test
	public void testUpgrade() throws Exception {
		long userId = _user.getUserId();

		_userLocalService.deleteUser(_user);

		Assert.assertNull(_userLocalService.fetchUser(userId));

		Assert.assertEquals(userId, _listTypeEntry.getUserId());

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.list.type.internal.upgrade.v1_3_2." +
				"ListTypeEntryUserIdUpgradeProcess");

		upgradeProcess.upgrade();

		User defaultServiceAccountUser =
			_userLocalService.fetchUserByScreenName(
				TestPropsValues.getCompanyId(), "default-service-account");
		_listTypeEntry =
			_listTypeEntryLocalService.getListTypeEntryByUuidAndCompanyId(
				_listTypeEntry.getUuid(), TestPropsValues.getCompanyId());

		Assert.assertEquals(
			defaultServiceAccountUser.getUserId(), _listTypeEntry.getUserId());
	}

	private static ListTypeDefinition _listTypeDefinition;

	@Inject
	private static ListTypeDefinitionLocalService
		_listTypeDefinitionLocalService;

	private static ListTypeEntry _listTypeEntry;

	@Inject
	private static ListTypeEntryLocalService _listTypeEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.list.type.internal.upgrade.registry.ListTypeServiceUpgradeStepRegistrator"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private static User _user;

	@Inject
	private static UserLocalService _userLocalService;

}