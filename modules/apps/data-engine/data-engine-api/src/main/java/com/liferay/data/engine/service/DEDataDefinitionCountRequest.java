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
import com.liferay.data.engine.executor.DECountRequest;
import com.liferay.data.engine.executor.DECountRequestExecutor;

/**
 * @author Marcela Cunha
 */
public class DEDataDefinitionCountRequest implements DECountRequest {

	@Override
	public DEDataDefinitionCountResponse accept(
			DECountRequestExecutor deCountRequestExecutor)
		throws DEDataDefinitionException {

		return deCountRequestExecutor.executeCountRequest(this);
	}

	public long getDEDataDefinitionGroupId() {
		return _deDataDefinitionGroupId;
	}

	public static final class Builder {

		public DEDataDefinitionCountRequest build() {
			return _deDataDefinitionCountRequest;
		}

		public Builder byGroupId(long deDataDefinitionGroupId) {
			_deDataDefinitionCountRequest._deDataDefinitionGroupId =
				deDataDefinitionGroupId;

			return this;
		}

		private final DEDataDefinitionCountRequest
			_deDataDefinitionCountRequest = new DEDataDefinitionCountRequest();

	}

	private DEDataDefinitionCountRequest() {
	}

	private long _deDataDefinitionGroupId;

}