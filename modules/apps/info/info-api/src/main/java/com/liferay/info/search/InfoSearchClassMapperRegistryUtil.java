/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.search;

import com.liferay.osgi.util.service.Snapshot;

/**
 * @author Jürgen Kappler
 */
public class InfoSearchClassMapperRegistryUtil {

	public static String getClassName(String searchClassName) {
		InfoSearchClassMapperRegistry infoSearchClassMapperRegistry =
			_infoSearchClassMapperRegistrySnapshot.get();

		return infoSearchClassMapperRegistry.getClassName(searchClassName);
	}

	public static String getSearchClassName(String className) {
		InfoSearchClassMapperRegistry infoSearchClassMapperRegistry =
			_infoSearchClassMapperRegistrySnapshot.get();

		return infoSearchClassMapperRegistry.getSearchClassName(className);
	}

	private static final Snapshot<InfoSearchClassMapperRegistry>
		_infoSearchClassMapperRegistrySnapshot = new Snapshot<>(
			InfoSearchClassMapperRegistryUtil.class,
			InfoSearchClassMapperRegistry.class);

}