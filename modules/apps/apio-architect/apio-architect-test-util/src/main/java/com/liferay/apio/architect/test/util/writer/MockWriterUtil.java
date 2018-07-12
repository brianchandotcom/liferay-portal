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

import static com.liferay.apio.architect.test.util.representor.MockRepresentorCreator.createFirstEmbeddedModelRepresentor;
import static com.liferay.apio.architect.test.util.representor.MockRepresentorCreator.createRootModelRepresentor;
import static com.liferay.apio.architect.test.util.representor.MockRepresentorCreator.createSecondEmbeddedModelRepresentor;
import static com.liferay.apio.architect.test.util.representor.MockRepresentorCreator.createThirdEmbeddedModelRepresentor;

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.impl.operation.OperationImpl;
import com.liferay.apio.architect.impl.request.RequestInfo;
import com.liferay.apio.architect.impl.single.model.SingleModelImpl;
import com.liferay.apio.architect.operation.HTTPMethod;
import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.single.model.SingleModel;
import com.liferay.apio.architect.test.util.identifier.FirstEmbeddedId;
import com.liferay.apio.architect.test.util.identifier.SecondEmbeddedId;
import com.liferay.apio.architect.test.util.identifier.ThirdEmbeddedId;
import com.liferay.apio.architect.test.util.model.FirstEmbeddedModel;
import com.liferay.apio.architect.test.util.model.SecondEmbeddedModel;
import com.liferay.apio.architect.test.util.model.ThirdEmbeddedModel;
import com.liferay.apio.architect.uri.Path;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

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
	 * Returns a model class's {@link Representor}.
	 *
	 * @return the model class's {@code Representor}
	 */
	public static Optional<Representor<?>>
		getRepresentorOptional(String resourceName) {

		if ("root".equals(resourceName)) {
			return Optional.of(createRootModelRepresentor(false));
		}

		if ("first".equals(resourceName)) {
			return Optional.of(createFirstEmbeddedModelRepresentor());
		}

		if ("second".equals(resourceName)) {
			return Optional.of(createSecondEmbeddedModelRepresentor());
		}

		return Optional.of(createThirdEmbeddedModelRepresentor());
	}

	/**
	 * Returns a {@link RequestInfo} with a mock {@code ServerURL}, {@code
	 * Embedded} request, and {@link
	 * com.liferay.apio.architect.language.AcceptLanguage} with {@code
	 * Locale#getDefault()}.
	 *
	 * @return the {@code RequestInfo}
	 */
	public static RequestInfo getRequestInfo() {
		return RequestInfo.create(
			builder -> builder.httpServletRequest(
				null
			).serverURL(
				() -> "localhost"
			).applicationURL(
				() -> "localhost/o/api"
			).embedded(
				Arrays.asList("embedded1", "embedded1.embedded")::contains
			).fields(
				__ -> string -> true
			).language(
				Locale::getDefault
			).build());
	}

	/**
	 * Returns a mock {@link SingleModel} (with the provided identifier as its
	 * ID) for an identifier class.
	 *
	 * @param  identifier the single model's identifier
	 * @param  identifierClass the identifier class
	 * @return the mock {@code SingleModel}
	 */
	public static Optional<SingleModel> getSingleModel(
		Object identifier, Class<? extends Identifier> identifierClass) {

		if (!(identifier instanceof String)) {
			return Optional.empty();
		}

		if (identifierClass.equals(FirstEmbeddedId.class)) {
			List<Operation> operations = Collections.singletonList(
				new OperationImpl(HTTPMethod.DELETE, "delete-operation"));

			return Optional.of(
				new SingleModelImpl<>(
					(FirstEmbeddedModel)() -> (String)identifier, "first",
					operations));
		}

		if (identifierClass.equals(SecondEmbeddedId.class)) {
			return Optional.of(
				new SingleModelImpl<>(
					(SecondEmbeddedModel)() -> (String)identifier, "second",
					Collections.emptyList()));
		}

		if (identifierClass.equals(ThirdEmbeddedId.class)) {
			return Optional.of(
				new SingleModelImpl<>(
					(ThirdEmbeddedModel)() -> (String)identifier, "third",
					Collections.emptyList()));
		}

		return Optional.empty();
	}

	/**
	 * Returns a mock {@link Path} from an identifier. The identifier must be a
	 * {@link String}, otherwise {@code Optional#empty()} is returned.
	 *
	 * @param  identifier the identifier
	 * @return the mock {@code Path} from the identifier, if the identifier is a
	 *         {@code String}; {@code Optional#empty()} otherwise
	 */
	public static Optional<Path> identifierToPath(
		String resourceName, Object identifier) {

		if (!(identifier instanceof String)) {
			return Optional.empty();
		}

		String string = (String)identifier;

		Function<String, Optional<Path>> function =
			name -> Optional.of(new Path(name, string));

		if ("root".equals(resourceName)) {
			return function.apply("model");
		}

		if ("first".equals(resourceName)) {
			return function.apply("first-inner-model");
		}

		if ("second".equals(resourceName)) {
			return function.apply("second-inner-model");
		}

		return function.apply("third-inner-model");
	}

	private MockWriterUtil() {
		throw new UnsupportedOperationException();
	}

}