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

package com.liferay.dynamic.data.mapping.form.builder.context;

import com.liferay.dynamic.data.mapping.model.DDMStructure;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rafael Praxedes
 */
public class DDMFormBuilderContextRequest {

	public static DDMFormBuilderContextRequest with(
		Optional<DDMStructure> ddmStructureOptional,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Locale locale,
		boolean readOnly) {

		DDMFormBuilderContextRequest ddmFormBuilderContextRequest =
			new DDMFormBuilderContextRequest();

		if (ddmStructureOptional.isPresent()) {
			ddmFormBuilderContextRequest.addProperty(
				"ddmStructure", ddmStructureOptional.get());
		}

		ddmFormBuilderContextRequest.setHttpServletRequest(httpServletRequest);
		ddmFormBuilderContextRequest.setHttpServletResponse(
			httpServletResponse);
		ddmFormBuilderContextRequest.setLocale(locale);
		ddmFormBuilderContextRequest.setReadOnly(readOnly);

		return ddmFormBuilderContextRequest;
	}

	public void addProperty(String key, Object value) {
		_properties.put(key, value);
	}

	public HttpServletRequest getHttpServletRequest() {
		return getProperty("request");
	}

	public HttpServletResponse getHttpServletResponse() {
		return getProperty("response");
	}

	public Locale getLocale() {
		return getProperty("locale");
	}

	public <T> T getProperty(String name) {
		return (T)_properties.get(name);
	}

	public boolean getReadOnly() {
		return getProperty("readOnly");
	}

	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		addProperty("request", httpServletRequest);
	}

	public void setHttpServletResponse(
		HttpServletResponse httpServletResponse) {

		addProperty("response", httpServletResponse);
	}

	public void setLocale(Locale locale) {
		addProperty("locale", locale);
	}

	public void setReadOnly(boolean readOnly) {
		addProperty("readOnly", readOnly);
	}

	private DDMFormBuilderContextRequest() {
	}

	private final Map<String, Object> _properties = new HashMap<>();

}