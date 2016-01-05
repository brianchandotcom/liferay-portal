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
			new LiferayIntegrationTestRule(),
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
	public void testImportUser1() throws Exception {
		User user = UserImporterUtil.importUser(
			1, "test@liferay-test.com", "test");

		Assert.assertEquals(user.getCompanyId(), 1);
	}

	@Test
	public void testImportUser2() throws Exception {
		User user = UserImporterUtil.importUser(
			2, 2, "test@liferay-test.com", "test");

		Assert.assertEquals(user.getCompanyId(), 2);
	}

	@Test
	public void testImportUserByScreenName() throws Exception {
		User user = UserImporterUtil.importUserByScreenName(3, "test");

		Assert.assertEquals(user.getCompanyId(), 3);
	}

	@Test
	public void testImportUsers1() throws Exception {
		_atomicState.reset();

		UserImporterUtil.importUsers();

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testImportUsers2() throws Exception {
		_atomicState.reset();

		UserImporterUtil.importUsers(1);

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testImportUsers3() throws Exception {
		_atomicState.reset();

		UserImporterUtil.importUsers(1, 1);

		Assert.assertTrue(_atomicState.isSet());
	}

	private static AtomicState _atomicState;

}