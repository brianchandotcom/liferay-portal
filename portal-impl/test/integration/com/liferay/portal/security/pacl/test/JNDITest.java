/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.security.pacl.PACLExecutionTestListener;
import com.liferay.portal.security.pacl.PACLIntegrationJUnitTestRunner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@ExecutionTestListeners(listeners = {PACLExecutionTestListener.class})
@RunWith(PACLIntegrationJUnitTestRunner.class)
public class JNDITest {

	@Test
	public void bind1() throws Exception {
		try {
			Context context = new InitialContext();

			context.bind("test-pacl-john 3:16", "John");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void bind2() throws Exception {
		try {
			Context context = new InitialContext();

			context.bind("test-pacl-luke", "Luke");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void bind3() throws Exception {
		try {
			Context context = new InitialContext();

			context.bind("test-pacl-Luke", "Luke");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void bind4() throws Exception {
		try {
			Context context = new InitialContext();

			context.bind("test-pacl-Mark", "Mark");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void bind5() throws Exception {
		try {
			Context context = new InitialContext();

			context.bind("test-pacl-mark", "Mark");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void bind6() throws Exception {
		try {
			Context context = new InitialContext();

			context.bind("test-pacl-matthew", "Matthew");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void bind7() throws Exception {
		try {
			Context context = new InitialContext();

			context.bind("test-pacl-Matthew", "Matthew");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void lookup1() throws Exception {
		try {
			Context context = new InitialContext();

			context.lookup("java_liferay:jdbc/LiferayPool");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void lookup2() throws Exception {
		try {
			Context context = new InitialContext();

			context.lookup("test-pacl-john 3:16");
		}
		catch (NameNotFoundException nnfe) {
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void lookup3() throws Exception {
		try {
			Context context = new InitialContext();

			context.lookup("test-pacl-luke");
		}
		catch (NameNotFoundException nnfe) {
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void lookup4() throws Exception {
		try {
			Context context = new InitialContext();

			context.lookup("test-pacl-Luke");
		}
		catch (NameNotFoundException nnfe) {
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void lookup5() throws Exception {
		try {
			Context context = new InitialContext();

			context.lookup("test-pacl-mark");
		}
		catch (NameNotFoundException nnfe) {
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void lookup6() throws Exception {
		try {
			Context context = new InitialContext();

			context.lookup("test-pacl-Mark");
		}
		catch (NameNotFoundException nnfe) {
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void lookup7() throws Exception {
		try {
			Context context = new InitialContext();

			context.lookup("test-pacl-matthew");
		}
		catch (NameNotFoundException nnfe) {
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void unbind1() throws Exception {
		try {
			Context context = new InitialContext();

			context.unbind("test-pacl-john 3:16");
		}
		catch (NameNotFoundException nnfe) {
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void unbind2() throws Exception {
		try {
			Context context = new InitialContext();

			context.unbind("test-pacl-luke");
		}
		catch (NameNotFoundException nnfe) {
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void unbind3() throws Exception {
		try {
			Context context = new InitialContext();

			context.unbind("test-pacl-Luke");
		}
		catch (NameNotFoundException nnfe) {
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void unbind4() throws Exception {
		try {
			Context context = new InitialContext();

			context.unbind("test-pacl-mark");
		}
		catch (NameNotFoundException nnfe) {
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void unbind5() throws Exception {
		try {
			Context context = new InitialContext();

			context.unbind("test-pacl-Mark");
		}
		catch (NameNotFoundException nnfe) {
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void unbind6() throws Exception {
		try {
			Context context = new InitialContext();

			context.unbind("test-pacl-matthew");
		}
		catch (NameNotFoundException nnfe) {
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void unbind7() throws Exception {
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