/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.graphql.dto;

/**
 * @author Javier de Arcos
 */
public class GraphQLDTOProperty {

	public static GraphQLDTOProperty of(
		String name, boolean readOnly, Class<?> typeClass) {

		return new GraphQLDTOProperty(name, readOnly, typeClass);
	}

	public static GraphQLDTOProperty of(String name, Class<?> typeClass) {
		return new GraphQLDTOProperty(name, false, typeClass);
	}

	public GraphQLDTOProperty(
		String name, boolean readOnly, Class<?> typeClass) {

		_name = name;
		_readOnly = readOnly;
		_typeClass = typeClass;
	}

	public GraphQLDTOProperty(String name, Class<?> typeClass) {
		this(name, false, typeClass);
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