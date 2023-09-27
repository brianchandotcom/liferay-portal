/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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