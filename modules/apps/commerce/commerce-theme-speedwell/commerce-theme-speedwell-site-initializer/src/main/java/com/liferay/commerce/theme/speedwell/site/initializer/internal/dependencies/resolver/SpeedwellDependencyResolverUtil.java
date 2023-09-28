/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.theme.speedwell.site.initializer.internal.dependencies.resolver;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

/**
 * @author Marco Leo
 */
public class SpeedwellDependencyResolverUtil {

	public static String getDependenciesPath() {
		return _DEPENDENCIES_PATH;
	}

	public static ClassLoader getDisplayTemplatesClassLoader() {
		return SpeedwellDependencyResolverUtil.class.getClassLoader();
	}

	public static String getDisplayTemplatesDependencyPath() {
		return _DEPENDENCIES_PATH + "display_templates/";
	}

	public static ClassLoader getDocumentsClassLoader() {
		return SpeedwellDependencyResolverUtil.class.getClassLoader();
	}

	public static String getDocumentsDependencyPath() {
		return _DEPENDENCIES_PATH + "documents/";
	}

	public static ClassLoader getImageClassLoader() {
		return SpeedwellDependencyResolverUtil.class.getClassLoader();
	}

	public static String getImageDependencyPath() {
		return _DEPENDENCIES_PATH + "images/";
	}

	public static String getJSON(String name) throws IOException {
		return StringUtil.read(
			SpeedwellDependencyResolverUtil.class.getClassLoader(),
			_DEPENDENCIES_PATH + name);
	}

	private static final String _DEPENDENCIES_PATH =
		"com/liferay/commerce/theme/speedwell/site/initializer/internal" +
			"/dependencies/";

}