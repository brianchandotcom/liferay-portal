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

package com.liferay.apio.architect.related;

import com.liferay.apio.architect.identifier.Identifier;

/**
 * Represents the relation between a resource and a collection. The type of the
 * resource's identifier must be a subclass of {@code Identifier}.
 *
 * @author Alejandro Hernández
 * @param  <T> the type of the resource's identifier
 */
public class RelatedCollection<T extends Identifier> {

	public RelatedCollection(String key, Class<T> identifierClass) {
		_key = key;
		_identifierClass = identifierClass;
	}

	/**
	 * Returns the class of the collection items' identifier.
	 *
	 * @return the class of the collection items' identifier
	 */
	public Class<T> getIdentifierClass() {
		return _identifierClass;
	}

	/**
	 * Returns the relation's key.
	 *
	 * @return the relation's key
	 */
	public String getKey() {
		return _key;
	}

	private final Class<T> _identifierClass;
	private final String _key;

}