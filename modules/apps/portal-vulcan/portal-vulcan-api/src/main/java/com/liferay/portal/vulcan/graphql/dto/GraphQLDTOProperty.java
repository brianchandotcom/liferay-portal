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

package com.liferay.portal.vulcan.graphql.dto;

/**
 * @author Javier de Arcos
 */
public class GraphQLDTOProperty {

	public static GraphQLDTOProperty of(String name, Class<?> typeClass) {
		return new GraphQLDTOProperty(name, typeClass, false);
	}

	public static GraphQLDTOProperty of(
		String name, Class<?> typeClass, boolean readOnly) {

		return new GraphQLDTOProperty(name, typeClass, readOnly);
	}

	public GraphQLDTOProperty(String name, Class<?> typeClass) {
		this(name, typeClass, false);
	}

	public GraphQLDTOProperty(
		String name, Class<?> typeClass, boolean readOnly) {

		_name = name;
		_typeClass = typeClass;
		_readOnly = readOnly;
	}

	public String getName() {
		return _name;
	}

	public Class<?> getTypeClass() {
		return _typeClass;
	}

	public boolean isReadOnly() {
		return _readOnly;
	}

	private final String _name;
	private final boolean _readOnly;
	private final Class<?> _typeClass;

}