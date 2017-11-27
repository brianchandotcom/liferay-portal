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

package com.liferay.vulcan.writer.util;

import com.liferay.vulcan.list.FunctionalList;
import com.liferay.vulcan.pagination.SingleModel;
import com.liferay.vulcan.request.RequestInfo;
import com.liferay.vulcan.resource.Representor;
import com.liferay.vulcan.resource.identifier.Identifier;
import com.liferay.vulcan.uri.Path;
import com.liferay.vulcan.writer.FieldsWriter;
import com.liferay.vulcan.writer.alias.PathFunction;
import com.liferay.vulcan.writer.alias.RepresentorFunction;

import java.util.Optional;

/**
 * This class provides util functions for writers.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 * @review
 */
public class WriterUtil {

	/**
	 * Return the {@code FieldsWriter} for a given model.
	 *
	 * @param  singleModel the single model
	 * @param  embeddedPathElements the embedded path element list
	 * @param  requestInfo the information of the current request
	 * @param  pathFunction the function to get the {@code Path}
	 * @param  representorFunction the function to get the {@code Representor}
	 * @return the {@code FieldsWriter} for the model
	 * @review
	 */
	public static <T> Optional<FieldsWriter<T, Identifier>> getFieldsWriter(
		SingleModel<T> singleModel, FunctionalList<String> embeddedPathElements,
		RequestInfo requestInfo, PathFunction pathFunction,
		RepresentorFunction representorFunction) {

		Optional<Representor<T, Identifier>> representorOptional =
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
	 * Return the {@code Path} for a given model. If no {@code Representor} or
	 * {@code Path} can be found for the model, {@code Optional#empty()} is
	 * returned.
	 *
	 * @param  singleModel the single model
	 * @param  pathFunction the function to get the {@code Path}
	 * @param  representorFunction the function to get the {@code Representor}
	 * @return the {@code Path} of the model if both its {@code Representor} and
	 *         {@code Path} are available; returns {@code Optional#empty()}
	 *         otherwise
	 * @review
	 */
	public static <T> Optional<Path> getPathOptional(
		SingleModel<T> singleModel, PathFunction pathFunction,
		RepresentorFunction representorFunction) {

		Optional<Representor<T, Identifier>> optional = getRepresentorOptional(
			singleModel.getModelClass(), representorFunction);

		return optional.flatMap(
			representor -> pathFunction.apply(
				representor.getIdentifier(singleModel.getModel()),
				representor.getIdentifierClass(), singleModel.getModelClass()));
	}

	/**
	 * Return the {@code Path} for a given model. If no {@code Representor} can
	 * be found for the model class, {@code Optional#empty()} is returned.
	 *
	 * @param  modelClass the class of the model
	 * @param  representorFunction the function to get the {@code Representor}
	 * @return the {@code Representor} of the model class if it's available;
	 *         returns {@code Optional#empty()} otherwise
	 * @review
	 */
	@SuppressWarnings("unchecked")
	public static <T, V extends Identifier> Optional<Representor<T, V>>
		getRepresentorOptional(
			Class<T> modelClass, RepresentorFunction representorFunction) {

		Optional<? extends Representor<?, ? extends Identifier>> optional =
			representorFunction.apply(modelClass);

		return optional.map(representor -> (Representor<T, V>)representor);
	}

	private WriterUtil() {
		throw new UnsupportedOperationException();
	}

}