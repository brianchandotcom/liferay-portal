/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

/**
 * @author Shuyang Zhou
 */
@BenchmarkMode(Mode.AverageTime)
@Fork(
	jvmArgsAppend = {
		"--add-opens=java.base/java.lang=ALL-UNNAMED",
		"--add-opens=java.base/java.lang.invoke=ALL-UNNAMED"
	},
	value = 1
)
@Measurement(iterations = 2)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 1)
public class ReflectionUtilBenchmark {

	@Benchmark
	public Field declaredFieldExistFetch() throws Exception {
		return ReflectionUtil.fetchDeclaredField(TestClass.class, "_s");
	}

	@Benchmark
	public Field declaredFieldExistGet() throws Exception {
		Field field = TestClass.class.getDeclaredField("_s");

		field.setAccessible(true);

		return field;
	}

	@Benchmark
	public Field declaredFieldNotExistFetch() throws Exception {
		return ReflectionUtil.fetchDeclaredField(TestClass.class, "_sx");
	}

	@Benchmark
	public Field declaredFieldNotExistGet() throws Exception {
		try {
			Field field = TestClass.class.getDeclaredField("_sx");

			field.setAccessible(true);

			return field;
		}
		catch (Exception exception) {
			return null;
		}
	}

	@Benchmark
	public Method declaredMethodExistFetch() throws Exception {
		return ReflectionUtil.fetchDeclaredMethod(
			TestClass.class, "_method", String.class);
	}

	@Benchmark
	public Method declaredMethodExistGet() throws Exception {
		return ReflectionUtil.getDeclaredMethod(
			TestClass.class, "_method", String.class);
	}

	@Benchmark
	public Method declaredMethodNotExistFetch() {
		return ReflectionUtil.fetchDeclaredMethod(
			TestClass.class, "_methodX", String.class);
	}

	@Benchmark
	public Method declaredMethodNotExistGet() {
		try {
			return ReflectionUtil.getDeclaredMethod(
				TestClass.class, "_methodX", String.class);
		}
		catch (Exception exception) {
			return null;
		}
	}

	@Benchmark
	public Field publicFieldExistFetch() throws Exception {
		return ReflectionUtil.fetchField(TestClass.class, "i");
	}

	@Benchmark
	public Field publicFieldExistGet() throws Exception {
		Field field = TestClass.class.getField("i");

		field.setAccessible(true);

		return field;
	}

	@Benchmark
	public Field publicFieldNotExistFetch() {
		return ReflectionUtil.fetchField(TestClass.class, "ix");
	}

	@Benchmark
	public Field publicFieldNotExistGet() {
		try {
			Field field = TestClass.class.getField("ix");

			field.setAccessible(true);

			return field;
		}
		catch (Exception exception) {
			return null;
		}
	}

	@Benchmark
	public Method publicMethodExistFetch() throws Exception {
		return ReflectionUtil.fetchMethod(TestClass.class, "method", int.class);
	}

	@Benchmark
	public Method publicMethodExistGet() throws Exception {
		Method method = TestClass.class.getMethod("method", int.class);

		method.setAccessible(true);

		return method;
	}

	@Benchmark
	public Method publicMethodNotExistFetch() {
		return ReflectionUtil.fetchMethod(
			TestClass.class, "methodX", int.class);
	}

	@Benchmark
	public Method publicMethodNotExistGet() {
		try {
			Method method = TestClass.class.getMethod("methodX", int.class);

			method.setAccessible(true);

			return method;
		}
		catch (Exception exception) {
			return null;
		}
	}

	public class TestClass {

		public String method(int i) {
			return null;
		}

		public int i;

		private int _method(String s) {
			return s.length();
		}

		private String _s;

	}

}