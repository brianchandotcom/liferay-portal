/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;

import java.io.File;

import java.net.URL;

import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;

/**
 * @author Manuel de la Peña
 */
public class PluginIntegrationTestHotDeployListener
	extends BaseHotDeployListener {

	@Override
	public void invokeDeploy(HotDeployEvent hotDeployEvent)
		throws HotDeployException {

		try {
			doInvokeDeploy(hotDeployEvent);
		}
		catch (Throwable t) {
			throwHotDeployException(
				hotDeployEvent, "Unable to register tests for ", t);
		}
	}

	@Override
	public void invokeUndeploy(HotDeployEvent hotDeployEvent)
		throws HotDeployException {

		try {
			doInvokeUndeploy(hotDeployEvent);
		}
		catch (Throwable t) {
			throwHotDeployException(
				hotDeployEvent, "Unable to register tests for ", t);
		}
	}

	protected void doInvokeDeploy(HotDeployEvent hotDeployEvent)
		throws Exception {

		List<Class<?>> testClasses = getAllClassesInIntegrationJar(
			hotDeployEvent);

		runTestClasses(testClasses);
	}

	protected void doInvokeUndeploy(HotDeployEvent hotDeployEvent)
		throws Exception {

		_log.debug("Undeploying tests for " + hotDeployEvent);
	}

	protected List<Class<?>> getAllClassesInIntegrationJar(
			HotDeployEvent hotDeployEvent)
		throws ClassNotFoundException {

		ClassLoader classLoader = hotDeployEvent.getContextClassLoader();

		URL url = classLoader.getResource("../lib");

		File file = new File(
			url.getFile(),
			hotDeployEvent.getServletContextName() + "-test-integration.jar");

		if (!file.exists()) {
			return Collections.emptyList();
		}

		List<Class<?>> classes = new ArrayList<Class<?>>();

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

		List<String> entries = zipReader.getEntries();

		for (String entry : entries) {
			if (!entry.endsWith(".class")) {
				continue;
			}

			String className = entry.replace("/", ".");

			className = className.substring(0, className.indexOf(".class"));

			Class<?> clazz = classLoader.loadClass(className);

			classes.add(clazz);
		}

		return classes;
	}

	protected boolean isTestClass(Class<?> clazz) {
		Class<?> declaringClass = ReflectionUtil.getAnnotationDeclaringClass(
			RunWith.class, clazz);

		if (declaringClass == null) {
			return false;
		}

		RunWith runWith = declaringClass.getAnnotation(RunWith.class);

		Class<? extends Runner> value = runWith.value();

		String className = clazz.getName();

		if (!className.endsWith("Test") ||
			!value.equals(LiferayPluginsIntegrationJUnitRunner.class)) {

			return false;
		}

		return true;
	}

	protected void runTestClasses(List<Class<?>> classes)
		throws RuntimeException {

		NumberFormat numberFormat = NumberFormat.getInstance();

		numberFormat.setMaximumFractionDigits(3);

		for (Class<?> clazz : classes) {
			if (!isTestClass(clazz)) {
				continue;
			}

			double startTime = System.currentTimeMillis();

			if (_log.isInfoEnabled()) {
				_log.info("Running " + clazz.getName());
			}

			Result result = JUnitCore.runClasses(clazz);

			if (_log.isInfoEnabled()) {
				double endTime = System.currentTimeMillis();

				StringBundler sb = new StringBundler(9);

				sb.append("Tests run: ");
				sb.append(result.getRunCount());
				sb.append(", Failures: ");
				sb.append(result.getIgnoreCount());
				sb.append(", Errors: ");
				sb.append(result.getFailureCount());
				sb.append(", Time elapsed: ");
				sb.append(
					numberFormat.format((endTime - startTime) / Time.SECOND));
				sb.append(" sec");

				_log.info(sb.toString());
			}

			for (Failure failure : result.getFailures()) {
				_log.error(failure.toString());
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PluginIntegrationTestHotDeployListener.class);

}