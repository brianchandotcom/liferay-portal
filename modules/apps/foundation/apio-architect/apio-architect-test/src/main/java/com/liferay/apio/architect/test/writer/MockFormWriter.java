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

package com.liferay.apio.architect.test.writer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.liferay.apio.architect.message.json.FormMessageMapper;
import com.liferay.apio.architect.request.RequestInfo;
import com.liferay.apio.architect.test.form.MockFormCreator;
import com.liferay.apio.architect.writer.FormWriter;

import java.util.Locale;

import javax.ws.rs.core.HttpHeaders;

/**
 * Provides methods that test {@link FormMessageMapper} objects.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class MockFormWriter {

	/**
	 * Returns a {@link RequestInfo} with the provided {@code HttpHeaders}, a
	 * mock {@link com.liferay.apio.architect.url.ServerURL}, and a mock {@code
	 * javax.servlet.http.HttpServletRequest}.
	 *
	 * @param  httpHeaders the HTTP headers
	 * @return the {@code RequestInfo}
	 */
	public static RequestInfo getRequestInfo(HttpHeaders httpHeaders) {
		return RequestInfo.create(
			builder -> builder.httpHeaders(
				httpHeaders
			).httpServletRequest(
				null
			).serverURL(
				() -> "localhost"
			).embedded(
				__ -> false
			).fields(
				__ -> string -> true
			).language(
				Locale::getDefault
			).build());
	}

	/**
	 * Writes a {@link com.liferay.apio.architect.form.Form} object.
	 *
	 * @param httpHeaders the request's HTTP headers
	 * @param formMessageMapper the {@code FormMessageMapper} to use for writing
	 *        the JSON object
	 */
	public static JsonObject write(
		HttpHeaders httpHeaders, FormMessageMapper formMessageMapper) {

		RequestInfo requestInfo = getRequestInfo(httpHeaders);

		FormWriter formWriter = FormWriter.create(
			builder -> builder.form(
				MockFormCreator.createForm()
			).formMessageMapper(
				formMessageMapper
			).requestInfo(
				requestInfo
			).build());

		return new Gson().fromJson(formWriter.write(), JsonObject.class);
	}

	private MockFormWriter() {
		throw new UnsupportedOperationException();
	}

}