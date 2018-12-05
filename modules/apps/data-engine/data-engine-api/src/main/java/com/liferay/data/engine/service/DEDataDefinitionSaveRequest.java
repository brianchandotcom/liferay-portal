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

package com.liferay.data.engine.service;

import com.liferay.data.engine.exception.DEDataDefinitionException;
import com.liferay.data.engine.executor.DESaveRequest;
import com.liferay.data.engine.executor.DESaveRequestExecutor;
import com.liferay.data.engine.model.DEDataDefinition;

/**
 * @author Leonardo Barros
 */
public final class DEDataDefinitionSaveRequest implements DESaveRequest {

	@Override
	public DEDataDefinitionSaveResponse accept(
			DESaveRequestExecutor deSaveRequestExecutor)
		throws DEDataDefinitionException {

		return deSaveRequestExecutor.executeSaveRequest(this);
	}

	public DEDataDefinition getDEDataDefinition() {
		return _deDataDefinition;
	}

	public long getGroupId() {
		return _groupId;
	}

	public long getUserId() {
		return _userId;
	}

	public static final class Builder {

		public Builder(DEDataDefinition deDataDefinition) {
			_dataDefinitionSaveRequest._deDataDefinition = deDataDefinition;
		}

		public DEDataDefinitionSaveRequest build() {
			return _dataDefinitionSaveRequest;
		}

		public Builder inGroup(long groupId) {
			_dataDefinitionSaveRequest._groupId = groupId;

			return this;
		}

		public Builder onBehalfOf(long userId) {
			_dataDefinitionSaveRequest._userId = userId;

			return this;
		}

		private final DEDataDefinitionSaveRequest _dataDefinitionSaveRequest =
			new DEDataDefinitionSaveRequest();

	}

	private DEDataDefinitionSaveRequest() {
	}

	private DEDataDefinition _deDataDefinition;
	private long _groupId;
	private long _userId;

}