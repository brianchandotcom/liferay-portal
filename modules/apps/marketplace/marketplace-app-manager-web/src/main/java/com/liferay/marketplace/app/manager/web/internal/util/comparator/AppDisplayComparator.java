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

package com.liferay.marketplace.app.manager.web.internal.util.comparator;

import com.liferay.marketplace.app.manager.web.internal.util.AppDisplay;

import java.util.Comparator;

/**
 * @author Ryan Park
 */
public class AppDisplayComparator implements Comparator<AppDisplay> {

	public AppDisplayComparator(String orderByType) {
		if (!orderByType.equals("asc")) {
			_ascending = false;
		}
		else {
			_ascending = true;
		}
	}

	@Override
	public int compare(AppDisplay appDisplay1, AppDisplay appDisplay2) {
		int value = appDisplay1.compareTo(appDisplay2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	private final boolean _ascending;

}