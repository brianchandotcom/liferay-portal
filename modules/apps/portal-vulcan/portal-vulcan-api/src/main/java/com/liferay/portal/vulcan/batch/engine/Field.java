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

package com.liferay.portal.vulcan.batch.engine;

/**
 * @author Javier de Arcos
 */
public class Field {

	public static Field of(
		String description, String name, boolean readOnly, boolean required,
		Class<?> typeClass, boolean writeOnly) {

		return new Field(
			_toAccessType(readOnly, writeOnly), description, name, required,
			typeClass);
	}

	public AccessType getAccessType() {
		return _accessType;
	}

	public String getDescription() {
		return _description;
	}

	public String getName() {
		return _name;
	}

	public Class<?> getTypeClass() {
		return _typeClass;
	}

	public boolean isRequired() {
		return _required;
	}

	public enum AccessType {

		READ, READWRITE, WRITE

	}

	private static AccessType _toAccessType(
		boolean readOnly, boolean writeOnly) {

		if (readOnly) {
			return AccessType.READ;
		}
		else if (writeOnly) {
			return AccessType.WRITE;
		}

		return AccessType.READWRITE;
	}

	private Field(
		AccessType accessType, String description, String name,
		boolean required, Class<?> typeClass) {

		_accessType = accessType;
		_description = description;
		_name = name;
		_required = required;
		_typeClass = typeClass;
	}

	private final AccessType _accessType;
	private final String _description;
	private final String _name;
	private final boolean _required;
	private final Class<?> _typeClass;

}