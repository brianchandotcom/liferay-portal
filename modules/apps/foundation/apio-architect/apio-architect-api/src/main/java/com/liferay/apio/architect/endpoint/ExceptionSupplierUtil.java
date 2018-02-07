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

import static javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED;

import com.liferay.apio.architect.operation.Method;

import java.util.function.Supplier;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 * Utility class for returning suppliers for the common exceptions inside
 * endpoints.
 *
 * <p>
 * This class shouldn't be instantiated
 * </p>
 *
 * @author Alejandro Hernández
 * @review
 */
public class ExceptionSupplierUtil {

	/**
	 * Return a supplier of {@code NotAllowedException}.
	 *
	 * @param  method the method that is not allowed
	 * @param  path the path in which the method is not allowed. The components
	 *         of the path will be joined using slashes ("/")
	 * @return a supplier of {@code NotAllowedException}
	 * @review
	 */
	public static Supplier<NotAllowedException> notAllowed(
		Method method, String... path) {

		String message =
			method.name() + " method is not allowed for path " +
				String.join("/", path);

		Response response = Response.status(METHOD_NOT_ALLOWED).build();

		return () -> new NotAllowedException(message, response);
	}

	/**
	 * Return a supplier of {@code NotFoundException}.
	 *
	 * @param  path the path in which no endpoint is found. The components of
	 *         the path will be joined using slashes ("/")
	 * @return a supplier of {@code NotFoundException}
	 * @review
	 */
	public static Supplier<NotFoundException> notFound(String... path) {
		return () -> new NotFoundException(
			"No endpoint found at path " + String.join("/", path));
	}

	private ExceptionSupplierUtil() {
		throw new UnsupportedOperationException();
	}

}