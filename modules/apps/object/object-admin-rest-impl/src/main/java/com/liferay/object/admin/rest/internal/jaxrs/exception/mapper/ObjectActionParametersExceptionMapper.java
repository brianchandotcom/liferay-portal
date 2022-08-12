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

package com.liferay.object.admin.rest.internal.jaxrs.exception.mapper;

import com.liferay.object.exception.ObjectActionParametersException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import java.util.Map;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Object.Admin.REST)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Object.Admin.REST.ObjectActionParametersExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class ObjectActionParametersExceptionMapper
	extends BaseExceptionMapper<ObjectActionParametersException> {

	@Override
	protected Problem getProblem(
		ObjectActionParametersException objectActionParametersException) {

		return new Problem(
			String.valueOf(
				_toJSONArray(objectActionParametersException.getMessageKeys())),
			Response.Status.BAD_REQUEST, null,
			"ObjectActionParametersException");
	}

	@Override
	protected boolean isSanitizedMapper() {
		return false;
	}

	private JSONArray _toJSONArray(Map<String, Object> messageKeys) {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (Map.Entry<String, Object> entry : messageKeys.entrySet()) {
			if (entry.getValue() instanceof Map) {
				jsonArray.put(
					JSONUtil.put(
						"fieldName", entry.getKey()
					).put(
						"messages",
						_toJSONArray((Map<String, Object>)entry.getValue())
					));

				continue;
			}

			jsonArray.put(
				JSONUtil.put(
					"fieldName", entry.getKey()
				).put(
					"message",
					_language.get(
						_acceptLanguage.getPreferredLocale(),
						(String)entry.getValue())
				));
		}

		return jsonArray;
	}

	@Context
	private AcceptLanguage _acceptLanguage;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}