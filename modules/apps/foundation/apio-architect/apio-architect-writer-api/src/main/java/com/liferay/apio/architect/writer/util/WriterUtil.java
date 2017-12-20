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

import com.liferay.apio.architect.list.FunctionalList;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.request.RequestInfo;
import com.liferay.apio.architect.single.model.SingleModel;
import com.liferay.apio.architect.uri.Path;
import com.liferay.apio.architect.writer.FieldsWriter;
import com.liferay.apio.architect.writer.alias.PathFunction;
import com.liferay.apio.architect.writer.alias.RepresentorFunction;

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
	 * @param  representorFunction the function to get the {@link Representor}
	 * @return the {@code FieldsWriter} for the model
	 */
	public static <T> Optional<FieldsWriter<T, ?>> getFieldsWriter(
		SingleModel<T> singleModel, FunctionalList<String> embeddedPathElements,
		RequestInfo requestInfo, PathFunction pathFunction,
		RepresentorFunction representorFunction) {

		Optional<Representor<T, ?>> representorOptional =
			getRepresentorOptional(
				singleModel.getModelClass(), representorFunction);

		Optional<Path> pathOptional = getPathOptional(
			singleModel, pathFunction, representorFunction);

		return representorOptional.flatMap(
			representor -> pathOptional.map(
				path -> new FieldsWriter<>(
					singleModel, requestInfo, representor, path,
					embeddedPathElements)));
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

		Optional<Representor<T, ?>> optional = getRepresentorOptional(
			singleModel.getModelClass(), representorFunction);

		return optional.flatMap(
			representor -> pathFunction.apply(
				representor.getIdentifier(singleModel.getModel()),
				representor.getIdentifierClass(), singleModel.getModelClass()));
	}

	/**
	 * Returns a model's {@link Representor}, if it exists. Otherwise, this
	 * method returns {@code Optional#empty()}.
	 *
	 * @param  modelClass the model's class
	 * @param  representorFunction the function that gets the {@code
	 *         Representor}
	 * @return the model's {@code Representor}, if it exists; returns {@code
	 *         Optional#empty()} otherwise
	 */
	@SuppressWarnings("unchecked")
	public static <T> Optional<Representor<T, ?>> getRepresentorOptional(
		Class<T> modelClass, RepresentorFunction representorFunction) {

		Optional<? extends Representor<?, ?>> optional =
			representorFunction.apply(modelClass);

		return optional.map(representor -> (Representor<T, ?>)representor);
	}

	private WriterUtil() {
		throw new UnsupportedOperationException();
	}

}