/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.function;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsafeConsumerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new CodeCoverageAssertor() {

				@Override
				public void appendAssertClasses(List<Class<?>> assertClasses) {
					assertClasses.add(UnsafeFunction.class);
					assertClasses.add(UnsafeRunnable.class);
					assertClasses.add(UnsafeSupplier.class);
				}

			},
			LiferayUnitTestRule.INSTANCE);

	@Test
	public void testAccept1() throws IOException {

		// Expecting IOException, RuntimeException stops the chain and
		// suppresses IOException

		try {
			UnsafeConsumer.accept(
				_exceptions,
				exception -> {
					throw exception;
				},
				IOException.class);
		}
		catch (Exception exception) {
			Assert.assertSame(_exceptions.get(1), exception);

			Throwable[] throwables = exception.getSuppressed();

			Assert.assertEquals(
				Arrays.toString(throwables), 1, throwables.length);

			Assert.assertSame(_exceptions.get(0), throwables[0]);
		}
	}

	@Test
	public void testAccept2() throws IOException {

		// Expecting RuntimeException, IOException stops the chain

		try {
			UnsafeConsumer.accept(
				_exceptions,
				exception -> {
					throw exception;
				},
				RuntimeException.class);
		}
		catch (Exception exception) {
			Assert.assertSame(_exceptions.get(0), exception);

			Throwable[] throwables = exception.getSuppressed();

			Assert.assertEquals(
				Arrays.toString(throwables), 0, throwables.length);
		}
	}

	@Test
	public void testAccept3() throws IOException {

		// Expecting Exception, full loop, IOException suppresses
		// RuntimeException and Exception

		try {
			UnsafeConsumer.accept(
				_exceptions,
				exception -> {
					throw exception;
				},
				Exception.class);
		}
		catch (Exception exception) {
			Assert.assertSame(_exceptions.get(0), exception);

			Throwable[] throwables = exception.getSuppressed();

			Assert.assertEquals(
				Arrays.toString(throwables), 2, throwables.length);
			Assert.assertSame(_exceptions.get(1), throwables[0]);
			Assert.assertSame(_exceptions.get(2), throwables[1]);
		}
	}

	@Test
	public void testAccept4() throws IOException {

		// Expecting Throwable, full loop, IOException suppresses
		// RuntimeException and Exception

		try {
			UnsafeConsumer.accept(
				_exceptions,
				exception -> {
					throw exception;
				});
		}
		catch (Throwable throwable) {
			Assert.assertSame(_exceptions.get(0), throwable);

			Throwable[] throwables = throwable.getSuppressed();

			Assert.assertEquals(
				Arrays.toString(throwables), 2, throwables.length);
			Assert.assertSame(_exceptions.get(1), throwables[0]);
			Assert.assertSame(_exceptions.get(2), throwables[1]);
		}
	}

	private final List<Exception> _exceptions = Arrays.asList(
		new IOException(), new RuntimeException(), new Exception());

}