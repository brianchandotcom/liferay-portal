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