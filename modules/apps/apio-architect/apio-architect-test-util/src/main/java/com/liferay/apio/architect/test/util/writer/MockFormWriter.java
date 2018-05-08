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

package com.liferay.apio.architect.test.util.writer;

import static com.liferay.apio.architect.test.util.writer.MockWriterUtil.getRequestInfo;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.liferay.apio.architect.message.json.FormMessageMapper;
import com.liferay.apio.architect.request.RequestInfo;
import com.liferay.apio.architect.test.util.form.MockFormCreator;
import com.liferay.apio.architect.writer.FormWriter;

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
				MockFormCreator.createForm("f", "s")
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