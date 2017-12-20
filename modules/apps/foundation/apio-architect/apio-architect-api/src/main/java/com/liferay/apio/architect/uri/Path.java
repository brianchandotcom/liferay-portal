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

package com.liferay.apio.architect.uri;

/**
 * Represents a resource path. For example, the ID and name of a resource
 * obtained from the URL {@code /p/customers/2012} is {@code 2012} and {@code
 * "customers"}, respectively.
 *
 * @author Alejandro Hernández
 */
public class Path {

	public Path(String name, String id) {
		_name = name;
		_id = id;
	}

	/**
	 * Returns the current resource's path as a URI.
	 *
	 * @return the current resource's path as a URI
	 */
	public String asURI() {
		return getName() + "/" + getId();
	}

	/**
	 * Returns the current path's ID.
	 *
	 * @return the current path's ID
	 */
	public String getId() {
		return _id;
	}

	/**
	 * Returns the current path's name.
	 *
	 * @return the current path's name
	 */
	public String getName() {
		return _name;
	}

	private final String _id;
	private final String _name;

}