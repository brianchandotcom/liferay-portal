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

package com.liferay.portal.security.pacl.test;

import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.PACLTestRule;

import java.net.Authenticator;
import java.net.ProxySelector;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class NetPermissionTest {

	@ClassRule
	@Rule
	public static final PACLTestRule paclTestRule = new PACLTestRule();

	@Test
	public void test1() throws Exception {
		try {
			Authenticator.setDefault(new Authenticator() {});

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void test2() throws Exception {
		try {
			new URL(
				Http.HTTP, "localhost", 80, ".",
				new URLStreamHandler() {

					@Override
					protected URLConnection openConnection(URL url) {
						return null;
					}

				}
			);
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void test3() throws Exception {
		try {
			ProxySelector.getDefault();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void test4() throws Exception {
		try {
			ProxySelector.setDefault(null);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

}