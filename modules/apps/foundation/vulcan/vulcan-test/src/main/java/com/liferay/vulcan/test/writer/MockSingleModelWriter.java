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

package com.liferay.vulcan.test.writer;

import static com.liferay.vulcan.test.resource.MockRepresentorCreator.createFirstEmbeddedModelRepresentor;
import static com.liferay.vulcan.test.resource.MockRepresentorCreator.createRootModelRepresentor;
import static com.liferay.vulcan.test.resource.MockRepresentorCreator.createSecondEmbeddedModelRepresentor;
import static com.liferay.vulcan.test.resource.MockRepresentorCreator.createThirdEmbeddedModelRepresentor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.liferay.vulcan.message.json.SingleModelMessageMapper;
import com.liferay.vulcan.pagination.SingleModel;
import com.liferay.vulcan.request.RequestInfo;
import com.liferay.vulcan.resource.Representor;
import com.liferay.vulcan.resource.identifier.Identifier;
import com.liferay.vulcan.resource.identifier.StringIdentifier;
import com.liferay.vulcan.test.resource.model.FirstEmbeddedModel;
import com.liferay.vulcan.test.resource.model.RootModel;
import com.liferay.vulcan.test.resource.model.SecondEmbeddedModel;
import com.liferay.vulcan.uri.Path;
import com.liferay.vulcan.writer.SingleModelWriter;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

import javax.ws.rs.core.HttpHeaders;

/**
 * This class provides methods that can be used for testing single model message
 * mappers.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 * @review
 */
public class MockSingleModelWriter {

	/**
	 * Returns the corresponding {@link Representor} for a given model class.
	 *
	 * @param  modelClass the model class
	 * @return the {@code Representor} of the model class
	 * @review
	 */
	public static <T> Optional<Representor<?, ? extends Identifier>>
		getRepresentorOptional(Class<T> modelClass) {

		if (modelClass == RootModel.class) {
			return Optional.of(createRootModelRepresentor(false));
		}

		if (modelClass == FirstEmbeddedModel.class) {
			return Optional.of(createFirstEmbeddedModelRepresentor());
		}

		if (modelClass == SecondEmbeddedModel.class) {
			return Optional.of(createSecondEmbeddedModelRepresentor());
		}

		return Optional.of(createThirdEmbeddedModelRepresentor());
	}

	/**
	 * Returns a {@link RequestInfo} with the provided {@link HttpHeaders}, a
	 * mock {@link com.liferay.vulcan.url.ServerURL}, a mock {@link
	 * com.liferay.vulcan.response.control.Embedded} request and a mock {@link
	 * com.liferay.vulcan.language.Language} with {@link Locale#US}.
	 *
	 * @param  httpHeaders the HTTP headers
	 * @return the request info
	 * @review
	 */
	public static RequestInfo getRequestInfo(HttpHeaders httpHeaders) {
		return RequestInfo.create(
			builder -> builder.httpHeaders(
				httpHeaders
			).serverURL(
				() -> "localhost"
			).embedded(
				() -> Arrays.asList("embedded1", "embedded1.embedded")::contains
			).language(
				() -> Locale.US
			).build());
	}

	/**
	 * Returns a mock {@link Path} from an {@link Identifier}. The {@code
	 * Identifier} must be of type {@link StringIdentifier}. If the {@code
	 * Identifier} is not a {@code StringIdentifier}, {@code Optional#empty()}
	 * is returned.
	 *
	 * @param  identifier the {@code Identifier}
	 * @param  modelClass the class of the model
	 * @return the {@code Path} originated from the {@code Identifier}, if its
	 *         of type {@code StringIdentifier}; {@code Optional#empty()}
	 *         otherwise
	 * @review
	 */
	public static Optional<Path> identifierToPath(
		Identifier identifier, Class<?> modelClass) {

		if (!(identifier instanceof StringIdentifier)) {
			return Optional.empty();
		}

		StringIdentifier stringIdentifier = (StringIdentifier)identifier;

		Function<String, Optional<Path>> function =
			name -> Optional.of(new Path(name, stringIdentifier.getId()));

		if (modelClass == RootModel.class) {
			return function.apply("model");
		}

		if (modelClass == FirstEmbeddedModel.class) {
			return function.apply("first-inner-model");
		}

		if (modelClass == SecondEmbeddedModel.class) {
			return function.apply("second-inner-model");
		}

		return function.apply("third-inner-model");
	}

	/**
	 * Writes a model of type {@link RootModel}, with hierarchy of embedded
	 * models and multiple fields.
	 *
	 * @param  httpHeaders the HTTP headers from the request
	 * @param  singleModelMessageMapper the message mapper to use for writing
	 *         the json object
	 * @review
	 */
	public static JsonObject write(
		HttpHeaders httpHeaders,
		SingleModelMessageMapper<RootModel> singleModelMessageMapper) {

		RequestInfo requestInfo = getRequestInfo(httpHeaders);

		SingleModel<RootModel> singleModel = new SingleModel<>(
			() -> "first", RootModel.class);

		SingleModelWriter<RootModel> singleModelWriter =
			SingleModelWriter.create(
				builder -> builder.singleModel(
					singleModel
				).modelMessageMapper(
					singleModelMessageMapper
				).pathFunction(
					(identifier, identifierClass, clazz) ->
						identifierToPath(identifier, clazz)
				).resourceNameFunction(
					__ -> Optional.of("models")
				).representorFunction(
					MockSingleModelWriter::getRepresentorOptional
				).requestInfo(
					requestInfo
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