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

package com.liferay.portal.test.rule;

import aQute.bnd.osgi.Builder;
import aQute.bnd.osgi.Jar;

import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.process.ClassPathUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.ClassTestRule;
import com.liferay.portal.module.framework.ModuleFrameworkUtilAdapter;

import java.io.File;
import java.io.InputStream;

import java.lang.reflect.Method;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.Properties;

import org.junit.runner.Description;

/**
 * @author Raymond Augé
 */
public class SyntheticBundleClassTestRule extends ClassTestRule<Long> {

	public SyntheticBundleClassTestRule(String bundlePackageName) {
		_bundlePackageName = bundlePackageName;
	}

	@Override
	public void afterClass(Description description, Long bundleId)
		throws PortalException {

		if (bundleId == null) {
			return;
		}

		ModuleFrameworkUtilAdapter.stopBundle(bundleId);

		ModuleFrameworkUtilAdapter.uninstallBundle(bundleId);
	}

	@Override
	public Long beforeClass(Description description) throws Exception {
		Class<?> testClass = description.getTestClass();

		byte[] data = _createBundleData(testClass, _bundlePackageName);

		Long bundleId = ModuleFrameworkUtilAdapter.addBundle(
			testClass.getName(), new UnsyncByteArrayInputStream(data));

		ModuleFrameworkUtilAdapter.startBundle(bundleId);

		return bundleId;
	}

	private static byte[] _createBundleData(
			Class<?> clazz, String bundlePackageName)
		throws Exception {

		File bndFile = new File("lib/development/biz.aQute.bnd.jar");

		ClassLoader classLoader = new URLClassLoader(
			ClassPathUtil.getClassPathURLs(
				ClassPathUtil.getJVMClassPath(false) + File.pathSeparator +
					bndFile.getAbsolutePath()),
			null);

		Class<?> reloadedClass = classLoader.loadClass(
			SyntheticBundleClassTestRule.class.getName());

		Method method = reloadedClass.getDeclaredMethod(
			"_doCreateBundleData", Class.class, String.class);

		method.setAccessible(true);

		return (byte[])method.invoke(null, clazz, bundlePackageName);
	}

	private static byte[] _doCreateBundleData(
			Class<?> clazz, String bundlePackageName)
		throws Exception {

		URL url = clazz.getResource("");

		String protocol = url.getProtocol();

		if (!protocol.equals("file")) {
			throw new IllegalStateException(
				"Test classes are not on the file system");
		}

		String basePath = url.getPath();

		Package pkg = clazz.getPackage();

		String packageName = pkg.getName();

		int index = basePath.indexOf(packageName.replace('.', '/') + '/');

		basePath = basePath.substring(0, index);

		File baseDir = new File(basePath);

		try (Builder builder = new Builder();
			InputStream inputStream = clazz.getResourceAsStream(
				bundlePackageName.replace('.', '/') + "/bnd.bnd")) {

			builder.setBundleSymbolicName(clazz.getName());
			builder.setBase(baseDir);
			builder.setClasspath(new File[] {baseDir});
			builder.setProperty(
				"bundle.package", packageName + "." + bundlePackageName);

			Properties properties = builder.getProperties();

			properties.load(inputStream);

			try (Jar jar = builder.build()) {
				UnsyncByteArrayOutputStream outputStream =
					new UnsyncByteArrayOutputStream();

				jar.write(outputStream);

				return outputStream.toByteArray();
			}
		}
	}

	private final String _bundlePackageName;

}