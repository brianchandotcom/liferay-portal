/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.internal.model.listener.test;

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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Costa
 */
@RunWith(Arquillian.class)
public class UserModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testOnAfterRemove() throws Exception {
		String listTypeEntryKey = RandomTestUtil.randomString();
		User user = UserTestUtil.addUser();

		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				null, user.getUserId(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				false,
				Collections.singletonList(
					ListTypeEntryUtil.createListTypeEntry(listTypeEntryKey)));

		_userLocalService.deleteUser(user);

		listTypeDefinition =
			_listTypeDefinitionLocalService.getListTypeDefinition(
				listTypeDefinition.getListTypeDefinitionId());

		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(), listTypeEntryKey);

		long defaultUserId = _userLocalService.getUserIdByScreenName(
			TestPropsValues.getCompanyId(), "default-service-account");

		Assert.assertEquals(defaultUserId, listTypeDefinition.getUserId());
		Assert.assertEquals(defaultUserId, listTypeEntry.getUserId());

		_listTypeDefinitionLocalService.deleteListTypeDefinition(
			listTypeDefinition);
	}

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Inject
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Inject
	private UserLocalService _userLocalService;

}