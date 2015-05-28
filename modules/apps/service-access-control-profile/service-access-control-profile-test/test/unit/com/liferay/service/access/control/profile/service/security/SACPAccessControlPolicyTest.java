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

package com.liferay.service.access.control.profile.service.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mika Koivisto
 */
public class SACPAccessControlPolicyTest {

	@Before
	public void setUp() {
		_sacpAccessControlPolicy = new SACPAccessControlPolicy();
	}

	@Test
	public void testMatches() {
		Assert.assertTrue(
			_sacpAccessControlPolicy.matches(
				"com.liferay.portal.service.UserService", "getUserById", "*"));
		Assert.assertTrue(
			_sacpAccessControlPolicy.matches(
				"com.liferay.portal.service.UserService", "getUserById",
				"com.liferay.portal.service.*"));
		Assert.assertTrue(
			_sacpAccessControlPolicy.matches(
				"com.liferay.portal.service.UserService", "getUserById",
				"com.liferay.portal.service.UserService"));
		Assert.assertTrue(
			_sacpAccessControlPolicy.matches(
				"com.liferay.portal.service.UserService", "getUserById",
				"com.liferay.portal.service.UserService#getUserById"));
		Assert.assertTrue(
			_sacpAccessControlPolicy.matches(
				"com.liferay.portal.service.UserService", "getUserById",
				"com.liferay.portal.service.UserService#get*"));
		Assert.assertTrue(
			_sacpAccessControlPolicy.matches(
				"com.liferay.portal.service.UserService", "getUserById",
				"com.liferay.portal.service.*#get*"));
		Assert.assertTrue(
			_sacpAccessControlPolicy.matches(
				"com.liferay.portal.service.UserService", "getUserById",
				"#get*"));
		Assert.assertFalse(
			_sacpAccessControlPolicy.matches(
				"com.liferay.portal.service.UserService", "getUserById",
				"com.liferay.portlet.*#get*"));
		Assert.assertFalse(
			_sacpAccessControlPolicy.matches(
				"com.liferay.portal.service.UserService", "getUserById",
				"com.liferay.portal.service.*#update*"));
	}

	private SACPAccessControlPolicy _sacpAccessControlPolicy;

}