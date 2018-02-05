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

import static com.liferay.apio.architect.operation.Method.DELETE;
import static com.liferay.apio.architect.operation.Method.UPDATE;
import static com.liferay.apio.architect.test.util.form.MockFormCreator.createForm;
import static com.liferay.apio.architect.test.util.representor.MockRepresentorCreator.createFirstEmbeddedModelRepresentor;
import static com.liferay.apio.architect.test.util.representor.MockRepresentorCreator.createRootModelRepresentor;
import static com.liferay.apio.architect.test.util.representor.MockRepresentorCreator.createSecondEmbeddedModelRepresentor;
import static com.liferay.apio.architect.test.util.representor.MockRepresentorCreator.createThirdEmbeddedModelRepresentor;

import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.request.RequestInfo;
import com.liferay.apio.architect.test.util.model.FirstEmbeddedModel;
import com.liferay.apio.architect.test.util.model.RootModel;
import com.liferay.apio.architect.test.util.model.SecondEmbeddedModel;
import com.liferay.apio.architect.uri.Path;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

import javax.ws.rs.core.HttpHeaders;

/**
 * Provides utility functions for mock writers.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class MockWriterUtil {

	/**
	 * Returns a model class's {@code Operations}.
	 *
	 * @param  modelClass the model class
	 * @return the model class's {@code Operations}
	 */
	public static List<Operation> getOperations(Class<?> modelClass) {
		if (modelClass == RootModel.class) {
			Operation deleteOperation = new Operation(
				DELETE, "delete-operation");
			Operation putOperation = new Operation(
				createForm("u", "r"), UPDATE, "update-operation");

			return Arrays.asList(deleteOperation, putOperation);
		}

		if (modelClass == FirstEmbeddedModel.class) {
			Operation operation = new Operation(DELETE, "delete-operation");

			return Collections.singletonList(operation);
		}

		return Collections.emptyList();
	}

	/**
	 * Returns a model class's {@link Representor}.
	 *
	 * @param  modelClass the model class
	 * @return the model class's {@code Representor}
	 */
	public static <T> Optional<Representor<?, ?>>
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
	 * Returns a {@link RequestInfo} with the provided {@code HttpHeaders}, a
	 * mock {@link com.liferay.apio.architect.url.ServerURL}, a mock {@link
	 * com.liferay.apio.architect.response.control.Embedded} request, and a mock
	 * {@link com.liferay.apio.architect.language.Language} with {@code
	 * Locale#getDefault()}.
	 *
	 * @param  httpHeaders the {@code HttpHeaders}
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
				Arrays.asList("embedded1", "embedded1.embedded")::contains
			).fields(
				__ -> string -> true
			).language(
				Locale::getDefault
			).build());
	}

	/**
	 * Returns a mock {@link Path} from an identifier. The identifier must be a
	 * {@link String}, otherwise {@code Optional#empty()} is returned.
	 *
	 * @param  identifier the identifier
	 * @param  identifierClass the identifier's class (ignored)
	 * @param  modelClass the model class
	 * @return the mock {@code Path} from the identifier, if the identifier is a
	 *         {@code String}; {@code Optional#empty()} otherwise
	 */
	@SuppressWarnings("unused")
	public static Optional<Path> identifierToPath(
		Object identifier, Class<?> identifierClass, Class<?> modelClass) {

		if (!(identifier instanceof String)) {
			return Optional.empty();
		}

		String string = (String)identifier;

		Function<String, Optional<Path>> function =
			name -> Optional.of(new Path(name, string));

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

	private MockWriterUtil() {
		throw new UnsupportedOperationException();
	}

}