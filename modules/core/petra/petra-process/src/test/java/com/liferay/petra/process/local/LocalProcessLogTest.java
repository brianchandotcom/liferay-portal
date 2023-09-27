/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.process.local;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.process.ProcessLog;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.reflect.Constructor;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class LocalProcessLogTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

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

		Assert.assertEquals(
			localProcessLog.toString(),
			StringBundler.concat(
				"{level=", ProcessLog.Level.DEBUG, ", message=", message,
				", throwable=", throwable, "}"));
	}

}