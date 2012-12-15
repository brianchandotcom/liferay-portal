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

package com.liferay.osgi.bootstrap;

import com.liferay.osgi.bootstrap.impl.ModuleFrameworkImpl;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.InputStream;

import org.osgi.framework.launch.Framework;

/**
 * @author Raymond Augé
 */
public class ModuleFrameworkUtil {

	public static Object addBundle(String location) throws PortalException {
		return getModuleFramework().addBundle(location);
	}

	public static Object addBundle(String location, InputStream inputStream)
		throws PortalException {
		return getModuleFramework().addBundle(location, inputStream);
	}

	public static Framework getFramework() {
		return getModuleFramework().getFramework();
	}

	public static ModuleFramework getModuleFramework() {
		if (_moduleFramework == null) {

			// This can't be injected by Spring since we're running before it,
			// but we're making it inject-able for testing.

			_moduleFramework = new ModuleFrameworkImpl();
		}

		return _moduleFramework;
	}

	public static String getState(long bundleId) throws PortalException {
		return getModuleFramework().getState(bundleId);
	}

	public static void registerContext(Object context) {
		getModuleFramework().registerContext(context);
	}

	public static void setBundleStartLevel(long bundleId, int startLevel)
		throws PortalException {
		getModuleFramework().setBundleStartLevel(bundleId, startLevel);
	}

	public static void setModuleFramework(ModuleFramework moduleFramework) {
		_moduleFramework = moduleFramework;
	}

	public static void startBundle(long bundleId) throws PortalException {
		getModuleFramework().startBundle(bundleId);
	}

	public static void startBundle(long bundleId, int options)
		throws PortalException {

		getModuleFramework().startBundle(bundleId, options);
	}

	public static void startFramework() throws Exception {
		getModuleFramework().startFramework();
	}

	public static void startRuntime() throws Exception {
		getModuleFramework().startRuntime();
	}

	public static void stopBundle(long bundleId) throws PortalException {
		getModuleFramework().stopBundle(bundleId);
	}

	public static void stopBundle(long bundleId, int options)
		throws PortalException {

		getModuleFramework().stopBundle(bundleId, options);
	}

	public static void stopFramework() throws Exception {
		getModuleFramework().stopFramework();
	}

	public static void stopRuntime() throws Exception {
		getModuleFramework().stopRuntime();
	}

	public static void uninstallBundle(long bundleId) throws PortalException {
		getModuleFramework().uninstallBundle(bundleId);
	}

	public static void updateBundle(long bundleId) throws PortalException {
		getModuleFramework().updateBundle(bundleId);
	}

	public static void updateBundle(long bundleId, InputStream inputStream)
		throws PortalException {

		getModuleFramework().updateBundle(bundleId, inputStream);
	}

	private static ModuleFramework _moduleFramework;

}