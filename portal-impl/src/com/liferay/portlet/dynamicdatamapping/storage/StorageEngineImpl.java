/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.StorageException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStorageLinkLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.query.Condition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eduardo Lundgren
 */
public class StorageEngineImpl implements StorageEngine {

	public long create(
			long companyId, long ddmStructureId, Fields fields,
			ServiceContext serviceContext)
		throws StorageException {

		StorageAdapter storageAdapter = getStructureStorageAdapter(
			ddmStructureId);

		return storageAdapter.create(
			companyId, ddmStructureId, fields, serviceContext);
	}

	public void deleteByClass(long classPK) throws StorageException {
		StorageAdapter storageAdapter = getClassStorageAdapter(classPK);

		storageAdapter.deleteByClass(classPK);
	}

	public void deleteByDDMStructure(long ddmStructureId)
		throws StorageException {

		StorageAdapter storageAdapter = getStructureStorageAdapter(
			ddmStructureId);

		storageAdapter.deleteByDDMStructure(ddmStructureId);
	}

	public Fields getFields(long classPK) throws StorageException {
		return getFields(classPK, null);
	}

	public Fields getFields(long classPK, List<String> fieldNames)
		throws StorageException {

		StorageAdapter storageAdapter = getClassStorageAdapter(classPK);

		return storageAdapter.getFields(classPK, fieldNames);
	}

	public List<Fields> getFieldsList(
			long ddmStructureId, List<String> fieldNames)
		throws StorageException {

		StorageAdapter storageAdapter = getStructureStorageAdapter(
			ddmStructureId);

		return storageAdapter.getFieldsList(ddmStructureId, fieldNames);
	}

	public List<Fields> getFieldsList(
			long ddmStructureId, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws StorageException {

		StorageAdapter storageAdapter = getStructureStorageAdapter(
			ddmStructureId);

		return storageAdapter.getFieldsList(
			ddmStructureId, fieldNames, orderByComparator);
	}

	public List<Fields> getFieldsList(
			long ddmStructureId, long[] classPKs, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws StorageException {

		StorageAdapter storageAdapter = getStructureStorageAdapter(
			ddmStructureId);

		return storageAdapter.getFieldsList(
			ddmStructureId, classPKs, fieldNames, orderByComparator);
	}

	public List<Fields> getFieldsList(
			long ddmStructureId, long[] classPKs,
			OrderByComparator orderByComparator)
		throws StorageException {

		StorageAdapter storageAdapter = getStructureStorageAdapter(
			ddmStructureId);

		return storageAdapter.getFieldsList(
			ddmStructureId, classPKs, orderByComparator);
	}

	public Map<Long, Fields> getFieldsMap(long ddmStructureId, long[] classPKs)
		throws StorageException {

		StorageAdapter storageAdapter = getStructureStorageAdapter(
			ddmStructureId);

		return storageAdapter.getFieldsMap(ddmStructureId, classPKs);
	}

	public Map<Long, Fields> getFieldsMap(
			long ddmStructureId, long[] classPKs, List<String> fieldNames)
		throws StorageException {

		StorageAdapter storageAdapter = getStructureStorageAdapter(
			ddmStructureId);

		return storageAdapter.getFieldsMap(
			ddmStructureId, classPKs, fieldNames);
	}

	public List<Fields> query(
			long ddmStructureId, List<String> fieldNames, Condition condition,
			OrderByComparator orderByComparator)
		throws StorageException {

		StorageAdapter storageAdapter = getStructureStorageAdapter(
			ddmStructureId);

		return storageAdapter.query(
			ddmStructureId, fieldNames, condition, orderByComparator);
	}

	public int queryCount(long ddmStructureId, Condition condition)
		throws StorageException {

		StorageAdapter storageAdapter = getStructureStorageAdapter(
			ddmStructureId);

		return storageAdapter.queryCount(ddmStructureId, condition);
	}

	public void setDefaultStorageAdapter(StorageAdapter defaultStorageAdapter) {
		_defaultStorageAdapter = defaultStorageAdapter;
	}

	public void setStorageAdapters(
		Map<String, StorageAdapter> storageAdapters) {

		_storageAdapters = storageAdapters;
	}

	public void update(
			long classPK, Fields fields, boolean mergeFields,
			ServiceContext serviceContext)
		throws StorageException {

		StorageAdapter storageAdapter = getClassStorageAdapter(classPK);

		storageAdapter.update(classPK, fields, mergeFields, serviceContext);
	}

	public void update(
			long classPK, Fields fields, ServiceContext serviceContext)
		throws StorageException {

		StorageAdapter storageAdapter = getClassStorageAdapter(classPK);

		storageAdapter.update(classPK, fields, serviceContext);
	}

	protected StorageAdapter getClassStorageAdapter(long classPK)
		throws StorageException {

		try {
			DDMStorageLink ddmStorageLink =
				DDMStorageLinkLocalServiceUtil.getClassStorageLink(classPK);

			return getStorageAdapter(ddmStorageLink.getStorageType());
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	protected StorageAdapter getStorageAdapter(String storageType) {
		StorageAdapter storageAdapter = _storageAdapters.get(storageType);

		if (storageAdapter == null) {
			storageAdapter = _defaultStorageAdapter;
		}

		return storageAdapter;
	}

	protected StorageAdapter getStructureStorageAdapter(long ddmStructureId)
		throws StorageException {

		try {
			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.getDDMStructure(ddmStructureId);

			return getStorageAdapter(ddmStructure.getStorageType());
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	private StorageAdapter _defaultStorageAdapter;
	private Map<String, StorageAdapter> _storageAdapters =
		new HashMap<String, StorageAdapter>();

}