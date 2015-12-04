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

package com.liferay.osgi.util;

import java.net.URL;

import java.util.Enumeration;

import org.osgi.framework.Bundle;

/**
 * @author Carlos Sierra Andrés
 */
public class BundleUtil {

	public static URL getResourceInBundleOrFragments(
		Bundle bundle, String name) {

		String dirName = "/";
		String fileName = name;

		int index = name.lastIndexOf('/');

		if (index > 0) {
			dirName = name.substring(0, index);
			fileName = name.substring(index + 1);
		}
		else if (index == 0) {
			fileName = name.substring(1);
		}

		if (fileName.length() == 0) {
			return bundle.getEntry(dirName);
		}

		Enumeration<URL> enumeration = bundle.findEntries(
			dirName, fileName, false);

		if ((enumeration == null) || !enumeration.hasMoreElements()) {
			return null;
		}

		return enumeration.nextElement();
	}

}