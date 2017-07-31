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

package com.liferay.vulcan.response.control;

import java.util.List;
import java.util.function.Predicate;

/**
 * Defines the fields context selected by clients. An instance of this interface
 * will be handed to {@link javax.ws.rs.ext.MessageBodyWriter}s so they can
 * decide which fields of resources to write.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
public interface Fields {

	/**
	 * Returns the predicate for a list of types to test if a certain field
	 * should be added to the representation.
	 *
	 * @param  types list of types of the resource.
	 * @return the predicate to test if a field should be added.
	 */
	public Predicate<String> getFieldsPredicate(List<String> types);

}