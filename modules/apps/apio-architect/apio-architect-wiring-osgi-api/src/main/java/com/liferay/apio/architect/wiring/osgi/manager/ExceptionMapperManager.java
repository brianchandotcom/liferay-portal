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

package com.liferay.apio.architect.wiring.osgi.manager;

import aQute.bnd.annotation.ProviderType;

import com.liferay.apio.architect.error.APIError;

import java.util.Optional;

/**
 * Provides methods to map exceptions to generic {@link APIError}
 * representations.
 *
 * @author Alejandro Hernández
 */
@ProviderType
public interface ExceptionMapperManager {

	/**
	 * Converts an exception to its generic {@link APIError} representation, if
	 * a valid {@link ExceptionMapper} exists. Returns {@code Optional#empty()}
	 * otherwise.
	 *
	 * <p>
	 * If no {@code ExceptionMapper} can be found for the exception class, this
	 * method tries to use the superclass of {@code ExceptionMapper}.
	 * </p>
	 *
	 * @param  exception the exception to map
	 * @return the exception's {@code APIError} representation, if a valid
	 *         {@code ExceptionMapper} is present; {@code Optional#empty()}
	 *         otherwise
	 */
	public <T extends Exception> Optional<APIError> map(T exception);

}