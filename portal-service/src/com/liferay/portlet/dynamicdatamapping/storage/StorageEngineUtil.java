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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.StorageException;
import com.liferay.portlet.dynamicdatamapping.storage.query.Condition;

import java.util.List;
import java.util.Map;

/**
 * @author Eduardo Lundgren
 */
public class StorageEngineUtil {

	public static long create(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws StorageException {

		return getStorageEngine().create(
			companyId, ddmStructureId, ddmFormValues, serviceContext);
	}

	public static long create(
			long companyId, long ddmStructureId, Fields fields,
			ServiceContext serviceContext)
		throws StorageException {

		return getStorageEngine().create(
			companyId, ddmStructureId, fields, serviceContext);
	}

	public static void deleteByClass(long classPK) throws StorageException {
		getStorageEngine().deleteByClass(classPK);
	}

	public static void deleteByDDMStructure(long ddmStructureId)
		throws StorageException {

		getStorageEngine().deleteByDDMStructure(ddmStructureId);
	}

	public static DDMFormValues getDDMFormValues(long classPK)
		throws StorageException {

		return getStorageEngine().getDDMFormValues(classPK);
	}

	public static Fields getFields(long classPK) throws StorageException {
		return getStorageEngine().getFields(classPK);
	}

	public static Fields getFields(long classPK, List<String> fieldNames)
		throws StorageException {

		return getStorageEngine().getFields(classPK, fieldNames);
	}

	public static List<Fields> getFieldsList(
			long ddmStructureId, List<String> fieldNames)
		throws StorageException {

		return getStorageEngine().getFieldsList(ddmStructureId, fieldNames);
	}

	public static List<Fields> getFieldsList(
			long ddmStructureId, List<String> fieldNames,
			OrderByComparator<Fields> orderByComparator)
		throws StorageException {

		return getStorageEngine().getFieldsList(
			ddmStructureId, fieldNames, orderByComparator);
	}

	public static List<Fields> getFieldsList(
			long ddmStructureId, long[] classPKs, List<String> fieldNames,
			OrderByComparator<Fields> orderByComparator)
		throws StorageException {

		return getStorageEngine().getFieldsList(
			ddmStructureId, classPKs, fieldNames, orderByComparator);
	}

	public static List<Fields> getFieldsList(
			long ddmStructureId, long[] classPKs,
			OrderByComparator<Fields> orderByComparator)
		throws StorageException {

		return getStorageEngine().getFieldsList(
			ddmStructureId, classPKs, orderByComparator);
	}

	public static Map<Long, Fields> getFieldsMap(
			long ddmStructureId, long[] classPKs)
		throws StorageException {

		return getStorageEngine().getFieldsMap(ddmStructureId, classPKs);
	}

	public static Map<Long, Fields> getFieldsMap(
			long ddmStructureId, long[] classPKs, List<String> fieldNames)
		throws StorageException {

		return getStorageEngine().getFieldsMap(
			ddmStructureId, classPKs, fieldNames);
	}

	public static StorageEngine getStorageEngine() {
		PortalRuntimePermission.checkGetBeanProperty(StorageEngineUtil.class);

		return _storageEngine;
	}

	public static List<Fields> query(
			long ddmStructureId, List<String> fieldNames, Condition condition,
			OrderByComparator<Fields> orderByComparator)
		throws StorageException {

		return getStorageEngine().query(
			ddmStructureId, fieldNames, condition, orderByComparator);
	}

	public static int queryCount(long ddmStructureId, Condition condition)
		throws StorageException {

		return getStorageEngine().queryCount(ddmStructureId, condition);
	}

	public static void update(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws StorageException {

		getStorageEngine().update(classPK, ddmFormValues, serviceContext);
	}

	public static void update(
			long classPK, Fields fields, boolean mergeFields,
			ServiceContext serviceContext)
		throws StorageException {

		getStorageEngine().update(classPK, fields, mergeFields, serviceContext);
	}

	public static void update(
			long classPK, Fields fields, ServiceContext serviceContext)
		throws StorageException {

		getStorageEngine().update(classPK, fields, serviceContext);
	}

	public void setStorageEngine(StorageEngine storageEngine) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_storageEngine = storageEngine;
	}

	private static StorageEngine _storageEngine;

}