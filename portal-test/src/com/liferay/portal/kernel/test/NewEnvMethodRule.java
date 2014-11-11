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

package com.liferay.portal.kernel.test;

import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessChannel;
import com.liferay.portal.kernel.process.ProcessConfig;
import com.liferay.portal.kernel.process.ProcessConfig.Builder;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.process.local.LocalProcessExecutor;
import com.liferay.portal.kernel.process.local.LocalProcessLauncher.ProcessContext;
import com.liferay.portal.kernel.process.local.LocalProcessLauncher.ShutdownHook;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.net.MalformedURLException;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Before;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * @author Shuyang Zhou
 */
public class NewEnvMethodRule implements MethodRule {

	@Override
	public Statement apply(
		Statement statement, FrameworkMethod frameworkMethod, Object target) {

		Method method = frameworkMethod.getMethod();

		Class<?> targetClass = target.getClass();

		NewEnv newEnv = findNewEnv(method, targetClass);

		if ((newEnv == null) || (newEnv.type() == NewEnv.Type.NONE)) {
			return statement;
		}

		if (NewEnv.Type.CLASSLOADER == newEnv.type()) {
			return new RunInNewClassLoaderStatement(
				targetClass.getName(), getMethodKeys(targetClass, Before.class),
				method, getMethodKeys(targetClass, After.class));
		}

		Builder builder = new Builder();

		builder.setArguments(createArguments(method));
		builder.setBootstrapClassPath(CLASS_PATH);
		builder.setRuntimeClassPath(CLASS_PATH);

		return new RunInNewJVMStatment(
			builder.build(), targetClass.getName(),
			getMethodKeys(targetClass, Before.class), new MethodKey(method),
			getMethodKeys(targetClass, After.class));
	}

	protected static void attachProcess(String message) {
		if (Boolean.getBoolean("attached")) {
			return;
		}

		ProcessContext.attach(
			message, 1000,
			new ShutdownHook() {

				@Override
				public boolean shutdown(
					int shutdownCode, Throwable shutdownThrowable) {

					System.exit(shutdownCode);

					return true;
				}

			});

		System.setProperty("attached", StringPool.TRUE);
	}

	protected static List<MethodKey> getMethodKeys(
		Class<?> targetClass, Class<? extends Annotation> annotationClass) {

		TestClass testClass = new TestClass(targetClass);

		List<FrameworkMethod> frameworkMethods = testClass.getAnnotatedMethods(
			annotationClass);

		List<MethodKey> methodKeys = new ArrayList<MethodKey>(
			frameworkMethods.size());

		for (FrameworkMethod annotatedFrameworkMethod : frameworkMethods) {
			methodKeys.add(new MethodKey(annotatedFrameworkMethod.getMethod()));
		}

		return methodKeys;
	}

	protected static void invoke(
			ClassLoader classLoader, MethodKey methodKey, Object object)
		throws Exception {

		methodKey = methodKey.transform(classLoader);

		Method method = methodKey.getMethod();

		method.invoke(object);
	}

	protected List<String> createArguments(Method method) {
		List<String> arguments = new ArrayList<String>();

		String agentLine = System.getProperty("junit.cobertura.agent");

		if (Validator.isNotNull(agentLine)) {
			arguments.add(agentLine);
			arguments.add("-Djunit.cobertura.agent=" + agentLine);
		}

		boolean coberturaParentDynamicallyInstrumented = Boolean.getBoolean(
			"cobertura.parent.dynamically.instrumented");

		if (coberturaParentDynamicallyInstrumented) {
			arguments.add("-Dcobertura.parent.dynamically.instrumented=true");
		}

		boolean junitCodeCoverage = Boolean.getBoolean("junit.code.coverage");

		if (junitCodeCoverage) {
			arguments.add("-Djunit.code.coverage=true");
		}

		boolean junitCodeCoverageDump = Boolean.getBoolean(
			"junit.code.coverage.dump");

		if (junitCodeCoverageDump) {
			arguments.add("-Djunit.code.coverage.dump=true");
		}

		boolean junitDebug = Boolean.getBoolean("junit.debug");

		if (junitDebug) {
			arguments.add(_JPDA_OPTIONS);
			arguments.add("-Djunit.debug=true");
		}

		arguments.add("-Djava.net.preferIPv4Stack=true");

		String fileName = System.getProperty(
			"net.sourceforge.cobertura.datafile");

		if (fileName != null) {
			arguments.add("-Dnet.sourceforge.cobertura.datafile=" + fileName);
		}

		return arguments;
	}

	protected ClassLoader createClassLoader(Method method) {
		try {
			return new URLClassLoader(
				ClassPathUtil.getClassPathURLs(CLASS_PATH), null);
		}
		catch (MalformedURLException murle) {
			throw new RuntimeException(murle);
		}
	}

	protected NewEnv findNewEnv(Method method, Class<?> clazz) {
		NewEnv newEnv = method.getAnnotation(NewEnv.class);

		if (newEnv == null) {
			newEnv = clazz.getAnnotation(NewEnv.class);
		}

		return newEnv;
	}

	protected ProcessCallable<Serializable> processProcessCallable(
		ProcessCallable<Serializable> processCallable,
		MethodKey testMethodKey) {

		return processCallable;
	}

	protected static final String CLASS_PATH = ClassPathUtil.getJVMClassPath(
		true);

	private static final String _JPDA_OPTIONS =
		"-agentlib:jdwp=transport=dt_socket,address=8001,server=y,suspend=y";

	private static final ProcessExecutor _processExecutor =
		new LocalProcessExecutor();

	static {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		PortalClassLoaderUtil.setClassLoader(contextClassLoader);
	}

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

		@Override
		public Serializable call() throws ProcessException {
			attachProcess("Attached " + toString());

			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader =
				currentThread.getContextClassLoader();

			try {
				Class<?> clazz = contextClassLoader.loadClass(_testClassName);

				Object object = clazz.newInstance();

				for (MethodKey beforeMethodKey : _beforeMethodKeys) {
					invoke(contextClassLoader, beforeMethodKey, object);
				}

				invoke(contextClassLoader, _testMethodKey, object);

				for (MethodKey afterMethodKey : _afterMethodKeys) {
					invoke(contextClassLoader, afterMethodKey, object);
				}
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return StringPool.BLANK;
		}

		@Override
		public String toString() {
			StringBundler sb = new StringBundler(4);

			sb.append(_testClassName);
			sb.append(StringPool.PERIOD);
			sb.append(_testMethodKey.getMethodName());
			sb.append("()");

			return sb.toString();
		}

		private static final long serialVersionUID = 1L;

		private final List<MethodKey> _afterMethodKeys;
		private final List<MethodKey> _beforeMethodKeys;
		private final String _testClassName;
		private final MethodKey _testMethodKey;

	}

	private class RunInNewClassLoaderStatement extends Statement {

		public RunInNewClassLoaderStatement(
			String testClassName, List<MethodKey> beforeMethodKeys,
			Method testMethod, List<MethodKey> afterMethodKeys) {

			_testClassName = testClassName;
			_beforeMethodKeys = beforeMethodKeys;
			_testMethodKey = new MethodKey(testMethod);
			_afterMethodKeys = afterMethodKeys;

			_newClassLoader = createClassLoader(testMethod);
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

				Object object = clazz.newInstance();

				for (MethodKey beforeMethodKey : _beforeMethodKeys) {
					invoke(_newClassLoader, beforeMethodKey, object);
				}

				invoke(_newClassLoader, _testMethodKey, object);

				for (MethodKey afterMethodKey : _afterMethodKeys) {
					invoke(_newClassLoader, afterMethodKey, object);
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

	private class RunInNewJVMStatment extends Statement {

		public RunInNewJVMStatment(
			ProcessConfig processConfig, String testClassName,
			List<MethodKey> beforeMethodKeys, MethodKey testMethodKey,
			List<MethodKey> afterMethodKeys) {

			_processConfig = processConfig;
			_testClassName = testClassName;
			_beforeMethodKeys = beforeMethodKeys;
			_testMethodKey = testMethodKey;
			_afterMethodKeys = afterMethodKeys;
		}

		@Override
		public void evaluate() throws Throwable {
			ProcessCallable<Serializable> processCallable =
				new TestProcessCallable(
					_testClassName, _beforeMethodKeys, _testMethodKey,
					_afterMethodKeys);

			processCallable = processProcessCallable(
				processCallable, _testMethodKey);

			ProcessChannel<Serializable> processChannel =
				_processExecutor.execute(_processConfig, processCallable);

			Future<Serializable> future =
				processChannel.getProcessNoticeableFuture();

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
		private final List<MethodKey> _beforeMethodKeys;
		private final ProcessConfig _processConfig;
		private final String _testClassName;
		private final MethodKey _testMethodKey;

	}

}