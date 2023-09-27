/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.theme.speedwell.site.initializer.internal.dependencies.resolver;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(service = SpeedwellDependencyResolver.class)
public class SpeedwellDependencyResolver {

	public String getDependenciesPath() {
		return _DEPENDENCIES_PATH;
	}

	public ClassLoader getDisplayTemplatesClassLoader() {
		return SpeedwellDependencyResolver.class.getClassLoader();
	}

	public String getDisplayTemplatesDependencyPath() {
		return _DEPENDENCIES_PATH + "display_templates/";
	}

	public ClassLoader getDocumentsClassLoader() {
		return SpeedwellDependencyResolver.class.getClassLoader();
	}

	public String getDocumentsDependencyPath() {
		return _DEPENDENCIES_PATH + "documents/";
	}

	public ClassLoader getImageClassLoader() {
		return SpeedwellDependencyResolver.class.getClassLoader();
	}

	public String getImageDependencyPath() {
		return _DEPENDENCIES_PATH + "images/";
	}

	public String getJSON(String name) throws IOException {
		return StringUtil.read(
			SpeedwellDependencyResolver.class.getClassLoader(),
			_DEPENDENCIES_PATH + name);
	}

	private static final String _DEPENDENCIES_PATH =
		"com/liferay/commerce/theme/speedwell/site/initializer/internal" +
			"/dependencies/";

}