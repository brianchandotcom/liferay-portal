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

package com.liferay.apio.architect.wiring.osgi.manager.representable;

import aQute.bnd.annotation.ProviderType;

import com.liferay.apio.architect.identifier.Identifier;

import java.util.Optional;

/**
 * Provides methods to retrieve generic identifier class information provided by
 * the different {@link com.liferay.apio.architect.representor.Representable}
 * instances.
 *
 * @author Alejandro Hernández
 * @see    com.liferay.apio.architect.representor.Representable
 */
@ProviderType
public interface IdentifierClassManager {

	/**
	 * Returns the resource name's identifier class.
	 *
	 * @param  name the resource name
	 * @return the resource name's identifier class
	 */
	public <T extends Identifier> Optional<Class<T>> getIdentifierClassOptional(
		String name);

}