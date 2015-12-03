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

import java.io.File;

import java.net.URL;

import java.util.Enumeration;

import org.osgi.framework.Bundle;

/**
 * @author Carlos Sierra Andrés
 */
public class BundleUtil {

	public static URL getResourceInBundleOrFragments(
		Bundle bundle, String name) {

		File file = new File(name);

		if (file.isDirectory()) {
			return bundle.getEntry(name);
		}

		Enumeration<URL> enumeration = bundle.findEntries(
			file.getParent(), file.getName(), false);

		if ((enumeration == null) || !enumeration.hasMoreElements()) {
			return null;
		}

		return enumeration.nextElement();
	}

}