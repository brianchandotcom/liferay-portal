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
import com.liferay.data.engine.executor.DEGetRequest;
import com.liferay.data.engine.executor.DEGetRequestExecutor;

/**
 * @author Leonardo Barros
 */
public final class DEDataDefinitionGetRequest implements DEGetRequest {

	@Override
	public DEDataDefinitionGetResponse accept(
			DEGetRequestExecutor deGetRequestExecutor)
		throws DEDataDefinitionException {

		return deGetRequestExecutor.executeGetRequest(this);
	}

	public long getDEDataDefinitionId() {
		return _deDataDefinitionId;
	}

	public static final class Builder {

		public DEDataDefinitionGetRequest build() {
			return _deDataDefinitionGetRequest;
		}

		public Builder byId(long deDataDefinitionId) {
			_deDataDefinitionGetRequest._deDataDefinitionId =
				deDataDefinitionId;

			return this;
		}

		private final DEDataDefinitionGetRequest _deDataDefinitionGetRequest =
			new DEDataDefinitionGetRequest();

	}

	private DEDataDefinitionGetRequest() {
	}

	private long _deDataDefinitionId;

}