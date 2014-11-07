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

package com.liferay.counter.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @generated
 */
@ProviderType
public class CounterFinderUtil {
	public static void afterPropertiesSet() {
		getFinder().afterPropertiesSet();
	}

	public static java.util.List<java.lang.String> getNames() {
		return getFinder().getNames();
	}

	public static java.lang.String getRegistryName() {
		return getFinder().getRegistryName();
	}

	public static long increment() {
		return getFinder().increment();
	}

	public static long increment(java.lang.String name) {
		return getFinder().increment(name);
	}

	public static long increment(java.lang.String name, int size) {
		return getFinder().increment(name, size);
	}

	public static void invalidate() {
		getFinder().invalidate();
	}

	public static void rename(java.lang.String oldName, java.lang.String newName) {
		getFinder().rename(oldName, newName);
	}

	public static void reset(java.lang.String name) {
		getFinder().reset(name);
	}

	public static void reset(java.lang.String name, long size) {
		getFinder().reset(name, size);
	}

	public static CounterFinder getFinder() {
		if (_finder == null) {
			_finder = (CounterFinder)PortalBeanLocatorUtil.locate(CounterFinder.class.getName());

			ReferenceRegistry.registerReference(CounterFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(CounterFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(CounterFinderUtil.class, "_finder");
	}

	private static CounterFinder _finder;
}