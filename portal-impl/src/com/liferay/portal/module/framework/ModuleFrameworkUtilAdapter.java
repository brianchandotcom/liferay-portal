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

package com.liferay.portal.module.framework;

import com.liferay.portal.kernel.exception.PortalException;

import java.io.InputStream;

import java.net.URL;

/**
 * This class is a simple wrapper in order to make the framework module running
 * under its own class loader.
 *
 * @author Miguel Pastor
 * @author Raymond Augé
 * @see    ModuleFrameworkClassLoader
 */
public class ModuleFrameworkUtilAdapter {

	public static long addBundle(String location) throws PortalException {
		return _moduleFramework.addBundle(location);
	}

	public static long addBundle(String location, InputStream inputStream)
		throws PortalException {

		return _moduleFramework.addBundle(location, inputStream);
	}

	public static URL getBundleResource(long bundleId, String name) {
		return _moduleFramework.getBundleResource(bundleId, name);
	}

	public static Object getFramework() {
		return _moduleFramework.getFramework();
	}

	public static String getState(long bundleId) throws PortalException {
		return _moduleFramework.getState(bundleId);
	}

	public static void initFramework() throws Exception {
		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(
			ModuleFrameworkAdapterHelper.getClassLoader());

		try {
			_moduleFramework.initFramework();
		}
		finally {
			currentThread.setContextClassLoader(classLoader);
		}
	}

	public static void registerContext(Object context) {
		_moduleFramework.registerContext(context);
	}

	public static void setBundleStartLevel(long bundleId, int startLevel)
		throws PortalException {

		_moduleFramework.setBundleStartLevel(bundleId, startLevel);
	}

	public static void setModuleFramework(ModuleFramework moduleFramework) {
		_moduleFramework = moduleFramework;

		_moduleFrameworkAdapterHelper.exec(
			"setModuleFramework", new Class<?>[] {ModuleFramework.class},
			_moduleFramework);
	}

	public static void startBundle(long bundleId) throws PortalException {
		_moduleFramework.startBundle(bundleId);
	}

	public static void startBundle(long bundleId, int options)
		throws PortalException {

		_moduleFramework.startBundle(bundleId, options);
	}

	public static void startFramework() throws Exception {
		_moduleFramework.startFramework();
	}

	public static void startRuntime() throws Exception {
		_moduleFramework.startRuntime();
	}

	public static void stopBundle(long bundleId) throws PortalException {
		_moduleFramework.stopBundle(bundleId);
	}

	public static void stopBundle(long bundleId, int options)
		throws PortalException {

		_moduleFramework.stopBundle(bundleId, options);
	}

	public static void stopFramework(long timeout) throws Exception {
		_moduleFramework.stopFramework(timeout);
	}

	public static void stopRuntime() throws Exception {
		_moduleFramework.stopRuntime();
	}

	public static void uninstallBundle(long bundleId) throws PortalException {
		_moduleFramework.uninstallBundle(bundleId);
	}

	public static void unregisterContext(Object context) {
		_moduleFramework.unregisterContext(context);
	}

	public static void updateBundle(long bundleId) throws PortalException {
		_moduleFramework.updateBundle(bundleId);
	}

	public static void updateBundle(long bundleId, InputStream inputStream)
		throws PortalException {

		_moduleFramework.updateBundle(bundleId, inputStream);
	}

	private static ModuleFramework _moduleFramework;
	private static final ModuleFrameworkAdapterHelper
		_moduleFrameworkAdapterHelper = new ModuleFrameworkAdapterHelper(
			"com.liferay.portal.bootstrap.ModuleFrameworkUtil");

	static {
		_moduleFramework = (ModuleFramework)_moduleFrameworkAdapterHelper.exec(
			"getModuleFramework", new Class<?>[0]);
	}

}