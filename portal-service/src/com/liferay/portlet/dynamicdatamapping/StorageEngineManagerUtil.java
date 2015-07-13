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

package com.liferay.portlet.dynamicdatamapping;

import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author Rafael Praxedes
 */
public class StorageEngineManagerUtil {

	public static long create(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws StorageException {

		StorageEngineManager storageEngineManager = _getStorageEngineManager();

		return storageEngineManager.create(
			companyId, ddmStructureId, ddmFormValues, serviceContext);
	}

	public static void deleteByClass(long classPK) throws StorageException {
		StorageEngineManager storageEngineManager = _getStorageEngineManager();

		storageEngineManager.deleteByClass(classPK);
	}

	public static void deleteByDDMStructure(long ddmStructureId)
		throws StorageException {

		StorageEngineManager storageEngineManager = _getStorageEngineManager();

		storageEngineManager.deleteByDDMStructure(ddmStructureId);
	}

	public static DDMFormValues getDDMFormValues(long classPK)
		throws StorageException {

		StorageEngineManager storageEngineManager = _getStorageEngineManager();

		return storageEngineManager.getDDMFormValues(classPK);
	}

	public static void update(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws StorageException {

		StorageEngineManager storageEngineManager = _getStorageEngineManager();

		storageEngineManager.update(classPK, ddmFormValues, serviceContext);
	}

	private static StorageEngineManager _getStorageEngineManager() {
		StorageEngineManager storageEngineManager =
			_instance._serviceTracker.getService();

		if (storageEngineManager == null) {
			return _dummyStorageEngineImpl;
		}

		return storageEngineManager;
	}

	private StorageEngineManagerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(StorageEngineManager.class);

		_serviceTracker.open();
	}

	private static final StorageEngineManagerUtil _instance =
		new StorageEngineManagerUtil();

	private static final DummyStorageEngineManagerImpl _dummyStorageEngineImpl =
		new DummyStorageEngineManagerImpl();

	private final ServiceTracker<StorageEngineManager,
										StorageEngineManager> _serviceTracker;

}