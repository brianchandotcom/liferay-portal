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

package com.liferay.portal.osgi.web.wab.extender.internal.definition.ordering;

import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Arrays;
import java.util.EnumMap;

/**
 * @author Vernon Singleton
 * @author Juan Gonzalez
 *
 */
public class OrderingImpl implements Ordering {

	public static final String OTHERS = Ordering.class.getName() + ".OTHERS";

	public OrderingImpl() {
		_routes = new EnumMap<>(Path.class);
		_routes.put(Path.BEFORE, new String[0]);
		_routes.put(Path.AFTER, new String[0]);
	}

	public EnumMap<Path, String[]> getRoutes() {
		return _routes;
	}

	public boolean isAfter(String name) {
		return (Arrays.binarySearch(_routes.get(Path.AFTER), name) >= 0);
	}

	public boolean isAfterOthers() {
		boolean value = false;

		if (_routes.get(Path.AFTER) != null) {
			value = (Arrays.binarySearch(_routes.get(Path.AFTER), OTHERS) >= 0);
		}

		return value;
	}

	public boolean isBefore(String name) {
		return (Arrays.binarySearch(_routes.get(Path.BEFORE), name) >= 0);
	}

	public boolean isBeforeOthers() {
		boolean value = false;

		if (_routes.get(Path.BEFORE) != null) {
			value =
				(Arrays.binarySearch(_routes.get(Path.BEFORE), OTHERS) >= 0);
		}

		return value;
	}

	public boolean isOrdered() {
		if (ArrayUtil.isNotEmpty(_routes.get(Path.BEFORE)) ||
			ArrayUtil.isNotEmpty(_routes.get(Path.AFTER))) {

			return true;
		}

		return false;
	}

	public void setRoutes(EnumMap<Path, String[]> routes) {
		_routes = routes;
	}

	private EnumMap<Path, String[]> _routes;

}