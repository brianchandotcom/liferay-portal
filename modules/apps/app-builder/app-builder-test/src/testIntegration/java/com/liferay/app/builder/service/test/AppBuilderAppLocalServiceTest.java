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

package com.liferay.app.builder.service.test;

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.model.AppBuilderAppVersion;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.app.builder.service.AppBuilderAppVersionLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AppBuilderAppLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddAppBuilderApp() throws Exception {
		AppBuilderApp appBuilderApp =
			_appBuilderAppLocalService.addAppBuilderApp(
				TestPropsValues.getGroupId(), TestPropsValues.getCompanyId(),
				TestPropsValues.getUserId(), true, RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString());

		AppBuilderAppVersion latestAppBuilderAppVersion =
			_appBuilderAppVersionLocalService.getLatestAppBuilderAppVersion(
				appBuilderApp.getAppBuilderAppId());

		Assert.assertNotNull(latestAppBuilderAppVersion);
		Assert.assertEquals("1.0", latestAppBuilderAppVersion.getVersion());
	}

	@Test
	public void testUpdateAppBuilderAppIncrementVersion() throws Exception {
		AppBuilderApp appBuilderApp =
			_appBuilderAppLocalService.addAppBuilderApp(
				TestPropsValues.getGroupId(), TestPropsValues.getCompanyId(),
				TestPropsValues.getUserId(), true, RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString());

		appBuilderApp = _appBuilderAppLocalService.updateAppBuilderApp(
			appBuilderApp.getUserId(), appBuilderApp.getAppBuilderAppId(),
			appBuilderApp.isActive(), appBuilderApp.getDdmStructureId(),
			RandomTestUtil.nextLong(), appBuilderApp.getDeDataListViewId(),
			appBuilderApp.getNameMap());

		AppBuilderAppVersion latestAppBuilderAppVersion =
			_appBuilderAppVersionLocalService.getLatestAppBuilderAppVersion(
				appBuilderApp.getAppBuilderAppId());

		Assert.assertEquals("2.0", latestAppBuilderAppVersion.getVersion());

		appBuilderApp = _appBuilderAppLocalService.updateAppBuilderApp(
			appBuilderApp.getUserId(), appBuilderApp.getAppBuilderAppId(),
			false, RandomTestUtil.nextLong(),
			appBuilderApp.getDdmStructureLayoutId(), RandomTestUtil.nextLong(),
			RandomTestUtil.randomLocaleStringMap());

		latestAppBuilderAppVersion =
			_appBuilderAppVersionLocalService.getLatestAppBuilderAppVersion(
				appBuilderApp.getAppBuilderAppId());

		Assert.assertEquals("2.0", latestAppBuilderAppVersion.getVersion());
	}

	@Inject
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	@Inject
	private AppBuilderAppVersionLocalService _appBuilderAppVersionLocalService;

}