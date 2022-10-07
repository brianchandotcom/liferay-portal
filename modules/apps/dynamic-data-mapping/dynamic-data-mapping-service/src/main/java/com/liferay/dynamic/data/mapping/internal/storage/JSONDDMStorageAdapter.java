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

package com.liferay.dynamic.data.mapping.internal.storage;

import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterDeleteRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterDeleteResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveResponse;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	property = "ddm.storage.adapter.type=json",
	service = DDMStorageAdapter.class
)
public class JSONDDMStorageAdapter implements DDMStorageAdapter {

	@Override
	public DDMStorageAdapterDeleteResponse delete(
			DDMStorageAdapterDeleteRequest ddmStorageAdapterDeleteRequest)
		throws StorageException {

		if (_log.isWarnEnabled()) {
			_log.warn(
				"JSON dynamic data mapping storage adapter is deprecated, " +
					"using default dynamic data mapping storage storage " +
						"adapter");
		}

		return _ddmStorageAdapter.delete(ddmStorageAdapterDeleteRequest);
	}

	@Override
	public DDMStorageAdapterGetResponse get(
			DDMStorageAdapterGetRequest ddmStorageAdapterGetRequest)
		throws StorageException {

		if (_log.isWarnEnabled()) {
			_log.warn(
				"JSON dynamic data mapping storage adapter is deprecated, " +
					"using default dynamic data mapping storage storage " +
						"adapter");
		}

		return _ddmStorageAdapter.get(ddmStorageAdapterGetRequest);
	}

	@Override
	public DDMStorageAdapterSaveResponse save(
			DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest)
		throws StorageException {

		if (_log.isWarnEnabled()) {
			_log.warn(
				"JSON dynamic data mapping storage adapter is deprecated, " +
					"using default dynamic data mapping storage storage " +
						"adapter");
		}

		return _ddmStorageAdapter.save(ddmStorageAdapterSaveRequest);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JSONDDMStorageAdapter.class);

	@Reference(target = "(ddm.storage.adapter.type=default)")
	private DDMStorageAdapter _ddmStorageAdapter;

}