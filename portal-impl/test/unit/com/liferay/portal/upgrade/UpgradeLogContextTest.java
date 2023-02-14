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

package com.liferay.portal.upgrade;

import com.liferay.portal.dao.db.MySQLDB;
import com.liferay.portal.kernel.log.LogContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.upgrade.BaseAdminPortletsUpgradeProcess;
import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.upgrade.log.UpgradeLogContext;
import com.liferay.portal.verify.VerifyProperties;

import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class UpgradeLogContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_logContext = UpgradeLogContext.getInstance();

		_context = ReflectionTestUtil.getFieldValue(_logContext, "_contexts");

		_defaultContext = ReflectionTestUtil.getFieldValue(
			_logContext, "_default_context");
	}

	@Test
	public void testNonupgradeClassWithContext() {
		_testInitialContextStatus();

		Assert.assertSame(
			Collections.emptyMap(),
			_logContext.getContext(CompanyTestUtil.class.getName()));

		try {
			UpgradeLogContext.setContext(CompanyTestUtil.class.getName());

			Assert.assertSame(
				Collections.emptyMap(),
				_logContext.getContext(CompanyTestUtil.class.getName()));
		}
		finally {
			UpgradeLogContext.clearContext();

			Assert.assertSame(
				Collections.emptyMap(),
				_logContext.getContext(CompanyTestUtil.class.getName()));
		}
	}

	@Test
	public void testUpgradeClassesDefaultContext() {
		_testInitialContextStatus();

		Assert.assertSame(
			_defaultContext,
			_logContext.getContext(
				BaseAdminPortletsUpgradeProcess.class.getName()));
		Assert.assertSame(
			_defaultContext,
			_logContext.getContext(
				BasePortletIdUpgradeProcess.class.getName()));
		Assert.assertSame(
			_defaultContext,
			_logContext.getContext(DBUpgrader.class.getName()));
		Assert.assertSame(
			_defaultContext,
			_logContext.getContext(LoggingTimer.class.getName()));
		Assert.assertSame(
			_defaultContext, _logContext.getContext(MySQLDB.class.getName()));
		Assert.assertSame(
			_defaultContext,
			_logContext.getContext(VerifyProperties.class.getName()));
		Assert.assertSame(
			_defaultContext,
			_logContext.getContext(
				"com.liferay.portal.upgrade.internal.registry." +
					"UpgradeStepRegistratorTracker"));
		Assert.assertSame(
			_defaultContext,
			_logContext.getContext(
				"com.liferay.portal.upgrade.internal.release." +
					"ReleaseManagerImpl"));
	}

	@Test
	public void testUpgradeClassWithContext() {
		_testInitialContextStatus();

		Assert.assertSame(
			_defaultContext,
			_logContext.getContext(DBUpgrader.class.getName()));

		try {
			UpgradeLogContext.setContext("Test");

			Assert.assertSame(
				_context, _logContext.getContext(DBUpgrader.class.getName()));
		}
		finally {
			UpgradeLogContext.clearContext();

			Assert.assertSame(
				_defaultContext,
				_logContext.getContext(DBUpgrader.class.getName()));
		}
	}

	private void _testInitialContextStatus() {
		Assert.assertTrue(_context.isEmpty());

		Assert.assertFalse(_defaultContext.isEmpty());

		Assert.assertNotSame(_context, _defaultContext);
	}

	private static Map<String, String> _context;
	private static Map<String, String> _defaultContext;
	private static LogContext _logContext;

}