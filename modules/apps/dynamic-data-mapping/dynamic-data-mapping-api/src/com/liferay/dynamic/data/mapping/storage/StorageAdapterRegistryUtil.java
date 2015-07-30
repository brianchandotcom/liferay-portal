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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class StorageAdapterRegistryUtil {

	public static StorageAdapter getDefaultStorageAdapter() {
		return getStorageAdapterRegistry().getDefaultStorageAdapter();
	}

	public static StorageAdapter getStorageAdapter(String storageType) {
		return getStorageAdapterRegistry().getStorageAdapter(storageType);
	}

	public static StorageAdapterRegistry getStorageAdapterRegistry() {
		PortalRuntimePermission.checkGetBeanProperty(
			StorageAdapterRegistryUtil.class);

		return _storageAdapterRegistry;
	}

	public static Set<String> getStorageTypes() {
		return getStorageAdapterRegistry().getStorageTypes();
	}

	public void setStorageAdapterRegistry(
		StorageAdapterRegistry storageAdapterRegistry) {

		PortalRuntimePermission.checkGetBeanProperty(getClass());

		_storageAdapterRegistry = storageAdapterRegistry;
	}

	private static StorageAdapterRegistry _storageAdapterRegistry;

}