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

package com.example.sample.hello;

/**
 * Provided methods to build a "hello" greeting.
 *
 * @author Andrea Di Giorgi
 */
public interface HelloBuilder {

	/**
	 * Returns the "hello" greeting.
	 *
	 * @return the "hello" greeting.
	 */
	public String getHello();

	/**
	 * Returns the name used to build a greeting.
	 *
	 * @return the name used to build a greeting.
	 */
	public String getName();

	/**
	 * Sets the name to use when building a greeting.
	 *
	 * @param name the name to use when building a greeting.
	 */
	public void setName(String name);

}