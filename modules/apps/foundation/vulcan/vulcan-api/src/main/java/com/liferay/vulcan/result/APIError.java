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

package com.liferay.vulcan.result;

import aQute.bnd.annotation.ProviderType;

import java.util.Optional;

/**
 * Instances of this interface describe an API error. Thrown {@link
 * javax.ws.rs.WebApplicationException} and descendants will be converted to a
 * special case of {@code APIError} with meaningful information. All other
 * exceptions will be converted to a {@code 500} error with a standard message.
 *
 * @author Alejandro Hernández
 * @review
 */
@ProviderType
public interface APIError {

	/**
	 * Returns the description of this {@code APIError}, specific to this {@code
	 * APIError} instance, if present. Returns {@code Optional#empty()}
	 * otherwise.
	 *
	 * @return the description of this error if present; {@code
	 *         Optional#empty()} otherwise.
	 * @review
	 */
	public Optional<String> getDescription();

	/**
	 * Returns the original exception of this error.
	 *
	 * @return the original exception of this error.
	 * @review
	 */
	public Exception getException();

	/**
	 * Returns the HTTP status code for this {@code APIError}.
	 *
	 * @return the HTTP status code of this error.
	 * @review
	 */
	public int getStatusCode();

	/**
	 * Returns the title of this {@code APIError}. The same for all {@code
	 * APIErrors} of the same type.
	 *
	 * @return the title of this error.
	 * @review
	 */
	public String getTitle();

	/**
	 * Returns the type of this error. Identifies errors with the same meaning.
	 *
	 * @return the type of this error.
	 * @review
	 */
	public String getType();

}