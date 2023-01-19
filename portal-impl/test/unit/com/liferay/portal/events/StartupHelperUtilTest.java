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

package com.liferay.portal.events;

import com.liferay.portal.dao.db.MySQLDB;
import com.liferay.portal.kernel.log.LogContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.upgrade.BaseAdminPortletsUpgradeProcess;
import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.verify.VerifyProperties;

import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class StartupHelperUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testUpgradeLogContext() throws ClassNotFoundException {
		Thread thread = Thread.currentThread();

		ClassLoader classLoader = thread.getContextClassLoader();

		LogContext logContext = ReflectionTestUtil.getFieldValue(
			classLoader.loadClass(
				StartupHelperUtil.class.getName() + "$UpgradeLogContext"),
			"_INSTANCE");

		Map<String, String> context = ReflectionTestUtil.getFieldValue(
			logContext, "_context");

		Assert.assertFalse(context.isEmpty());

		Assert.assertSame(
			context, logContext.getContext(DBUpgrader.class.getName()));
		Assert.assertSame(
			context, logContext.getContext(LoggingTimer.class.getName()));
		Assert.assertSame(
			context,
			logContext.getContext(
				"com.liferay.portal.upgrade.internal.release." +
					"ReleaseManagerImpl"));
		Assert.assertSame(
			context,
			logContext.getContext(
				"com.liferay.portal.upgrade.internal.registry." +
					"UpgradeStepRegistratorTracker"));
		Assert.assertSame(
			context, logContext.getContext(VerifyProperties.class.getName()));
		Assert.assertSame(
			context, logContext.getContext(MySQLDB.class.getName()));
		Assert.assertSame(
			context,
			logContext.getContext(
				BaseAdminPortletsUpgradeProcess.class.getName()));
		Assert.assertSame(
			context,
			logContext.getContext(BasePortletIdUpgradeProcess.class.getName()));

		Assert.assertSame(
			Collections.emptyMap(),
			logContext.getContext(StartupHelperUtilTest.class.getName()));
	}

}