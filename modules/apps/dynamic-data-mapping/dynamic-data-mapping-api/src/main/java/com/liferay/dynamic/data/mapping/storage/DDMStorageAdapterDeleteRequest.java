/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.storage;

/**
 * @author Leonardo Barros
 */
public final class DDMStorageAdapterDeleteRequest {

	public long getPrimaryKey() {
		return _primaryKey;
	}

	public static class Builder {

		public static Builder newBuilder(long primaryKey) {
			return new Builder(primaryKey);
		}

		public DDMStorageAdapterDeleteRequest build() {
			return _ddmStorageAdapterDeleteRequest;
		}

		private Builder(long primaryKey) {
			_ddmStorageAdapterDeleteRequest._primaryKey = primaryKey;
		}

		private final DDMStorageAdapterDeleteRequest
			_ddmStorageAdapterDeleteRequest =
				new DDMStorageAdapterDeleteRequest();

	}

	private DDMStorageAdapterDeleteRequest() {
	}

	private long _primaryKey;

}