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
import com.liferay.portal.model.User;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.util.test.AtomicState;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class UserImporterUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.userimporterutil"));

	@BeforeClass
	public static void setUpClass() {
		_atomicState = new AtomicState();
	}

	@AfterClass
	public static void tearDownClass() {
		_atomicState.close();
	}

	@Test
	public void testImportUser1() {
		try {
			User user = UserImporterUtil.importUser(
				1, "test@liferay-test.com", "test");
			Assert.assertEquals(user.getCompanyId(), 1);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testImportUser2() {
		try {
			User user = UserImporterUtil.importUser(
				2, 2, "test@liferay-test.com", "test");
			Assert.assertEquals(user.getCompanyId(), 2);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testImportUser3() {
		try {
			User user = UserImporterUtil.importUserByScreenName(3, "test");
			Assert.assertEquals(user.getCompanyId(), 3);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testNoReturnImportUser1() {
		_atomicState.reset();

		try {
			UserImporterUtil.importUsers();
		}
		catch (Exception e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testNoReturnImportUser2() {
		_atomicState.reset();

		try {
			UserImporterUtil.importUsers(1);
		}
		catch (Exception e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testNoReturnImportUser3() {
		_atomicState.reset();

		try {
			UserImporterUtil.importUsers(1, 1);
		}
		catch (Exception e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	private static AtomicState _atomicState;

}