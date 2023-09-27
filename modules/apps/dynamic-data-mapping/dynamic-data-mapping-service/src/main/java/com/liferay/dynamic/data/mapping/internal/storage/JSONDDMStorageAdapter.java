/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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