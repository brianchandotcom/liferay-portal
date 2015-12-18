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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class TransientTokenUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testCheckTokenExpired() throws Exception {
		String tokenString = TransientTokenUtil.createToken(10);

		Thread.sleep(20);

		Assert.assertFalse(TransientTokenUtil.checkToken(tokenString));
	}

	@Test
	public void testCheckTokenNotExist() {
		Assert.assertFalse(TransientTokenUtil.checkToken("test1"));
		Assert.assertFalse(TransientTokenUtil.checkToken("test2"));
	}

	@Test
	public void testCheckTokenValid() {
		String tokenString = TransientTokenUtil.createToken(100);

		Assert.assertTrue(TransientTokenUtil.checkToken(tokenString));
	}

	@Test
	public void testClearAll() {
		String tokenString = TransientTokenUtil.createToken(100);

		Assert.assertTrue(TransientTokenUtil.checkToken(tokenString));

		TransientTokenUtil.clearAll();

		Assert.assertFalse(TransientTokenUtil.checkToken(tokenString));
	}

}