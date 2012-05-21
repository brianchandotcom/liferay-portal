/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.atom;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.List;

/**
 * @author Igor Spasic
 */
public class AtomCollectionAdapterRegistryUtil {

	public static AtomCollectionAdapter<?> getAtomCollectionAdapter(
		String collectionName) {

		return getAtomCollectionAdapterRegistry().getAtomCollectionAdapter(
			collectionName);
	}

	public static AtomCollectionAdapterRegistry
		getAtomCollectionAdapterRegistry() {

		PortalRuntimePermission.checkGetBeanProperty(
			AtomCollectionAdapterRegistryUtil.class);

		return _atomCollectionAdapterRegistry;
	}

	public static List<AtomCollectionAdapter<?>> getAtomCollectionAdapters() {
		return getAtomCollectionAdapterRegistry().getAtomCollectionAdapters();
	}

	public static void register(AtomCollectionAdapter<?> atomCollectionAdapter)
		throws AtomException {

		getAtomCollectionAdapterRegistry().register(atomCollectionAdapter);
	}

	public static void register(
			List<AtomCollectionAdapter<?>> atomCollectionAdapters)
		throws AtomException {

		for (AtomCollectionAdapter<?> atomCollectionAdapter :
				atomCollectionAdapters) {

			register(atomCollectionAdapter);
		}
	}

	public static void unregister(
		AtomCollectionAdapter<?> atomCollectionAdapter) {

		getAtomCollectionAdapterRegistry().unregister(atomCollectionAdapter);
	}

	public static void unregister(
		List<AtomCollectionAdapter<?>> atomCollectionAdapters) {

		for (AtomCollectionAdapter<?> atomCollectionAdapter :
				atomCollectionAdapters) {

			unregister(atomCollectionAdapter);
		}
	}

	public void setAtomCollectionAdapterRegistry(
		AtomCollectionAdapterRegistry atomCollectionAdapterRegistry) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_atomCollectionAdapterRegistry = atomCollectionAdapterRegistry;
	}

	private static AtomCollectionAdapterRegistry _atomCollectionAdapterRegistry;

}