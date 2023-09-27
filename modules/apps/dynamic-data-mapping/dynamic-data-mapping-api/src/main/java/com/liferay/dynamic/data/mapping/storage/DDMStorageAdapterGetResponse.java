/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.storage;

/**
 * @author Leonardo Barros
 */
public final class DDMStorageAdapterGetResponse {

	public DDMFormValues getDDMFormValues() {
		return _ddmFormValues;
	}

	public static class Builder {

		public static Builder newBuilder(DDMFormValues ddmFormValues) {
			return new Builder(ddmFormValues);
		}

		public DDMStorageAdapterGetResponse build() {
			return _ddmStorageAdapterGetResponse;
		}

		private Builder(DDMFormValues ddmFormValues) {
			_ddmStorageAdapterGetResponse._ddmFormValues = ddmFormValues;
		}

		private final DDMStorageAdapterGetResponse
			_ddmStorageAdapterGetResponse = new DDMStorageAdapterGetResponse();

	}

	private DDMStorageAdapterGetResponse() {
	}

	private DDMFormValues _ddmFormValues;

}