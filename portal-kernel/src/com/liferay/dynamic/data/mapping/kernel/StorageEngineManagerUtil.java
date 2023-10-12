/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.kernel;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Rafael Praxedes
 */
public class StorageEngineManagerUtil {

	public static long create(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws PortalException {

		StorageEngineManager storageEngineManager =
			_storageEngineManagerSnapshot.get();

		return storageEngineManager.create(
			companyId, ddmStructureId, ddmFormValues, serviceContext);
	}

	public static void deleteByClass(long classPK) throws PortalException {
		StorageEngineManager storageEngineManager =
			_storageEngineManagerSnapshot.get();

		storageEngineManager.deleteByClass(classPK);
	}

	public static DDMFormValues getDDMFormValues(long classPK)
		throws PortalException {

		StorageEngineManager storageEngineManager =
			_storageEngineManagerSnapshot.get();

		return storageEngineManager.getDDMFormValues(classPK);
	}

	public static DDMFormValues getDDMFormValues(
			long ddmStructureId, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException {

		StorageEngineManager storageEngineManager =
			_storageEngineManagerSnapshot.get();

		return storageEngineManager.getDDMFormValues(
			ddmStructureId, fieldNamespace, serviceContext);
	}

	public static void update(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws PortalException {

		StorageEngineManager storageEngineManager =
			_storageEngineManagerSnapshot.get();

		storageEngineManager.update(classPK, ddmFormValues, serviceContext);
	}

	private static final Snapshot<StorageEngineManager>
		_storageEngineManagerSnapshot = new Snapshot<>(
			StorageEngineManagerUtil.class, StorageEngineManager.class);

}