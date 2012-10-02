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

package com.liferay.portal.kernel.test.plugins;

import com.liferay.portal.kernel.deploy.hot.BaseHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runners.model.InitializationError;

/**
 * @author Manuel de la Peña
 */
public class PluginIntegrationTestHotDeployListener
	extends BaseHotDeployListener {

	public void invokeDeploy(HotDeployEvent hotDeployEvent)
		throws HotDeployException {

		try {
			doInvokeDeploy(hotDeployEvent);
		}
		catch (Throwable t) {
			throwHotDeployException(
				hotDeployEvent, "Error registering tests for ", t);
		}
	}

	public void invokeUndeploy(HotDeployEvent hotDeployEvent)
		throws HotDeployException {

		try {
			doInvokeUndeploy(hotDeployEvent);
		}
		catch (Throwable t) {
			throwHotDeployException(
				hotDeployEvent, "Error unregistering tests for ", t);
		}
	}

	protected void doInvokeDeploy(HotDeployEvent hotDeployEvent)
		throws Exception {

		List<Class<?>> testClasses = _getAllClassesInIntegrationJar(
			hotDeployEvent);

		_runTestClasses(testClasses);
	}

	protected void doInvokeUndeploy(HotDeployEvent hotDeployEvent)
		throws Exception {

		_log.debug("Undeploying tests for " + hotDeployEvent);
	}

	private List<Class<?>> _getAllClassesInIntegrationJar(
			HotDeployEvent hotDeployEvent)
		throws ClassNotFoundException, MalformedURLException {

		ClassLoader contextClassLoader = hotDeployEvent.getContextClassLoader();

		URL url = contextClassLoader.getResource("../lib");

		// this file name depends on build-common-plugin.xml

		String testJarFile =
			hotDeployEvent.getServletContextName() + "-test-integration.jar";

		List<Class<?>> classes = new ArrayList<Class<?>>();

		File file = new File(url.getFile(), testJarFile);

		if (!file.exists()) {
			return classes;
		}

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

		List<String> entries = zipReader.getEntries();

		for (String entry : entries) {
			if (!entry.endsWith(".class")) {
				continue;
			}

			String qualifiedName = entry.replace("/", ".");
			qualifiedName = qualifiedName.substring(
				0, qualifiedName.indexOf(".class"));

			Class c = contextClassLoader.loadClass(qualifiedName);

			classes.add(c);
		}

		return classes;
	}

	private void _runTestClasses(List<Class<?>> classes)
		throws ClassNotFoundException, InitializationError, RuntimeException {

		for (Class clazz : classes) {
			if (!_isTestClass(clazz)) {
				continue;
			}

			_log.info("Running " + clazz.getName());

			Result result = JUnitCore.runClasses(clazz);

			int runTests = result.getRunCount();
			int failureTests = result.getIgnoreCount();
			int errorsTests = result.getFailureCount();

			_log.info(
				"Tests run: " + runTests + ", Failures: " + failureTests +
					", Errors: " + errorsTests);

			for (Failure failure : result.getFailures()) {
				_log.error(failure.toString());
			}
		}
	}

	private boolean _isTestClass(Class<?> clazz) {
		Class<?> declaringClass =
			ReflectionUtil.getAnnotationDeclaringClass(
				RunWith.class, clazz);

		if (declaringClass == null) {
			return false;
		}

		RunWith runWith = declaringClass.getAnnotation(RunWith.class);

		Class<? extends Runner> value = runWith.value();

		String className = clazz.getName();

		if (!value.equals(LiferayPluginsIntegrationJUnitRunner.class) ||
				!className.endsWith("Test")) {

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PluginIntegrationTestHotDeployListener.class);

}