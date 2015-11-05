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

package com.liferay.portal.kernel.display.context.util;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 */
public abstract class BaseStrutsRequestHelper extends BaseRequestHelper {

	public BaseStrutsRequestHelper(HttpServletRequest request) {
		super(request);
	}

	public String getLogicalPath() {
		if (_logicalPath == null) {
			_logicalPath = ParamUtil.getString(
				getRequest(), ActionRequest.ACTION_NAME);

			if (Validator.isNull(_logicalPath)) {
				_logicalPath = ParamUtil.getString(
					getRequest(), "mvcRenderCommandName");
			}
		}

		return _logicalPath;
	}

	private String _logicalPath;

}