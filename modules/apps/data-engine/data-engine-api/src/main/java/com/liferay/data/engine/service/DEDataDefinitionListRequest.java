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
import com.liferay.data.engine.executor.DEListRequest;
import com.liferay.data.engine.executor.DEListRequestExecutor;

/**
 * @author Gabriel Albuquerque
 */
public final class DEDataDefinitionListRequest implements DEListRequest {

	@Override
	public DEDataDefinitionListResponse accept(
			DEListRequestExecutor deListRequestExecutor)
		throws DEDataDefinitionException {

		return deListRequestExecutor.executeListRequest(this);
	}

	public long getGroupId() {
		return _groupId;
	}

	public static final class Builder {

		public DEDataDefinitionListRequest build() {
			return _deDataDefinitionListRequest;
		}

		public Builder byGroupId(long groupId) {
			_deDataDefinitionListRequest._groupId = groupId;

			return this;
		}

		private final DEDataDefinitionListRequest _deDataDefinitionListRequest =
			new DEDataDefinitionListRequest();

	}

	private DEDataDefinitionListRequest() {
	}

	private long _groupId;

}