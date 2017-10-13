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

package com.liferay.vulcan.uri;

/**
 * Represents a resource path.
 *
 * <p>
 * For example a resource obtained in the URL: {@code /p/customers/2012} will
 * have a path containing {@code 2012} and {@code "customers"} as the ID and
 * name respectively.
 * </p>
 *
 * @author Alejandro Hernández
 * @review
 */
public class Path {

	public Path() {
		_name = "";
		_id = "";
	}

	public Path(String name, String id) {
		_name = name;
		_id = id;
	}

	/**
	 * Returns this resource's path as an URI.
	 *
	 * @return the chain of identifiers as an URI.
	 * @review
	 */
	public String asURI() {
		return "/" + getName() + "/" + getId();
	}

	/**
	 * Returns the ID part of this {@code Path}.
	 *
	 * <p>
	 * For example with a resource obtained in the URI: {@code
	 * /p/customers/2012} this method will return {@code 2012}.
	 * </p>
	 *
	 * @return the ID part of the {@code Path}.
	 * @review
	 */
	public String getId() {
		return _id;
	}

	/**
	 * Returns the name part of this {@code Path}
	 *
	 * <p>
	 * For example with a resource obtained in the URI: {@code
	 * /p/customers/2012} this method will return {@code "customer"}.
	 * </p>
	 *
	 * @return the name part of the {@code Path}.
	 * @review
	 */
	public String getName() {
		return _name;
	}

	private final String _id;
	private final String _name;

}