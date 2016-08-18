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

package com.liferay.portal.security.service.access.policy.internal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mika Koivisto
 */
public class SAPAccessControlPolicyTest {

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