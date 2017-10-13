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

package com.liferay.vulcan.converter;

import aQute.bnd.annotation.ConsumerType;

import com.liferay.vulcan.result.APIError;

/**
 * Instances of this interface will be used to convert between a Java exception
 * and its {@link APIError} representation. Use the exception message to provide
 * meaningful information about the error.
 *
 * @author Alejandro Hernández
 * @review
 */
@ConsumerType
public interface ExceptionConverter<T extends Exception> {

	/**
	 * Converts an exception to its {@code APIError} representation.
	 *
	 * @param  exception the exception to be converted.
	 * @return the {@code APIError} representation for the exception.
	 * @review
	 */
	public APIError convert(T exception);

}