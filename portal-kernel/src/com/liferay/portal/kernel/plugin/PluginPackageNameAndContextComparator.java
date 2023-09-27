/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.plugin;

import java.util.Comparator;

/**
 * @author Jorge Ferrer
 */
public class PluginPackageNameAndContextComparator
	implements Comparator<PluginPackage> {

	@Override
	public int compare(PluginPackage package1, PluginPackage package2) {
		String name1 = package1.getName();
		String name2 = package2.getName();

		int value = name1.compareTo(name2);

		if (value == 0) {
			String context1 = package1.getContext();
			String context2 = package2.getContext();

			value = context1.compareTo(context2);
		}

		return value;
	}

}