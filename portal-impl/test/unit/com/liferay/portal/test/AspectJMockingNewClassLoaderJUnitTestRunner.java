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

package com.liferay.portal.test;

import com.liferay.portal.aspectj.WeavingClassLoader;
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.test.NewClassLoaderJUnitTestRunner;
import com.liferay.portal.kernel.util.StringPool;

import java.io.File;

import java.lang.reflect.Method;

import java.net.MalformedURLException;
import java.net.URL;

import org.aspectj.lang.annotation.Aspect;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

/**
 * @author Shuyang Zhou
 */
public class AspectJMockingNewClassLoaderJUnitTestRunner
	extends NewClassLoaderJUnitTestRunner {

	public AspectJMockingNewClassLoaderJUnitTestRunner(Class<?> clazz)
		throws InitializationError {

		super(clazz);
	}

	@Override
	protected ClassLoader createClassLoader(FrameworkMethod frameworkMethod) {
		AdviseWith adviseWith = frameworkMethod.getAnnotation(AdviseWith.class);

		if (adviseWith == null) {
			return super.createClassLoader(frameworkMethod);
		}

		Class<?>[] adviceClasses = adviseWith.adviceClasses();

		if ((adviceClasses == null) || (adviceClasses.length == 0)) {
			return super.createClassLoader(frameworkMethod);
		}

		for (Class<?> adviceClass : adviceClasses) {
			Aspect aspect = adviceClass.getAnnotation(Aspect.class);

			if (aspect == null) {
				throw new IllegalArgumentException(
					"Class " + adviceClass.getName() + " is not an aspect");
			}
		}

		String jvmClassPath = ClassPathUtil.getJVMClassPath(true);

		URL[] urls = null;

		try {
			urls = ClassPathUtil.getClassPathURLs(jvmClassPath);
		}
		catch (MalformedURLException murle) {
			throw new RuntimeException(murle);
		}

		String dumpDirName = System.getProperty("junit.aspectj.dump");

		Method method = frameworkMethod.getMethod();

		Class<?> clazz = method.getDeclaringClass();

		String className = clazz.getName();

		File dumpDir = new File(
			dumpDirName,
			className.concat(StringPool.PERIOD).concat(method.getName()));

		return new WeavingClassLoader(urls, adviceClasses, dumpDir);
	}

}