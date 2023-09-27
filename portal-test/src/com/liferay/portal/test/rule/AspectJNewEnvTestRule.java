/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.test.rule;

import com.liferay.petra.io.Deserializer;
import com.liferay.petra.io.Serializer;
import com.liferay.petra.process.ClassPathUtil;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.aspects.WeavingClassLoader;

import java.io.File;
import java.io.Serializable;

import java.net.MalformedURLException;

import java.nio.ByteBuffer;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Shuyang Zhou
 */
public class AspectJNewEnvTestRule extends NewEnvTestRule {

	public static final AspectJNewEnvTestRule INSTANCE =
		new AspectJNewEnvTestRule();

	@Override
	public Statement apply(Statement statement, Description description) {
		AdviseWith adviseWith = description.getAnnotation(AdviseWith.class);

		if (adviseWith == null) {
			return statement;
		}

		return super.apply(statement, description);
	}

	@Override
	protected List<String> createArguments(Description description) {
		List<String> arguments = super.createArguments(description);

		AdviseWith adviseWith = description.getAnnotation(AdviseWith.class);

		if (adviseWith == null) {
			return arguments;
		}

		Class<?>[] adviceClasses = adviseWith.adviceClasses();

		if (ArrayUtil.isEmpty(adviceClasses)) {
			return arguments;
		}

		StringBundler sb = new StringBundler((adviceClasses.length * 2) + 1);

		sb.append("-DaspectClasses=");

		for (Class<?> adviceClass : adviceClasses) {
			Aspect aspect = adviceClass.getAnnotation(Aspect.class);

			if (aspect == null) {
				throw new IllegalArgumentException(
					"Class " + adviceClass.getName() + " is not an aspect");
			}

			sb.append(adviceClass.getName());
			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		arguments.add(sb.toString());

		return arguments;
	}

	@Override
	protected ClassLoader createClassLoader(Description description) {
		AdviseWith adviseWith = description.getAnnotation(AdviseWith.class);

		if (adviseWith == null) {
			return super.createClassLoader(description);
		}

		Class<?>[] adviceClasses = adviseWith.adviceClasses();

		if (ArrayUtil.isEmpty(adviceClasses)) {
			return super.createClassLoader(description);
		}

		for (Class<?> adviceClass : adviceClasses) {
			Aspect aspect = adviceClass.getAnnotation(Aspect.class);

			if (aspect == null) {
				throw new IllegalArgumentException(
					"Class " + adviceClass.getName() + " is not an aspect");
			}
		}

		File dumpDir = new File(
			System.getProperty("junit.aspectj.dump"),
			StringBundler.concat(
				description.getClassName(), StringPool.PERIOD,
				description.getMethodName()));

		try {
			return new WeavingClassLoader(
				ClassPathUtil.getClassPathURLs(CLASS_PATH), adviceClasses,
				dumpDir);
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	@Override
	protected ProcessCallable<Serializable> processProcessCallable(
		ProcessCallable<Serializable> processCallable, MethodKey methodKey) {

		Class<?> clazz = methodKey.getDeclaringClass();

		String className = clazz.getName();

		File dumpDir = new File(
			System.getProperty("junit.aspectj.dump"),
			StringBundler.concat(
				className, StringPool.PERIOD, methodKey.getMethodName()));

		return new SwitchClassLoaderProcessCallable(processCallable, dumpDir);
	}

	private AspectJNewEnvTestRule() {
	}

	private static class SwitchClassLoaderProcessCallable
		implements ProcessCallable<Serializable> {

		public SwitchClassLoaderProcessCallable(
			ProcessCallable<Serializable> processCallable, File dumpDir) {

			_dumpDir = dumpDir;

			Serializer serializer = new Serializer();

			serializer.writeObject(processCallable);

			ByteBuffer byteBuffer = serializer.toByteBuffer();

			_encodedProcessCallable = byteBuffer.array();

			_toString = processCallable.toString();
		}

		@Override
		public Serializable call() throws ProcessException {
			attachProcess("Attached " + toString());

			String[] aspectClassNames = StringUtil.split(
				System.getProperty("aspectClasses"));

			Class<?>[] aspectClasses = new Class<?>[aspectClassNames.length];

			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader =
				currentThread.getContextClassLoader();

			try {
				for (int i = 0; i < aspectClassNames.length; i++) {
					aspectClasses[i] = contextClassLoader.loadClass(
						aspectClassNames[i]);
				}

				WeavingClassLoader weavingClassLoader = new WeavingClassLoader(
					ClassPathUtil.getClassPathURLs(
						ClassPathUtil.getJVMClassPath(true)),
					aspectClasses, _dumpDir);

				currentThread.setContextClassLoader(weavingClassLoader);

				Deserializer deserializer = new Deserializer(
					ByteBuffer.wrap(_encodedProcessCallable));

				return ReflectionTestUtil.invoke(
					(ProcessCallable)deserializer.readObject(), "call",
					new Class<?>[0]);
			}
			catch (Exception exception) {
				throw new ProcessException(exception);
			}
			finally {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}

		@Override
		public String toString() {
			return _toString;
		}

		private static final long serialVersionUID = 1L;

		private final File _dumpDir;
		private final byte[] _encodedProcessCallable;
		private final String _toString;

	}

}