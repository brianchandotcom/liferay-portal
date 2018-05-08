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

package com.liferay.apio.architect.endpoint;

import static com.liferay.apio.architect.endpoint.ExceptionSupplierUtil.notFound;

import com.liferay.apio.architect.file.BinaryFile;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.single.model.SingleModel;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Declares the endpoint for binary operations.
 *
 * @author Alejandro Hernández
 */
public class BinaryEndpoint {

	public BinaryEndpoint(
		Function<String, Optional<Representor<Object, Object>>>
			representorFunction,
		BiFunction<String, String, Try<SingleModel<Object>>>
			singleModelFunction) {

		_representorFunction = representorFunction;
		_singleModelFunction = singleModelFunction;
	}

	/**
	 * Returns the {@code InputStream} for the specified resource.
	 *
	 * @param  name the resource's name, extracted from the URL
	 * @param  id the resource's ID
	 * @param  binaryId the binary resource's ID
	 * @return the binary file's {@code java.io.InputStream}, or an exception if
	 *         an error occurred
	 */
	@GET
	@Path("{name}/{id}/{binaryId}")
	public Try<BinaryFile> getCollectionItemBinaryFileTry(
		@PathParam("name") String name, @PathParam("id") String id,
		@PathParam("binaryId") String binaryId) {

		return _singleModelFunction.apply(
			name, id
		).map(
			SingleModel::getModel
		).mapOptional(
			model -> _representorFunction.apply(
				name
			).map(
				Representor::getBinaryFunctions
			).map(
				binaryFunctions -> binaryFunctions.get(binaryId)
			).map(
				binaryFunction -> binaryFunction.apply(model)
			),
			notFound(name, id, binaryId)
		);
	}

	private final Function<String, Optional<Representor<Object, Object>>>
		_representorFunction;
	private final BiFunction<String, String, Try<SingleModel<Object>>>
		_singleModelFunction;

}