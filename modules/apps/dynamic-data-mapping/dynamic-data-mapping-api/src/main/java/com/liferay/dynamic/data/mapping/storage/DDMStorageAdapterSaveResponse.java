/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.storage;

/**
 * @author Leonardo Barros
 */
public final class DDMStorageAdapterSaveResponse {

	public long getPrimaryKey() {
		return _primaryKey;
	}

	public static class Builder {

		public static Builder newBuilder(long primaryKey) {
			return new Builder(primaryKey);
		}

		public DDMStorageAdapterSaveResponse build() {
			return _ddmStorageAdapterSaveResponse;
		}

		private Builder(long primaryKey) {
			_ddmStorageAdapterSaveResponse._primaryKey = primaryKey;
		}

		private final DDMStorageAdapterSaveResponse
			_ddmStorageAdapterSaveResponse =
				new DDMStorageAdapterSaveResponse();

	}

	private DDMStorageAdapterSaveResponse() {
	}

	private long _primaryKey;

}