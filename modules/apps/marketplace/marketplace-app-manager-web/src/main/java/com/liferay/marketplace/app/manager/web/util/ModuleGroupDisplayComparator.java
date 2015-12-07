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

package com.liferay.marketplace.app.manager.web.util;

import java.util.Comparator;

/**
 * @author Ryan Park
 */
public class ModuleGroupDisplayComparator
	implements Comparator<ModuleGroupDisplay> {

	public ModuleGroupDisplayComparator(String orderByType) {
		if (!orderByType.equals("asc")) {
			_ascending = false;
		}
		else {
			_ascending = true;
		}
	}

	@Override
	public int compare(
		ModuleGroupDisplay moduleGroupDisplay1,
		ModuleGroupDisplay moduleGroupDisplay2) {

		int value = moduleGroupDisplay1.compareTo(moduleGroupDisplay2);

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	private final boolean _ascending;

}