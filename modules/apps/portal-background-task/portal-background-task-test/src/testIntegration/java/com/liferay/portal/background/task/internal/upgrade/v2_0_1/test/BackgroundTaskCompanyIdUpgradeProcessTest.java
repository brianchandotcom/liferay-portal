/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.background.task.internal.upgrade.v2_0_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge Avalos
 */
@RunWith(Arquillian.class)
public class BackgroundTaskCompanyIdUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(new LiferayIntegrationTestRule());

	@Before
	public void setUp() {
		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.createBackgroundTask(
				_BACKGROUND_TASK_ID);

		backgroundTask.setTaskContextMap(
			HashMapBuilder.<String, Serializable>put(
				"companyId", _COMPANY_ID
			).put(
				_KEY_THREAD_LOCAL_VALUES, _initializeThreadLocalValues()
			).build());

		_backgroundTaskLocalService.updateBackgroundTask(backgroundTask);
	}

	@Test
	public void testUpgradeProcess() throws PortalException {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.portal.background.task.internal.upgrade.v2_0_1." +
				"BackgroundTaskCompanyIdUpgradeProcess");

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.getBackgroundTask(_BACKGROUND_TASK_ID);

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		Assert.assertEquals(_COMPANY_ID, taskContextMap.get("companyId"));

		Map<String, Serializable> threadLocalValues =
			(Map<String, Serializable>)taskContextMap.get(
				_KEY_THREAD_LOCAL_VALUES);

		Assert.assertEquals(_COMPANY_ID, threadLocalValues.get("companyId"));

		upgradeProcess.upgrade();

		backgroundTask = _backgroundTaskLocalService.getBackgroundTask(
			_BACKGROUND_TASK_ID);

		taskContextMap = backgroundTask.getTaskContextMap();

		Assert.assertNull(taskContextMap.get("companyId"));

		threadLocalValues = (Map<String, Serializable>)taskContextMap.get(
			_KEY_THREAD_LOCAL_VALUES);

		Assert.assertEquals(
			_CLUSTER_INVOKE_ENABLED, threadLocalValues.get("clusterInvoke"));
		Assert.assertEquals(
			_defaultLocale, threadLocalValues.get("defaultLocale"));
		Assert.assertEquals(_GROUP_ID, threadLocalValues.get("groupId"));
		Assert.assertEquals(
			_PRINCIPAL_NAME, threadLocalValues.get("principalName"));
		Assert.assertNull(threadLocalValues.get("principalPassword"));
		Assert.assertEquals(
			_siteDefaultLocale, threadLocalValues.get("siteDefaultLocale"));
		Assert.assertEquals(
			_themeDisplayLocale, threadLocalValues.get("themeDisplayLocale"));

		Assert.assertNull(threadLocalValues.get("companyId"));
	}

	private HashMap<String, Serializable> _initializeThreadLocalValues() {
		return HashMapBuilder.<String, Serializable>put(
			"clusterInvoke", _CLUSTER_INVOKE_ENABLED
		).put(
			"companyId", _COMPANY_ID
		).put(
			"defaultLocale", _defaultLocale
		).put(
			"groupId", _GROUP_ID
		).put(
			"principalName", _PRINCIPAL_NAME
		).put(
			"siteDefaultLocale", _siteDefaultLocale
		).put(
			"themeDisplayLocale", _themeDisplayLocale
		).build();
	}

	private static final long _BACKGROUND_TASK_ID = RandomTestUtil.randomLong();

	private static final boolean _CLUSTER_INVOKE_ENABLED = true;

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private static final String _KEY_THREAD_LOCAL_VALUES = "threadLocalValues";

	private static final String _PRINCIPAL_NAME = String.valueOf(
		RandomTestUtil.randomLong());

	@Inject(
		filter = "component.name=com.liferay.portal.background.task.internal.upgrade.registry.BackgroundTaskServiceUpgradeStepRegistrator"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	private final Locale _defaultLocale = LocaleUtil.US;
	private final Locale _siteDefaultLocale = LocaleUtil.CANADA;
	private final Locale _themeDisplayLocale = LocaleUtil.FRANCE;

}