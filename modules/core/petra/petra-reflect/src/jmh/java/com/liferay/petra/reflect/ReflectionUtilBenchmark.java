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
	public Field testFetchDeclaredFieldExist() throws Exception {
		return ReflectionUtil.fetchDeclaredField(TestClass.class, "_s");
	}

	@Benchmark
	public Field testFetchDeclaredFieldExistAccessibleFalse() throws Exception {
		return ReflectionUtil.fetchDeclaredField(false, TestClass.class, "_s");
	}

	@Benchmark
	public Field testFetchDeclaredFieldNotExist() throws Exception {
		return ReflectionUtil.fetchDeclaredField(TestClass.class, "_sx");
	}

	@Benchmark
	public Method testFetchDeclaredMethodExist() throws Exception {
		return ReflectionUtil.fetchDeclaredMethod(
			TestClass.class, "_method", String.class);
	}

	@Benchmark
	public Method testFetchDeclaredMethodExistAccessibleFalse()
		throws Exception {

		return ReflectionUtil.fetchDeclaredMethod(
			false, TestClass.class, "_method", String.class);
	}

	@Benchmark
	public Method testFetchDeclaredMethodNotExist() {
		return ReflectionUtil.fetchDeclaredMethod(
			TestClass.class, "_methodX", String.class);
	}

	@Benchmark
	public Field testFetchFieldExist() throws Exception {
		return ReflectionUtil.fetchField(TestClass.class, "i");
	}

	@Benchmark
	public Field testFetchFieldExistAccessibleFalse() throws Exception {
		return ReflectionUtil.fetchField(false, TestClass.class, "i");
	}

	@Benchmark
	public Field testFetchFieldNotExist() {
		return ReflectionUtil.fetchField(TestClass.class, "ix");
	}

	@Benchmark
	public Method testFetchMethodExist() throws Exception {
		return ReflectionUtil.fetchMethod(TestClass.class, "method", int.class);
	}

	@Benchmark
	public Method testFetchMethodExistAccessibleFalse() throws Exception {
		return ReflectionUtil.fetchMethod(
			false, TestClass.class, "method", int.class);
	}

	@Benchmark
	public Method testFetchMethodNotExist() {
		return ReflectionUtil.fetchMethod(
			TestClass.class, "methodX", int.class);
	}

	@Benchmark
	public Field testGetDeclaredFieldExist() throws Exception {
		Field field = TestClass.class.getDeclaredField("_s");

		field.setAccessible(true);

		return field;
	}

	@Benchmark
	public Field testGetDeclaredFieldExistAccessibleFalse() throws Exception {
		return TestClass.class.getDeclaredField("_s");
	}

	@Benchmark
	public Field testGetDeclaredFieldNotExist() throws Exception {
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
	public Method testGetDeclaredMethodExist() throws Exception {
		return ReflectionUtil.getDeclaredMethod(
			TestClass.class, "_method", String.class);
	}

	@Benchmark
	public Method testGetDeclaredMethodExistAccessibleFalse() throws Exception {
		return ReflectionUtil.getDeclaredMethod(
			false, TestClass.class, "_method", String.class);
	}

	@Benchmark
	public Method testGetDeclaredMethodNotExist() {
		try {
			return ReflectionUtil.getDeclaredMethod(
				TestClass.class, "_methodX", String.class);
		}
		catch (Exception exception) {
			return null;
		}
	}

	@Benchmark
	public Field testGetFieldExist() throws Exception {
		Field field = TestClass.class.getField("i");

		field.setAccessible(true);

		return field;
	}

	@Benchmark
	public Field testGetFieldExistAccessibleFalse() throws Exception {
		return TestClass.class.getField("i");
	}

	@Benchmark
	public Field testGetFieldNotExist() {
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
	public Method testGetMethodExist() throws Exception {
		Method method = TestClass.class.getMethod("method", int.class);

		method.setAccessible(true);

		return method;
	}

	@Benchmark
	public Method testGetMethodExistAccessibleFalse() throws Exception {
		return TestClass.class.getMethod("method", int.class);
	}

	@Benchmark
	public Method testGetMethodNotExist() {
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