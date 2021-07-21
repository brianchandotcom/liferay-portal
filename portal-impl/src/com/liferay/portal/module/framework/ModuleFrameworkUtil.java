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

import java.util.Iterator;
import java.util.ServiceLoader;

import org.osgi.framework.launch.Framework;

/**
 * This class is a simple wrapper in order to make the framework module running
 * under its own class loader.
 *
 * @author Miguel Pastor
 * @author Raymond Augé
 * @see    ModuleFrameworkClassLoader
 */
public class ModuleFrameworkUtil {

	public static Framework getFramework() {
		return _moduleFramework.getFramework();
	}

	public static void initFramework() throws Exception {
		_moduleFramework.initFramework();
	}

	public static void registerContext(Object context) {
		_moduleFramework.registerContext(context);
	}

	public static void startFramework() throws Exception {
		_moduleFramework.startFramework();
	}

	public static void stopFramework(long timeout) throws Exception {
		_moduleFramework.stopFramework(timeout);
	}

	public static void unregisterContext(Object context) {
		_moduleFramework.unregisterContext(context);
	}

	private static final ModuleFramework _moduleFramework;

	static {
		ServiceLoader<ModuleFramework> serviceLoader = ServiceLoader.load(
			ModuleFramework.class, ModuleFrameworkUtil.class.getClassLoader());

		Iterator<ModuleFramework> iterator = serviceLoader.iterator();

		if (!iterator.hasNext()) {
			throw new ExceptionInInitializerError(
				"Unable to locate module framework implementation");
		}

		_moduleFramework = iterator.next();
	}

}