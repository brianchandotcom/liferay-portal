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

package com.liferay.petra.function;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsafeConsumerTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(UnsafeFunction.class);
				assertClasses.add(UnsafeRunnable.class);
				assertClasses.add(UnsafeSupplier.class);
			}

		};

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
		catch (Exception e) {
			Assert.assertSame(_exceptions.get(1), e);

			Throwable[] throwables = e.getSuppressed();

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
		catch (Exception e) {
			Assert.assertSame(_exceptions.get(0), e);

			Throwable[] throwables = e.getSuppressed();

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
		catch (Exception e) {
			Assert.assertSame(_exceptions.get(0), e);

			Throwable[] throwables = e.getSuppressed();

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
		catch (Throwable t) {
			Assert.assertSame(_exceptions.get(0), t);

			Throwable[] throwables = t.getSuppressed();

			Assert.assertEquals(
				Arrays.toString(throwables), 2, throwables.length);
			Assert.assertSame(_exceptions.get(1), throwables[0]);
			Assert.assertSame(_exceptions.get(2), throwables[1]);
		}
	}

	private final List<Exception> _exceptions = Arrays.asList(
		new IOException(), new RuntimeException(), new Exception());

}