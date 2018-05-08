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

package com.liferay.apio.architect.provider;

import aQute.bnd.annotation.ConsumerType;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides instances of {@code T} that are dependent on the current request.
 * Developers can then provide these instances in the methods added with any of
 * the route builders.
 *
 * @author Alejandro Hernández
 * @param  <T> the type of the instance to provide
 */
@ConsumerType
public interface Provider<T> {

	/**
	 * Creates an instance of {@code T} for the current request.
	 *
	 * @param  httpServletRequest the current request
	 * @return the instance of {@code T}
	 */
	public T createContext(HttpServletRequest httpServletRequest);

}