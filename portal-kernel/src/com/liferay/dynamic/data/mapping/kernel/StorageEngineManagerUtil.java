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

package com.liferay.dynamic.data.mapping.kernel;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Rafael Praxedes
 */
public class StorageEngineManagerUtil {

	public static long create(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws PortalException {

		return _storageEngineManager.create(
			companyId, ddmStructureId, ddmFormValues, serviceContext);
	}

	public static void deleteByClass(long classPK) throws PortalException {
		_storageEngineManager.deleteByClass(classPK);
	}

	public static DDMFormValues getDDMFormValues(long classPK)
		throws PortalException {

		return _storageEngineManager.getDDMFormValues(classPK);
	}

	public static DDMFormValues getDDMFormValues(
			long ddmStructureId, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException {

		return _storageEngineManager.getDDMFormValues(
			ddmStructureId, fieldNamespace, serviceContext);
	}

	public static void update(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws PortalException {

		_storageEngineManager.update(classPK, ddmFormValues, serviceContext);
	}

	private static volatile StorageEngineManager _storageEngineManager =
		ServiceProxyFactory.newServiceTrackedInstance(
			StorageEngineManager.class, StorageEngineManagerUtil.class,
			"_storageEngineManager", false);

}