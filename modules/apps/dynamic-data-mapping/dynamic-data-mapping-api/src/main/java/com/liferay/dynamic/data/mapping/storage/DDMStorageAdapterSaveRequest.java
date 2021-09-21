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

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;

/**
 * @author Leonardo Barros
 */
public final class DDMStorageAdapterSaveRequest {

	public String getClassName() {
		return _className;
	}

	public DDMFormInstance getDDMFormInstance() {
		return _ddmFormInstance;
	}

	public DDMFormValues getDDMFormValues() {
		return _ddmFormValues;
	}

	public long getGroupId() {
		return _groupId;
	}

	public long getPrimaryKey() {
		return _primaryKey;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public long getScopeGroupId() {
		return 0;
	}

	public long getStructureId() {
		return _structureId;
	}

	public long getUserId() {
		return _userId;
	}

	public String getUuid() {
		return _uuid;
	}

	public boolean isInsert() {
		if (_primaryKey == 0) {
			return true;
		}

		return false;
	}

	public static class Builder {

		public static Builder newBuilder(
			long userId, DDMFormValues ddmFormValues) {

			return new Builder(userId, ddmFormValues);
		}

		/**
		 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #newBuilder(long, DDMFormValues)}
		 */
		@Deprecated
		public static Builder newBuilder(
			long userId, long scopeGroupId, DDMFormValues ddmFormValues) {

			return new Builder(userId, ddmFormValues);
		}

		public DDMStorageAdapterSaveRequest build() {
			return _ddmStorageAdapterSaveRequest;
		}

		public Builder withClassName(String className) {
			_ddmStorageAdapterSaveRequest._className = className;

			return this;
		}

		public Builder withDDMFormInstance(DDMFormInstance ddmFormInstance) {
			_ddmStorageAdapterSaveRequest._ddmFormInstance = ddmFormInstance;

			return this;
		}

		public Builder withGroupId(long groupId) {
			_ddmStorageAdapterSaveRequest._groupId = groupId;

			return this;
		}

		public Builder withPrimaryKey(long primaryKey) {
			_ddmStorageAdapterSaveRequest._primaryKey = primaryKey;

			return this;
		}

		public Builder withStructureId(long structureId) {
			_ddmStorageAdapterSaveRequest._structureId = structureId;

			return this;
		}

		public Builder withUuid(String uuid) {
			_ddmStorageAdapterSaveRequest._uuid = uuid;

			return this;
		}

		private Builder(long userId, DDMFormValues ddmFormValues) {
			_ddmStorageAdapterSaveRequest._userId = userId;
			_ddmStorageAdapterSaveRequest._ddmFormValues = ddmFormValues;
		}

		private final DDMStorageAdapterSaveRequest
			_ddmStorageAdapterSaveRequest = new DDMStorageAdapterSaveRequest();

	}

	private DDMStorageAdapterSaveRequest() {
	}

	private String _className;
	private DDMFormInstance _ddmFormInstance;
	private DDMFormValues _ddmFormValues;
	private long _groupId;
	private long _primaryKey;
	private long _structureId;
	private long _userId;
	private String _uuid;

}