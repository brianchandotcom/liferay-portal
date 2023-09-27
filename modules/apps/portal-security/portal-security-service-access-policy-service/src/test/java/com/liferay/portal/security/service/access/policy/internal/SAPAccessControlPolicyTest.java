/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.policy.internal;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Mika Koivisto
 */
public class SAPAccessControlPolicyTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_sapAccessControlPolicy = new SAPAccessControlPolicy();
	}

	@Test
	public void testMatches() {
		Assert.assertTrue(
			_sapAccessControlPolicy.matches(
				"com.liferay.portal.kernel.service.UserService", "getUserById",
				"*"));
		Assert.assertTrue(
			_sapAccessControlPolicy.matches(
				"com.liferay.portal.kernel.service.UserService", "getUserById",
				"com.liferay.portal.kernel.service.*"));
		Assert.assertTrue(
			_sapAccessControlPolicy.matches(
				"com.liferay.portal.kernel.service.UserService", "getUserById",
				"com.liferay.portal.kernel.service.UserService"));
		Assert.assertTrue(
			_sapAccessControlPolicy.matches(
				"com.liferay.portal.kernel.service.UserService", "getUserById",
				"com.liferay.portal.kernel.service.UserService#getUserById"));
		Assert.assertTrue(
			_sapAccessControlPolicy.matches(
				"com.liferay.portal.kernel.service.UserService", "getUserById",
				"com.liferay.portal.kernel.service.UserService#get*"));
		Assert.assertTrue(
			_sapAccessControlPolicy.matches(
				"com.liferay.portal.kernel.service.UserService", "getUserById",
				"com.liferay.portal.kernel.service.*#get*"));
		Assert.assertTrue(
			_sapAccessControlPolicy.matches(
				"com.liferay.portal.kernel.service.UserService", "getUserById",
				"#get*"));
		Assert.assertFalse(
			_sapAccessControlPolicy.matches(
				"com.liferay.portal.kernel.service.UserService", "getUserById",
				"com.liferay.portlet.*#get*"));
		Assert.assertFalse(
			_sapAccessControlPolicy.matches(
				"com.liferay.portal.kernel.service.UserService", "getUserById",
				"com.liferay.portal.service.*#update*"));
	}

	private SAPAccessControlPolicy _sapAccessControlPolicy;

}