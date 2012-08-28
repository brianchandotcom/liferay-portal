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

import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import java.lang.reflect.InvocationTargetException;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * @author Shuyang Zhou
 */
public class NewClassLoaderJUnitTestRunner extends BlockJUnit4ClassRunner {

	public NewClassLoaderJUnitTestRunner(Class<?> clazz)
		throws InitializationError {

		super(clazz);
	}

	protected ClassLoader createClassLoader(FrameworkMethod frameworkMethod) {
		String jvmClassPath = ClassPathUtil.getJVMClassPath(true);

		URL[] urls = null;

		try {
			urls = ClassPathUtil.getClassPathURLs(jvmClassPath);
		}
		catch (MalformedURLException murle) {
			throw new RuntimeException(murle);
		}

		return new URLClassLoader(urls, null);
	}

	@Override
	protected Statement methodBlock(FrameworkMethod frameworkMethod) {
		TestClass testClass = getTestClass();

		List<FrameworkMethod> beforeFrameworkMethods =
			testClass.getAnnotatedMethods(Before.class);

		List<FrameworkMethod> afterFrameworkMethods =
			testClass.getAnnotatedMethods(After.class);

		Class<?> clazz = testClass.getJavaClass();

		return new RunInNewClassLoaderStatement(
			clazz, beforeFrameworkMethods, frameworkMethod,
			afterFrameworkMethods);
	}

	private class RunInNewClassLoaderStatement extends Statement {

		public RunInNewClassLoaderStatement(
			Class<?> testClass, List<FrameworkMethod> beforeFrameworkMethods,
			FrameworkMethod testFrameworkMethod,
			List<FrameworkMethod> afterFrameworkMethods) {

			_testClassName = testClass.getName();

			_beforeMethodKeys = new ArrayList<MethodKey>(
				beforeFrameworkMethods.size());

			for (FrameworkMethod frameworkMethod : beforeFrameworkMethods) {
				_beforeMethodKeys.add(
					new MethodKey(frameworkMethod.getMethod()));
			}

			_testMethodKey = new MethodKey(testFrameworkMethod.getMethod());

			_afterMethodKeys = new ArrayList<MethodKey>(
				afterFrameworkMethods.size());

			for (FrameworkMethod frameworkMethod : afterFrameworkMethods) {
				_afterMethodKeys.add(
					new MethodKey(frameworkMethod.getMethod()));
			}

			_newClassLoader = createClassLoader(testFrameworkMethod);
		}

		@Override
		public void evaluate() throws Throwable {
			MethodCache.reset();

			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader =
				currentThread.getContextClassLoader();

			currentThread.setContextClassLoader(_newClassLoader);

			try {
				Class<?> clazz = _newClassLoader.loadClass(_testClassName);

				Object testObject = clazz.newInstance();

				for (MethodKey beforeMethodKey : _beforeMethodKeys) {
					new MethodHandler(beforeMethodKey).invoke(testObject);
				}

				new MethodHandler(_testMethodKey).invoke(testObject);

				for (MethodKey afterMethodKey : _afterMethodKeys) {
					new MethodHandler(afterMethodKey).invoke(testObject);
				}
			}
			catch (InvocationTargetException ite) {
				throw ite.getTargetException();
			}
			finally {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}

		private final List<MethodKey> _afterMethodKeys;
		private final List<MethodKey> _beforeMethodKeys;
		private final ClassLoader _newClassLoader;
		private final String _testClassName;
		private final MethodKey _testMethodKey;

	}

}