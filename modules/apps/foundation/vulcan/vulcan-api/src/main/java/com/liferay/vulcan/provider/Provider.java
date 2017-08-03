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

package com.liferay.vulcan.provider;

import javax.servlet.http.HttpServletRequest;

/**
 * Instances of this interface will be used to provide instances of different
 * classes that are dependent on the current request.
 *
 * The class of the provider can then be provided as a parameter in {@link
 * com.liferay.vulcan.resource.builder.RoutesBuilder} methods.
 *
 * @author Alejandro Hernández
 */
public interface Provider<T> {

	/**
	 * Creates an instance of the provided class for the current request.
	 *
	 * @param  httpServletRequest current request.
	 * @return the instance of the provided class.
	 */
	public T createContext(HttpServletRequest httpServletRequest);

}