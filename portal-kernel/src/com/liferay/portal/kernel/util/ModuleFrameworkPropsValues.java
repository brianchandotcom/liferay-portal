/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static final String[]
		MODULE_FRAMEWORK_CONFIGURATION_BUNDLE_SYMBOLIC_NAMES =
			SystemProperties.getArray(
				"module.framework.configuration.bundle.symbolic.names");

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

	public static final String[] MODULE_FRAMEWORK_STATIC_JARS =
		SystemProperties.getArray("module.framework.static.jars");

	public static final String[] MODULE_FRAMEWORK_SYSTEM_PACKAGES_EXTRA =
		SystemProperties.getArray("module.framework.system.packages.extra");

	public static final int MODULE_FRAMEWORK_WEB_START_LEVEL =
		GetterUtil.getInteger(
			SystemProperties.get("module.framework.web.start.level"));

}