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

package com.liferay.talend.tliferayoutput;

import com.liferay.talend.common.oas.constants.OASConstants;

/**
 * @author Zoltán Takács
 */
public enum Action {

	Delete(OASConstants.OPERATION_DELETE), Insert(OASConstants.OPERATION_POST),
	Unavailable("noop"), Update(OASConstants.OPERATION_PATCH);

	public static Action toAction(String methodName) {
		for (Action action : values()) {
			if (methodName.equals(action._method)) {
				return action;
			}
		}

		return Unavailable;
	}

	public String getMethodName() {
		return _method;
	}

	private Action(String method) {
		_method = method;
	}

	private final String _method;

}