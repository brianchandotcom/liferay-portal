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
 * Constants for {@code ServiceReference}'s properties that store the generic
 * classes of the managed interfaces.
 *
 * @author Alejandro Hernández
 * @review
 */
public class TypeArgumentProperties {

	/**
	 * Represents the class of a resource's identifier.
	 *
	 * @review
	 */
	public static final String IDENTIFIER_CLASS =
		"apio.architect.principal.type.argument";

	/**
	 * Represents the class of a resource's model.
	 *
	 * @review
	 */
	public static final String MODEL_CLASS = "apio.architect.model.class";

	/**
	 * Represents the class of a resource parent's identifier.
	 *
	 * @review
	 */
	public static final String PARENT_IDENTIFIER_CLASS =
		"apio.architect.parent.identifier.class";

	/**
	 * Represents the principal type argument of a managed interface.
	 *
	 * @review
	 */
	public static final String PRINCIPAL_TYPE_ARGUMENT =
		"apio.architect.principal.type.argument";

}