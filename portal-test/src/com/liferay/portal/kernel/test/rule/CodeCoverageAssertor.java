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

package com.liferay.portal.kernel.test.rule;

import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Shuyang Zhou
 */
public class CodeCoverageAssertor implements TestRule {

	public static final CodeCoverageAssertor INSTANCE =
		new CodeCoverageAssertor();

	public CodeCoverageAssertor() {
		this(null, null, true);
	}

	public CodeCoverageAssertor(
		String[] includes, String[] excludes, boolean includeInnerClasses) {

		_includes = includes;
		_excludes = excludes;
		_includeInnerClasses = includeInnerClasses;
	}

	public void appendAssertClasses(List<Class<?>> assertClasses) {
	}

	@Override
	public Statement apply(
		final Statement statement, final Description description) {

		if (description.getMethodName() != null) {
			return statement;
		}

		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				String className = beforeClass(description);

				try {
					statement.evaluate();
				}
				finally {
					afterClass(description, className);
				}
			}

		};
	}

	protected void afterClass(Description description, String className)
		throws Exception {

		List<Class<?>> assertClasses = new ArrayList<>();

		ClassLoader classLoader = getClassLoader();

		Class<?> clazz = classLoader.loadClass(className);

		assertClasses.add(clazz);

		appendAssertClasses(assertClasses);

		_purgeSyntheticClasses(assertClasses);

		_ASSERT_COVERAGE_METHOD.invoke(
			null, _includeInnerClasses,
			assertClasses.toArray(new Class<?>[assertClasses.size()]));
	}

	protected String beforeClass(Description description) throws Exception {
		String className = description.getClassName();

		if (className.endsWith("Test")) {
			className = className.substring(0, className.length() - 4);
		}

		String[] includes = _includes;

		if (includes == null) {
			includes = _generateIncludes(className);
		}

		_DYNAMICALLY_INSTRUMENT_METHOD.invoke(null, includes, _excludes);

		return className;
	}

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	private static void _purgeSyntheticClasses(List<Class<?>> assertClasses) {
		Iterator<Class<?>> iterator = assertClasses.iterator();

		while (iterator.hasNext()) {
			Class<?> assertClass = iterator.next();

			if (assertClass.isSynthetic()) {
				iterator.remove();
			}
		}
	}

	private String[] _generateIncludes(String mainClassName) throws Exception {
		List<Class<?>> assertClasses = new ArrayList<>();

		String jvmClassPath = ClassPathUtil.getJVMClassPath(false);

		URL[] urls = ClassPathUtil.getClassPathURLs(jvmClassPath);

		ClassLoader classLoader = new URLClassLoader(urls, null);

		Class<?> mainClass = classLoader.loadClass(mainClassName);

		assertClasses.add(mainClass);

		if (_includeInnerClasses) {
			assertClasses.addAll(Arrays.asList(mainClass.getDeclaredClasses()));
		}

		if (getClass() != CodeCoverageAssertor.class) {
			Class<?> reloadedClass = classLoader.loadClass(
				getClass().getName());

			Method appendAssertClassesMethod = reloadedClass.getMethod(
				"appendAssertClasses", List.class);

			appendAssertClassesMethod.setAccessible(true);

			Constructor<?> constructor = reloadedClass.getDeclaredConstructor();

			constructor.setAccessible(true);

			Object reloadedObject = constructor.newInstance();

			appendAssertClassesMethod.invoke(reloadedObject, assertClasses);
		}

		_purgeSyntheticClasses(assertClasses);

		String[] includes = new String[assertClasses.size()];

		for (int i = 0; i < assertClasses.size(); i++) {
			Class<?> assertClass = assertClasses.get(i);

			includes[i] = StringUtil.replace(
				assertClass.getName(), new String[] {".", "$"},
				new String[] {"/", "\\$"});
		}

		return includes;
	}

	private static final Method _ASSERT_COVERAGE_METHOD;

	private static final Method _DYNAMICALLY_INSTRUMENT_METHOD;

	static {
		ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

		try {
			Class<?> instrumentationAgentClass = systemClassLoader.loadClass(
				"com.liferay.whip.agent.InstrumentationAgent");

			_ASSERT_COVERAGE_METHOD = instrumentationAgentClass.getMethod(
				"assertCoverage", boolean.class, Class[].class);
			_DYNAMICALLY_INSTRUMENT_METHOD =
				instrumentationAgentClass.getMethod(
					"dynamicallyInstrument", String[].class, String[].class);
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private final String[] _excludes;
	private final boolean _includeInnerClasses;
	private final String[] _includes;

}