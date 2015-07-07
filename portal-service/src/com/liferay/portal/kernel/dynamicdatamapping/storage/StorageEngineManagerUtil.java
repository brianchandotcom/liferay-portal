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

package com.liferay.portal.kernel.dynamicdatamapping.storage;

import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.StorageException;
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

		return _getStorageEngineManager().create(companyId, ddmStructureId,
			ddmFormValues, serviceContext);
	}

	public static void deleteByClass(long classPK) throws StorageException {
		_getStorageEngineManager().deleteByClass(classPK);
	}

	public static void deleteByDDMStructure(long ddmStructureId)
		throws StorageException {

		_getStorageEngineManager().deleteByDDMStructure(ddmStructureId);
	}

	public static DDMFormValues getDDMFormValues(long classPK)
		throws StorageException {

		return _getStorageEngineManager().getDDMFormValues(classPK);
	}

	public static String getStorageType() {
		return _getStorageEngineManager().getStorageType();
	}

	public static void update(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws StorageException {

		_getStorageEngineManager().update(classPK, ddmFormValues,
			serviceContext);
	}

	private static StorageEngineManager _getStorageEngineManager() {
		return _instance._serviceTracker.getService();
	}

	private StorageEngineManagerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(StorageEngineManager.class);

		_serviceTracker.open();
	}

	private static final StorageEngineManagerUtil _instance =
		new StorageEngineManagerUtil();

	private final ServiceTracker<StorageEngineManager,
										StorageEngineManager> _serviceTracker;

}