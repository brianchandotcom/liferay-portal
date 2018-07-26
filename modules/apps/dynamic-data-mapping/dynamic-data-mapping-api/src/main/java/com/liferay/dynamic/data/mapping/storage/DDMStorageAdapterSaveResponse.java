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