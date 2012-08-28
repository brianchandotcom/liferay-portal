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
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
public class NewJVMJUnitTestRunner extends BlockJUnit4ClassRunner {

	public NewJVMJUnitTestRunner(Class<?> clazz) throws InitializationError {
		super(clazz);

		_classPath = ClassPathUtil.getJVMClassPath(false);
	}

	protected List<String> createArguments(FrameworkMethod frameworkMethod) {
		List<String> arguments = new ArrayList<String>();

		boolean junitDebug = Boolean.getBoolean("junit.debug");

		if (junitDebug) {
			arguments.add(_JPDA_OPTIONS);
		}

		return arguments;
	}

	@Override
	protected Statement methodBlock(FrameworkMethod frameworkMethod) {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		PortalClassLoaderUtil.setClassLoader(contextClassLoader);

		TestClass testClass = getTestClass();

		List<FrameworkMethod> beforeFrameworkMethods =
			testClass.getAnnotatedMethods(Before.class);

		List<FrameworkMethod> afterFrameworkMethods =
			testClass.getAnnotatedMethods(After.class);

		List<String> arguments = createArguments(frameworkMethod);

		Class<?> clazz = testClass.getJavaClass();

		return new RunInNewJVMStatment(
			_classPath, arguments, clazz, beforeFrameworkMethods,
			frameworkMethod, afterFrameworkMethods);
	}

	protected ProcessCallable<Serializable> processProcessCallable(
		ProcessCallable<Serializable> processCallable,
		MethodKey testMethodKey) {

		return processCallable;
	}

	private static final String _JPDA_OPTIONS =
		"-agentlib:jdwp=transport=dt_socket,address=8001,server=y,suspend=y";

	private final String _classPath;

	private static class TestProcessCallable
		implements ProcessCallable<Serializable> {

		public TestProcessCallable(
			String testClassName, List<MethodKey> beforeMethodKeys,
			MethodKey testMethodKey, List<MethodKey> afterMethodKeys) {

			_testClassName = testClassName;
			_beforeMethodKeys = beforeMethodKeys;
			_testMethodKey = testMethodKey;
			_afterMethodKeys = afterMethodKeys;
		}

		public Serializable call() throws ProcessException {
			ProcessExecutor.ProcessContext.attach(
				"Attached " + toString(), 1000,
				new ProcessExecutor.ShutdownHook() {

					public boolean shutdown(
						int shutdownCode, Throwable shutdownThrowable) {

						return true;
					}
				});

			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader =
				currentThread.getContextClassLoader();

			try {
				Class<?> clazz = contextClassLoader.loadClass(_testClassName);

				Object testObject = clazz.newInstance();

				for (MethodKey beforeMethodKey : _beforeMethodKeys) {
					new MethodHandler(beforeMethodKey).invoke(testObject);
				}

				new MethodHandler(_testMethodKey).invoke(testObject);

				for (MethodKey afterMethodKey : _afterMethodKeys) {
					new MethodHandler(afterMethodKey).invoke(testObject);
				}
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return StringPool.BLANK;
		}

		public String toString() {
			StringBundler sb = new StringBundler();

			sb.append(_testClassName);
			sb.append(StringPool.PERIOD);
			sb.append(_testMethodKey.getMethodName());
			sb.append("()");

			return sb.toString();
		}

		private final List<MethodKey> _afterMethodKeys;
		private final List<MethodKey> _beforeMethodKeys;
		private final String _testClassName;
		private final MethodKey _testMethodKey;

	}

	private class RunInNewJVMStatment extends Statement {

		public RunInNewJVMStatment(
			String classPath, List<String> arguments, Class<?> testClass,
			List<FrameworkMethod> beforeFrameworkMethods,
			FrameworkMethod testFrameworkMethod,
			List<FrameworkMethod> afterFrameworkMethods) {

			_classPath = classPath;
			_arguments = arguments;
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
		}

		@Override
		public void evaluate() throws Throwable {
			ProcessCallable processCallable = new TestProcessCallable(
				_testClassName, _beforeMethodKeys, _testMethodKey,
				_afterMethodKeys);

			processCallable = processProcessCallable(
				processCallable, _testMethodKey);

			Future<String> future = ProcessExecutor.execute(
				_classPath, _arguments, processCallable);

			try {
				future.get();
			}
			catch (ExecutionException ee) {
				Throwable cause = ee.getCause();

				while ((cause instanceof ProcessException) ||
					(cause instanceof InvocationTargetException)) {

					cause = cause.getCause();
				}

				throw cause;
			}
		}

		private final List<MethodKey> _afterMethodKeys;
		private final List<String> _arguments;
		private final List<MethodKey> _beforeMethodKeys;
		private final String _classPath;
		private final String _testClassName;
		private final MethodKey _testMethodKey;

	}

}