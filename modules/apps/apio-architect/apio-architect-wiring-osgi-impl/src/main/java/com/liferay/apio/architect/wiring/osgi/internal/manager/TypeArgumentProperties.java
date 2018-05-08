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

package com.liferay.apio.architect.wiring.osgi.internal.manager;

/**
 * Defines constants for {@code ServiceReference} properties that store the
 * generic classes of managed interfaces.
 *
 * @author Alejandro Hernández
 */
public class TypeArgumentProperties {

	/**
	 * Represents the class of a resource's identifier.
	 */
	public static final String KEY_IDENTIFIER_CLASS =
		"apio.architect.principal.type.argument";

	/**
	 * Represents the class of a parent resource's identifier.
	 */
	public static final String KEY_PARENT_IDENTIFIER_CLASS =
		"apio.architect.parent.identifier.class";

	/**
	 * Represents a managed interface's principal type argument.
	 */
	public static final String KEY_PRINCIPAL_TYPE_ARGUMENT =
		"apio.architect.principal.type.argument";

}