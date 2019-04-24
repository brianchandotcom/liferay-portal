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

package com.liferay.portal.error.code.dynamic.include.impl;

import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.MapUtil;

import java.io.PrintWriter;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	property = "error.code.content.type=text/plain",
	service = DynamicInclude.class
)
public class TextPlainPortalErrorCodeDynamicInclude
	extends BasePortalErrorCodeDynamicInclude {

	@Activate
	protected void activate(Map<String, Object> properties) {
		_errorCodeContentType = MapUtil.getString(
			properties, "error.code.content.type", "text/plain");
	}

	@Override
	protected String getContentType() {
		return _errorCodeContentType;
	}

	@Override
	protected void writeMessage(
		String contentType, String message, PrintWriter printWriter) {

		printWriter.write(message);
	}

	private String _errorCodeContentType;

}