/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.soy.internal;

/**
 * @author     Andrea Di Giorgi
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public interface SoyPluginConstants {

	public static final String CONFIG_JS_MODULES_TASK_NAME = "configJSModules";

	public static final String JS_MODULE_CONFIG_GENERATOR_PLUGIN_ID =
		"com.liferay.js.module.config.generator";

	public static final String JS_TRANSPILER_PLUGIN_ID =
		"com.liferay.js.transpiler";

	public static final String TRANSPILE_JS_TASK_NAME = "transpileJS";

}