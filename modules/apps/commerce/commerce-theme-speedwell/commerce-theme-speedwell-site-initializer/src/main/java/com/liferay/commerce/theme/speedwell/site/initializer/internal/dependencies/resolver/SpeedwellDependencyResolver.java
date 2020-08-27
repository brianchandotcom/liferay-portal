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

package com.liferay.commerce.theme.speedwell.site.initializer.internal.dependencies.resolver;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = SpeedwellDependencyResolver.class)
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