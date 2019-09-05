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

package com.liferay.counter.kernel.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CounterFinderUtil {

	public static java.util.List<String> getNames() {
		return getFinder().getNames();
	}

	public static String getRegistryName() {
		return getFinder().getRegistryName();
	}

	public static long increment() {
		return getFinder().increment();
	}

	public static long increment(String name) {
		return getFinder().increment(name);
	}

	public static long increment(String name, int size) {
		return getFinder().increment(name, size);
	}

	public static void invalidate() {
		getFinder().invalidate();
	}

	public static void rename(String oldName, String newName) {
		getFinder().rename(oldName, newName);
	}

	public static void reset(String name) {
		getFinder().reset(name);
	}

	public static void reset(String name, long size) {
		getFinder().reset(name, size);
	}

	public static CounterFinder getFinder() {
		if (_finder == null) {
			_finder = (CounterFinder)PortalBeanLocatorUtil.locate(
				CounterFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(CounterFinder finder) {
		_finder = finder;
	}

	private static CounterFinder _finder;

}