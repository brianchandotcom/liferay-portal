/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.internal.upgrade.v1_4_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.list.type.entry.util.ListTypeEntryUtil;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Sousa
 */
@RunWith(Arquillian.class)
public class ListTypeEntryUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpgrade() throws Exception {
		String customKey = RandomTestUtil.randomString();

		ListTypeDefinition customListTypeDefinition = _addListTypeDefinition(
			TestPropsValues.getUserId(), false, customKey);

		String systemKey = RandomTestUtil.randomString();

		ListTypeDefinition systemListTypeDefinition = _addListTypeDefinition(
			TestPropsValues.getUserId(), true, systemKey);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			_multiVMPool.clear();
		}

		ListTypeEntry customListTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				customListTypeDefinition.getListTypeDefinitionId(), customKey);

		Assert.assertFalse(customListTypeEntry.isSystem());

		ListTypeEntry systemListTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				systemListTypeDefinition.getListTypeDefinitionId(), systemKey);

		Assert.assertTrue(systemListTypeEntry.isSystem());

		_listTypeDefinitionLocalService.deleteListTypeDefinition(
			customListTypeDefinition);
		_listTypeDefinitionLocalService.deleteListTypeDefinition(
			systemListTypeDefinition);
	}

	private ListTypeDefinition _addListTypeDefinition(
			long userId, boolean system, String key)
		throws Exception {

		return _listTypeDefinitionLocalService.addListTypeDefinition(
			null, userId,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			system,
			Collections.singletonList(
				ListTypeEntryUtil.createListTypeEntry(key)),
			new ServiceContext());
	}

	private static final String _CLASS_NAME =
		"com.liferay.list.type.internal.upgrade.v1_4_0." +
			"ListTypeEntryUpgradeProcess";

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Inject
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject(
		filter = "component.name=com.liferay.list.type.internal.upgrade.registry.ListTypeServiceUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}