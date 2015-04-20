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

import com.liferay.portal.test.rule.PACLTestRule;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class JNDITest {

	@ClassRule
	@Rule
	public static final PACLTestRule paclTestRule = new PACLTestRule();

	@Test
	public void testBind1() throws Exception {
		Context context = new InitialContext();

		context.bind("test-pacl-john 3:16", "John");
	}

	@Test
	public void testBind2() throws Exception {
		Context context = new InitialContext();

		context.bind("test-pacl-luke", "Luke");
	}

	@Test
	public void testBind3() throws Exception {
		Context context = new InitialContext();

		context.bind("test-pacl-Luke", "Luke");
	}

	@Test
	public void testBind4() throws Exception {
		Context context = new InitialContext();

		context.bind("test-pacl-Mark", "Mark");
	}

	@Test
	public void testBind5() throws Exception {
		Context context = new InitialContext();

		context.bind("test-pacl-mark", "Mark");
	}

	@Test
	public void testBind6() throws Exception {
		Context context = new InitialContext();

		context.bind("test-pacl-matthew", "Matthew");
	}

	@Test
	public void testBind7() throws Exception {
		try {
			Context context = new InitialContext();

			context.bind("test-pacl-Matthew", "Matthew");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testLookup1() throws Exception {
		try {
			Context context = new InitialContext();

			context.lookup("java_liferay:jdbc/LiferayPool");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testLookup2() throws Exception {
		try {
			Context context = new InitialContext();

			context.lookup("test-pacl-john 3:16");
		}
		catch (NameNotFoundException nnfe) {
		}
	}

	@Test
	public void testLookup3() throws Exception {
		try {
			Context context = new InitialContext();

			context.lookup("test-pacl-luke");
		}
		catch (NameNotFoundException nnfe) {
		}
	}

	@Test
	public void testLookup4() throws Exception {
		try {
			Context context = new InitialContext();

			context.lookup("test-pacl-Luke");
		}
		catch (NameNotFoundException nnfe) {
		}
	}

	@Test
	public void testLookup5() throws Exception {
		try {
			Context context = new InitialContext();

			context.lookup("test-pacl-mark");
		}
		catch (NameNotFoundException nnfe) {
		}
	}

	@Test
	public void testLookup6() throws Exception {
		try {
			Context context = new InitialContext();

			context.lookup("test-pacl-Mark");
		}
		catch (NameNotFoundException nnfe) {
		}
	}

	@Test
	public void testLookup7() throws Exception {
		try {
			Context context = new InitialContext();

			context.lookup("test-pacl-matthew");
		}
		catch (NameNotFoundException nnfe) {
		}
	}

	@Test
	public void testUnbind1() throws Exception {
		try {
			Context context = new InitialContext();

			context.unbind("test-pacl-john 3:16");
		}
		catch (NameNotFoundException nnfe) {
		}
	}

	@Test
	public void testUnbind2() throws Exception {
		try {
			Context context = new InitialContext();

			context.unbind("test-pacl-luke");
		}
		catch (NameNotFoundException nnfe) {
		}
	}

	@Test
	public void testUnbind3() throws Exception {
		try {
			Context context = new InitialContext();

			context.unbind("test-pacl-Luke");
		}
		catch (NameNotFoundException nnfe) {
		}
	}

	@Test
	public void testUnbind4() throws Exception {
		try {
			Context context = new InitialContext();

			context.unbind("test-pacl-mark");
		}
		catch (NameNotFoundException nnfe) {
		}
	}

	@Test
	public void testUnbind5() throws Exception {
		try {
			Context context = new InitialContext();

			context.unbind("test-pacl-Mark");
		}
		catch (NameNotFoundException nnfe) {
		}
	}

	@Test
	public void testUnbind6() throws Exception {
		try {
			Context context = new InitialContext();

			context.unbind("test-pacl-matthew");
		}
		catch (NameNotFoundException nnfe) {
		}
	}

	@Test
	public void testUnbind7() throws Exception {
		try {
			Context context = new InitialContext();

			context.unbind("test-pacl-Matthew");

			Assert.fail();
		}
		catch (NameNotFoundException nnfe) {
		}
		catch (SecurityException se) {
		}
	}

}