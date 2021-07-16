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

package com.liferay.talend.properties.resource;

import com.liferay.talend.common.oas.constants.OASConstants;

/**
 * @author Zoltán Takács
 */
public enum Operation {

	Delete(OASConstants.OPERATION_DELETE), Get(OASConstants.OPERATION_GET),
	Insert(OASConstants.OPERATION_POST), Replace(OASConstants.OPERATION_PUT),
	Unavailable("noop"), Update(OASConstants.OPERATION_PATCH);

	public static Operation toOperation(String methodName) {
		for (Operation operation : values()) {
			if (methodName.equals(operation._httpMethod)) {
				return operation;
			}
		}

		return Unavailable;
	}

	public String getHttpMethod() {
		return _httpMethod;
	}

	private Operation(String httpMethod) {
		_httpMethod = httpMethod;
	}

	private final String _httpMethod;

}