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

package com.liferay.bean.portlet.cdi.extension.internal.mvc;

import javax.mvc.binding.ParamError;

/**
 * @author  Neil Griffin
 */
public class ParamErrorImpl implements ParamError {

	public ParamErrorImpl(String message, String paramName) {
		_message = message;
		_paramName = paramName;
	}

	@Override
	public String getMessage() {
		return _message;
	}

	@Override
	public String getParamName() {
		return _paramName;
	}

	private final String _message;
	private final String _paramName;

}