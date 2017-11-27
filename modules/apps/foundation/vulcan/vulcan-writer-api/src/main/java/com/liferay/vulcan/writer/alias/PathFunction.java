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

package com.liferay.vulcan.writer.alias;

import com.liferay.vulcan.function.TriFunction;
import com.liferay.vulcan.resource.identifier.Identifier;
import com.liferay.vulcan.uri.Path;

import java.util.Optional;

/**
 * Defines a type alias for a function that receives an {@code Identifier}, the
 * class of that {@code Identifier}, and the class of the identifier model, and
 * returns an {@code Optional} {@code Path} for that {@code Identifier}.
 *
 * @author Alejandro Hernández
 * @review
 */
@FunctionalInterface
public interface PathFunction extends
	TriFunction
		<Identifier, Class<? extends Identifier>, Class<?>, Optional<Path>> {
}