/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.lpkg;

import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.SystemProperties;

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
		String staticLPKGBundleSymbolicNames = SystemProperties.get(
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