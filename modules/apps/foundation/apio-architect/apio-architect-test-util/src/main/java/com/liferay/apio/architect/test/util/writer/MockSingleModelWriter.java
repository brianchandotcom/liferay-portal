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

import com.liferay.apio.architect.message.json.SingleModelMessageMapper;
import com.liferay.apio.architect.request.RequestInfo;
import com.liferay.apio.architect.single.model.SingleModel;
import com.liferay.apio.architect.test.util.model.RootModel;
import com.liferay.apio.architect.writer.SingleModelWriter;

import java.util.Optional;

import javax.ws.rs.core.HttpHeaders;

/**
 * Provides methods that test {@code SingleModelMessageMapper} objects.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class MockSingleModelWriter {

	/**
	 * Writes a {@link RootModel}, with the hierarchy of embedded models and
	 * multiple fields.
	 *
	 * @param httpHeaders the request's {@code HttpHeaders}
	 * @param singleModelMessageMapper the {@link SingleModelMessageMapper} to
	 *        use for writing the JSON object
	 */
	public static JsonObject write(
		HttpHeaders httpHeaders,
		SingleModelMessageMapper<RootModel> singleModelMessageMapper) {

		RequestInfo requestInfo = getRequestInfo(httpHeaders);

		SingleModel<RootModel> singleModel = new SingleModel<>(
			() -> "first", "root");

		SingleModelWriter<RootModel> singleModelWriter =
			SingleModelWriter.create(
				builder -> builder.singleModel(
					singleModel
				).modelMessageMapper(
					singleModelMessageMapper
				).operationsFunction(
					MockWriterUtil::getOperations
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

		Optional<String> optional = singleModelWriter.write();

		if (!optional.isPresent()) {
			throw new AssertionError("Writer failed to write");
		}

		return new Gson().fromJson(optional.get(), JsonObject.class);
	}

	private MockSingleModelWriter() {
		throw new UnsupportedOperationException();
	}

}