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

package com.liferay.apio.architect.wiring.osgi.internal.manager.resource;

/**
 * Constants for {@code ServiceReference}'s properties that store the generic
 * classes of the resources.
 *
 * @author Alejandro Hernández
 * @review
 */
public enum ResourceClass {

	ITEM_IDENTIFIER_CLASS, MODEL_CLASS, PARENT_IDENTIFIER_CLASS,
	PARENT_MODEL_CLASS;

	/**
	 * Returns the name of the class in order to be used as a key inside {@code
	 * ServiceReference}'s properties.
	 *
	 * @return the name of the class in order to be used as a key inside {@code
	 *         ServiceReference}'s properties
	 */
	public String getName() {
		if (this == ITEM_IDENTIFIER_CLASS) {
			return "apio.architect.item.identifier.class";
		}
		else if (this == MODEL_CLASS) {
			return "apio.architect.model.class";
		}
		else if (this == PARENT_IDENTIFIER_CLASS) {
			return "apio.architect.parent.identifier.class";
		}
		else if (this == PARENT_MODEL_CLASS) {
			return "apio.architect.parent.model.class";
		}

		throw new IllegalArgumentException();
	}

}