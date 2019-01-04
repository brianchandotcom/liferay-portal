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

import com.liferay.data.engine.model.DEDataDefinition;

/**
 * @author Leonardo Barros
 */
public final class DEDataDefinitionSaveRequest {

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
			_deDataDefinitionSaveRequest._deDataDefinition = deDataDefinition;
		}

		public DEDataDefinitionSaveRequest build() {
			return _deDataDefinitionSaveRequest;
		}

		public Builder inGroup(long groupId) {
			_deDataDefinitionSaveRequest._groupId = groupId;

			return this;
		}

		public Builder onBehalfOf(long userId) {
			_deDataDefinitionSaveRequest._userId = userId;

			return this;
		}

		private final DEDataDefinitionSaveRequest _deDataDefinitionSaveRequest =
			new DEDataDefinitionSaveRequest();

	}

	private DEDataDefinitionSaveRequest() {
	}

	private DEDataDefinition _deDataDefinition;
	private long _groupId;
	private long _userId;

}