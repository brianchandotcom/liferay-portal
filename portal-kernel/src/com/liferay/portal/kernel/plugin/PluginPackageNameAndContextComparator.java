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