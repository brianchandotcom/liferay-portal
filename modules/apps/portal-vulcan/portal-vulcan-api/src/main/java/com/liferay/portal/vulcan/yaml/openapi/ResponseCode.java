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

package com.liferay.portal.vulcan.yaml.openapi;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Javier de Arcos
 */
public class ResponseCode {

	public ResponseCode(String code) {
		if (StringUtil.equals(code, "default")) {
			_defaultResponse = true;
			_httpCode = null;
		}
		else {
			_defaultResponse = false;
			_httpCode = GetterUtil.getIntegerStrict(code);
		}
	}

	public Integer getHttpCode() {
		return _httpCode;
	}

	public boolean isDefaultResponse() {
		return _defaultResponse;
	}

	@Override
	public String toString() {
		if (_defaultResponse) {
			return "default";
		}

		return _httpCode.toString();
	}

	private final boolean _defaultResponse;
	private final Integer _httpCode;

}