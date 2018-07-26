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