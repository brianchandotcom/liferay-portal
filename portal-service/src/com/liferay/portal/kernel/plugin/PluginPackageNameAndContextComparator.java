/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
		int result = package1.getName().compareTo(package2.getName());

		if (result == 0) {
			result = package1.getContext().compareTo(package2.getContext());
		}

		return result;
	}

}