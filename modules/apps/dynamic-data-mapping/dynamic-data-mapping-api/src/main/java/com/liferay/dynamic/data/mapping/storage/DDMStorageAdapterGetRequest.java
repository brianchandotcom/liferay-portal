/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.storage;

import com.liferay.dynamic.data.mapping.model.DDMForm;

/**
 * @author Leonardo Barros
 */
public final class DDMStorageAdapterGetRequest {

	public DDMForm getDDMForm() {
		return _ddmForm;
	}

	public long getPrimaryKey() {
		return _primaryKey;
	}

	public static class Builder {

		public static Builder newBuilder(long primaryKey, DDMForm ddmForm) {
			return new Builder(primaryKey, ddmForm);
		}

		public DDMStorageAdapterGetRequest build() {
			return _ddmStorageAdapterGetRequest;
		}

		private Builder(long primaryKey, DDMForm ddmForm) {
			_ddmStorageAdapterGetRequest._primaryKey = primaryKey;
			_ddmStorageAdapterGetRequest._ddmForm = ddmForm;
		}

		private final DDMStorageAdapterGetRequest _ddmStorageAdapterGetRequest =
			new DDMStorageAdapterGetRequest();

	}

	private DDMStorageAdapterGetRequest() {
	}

	private DDMForm _ddmForm;
	private long _primaryKey;

}