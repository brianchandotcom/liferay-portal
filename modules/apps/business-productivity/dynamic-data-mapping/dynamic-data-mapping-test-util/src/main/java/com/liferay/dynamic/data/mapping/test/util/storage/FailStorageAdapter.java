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

package com.liferay.dynamic.data.mapping.test.util.storage;

import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.storage.BaseStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Rafael Praxedes
 */
public class FailStorageAdapter extends BaseStorageAdapter {

	public static final String STORAGE_TYPE = "Fail";

	@Override
	public String getStorageType() {
		return STORAGE_TYPE;
	}

	@Override
	protected long doCreate(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws Exception {

		throw new StorageException();
	}

	@Override
	protected void doDeleteByClass(long classPK) throws Exception {
		throw new StorageException();
	}

	@Override
	protected void doDeleteByDDMStructure(long ddmStructureId)
		throws Exception {

		throw new StorageException();
	}

	@Override
	protected DDMFormValues doGetDDMFormValues(long classPK) throws Exception {
		throw new StorageException();
	}

	@Override
	protected void doUpdate(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws Exception {

		throw new StorageException();
	}

}