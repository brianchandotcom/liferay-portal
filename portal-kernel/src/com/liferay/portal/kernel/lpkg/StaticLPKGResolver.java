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

	public static final String STATIC_LPKG_BUNDLE_SYMBOLIC_NAME =
		"static.lpkg.bundle.symbolic.name";

	public static final String STATIC_LPKG_FILE_NAME = "static.lpkg.file.name";

	public static String getStaticLPKGBundleSymbolicName() {
		return _STATIC_LPKGBUNDLE_SYMBOLIC_NAME;
	}

	public static String getStaticLPKGFileName() {
		return _STATIC_LPKGFILE_NAME;
	}

	private static final String _STATIC_LPKGBUNDLE_SYMBOLIC_NAME;

	private static final String _STATIC_LPKGFILE_NAME;

	static {
		String staticLPKGBundleSymbolicName = System.getProperty(
			STATIC_LPKG_BUNDLE_SYMBOLIC_NAME);

		String name = ReleaseInfo.getName();

		if (staticLPKGBundleSymbolicName == null) {
			if (name.contains("Community")) {
				_STATIC_LPKGBUNDLE_SYMBOLIC_NAME ="Liferay CE Static";
			}
			else {
				_STATIC_LPKGBUNDLE_SYMBOLIC_NAME = "Liferay DXP Static";
			}
		}
		else {
			_STATIC_LPKGBUNDLE_SYMBOLIC_NAME = staticLPKGBundleSymbolicName;
		}

		String staticLPKGFileName = System.getProperty(STATIC_LPKG_FILE_NAME);

		if (staticLPKGFileName == null) {
			_STATIC_LPKGFILE_NAME = _STATIC_LPKGBUNDLE_SYMBOLIC_NAME.concat(
				".lpkg");
		}
		else {
			_STATIC_LPKGFILE_NAME = staticLPKGFileName;
		}
	}

}