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

package com.liferay.source.formatter;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class PropertiesSourceProcessor extends BaseSourceProcessor {

	@Override
	protected List<String> doGetFileNames() throws Exception {
		return getFileNames(new String[] {"**/docroot/dtd/**"}, getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		if (portalSource) {
			return new String[] {
				"**/app-server.properties", "**/ci.properties",
				"**/lib/*/dependencies.properties", "**/framework.properties",
				"**/gradle-wrapper.properties", "**/imported-files.properties",
				"**/Language*.properties",
				"**/liferay-plugin-package*.properties", "**/portal.properties",
				"**/portal-ext.properties", "**/portal-legacy-*.properties",
				"**/portlet.properties", "**/source-formatter.properties",
				"**/test.properties"
			};
		}

		return new String[] {
			"**/liferay-plugin-package.properties", "**/portal.properties",
			"**/portal-ext.properties", "**/portlet.properties",
			"**/source-formatter.properties"
		};
	}

}