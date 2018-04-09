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

import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class StaticLPKGResolver {

	public static List<String> getStaticLPKGBundleSymbolicNames() {
		return _staticLPKGBundleSymbolicNames;
	}

	public static List<String> getStaticLPKGFileNames() {
		return _staticLPKGFileNames;
	}

	private static final List<String> _staticLPKGBundleSymbolicNames;
	private static final List<String> _staticLPKGFileNames;

	static {
		String staticLPKGBundleSymbolicNames = System.getProperty(
			"static.lpkg.bundle.symbolic.names");

		List<String> staticLPKGBundleSymbolicNameList = StringUtil.split(
			staticLPKGBundleSymbolicNames);

		String name = ReleaseInfo.getName();

		String lpkgSymbolicNamePrefix = "Liferay ";

		if (name.contains("Community")) {
			lpkgSymbolicNamePrefix = "Liferay CE ";
		}

		for (int i = 0; i < staticLPKGBundleSymbolicNameList.size(); i++) {
			staticLPKGBundleSymbolicNameList.set(
				i,
				lpkgSymbolicNamePrefix.concat(
					staticLPKGBundleSymbolicNameList.get(i)));
		}

		_staticLPKGBundleSymbolicNames = staticLPKGBundleSymbolicNameList;

		String staticLPKGFileNames = System.getProperty(
			"static.lpkg.file.names");

		List<String> staticLPKGFileNameList = new ArrayList<>(
			staticLPKGBundleSymbolicNameList.size());

		if (staticLPKGFileNames == null) {
			for (String staticLPKGBundleSymbolicName :
					staticLPKGBundleSymbolicNameList) {

				staticLPKGFileNameList.add(
					staticLPKGBundleSymbolicName.concat(".lpkg"));
			}
		}
		else {
			staticLPKGFileNameList = StringUtil.split(staticLPKGFileNames);
		}

		_staticLPKGFileNames = staticLPKGFileNameList;
	}

}