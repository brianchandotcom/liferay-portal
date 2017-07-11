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
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessChannel;
import com.liferay.portal.kernel.process.ProcessConfig;
import com.liferay.portal.kernel.process.ProcessConfig.Builder;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.process.local.LocalProcessExecutor;
import com.liferay.portal.kernel.process.local.LocalProcessLauncher.ProcessContext;
import com.liferay.portal.kernel.process.local.LocalProcessLauncher.ShutdownHook;
import com.liferay.portal.kernel.test.rule.BaseTestRule.StatementWrapper;
import com.liferay.portal.kernel.test.rule.NewEnv.Environment;
import com.liferay.portal.kernel.test.rule.NewEnv.JVMArgsLine;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.net.MalformedURLException;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * @author Shuyang Zhou
 */
public class NewEnvTestRule implements TestRule {

	public static final NewEnvTestRule INSTANCE = new NewEnvTestRule();

	@Override
	public Statement apply(Statement statement, Description description) {
		String methodName = description.getMethodName();

		if (methodName == null) {
			return statement;
		}

		NewEnv newEnv = findNewEnv(description);

		if ((newEnv == null) || (newEnv.type() == NewEnv.Type.NONE)) {
			return statement;
		}

		if (NewEnv.Type.CLASSLOADER == newEnv.type()) {
			return new RunInNewClassLoaderStatement(statement, description);
		}

		Builder builder = new Builder();

		builder.setArguments(createArguments(description));
		builder.setBootstrapClassPath(CLASS_PATH);
		builder.setRuntimeClassPath(CLASS_PATH);

		setEnvironment(builder, description);

		return new RunInNewJVMStatment(builder.build(), statement, description);
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

		List<MethodKey> methodKeys = new ArrayList<>(frameworkMethods.size());

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

	protected NewEnvTestRule() {
	}

	protected List<String> createArguments(Description description) {
		List<String> arguments = new ArrayList<>();

		Class<?> testClass = description.getTestClass();

		JVMArgsLine jvmArgsLine = testClass.getAnnotation(JVMArgsLine.class);

		if (jvmArgsLine != null) {
			arguments.addAll(processJVMArgsLine(jvmArgsLine));
		}

		jvmArgsLine = description.getAnnotation(JVMArgsLine.class);

		if (jvmArgsLine != null) {
			arguments.addAll(processJVMArgsLine(jvmArgsLine));
		}

		arguments.add("-Djava.net.preferIPv4Stack=true");

		if (Boolean.getBoolean("jvm.debug")) {
			arguments.add(_JPDA_OPTIONS);
			arguments.add("-Djvm.debug=true");
		}

		arguments.add("-Dliferay.mode=test");
		arguments.add("-Dsun.zip.disableMemoryMapping=true");

		String whipAgentLine = System.getProperty("whip.agent");

		if (Validator.isNotNull(whipAgentLine)) {
			arguments.add(whipAgentLine);
			arguments.add("-Dwhip.agent=" + whipAgentLine);
		}

		String fileName = System.getProperty("whip.datafile");

		if (fileName != null) {
			arguments.add("-Dwhip.datafile=" + fileName);
		}

		if (Boolean.getBoolean("whip.instrument.dump")) {
			arguments.add("-Dwhip.instrument.dump=true");
		}

		if (Boolean.getBoolean("whip.static.instrument")) {
			arguments.add("-Dwhip.static.instrument=true");
		}

		return arguments;
	}

	protected ClassLoader createClassLoader(Description description) {
		try {
			return new URLClassLoader(
				ClassPathUtil.getClassPathURLs(CLASS_PATH), null);
		}
		catch (MalformedURLException murle) {
			throw new RuntimeException(murle);
		}
	}

	protected NewEnv findNewEnv(Description description) {
		NewEnv newEnv = description.getAnnotation(NewEnv.class);

		if (newEnv == null) {
			Class<?> testClass = description.getTestClass();

			newEnv = testClass.getAnnotation(NewEnv.class);
		}

		return newEnv;
	}

	protected Map<String, String> processEnvironmentVariables(
		String[] variables) {

		Map<String, String> environmentMap = new HashMap<>();

		for (String variable : variables) {
			String resolvedVariable = resolveSystemProperty(variable);

			String[] parts = StringUtil.split(resolvedVariable, CharPool.EQUAL);

			if (parts.length != 2) {
				throw new IllegalArgumentException(
					"Wrong environment variable " + variable + " resolved as " +
						resolvedVariable + ". Need to be \"key=value\" format");
			}

			environmentMap.put(parts[0], parts[1]);
		}

		return environmentMap;
	}

	protected List<String> processJVMArgsLine(JVMArgsLine jvmArgsLine) {
		String[] jvmArgs = StringUtil.split(
			jvmArgsLine.value(), StringPool.SPACE);

		List<String> jvmArgsList = new ArrayList<>(jvmArgs.length);

		for (String jvmArg : jvmArgs) {
			jvmArgsList.add(resolveSystemProperty(jvmArg));
		}

		return jvmArgsList;
	}

	protected ProcessCallable<Serializable> processProcessCallable(
		ProcessCallable<Serializable> processCallable,
		MethodKey testMethodKey) {

		return processCallable;
	}

	protected String resolveSystemProperty(String value) {
		Matcher matcher = _systemPropertyReplacePattern.matcher(value);

		StringBuffer sb = new StringBuffer();

		while (matcher.find()) {
			String key = matcher.group(1);

			matcher.appendReplacement(
				sb, GetterUtil.getString(System.getProperty(key)));
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	protected void setEnvironment(Builder builder, Description description) {
		Map<String, String> environmentMap = new HashMap<>(System.getenv());

		Class<?> testClass = description.getTestClass();

		Environment environment = testClass.getAnnotation(Environment.class);

		if (environment != null) {
			Map<String, String> map = processEnvironmentVariables(
				environment.variables());

			if (environment.append()) {
				environmentMap.putAll(map);
			}
			else {
				environmentMap = map;
			}
		}

		environment = description.getAnnotation(Environment.class);

		if (environment != null) {
			Map<String, String> map = processEnvironmentVariables(
				environment.variables());

			if (environment.append()) {
				environmentMap.putAll(map);
			}
			else {
				environmentMap = map;
			}
		}

		builder.setEnvironment(environmentMap);
	}

	protected static final String CLASS_PATH = ClassPathUtil.getJVMClassPath(
		true);

	private static final String _JPDA_OPTIONS =
		"-agentlib:jdwp=transport=dt_socket,address=8001,server=y,suspend=y";

	private static final ProcessExecutor _processExecutor =
		new LocalProcessExecutor();
	private static final Pattern _systemPropertyReplacePattern =
		Pattern.compile("\\$\\{(.*)\\}");

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

			System.setProperty(
				SystemProperties.SYSTEM_PROPERTIES_QUIET, StringPool.TRUE);

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

	private class RunInNewClassLoaderStatement extends StatementWrapper {

		public RunInNewClassLoaderStatement(
			Statement statement, Description description) {

			super(statement);

			Class<?> testClass = description.getTestClass();

			_afterMethodKeys = getMethodKeys(testClass, After.class);
			_beforeMethodKeys = getMethodKeys(testClass, Before.class);

			_newClassLoader = createClassLoader(description);
			_testClassName = testClass.getName();
			_testMethodKey = new MethodKey(
				testClass, description.getMethodName());
		}

		@Override
		public void evaluate() throws Throwable {
			MethodCache.reset();

			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader =
				currentThread.getContextClassLoader();

			currentThread.setContextClassLoader(_newClassLoader);

			String quiet = System.getProperty(
				SystemProperties.SYSTEM_PROPERTIES_QUIET);

			System.setProperty(
				SystemProperties.SYSTEM_PROPERTIES_QUIET, StringPool.TRUE);

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
				if (quiet == null) {
					System.clearProperty(
						SystemProperties.SYSTEM_PROPERTIES_QUIET);
				}
				else {
					System.setProperty(
						SystemProperties.SYSTEM_PROPERTIES_QUIET, quiet);
				}

				currentThread.setContextClassLoader(contextClassLoader);

				MethodCache.reset();
			}
		}

		private final List<MethodKey> _afterMethodKeys;
		private final List<MethodKey> _beforeMethodKeys;
		private final ClassLoader _newClassLoader;
		private final String _testClassName;
		private final MethodKey _testMethodKey;

	}

	private class RunInNewJVMStatment extends StatementWrapper {

		public RunInNewJVMStatment(
			ProcessConfig processConfig, Statement statement,
			Description description) {

			super(statement);

			_processConfig = processConfig;

			Class<?> testClass = description.getTestClass();

			_afterMethodKeys = getMethodKeys(testClass, After.class);
			_beforeMethodKeys = getMethodKeys(testClass, Before.class);
			_testClassName = testClass.getName();
			_testMethodKey = new MethodKey(
				testClass, description.getMethodName());
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