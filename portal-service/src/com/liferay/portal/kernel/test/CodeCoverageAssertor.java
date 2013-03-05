/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.test;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * All assert classes are required to be added after tests finish to avoid eager
 * class loading.<p>
 *
 * The main class is inferred by the test class name, so make sure you always
 * follow the unit test naming pattern. For dependent classes, extend
 * CodeCoverageAssertor to overwrite
 * {@link #appendAssertClasses(java.util.List)} to append Class literals. As
 * Class literals are lazily loaded, it won't trigger an eager loading.
 *
 * @author Shuyang Zhou
 */
public class CodeCoverageAssertor implements TestRule {

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

	public Statement apply(
		final Statement statement, final Description description) {

		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				_dynamicInstrumentMethod.invoke(null, _includes, _excludes);

				try {
					statement.evaluate();
				}
				finally {
					List<Class<?>> assertClassList = new ArrayList<Class<?>>();

					String className = description.getClassName();

					if (className.endsWith("Test")) {
						className = className.substring(
							0, className.length() - 4);

						ClassLoader classLoader = getClass().getClassLoader();

						Class<?> clazz = classLoader.loadClass(className);

						assertClassList.add(clazz);
					}

					appendAssertClasses(assertClassList);

					_assertCoverageMethod.invoke(
						null, _includeInnerClasses,
						assertClassList.toArray(
							new Class<?>[assertClassList.size()]));
				}
			}
		};
	}

	private static final Method _assertCoverageMethod;
	private static final Method _dynamicInstrumentMethod;

	private final String[] _excludes;
	private final boolean _includeInnerClasses;
	private final String[] _includes;

	static {
		ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

		try {
			Class<?> instrumentationAgentClass = systemClassLoader.loadClass(
				"net.sourceforge.cobertura.instrument.InstrumentationAgent");

			_assertCoverageMethod = instrumentationAgentClass.getMethod(
				"assertCoverage", boolean.class, Class[].class);

			_dynamicInstrumentMethod = instrumentationAgentClass.getMethod(
				"dynamicInstrument", String[].class, String[].class);
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}

	}

}