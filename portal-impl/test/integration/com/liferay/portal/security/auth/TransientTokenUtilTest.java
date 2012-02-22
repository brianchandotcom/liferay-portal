/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.util.BaseTestCase;

/**
 * @author Shuyang Zhou
 */
public class TransientTokenUtilTest extends BaseTestCase {

	public void testCheckTokenExpired() throws Exception {
		String tokenString = TransientTokenUtil.createToken(10);

		Thread.sleep(20);

		assertFalse(TransientTokenUtil.checkToken(tokenString));
	}

	public void testCheckTokenNotExist() {
		assertFalse(TransientTokenUtil.checkToken("test1"));
		assertFalse(TransientTokenUtil.checkToken("test2"));
	}

	public void testCheckTokenValid() {
		String tokenString = TransientTokenUtil.createToken(100);

		assertTrue(TransientTokenUtil.checkToken(tokenString));
	}

	public void testClearAll() {
		String tokenString = TransientTokenUtil.createToken(100);

		assertTrue(TransientTokenUtil.checkToken(tokenString));

		TransientTokenUtil.clearAll();

		assertFalse(TransientTokenUtil.checkToken(tokenString));
	}

}