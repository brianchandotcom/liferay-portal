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

package com.liferay.portal.kernel.util;

/**
 * @author Jiaxu Wei
 */
public class ModuleFrameworkPropsValues {

	public static final int MODULE_FRAMEWORK_BEGINNING_START_LEVEL =
		GetterUtil.getInteger(
			SystemProperties.get("module.framework.beginning.start.level"));

	public static final boolean MODULE_FRAMEWORK_CONCURRENT_STARTUP_ENABLED =
		GetterUtil.getBoolean(
			SystemProperties.get(
				"module.framework.concurrent.startup.enabled"));

	public static final int MODULE_FRAMEWORK_DYNAMIC_INSTALL_START_LEVEL =
		GetterUtil.getInteger(
			SystemProperties.get(
				"module.framework.dynamic.install.start.level"));

	public static final String MODULE_FRAMEWORK_FILE_INSTALL_CONFIG_ENCODING =
		SystemProperties.get("module.framework.file.install.config.encoding");

	public static final int MODULE_FRAMEWORK_RUNTIME_START_LEVEL =
		GetterUtil.getInteger(
			SystemProperties.get("module.framework.runtime.start.level"));

	public static final String[] MODULE_FRAMEWORK_SERVICES_IGNORED_INTERFACES =
		SystemProperties.getArray(
			"module.framework.services.ignored.interfaces");

	public static final String[] MODULE_FRAMEWORK_SYSTEM_PACKAGES_EXTRA =
		SystemProperties.getArray("module.framework.system.packages.extra");

	public static final int MODULE_FRAMEWORK_WEB_START_LEVEL =
		GetterUtil.getInteger(
			SystemProperties.get("module.framework.web.start.level"));

}