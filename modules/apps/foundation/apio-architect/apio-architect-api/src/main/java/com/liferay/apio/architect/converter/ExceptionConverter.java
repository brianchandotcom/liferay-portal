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

package com.liferay.apio.architect.converter;

import aQute.bnd.annotation.ConsumerType;

import com.liferay.apio.architect.error.APIError;

/**
 * Defines an interface whose instances convert between an exception and its
 * {@link APIError} representation. Use the exception's message to provide
 * meaningful information about the error.
 *
 * @author Alejandro Hernández
 * @param  <T> the exception's type
 */
@ConsumerType
public interface ExceptionConverter<T extends Exception> {

	/**
	 * Converts an exception to its {@link APIError} representation.
	 *
	 * @param  exception the exception to convert
	 * @return the exception's {@link APIError} representation
	 */
	public APIError convert(T exception);

}