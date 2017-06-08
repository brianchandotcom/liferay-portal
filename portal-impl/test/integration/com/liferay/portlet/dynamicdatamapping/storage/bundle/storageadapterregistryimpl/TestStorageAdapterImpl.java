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

package com.liferay.portlet.dynamicdatamapping.storage.bundle.storageadapterregistryimpl;

import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.storage.StorageAdapter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Philip Jones
 */
@Component(
	immediate = true,
	property = {
		"layout.type=testStorageAdapterImpl",
		"service.ranking:Integer=" + Integer.MAX_VALUE
	}
)
public class TestStorageAdapterImpl implements StorageAdapter {

	@Override
	public long create(
		long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
		ServiceContext serviceContext) {

		return 0;
	}

	@Override
	public void deleteByClass(long classPK) {
	}

	@Override
	public void deleteByDDMStructure(long ddmStructureId) {
	}

	@Override
	public DDMFormValues getDDMFormValues(long classPK) {
		return null;
	}

	@Override
	public String getStorageType() {
		return "testStorageAdapterImpl";
	}

	@Override
	public void update(
		long classPK, DDMFormValues ddmFormValues,
		ServiceContext serviceContext) {
	}

}