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

package com.liferay.portal.security.exportimport;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.model.impl.ContactImpl;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.util.test.AtomicStateUtil;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class UserExporterUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.userexporterutil"));

	@BeforeClass
	public static void setUpClass() {
		_atomicStateUtil = new AtomicStateUtil();
	}

	@AfterClass
	public static void tearDownClass() {
		_atomicStateUtil.close();
	}

	@Test
	public void testExportUser1() {
		_atomicStateUtil.resetState();

		try {
			UserExporterUtil.exportUser(new ContactImpl(), null);
		}
		catch (Exception e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicStateUtil.isStateSet());
	}

	@Test
	public void testExportUser2() {
		_atomicStateUtil.resetState();

		try {
			UserExporterUtil.exportUser(1, 1, null);
		}
		catch (Exception e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicStateUtil.isStateSet());
	}

	@Test
	public void testExportUser3() {
		_atomicStateUtil.resetState();

		try {
			UserExporterUtil.exportUser(new UserImpl(), null);
		}
		catch (Exception e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicStateUtil.isStateSet());
	}

	private static AtomicStateUtil _atomicStateUtil;

}