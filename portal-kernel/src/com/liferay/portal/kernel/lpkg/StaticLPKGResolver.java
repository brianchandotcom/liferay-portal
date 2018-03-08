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

package com.liferay.portal.kernel.lpkg;

import com.liferay.portal.kernel.util.ReleaseInfo;

/**
 * @author Shuyang Zhou
 */
public class StaticLPKGResolver {

	public static String[] getStaticLPKGBundleSymbolicNames() {
		return _STATIC_LPKG_BUNDLE_SYMBOLIC_NAMES;
	}

	public static String[] getStaticLPKGFileNames() {
		return _STATIC_LPKG_FILE_NAMES;
	}

	private static final String[] _STATIC_LPKG_BUNDLE_SYMBOLIC_NAMES;

	private static final String[] _STATIC_LPKG_FILE_NAMES;

	static {
		String staticLPKGBundleSymbolicNames = System.getProperty(
			"static.lpkg.bundle.symbolic.names");

		String[] staticLPKGBundleSymbolicNameArray =
			staticLPKGBundleSymbolicNames.split(",");

		String name = ReleaseInfo.getName();

		String lpkgSymbolicNamePrefix = "Liferay ";

		if (name.contains("Community")) {
			lpkgSymbolicNamePrefix = "Liferay CE ";
		}

		for (int i = 0; i < staticLPKGBundleSymbolicNameArray.length; i++) {
			staticLPKGBundleSymbolicNameArray[i] =
				lpkgSymbolicNamePrefix.concat(
					staticLPKGBundleSymbolicNameArray[i]);
		}

		_STATIC_LPKG_BUNDLE_SYMBOLIC_NAMES = staticLPKGBundleSymbolicNameArray;

		String staticLPKGFileNames = System.getProperty(
			"static.lpkg.file.names");

		String[] staticLPKGFileNameArray =
			new String[staticLPKGBundleSymbolicNameArray.length];

		if (staticLPKGFileNames == null) {
			for (int i = 0; i < staticLPKGFileNameArray.length; i++) {
				staticLPKGFileNameArray[i] =
					staticLPKGBundleSymbolicNameArray[i].concat(".lpkg");
			}
		}
		else {
			staticLPKGFileNameArray = staticLPKGFileNames.split(",");
		}

		_STATIC_LPKG_FILE_NAMES = staticLPKGFileNameArray;
	}

}