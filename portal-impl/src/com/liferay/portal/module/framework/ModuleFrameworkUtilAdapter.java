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

package com.liferay.portal.module.framework;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;

import java.io.InputStream;

/**
 * This class is a simple wrapper in order to make the framework module running
 * under its own classlaoder
 *
 * @author Miguel Pastor
 * @author Raymond Augé
 *
 * @see {@link ModuleFrameworkClassloader}
 */
public class ModuleFrameworkUtilAdapter {

	public static Object addBundle(String location) throws PortalException {
		return _moduleFrameworkAdapterHelper.execute("addBundle", location);
	}

	public static Object addBundle(
		String location, InputStream inputStream) throws PortalException {

		return _moduleFrameworkAdapterHelper.execute(
			"addBundle", location, inputStream);
	}

	public static Object getFramework() {
		return _moduleFrameworkAdapterHelper.execute("getFramework");
	}

	public static String getState(long bundleId) throws PortalException {
		return (String) _moduleFrameworkAdapterHelper.execute(
			"getState", bundleId);
	}

	public static void registerContext(Object context) {
		_moduleFrameworkAdapterHelper.exec(
			"registerContext", new Class<?>[]{Object.class}, context);
	}

	public static void setBundleStartLevel(long bundleId, int startLevel)
			throws PortalException {

		_moduleFrameworkAdapterHelper.execute(
			"setBundleStartLevel", bundleId, startLevel);
	}

	public static void startBundle(long bundleId) throws PortalException {
		_moduleFrameworkAdapterHelper.execute("startBundle", bundleId);
	}

	public static void startBundle(long bundleId, int options)
		throws PortalException {

		_moduleFrameworkAdapterHelper.execute("startBundle", bundleId, options);
	}

	public static void startFramework() throws Exception {
		ClassLoader current = PACLClassLoaderUtil.getContextClassLoader();

		PACLClassLoaderUtil.setContextClassLoader(
			ModuleFrameworkAdapterHelper.getClassLoader());

		try {
			_moduleFrameworkAdapterHelper.execute("startFramework");
		}
		finally {
			PACLClassLoaderUtil.setContextClassLoader(current);
		}
	}

	public static void startRuntime() throws Exception {
		_moduleFrameworkAdapterHelper.execute("startRuntime");
	}

	public static void stopBundle(long bundleId) throws PortalException {
		_moduleFrameworkAdapterHelper.execute("stopBundle", bundleId);
	}

	public static void stopBundle(long bundleId, int options)
		throws PortalException {

		_moduleFrameworkAdapterHelper.execute("stopBundle", bundleId, options);
	}

	public static void stopFramework() throws Exception {
		_moduleFrameworkAdapterHelper.execute("stopFramework");
	}

	public static void stopRuntime() throws Exception {
		_moduleFrameworkAdapterHelper.execute("stopRuntime");
	}

	public static void uninstallBundle(long bundleId) throws PortalException {
		_moduleFrameworkAdapterHelper.execute("uninstallBundle", bundleId);
	}

	public static void updateBundle(long bundleId) throws PortalException {
		_moduleFrameworkAdapterHelper.execute("updateBundle", bundleId);
	}

	public static void updateBundle(long bundleId, InputStream inputStream)
		throws PortalException {

		_moduleFrameworkAdapterHelper.execute(
			"updateBundle", bundleId, inputStream);
	}

	private static ModuleFrameworkAdapterHelper _moduleFrameworkAdapterHelper =
		new ModuleFrameworkAdapterHelper(
			"com.liferay.osgi.bootstrap.ModuleFrameworkUtil");

}