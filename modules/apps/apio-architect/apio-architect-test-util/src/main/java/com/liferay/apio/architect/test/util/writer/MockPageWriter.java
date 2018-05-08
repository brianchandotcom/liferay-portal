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

import static com.liferay.apio.architect.operation.Method.POST;
import static com.liferay.apio.architect.test.util.form.MockFormCreator.createForm;
import static com.liferay.apio.architect.test.util.writer.MockWriterUtil.getRequestInfo;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.liferay.apio.architect.message.json.PageMessageMapper;
import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.request.RequestInfo;
import com.liferay.apio.architect.test.util.model.RootModel;
import com.liferay.apio.architect.uri.Path;
import com.liferay.apio.architect.writer.PageWriter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.HttpHeaders;

/**
 * Provides methods that test {@code PageMessageMapper} objects.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class MockPageWriter {

	/**
	 * Writes a Collection of {@link RootModel}, with the hierarchy of embedded
	 * models and multiple fields.
	 *
	 * @param httpHeaders the request's {@code HttpHeaders}
	 * @param pageMessageMapper the {@link PageMessageMapper} to use for writing
	 *        the JSON object
	 */
	public static JsonObject write(
		HttpHeaders httpHeaders,
		PageMessageMapper<RootModel> pageMessageMapper) {

		RequestInfo requestInfo = getRequestInfo(httpHeaders);

		Collection<RootModel> items = Arrays.asList(
			() -> "1", () -> "2", () -> "3");

		PageItems<RootModel> pageItems = new PageItems<>(items, 9);

		Pagination pagination = new Pagination(3, 2);

		Path path = new Path("name", "id");

		List<Operation> operations = Collections.singletonList(
			new Operation(createForm("c", "p"), POST, "create-operation"));

		Page<RootModel> page = new Page<>(
			"root", pageItems, pagination, path, operations);

		PageWriter<RootModel> pageWriter = PageWriter.create(
			builder -> builder.page(
				page
			).pageMessageMapper(
				pageMessageMapper
			).pathFunction(
				MockWriterUtil::identifierToPath
			).resourceNameFunction(
				__ -> Optional.of("models")
			).representorFunction(
				MockWriterUtil::getRepresentorOptional
			).requestInfo(
				requestInfo
			).singleModelFunction(
				MockWriterUtil::getSingleModel
			).build());

		return new Gson().fromJson(pageWriter.write(), JsonObject.class);
	}

	private MockPageWriter() {
		throw new UnsupportedOperationException();
	}

}