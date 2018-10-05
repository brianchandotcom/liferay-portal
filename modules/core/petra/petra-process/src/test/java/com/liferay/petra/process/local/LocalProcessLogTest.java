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

package com.liferay.petra.process.local;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.process.ProcessLog;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.lang.reflect.Constructor;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class LocalProcessLogTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testEquals() {
		String message = "message";

		Throwable throwable = new Throwable();

		LocalProcessLog localProcessLog = new LocalProcessLog(
			ProcessLog.Level.DEBUG, "message", throwable);

		Assert.assertTrue(localProcessLog.equals(localProcessLog));
		Assert.assertFalse(localProcessLog.equals(message));
		Assert.assertFalse(
			localProcessLog.equals(
				new LocalProcessLog(
					ProcessLog.Level.ERROR, message, throwable)));
		Assert.assertFalse(
			localProcessLog.equals(
				new LocalProcessLog(
					ProcessLog.Level.DEBUG, "message2", throwable)));
		Assert.assertFalse(
			localProcessLog.equals(
				new LocalProcessLog(
					ProcessLog.Level.DEBUG, message, new Throwable())));
		Assert.assertTrue(
			localProcessLog.equals(
				new LocalProcessLog(
					ProcessLog.Level.DEBUG, message, throwable)));
	}

	@Test
	public void testGetters() {
		String message = "message";
		Throwable throwable = new Throwable();

		LocalProcessLog localProcessLog = new LocalProcessLog(
			ProcessLog.Level.DEBUG, message, throwable);

		Assert.assertSame(ProcessLog.Level.DEBUG, localProcessLog.getLevel());
		Assert.assertSame(message, localProcessLog.getMessage());
		Assert.assertSame(throwable, localProcessLog.getThrowable());
	}

	@Test
	public void testHashCode() {
		String message = "message";
		Throwable throwable = new Throwable();

		LocalProcessLog localProcessLog = new LocalProcessLog(
			ProcessLog.Level.DEBUG, message, throwable);

		int hash = HashUtil.hash(0, ProcessLog.Level.DEBUG);

		hash = HashUtil.hash(hash, message);

		Assert.assertEquals(
			localProcessLog.hashCode(), HashUtil.hash(hash, throwable));
	}

	@Test
	public void testModifiers() {
		Assert.assertEquals(0, LocalProcessLog.class.getModifiers());

		Constructor<?>[] constructors =
			LocalProcessLog.class.getDeclaredConstructors();

		Assert.assertEquals(
			Arrays.toString(constructors), 1, constructors.length);

		Constructor<?> constructor = constructors[0];

		Assert.assertEquals(0, constructor.getModifiers());
	}

	@Test
	public void testToString() {
		String message = "message";
		Throwable throwable = new Throwable();

		LocalProcessLog localProcessLog = new LocalProcessLog(
			ProcessLog.Level.DEBUG, message, throwable);

		StringBundler sb = new StringBundler(7);

		sb.append("{level=");
		sb.append(ProcessLog.Level.DEBUG);
		sb.append(", message=");
		sb.append(message);
		sb.append(", throwable=");
		sb.append(throwable);
		sb.append("}");

		Assert.assertEquals(localProcessLog.toString(), sb.toString());
	}

}