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

package com.liferay.portal.error.code.internal.servlet.taglib;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.MapUtil;

import java.io.PrintWriter;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	property = "mime.type=application/json", service = DynamicInclude.class
)
public class ApplicationJsonPortalErrorCodeDynamicInclude
	extends BasePortalErrorCodeDynamicInclude {

	@Activate
	protected void activate(Map<String, Object> properties) {
		_mimeType = MapUtil.getString(
			properties, "mime.type", "application/json");
	}

	@Override
	protected String getMimeType() {
		return _mimeType;
	}

	@Override
	protected void writeDetailedMessage(
		String message, String requestURI, int statusCode, Throwable throwable,
		PrintWriter printWriter) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put("message", message);
		jsonObject.put("requestURI", message);
		jsonObject.put("statusCode", statusCode);

		if (throwable != null) {
			try {
				jsonObject.put(
					"throwable",
					_jsonFactory.createJSONObject(
						_jsonFactory.serializeThrowable(throwable)));
			}
			catch (JSONException jsone) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to serialize throwable " + throwable.toString(),
						jsone);
				}

				jsonObject.put(
					"throwableSerialized",
					_jsonFactory.serializeThrowable(throwable));
			}
		}

		printWriter.write(jsonObject.toString());
	}

	@Override
	protected void writeMessage(
		int statusCode, String message, PrintWriter printWriter) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put("message", message);
		jsonObject.put("statusCode", statusCode);

		printWriter.write(jsonObject.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ApplicationJsonPortalErrorCodeDynamicInclude.class);

	@Reference
	private JSONFactory _jsonFactory;

	private String _mimeType;

}