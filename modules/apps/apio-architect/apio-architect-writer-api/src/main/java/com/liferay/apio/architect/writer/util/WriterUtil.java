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

package com.liferay.apio.architect.writer.util;

import static com.liferay.apio.architect.unsafe.Unsafe.unsafeCast;

import com.liferay.apio.architect.list.FunctionalList;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.request.RequestInfo;
import com.liferay.apio.architect.single.model.SingleModel;
import com.liferay.apio.architect.uri.Path;
import com.liferay.apio.architect.writer.FieldsWriter;
import com.liferay.apio.architect.writer.alias.PathFunction;
import com.liferay.apio.architect.writer.alias.RepresentorFunction;
import com.liferay.apio.architect.writer.alias.SingleModelFunction;

import java.util.Optional;

/**
 * Provides utility functions for writers.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class WriterUtil {

	/**
	 * Returns the {@link FieldsWriter} for a given model.
	 *
	 * @param  singleModel the single model
	 * @param  embeddedPathElements the embedded path element list
	 * @param  requestInfo the current request's information
	 * @param  pathFunction the function to get the {@link Path}
	 * @param  rootRepresentorFunction the function to get the parent model's
	 *         {@link Representor}
	 * @param  rootSingleModel the parent model
	 * @return the {@code FieldsWriter} for the model
	 */
	public static <T, S> Optional<FieldsWriter<T, ?>> getFieldsWriter(
		SingleModel<T> singleModel, FunctionalList<String> embeddedPathElements,
		RequestInfo requestInfo, PathFunction pathFunction,
		RepresentorFunction representorFunction,
		RepresentorFunction rootRepresentorFunction,
		SingleModelFunction singleModelFunction,
		SingleModel<S> rootSingleModel) {

		Optional<Representor<T, ?>> representorOptional = unsafeCast(
			representorFunction.apply(singleModel.getResourceName()));

		Optional<Path> pathOptional = getPathOptional(
			singleModel, pathFunction, representorFunction,
			rootRepresentorFunction, rootSingleModel);

		return representorOptional.flatMap(
			representor -> pathOptional.map(
				path -> new FieldsWriter<>(
					singleModel, requestInfo, representor, path,
					embeddedPathElements, singleModelFunction)));
	}

	/**
	 * Returns the {@link FieldsWriter} for a given model.
	 *
	 * @param  singleModel the single model
	 * @param  embeddedPathElements the embedded path element list
	 * @param  requestInfo the current request's information
	 * @param  pathFunction the function to get the {@link Path}
	 * @param  representorFunction the function to get the {@link Representor}
	 * @return the {@code FieldsWriter} for the model
	 */
	public static <T> Optional<FieldsWriter<T, ?>> getFieldsWriter(
		SingleModel<T> singleModel, FunctionalList<String> embeddedPathElements,
		RequestInfo requestInfo, PathFunction pathFunction,
		RepresentorFunction representorFunction,
		SingleModelFunction singleModelFunction) {

		return getFieldsWriter(
			singleModel, embeddedPathElements, requestInfo, pathFunction,
			representorFunction, null, singleModelFunction, null);
	}

	/**
	 * Returns a model's {@link Path}, if the model's {@code Representor} and
	 * {@code Path} exist. Otherwise, this method returns {@code
	 * Optional#empty()}.
	 *
	 * @param  singleModel the single model
	 * @param  pathFunction the function that gets the {@code Path}
	 * @param  representorFunction the function that gets the {@code
	 *         Representor}
	 * @return the model's {@code Path}, if the model's {@code Representor} and
	 *         {@code Path} exist; returns {@code Optional#empty()} otherwise
	 */
	public static <T> Optional<Path> getPathOptional(
		SingleModel<T> singleModel, PathFunction pathFunction,
		RepresentorFunction representorFunction) {

		return getPathOptional(
			singleModel, pathFunction, representorFunction, null, null);
	}

	/**
	 * Returns a model's {@link com.liferay.apio.architect.uri.Path}, if the
	 * model's {@link com.liferay.apio.architect.representor.Representor} and
	 * {@code Path} exist; otherwise returns {@code Optional#empty()}.
	 *
	 * @param  singleModel the single model
	 * @param  pathFunction the function that gets the {@code Path}
	 * @param  representorFunction the function that gets the {@code
	 *         Representor}
	 * @param  rootRepresentorFunction the function that gets the parent model's
	 *         {@code Representor}
	 * @param  rootSingleModel the parent model
	 * @return the model's {@code Path}, if the model's {@code Representor} and
	 *         {@code Path} exist; returns {@code Optional#empty()} otherwise
	 */
	public static <T, S> Optional<Path> getPathOptional(
		SingleModel<T> singleModel, PathFunction pathFunction,
		RepresentorFunction representorFunction,
		RepresentorFunction rootRepresentorFunction,
		SingleModel<S> rootSingleModel) {

		Optional<Representor<T, ?>> optional = unsafeCast(
			representorFunction.apply(singleModel.getResourceName()));

		return optional.flatMap(
			representor -> {
				if (representor.getIdentifierFunction() == null) {
					Optional<Representor<S, ?>> representorOptional =
						unsafeCast(
							rootRepresentorFunction.apply(
								rootSingleModel.getResourceName()));

					if (representorOptional.isPresent()) {
						Representor<S, ?> rootRepresentor =
							representorOptional.get();

						return pathFunction.apply(
							rootSingleModel.getResourceName(),
							rootRepresentor.getIdentifier(
								rootSingleModel.getModel()));
					}
				}

				return pathFunction.apply(
					singleModel.getResourceName(),
					representor.getIdentifier(singleModel.getModel()));
			});
	}

	private WriterUtil() {
		throw new UnsupportedOperationException();
	}

}