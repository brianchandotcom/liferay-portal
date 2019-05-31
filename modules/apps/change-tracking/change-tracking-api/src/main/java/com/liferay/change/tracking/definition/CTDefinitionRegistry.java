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

package com.liferay.change.tracking.definition;

import java.util.List;
import java.util.Optional;

/**
 * @author Máté Thurzó
 */
public interface CTDefinitionRegistry {

	public List<CTDefinition<?, ?>> getAllCTDefinitions();

	public Optional<CTDefinition<?, ?>> getCTDefinitionOptionalByResourceClass(
		Class<?> clazz);

	public Optional<CTDefinition<?, ?>>
		getCTDefinitionOptionalByResourceClassName(String className);

	public Optional<CTDefinition<?, ?>> getCTDefinitionOptionalByVersionClass(
		Class<?> clazz);

	public Optional<CTDefinition<?, ?>>
		getCTDefinitionOptionalByVersionClassName(String className);

}