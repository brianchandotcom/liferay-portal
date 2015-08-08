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

package com.liferay.dynamic.data.mapping.storage.impl;

import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.model.DDMStorageLink;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageAdapter;
import com.liferay.dynamic.data.mapping.storage.StorageAdapterRegistryUtil;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.service.ServiceContext;

/**
 * @author Eduardo Lundgren
 */
@OSGiBeanProperties(service = StorageEngine.class)
public class StorageEngineImpl implements StorageEngine {

	@Override
	public long create(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws StorageException {

		StorageAdapter storageAdapter = getStructureStorageAdapter(
			ddmStructureId);

		return storageAdapter.create(
			companyId, ddmStructureId, ddmFormValues, serviceContext);
	}

	@Override
	public void deleteByClass(long classPK) throws StorageException {
		StorageAdapter storageAdapter = getClassStorageAdapter(classPK);

		storageAdapter.deleteByClass(classPK);
	}

	@Override
	public void deleteByDDMStructure(long ddmStructureId)
		throws StorageException {

		StorageAdapter storageAdapter = getStructureStorageAdapter(
			ddmStructureId);

		storageAdapter.deleteByDDMStructure(ddmStructureId);
	}

	@Override
	public DDMFormValues getDDMFormValues(long classPK)
		throws StorageException {

		StorageAdapter storageAdapter = getClassStorageAdapter(classPK);

		return storageAdapter.getDDMFormValues(classPK);
	}

	@Override
	public String getStorageType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws StorageException {

		StorageAdapter storageAdapter = getClassStorageAdapter(classPK);

		storageAdapter.update(classPK, ddmFormValues, serviceContext);
	}

	protected StorageAdapter getClassStorageAdapter(long classPK)
		throws StorageException {

		try {
			DDMStorageLink ddmStorageLink =
				DDMStorageLinkLocalServiceUtil.getClassStorageLink(classPK);

			return getStorageAdapter(ddmStorageLink.getStorageType());
		}
		catch (NoSuchStructureException nsse) {
			return StorageAdapterRegistryUtil.getDefaultStorageAdapter();
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	protected StorageAdapter getStorageAdapter(String storageType) {
		StorageAdapter storageAdapter =
			StorageAdapterRegistryUtil.getStorageAdapter(storageType);

		if (storageAdapter != null) {
			return storageAdapter;
		}

		return StorageAdapterRegistryUtil.getDefaultStorageAdapter();
	}

	protected StorageAdapter getStructureStorageAdapter(long ddmStructureId)
		throws StorageException {

		try {
			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.getDDMStructure(ddmStructureId);

			return getStorageAdapter(ddmStructure.getStorageType());
		}
		catch (NoSuchStructureException nsse) {
			return StorageAdapterRegistryUtil.getDefaultStorageAdapter();
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

}