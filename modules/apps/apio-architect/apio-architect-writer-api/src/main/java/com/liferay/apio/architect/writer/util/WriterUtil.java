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
import com.liferay.apio.architect.representor.BaseRepresentor;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.request.RequestInfo;
import com.liferay.apio.architect.single.model.SingleModel;
import com.liferay.apio.architect.unsafe.Unsafe;
import com.liferay.apio.architect.uri.Path;
import com.liferay.apio.architect.writer.FieldsWriter;
import com.liferay.apio.architect.writer.alias.BaseRepresentorFunction;
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
	 * Returns the {@link FieldsWriter} for a given model, if the model's {@code
	 * Representor} exists. Otherwise, this method returns {@code
	 * Optional#empty()}.
	 *
	 * @param  singleModel the single model
	 * @param  embeddedPathElements the embedded path element list
	 * @param  requestInfo the current request's information
	 * @param  baseRepresentorFunction the function to get the {@link
	 *         BaseRepresentor}
	 * @param  singleModelFunction the function to get other {@link SingleModel}
	 * @param  path the path for the single model
	 * @return the {@code FieldsWriter} for the model, if the model's {@code
	 *         Representor} exists; returns {@code Optional#empty()} otherwise
	 */
	public static <T> Optional<FieldsWriter<T>> getFieldsWriter(
		SingleModel<T> singleModel, FunctionalList<String> embeddedPathElements,
		RequestInfo requestInfo,
		BaseRepresentorFunction baseRepresentorFunction,
		SingleModelFunction singleModelFunction, Path path) {

		return baseRepresentorFunction.apply(
			singleModel.getResourceName()
		).<BaseRepresentor<T>>map(
			Unsafe::unsafeCast
		).map(
			baseRepresentor -> new FieldsWriter<>(
				singleModel, requestInfo, baseRepresentor, path,
				embeddedPathElements, singleModelFunction)
		);
	}

	/**
	 * Returns a model's {@link Path}, if the model's {@code Representor} and
	 * {@code Path} exist. Otherwise, this method returns {@code
	 * Optional#empty()}.
	 *
	 * @param  singleModel the single model
	 * @param  pathFunction the function that gets the {@code Path}
	 * @param  baseRepresentorFunction the function that gets the {@code
	 *         BaseRepresentor}
	 * @return the model's {@code Path}, if the model's {@code Representor} and
	 *         {@code Path} exist; returns {@code Optional#empty()} otherwise
	 */
	public static <T> Optional<Path> getPathOptional(
		SingleModel<T> singleModel, PathFunction pathFunction,
		BaseRepresentorFunction baseRepresentorFunction) {

		return getPathOptional(
			singleModel, pathFunction, baseRepresentorFunction, null, null);
	}

	/**
	 * Returns a model's {@link com.liferay.apio.architect.uri.Path}, if the
	 * model's {@link com.liferay.apio.architect.representor.Representor} and
	 * {@code Path} exist; otherwise returns {@code Optional#empty()}.
	 *
	 * @param  singleModel the single model
	 * @param  pathFunction the function that gets the {@code Path}
	 * @param  baseRepresentorFunction the function that gets the {@code
	 *         BaseRepresentor}
	 * @param  rootRepresentorFunction the function that gets the parent model's
	 *         {@code Representor}
	 * @param  rootSingleModel the parent model
	 * @return the model's {@code Path}, if the model's {@code Representor} and
	 *         {@code Path} exist; returns {@code Optional#empty()} otherwise
	 */
	public static <T, S> Optional<Path> getPathOptional(
		SingleModel<T> singleModel, PathFunction pathFunction,
		BaseRepresentorFunction baseRepresentorFunction,
		RepresentorFunction rootRepresentorFunction,
		SingleModel<S> rootSingleModel) {

		return baseRepresentorFunction.apply(
			singleModel.getResourceName()
		).<BaseRepresentor<T>>map(
			Unsafe::unsafeCast
		).flatMap(
			baseRepresentor -> {
				if (baseRepresentor.isNested()) {
					return rootRepresentorFunction.apply(
						rootSingleModel.getResourceName()
					).<Representor<S>>map(
						Unsafe::unsafeCast
					).flatMap(
						representor -> pathFunction.apply(
							rootSingleModel.getResourceName(),
							representor.getIdentifier(
								rootSingleModel.getModel()))
					);
				}

				Representor<T> representor = (Representor<T>)baseRepresentor;

				return pathFunction.apply(
					singleModel.getResourceName(),
					representor.getIdentifier(singleModel.getModel()));
			}
		);
	}

	private WriterUtil() {
		throw new UnsupportedOperationException();
	}

}