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

package com.liferay.change.tracking.configuration;

import java.util.List;
import java.util.Optional;

/**
 * @author Máté Thurzó
 */
public interface CTConfigurationRegistry {

	public List<CTConfiguration<?, ?>> getAllCTConfigurations();

	public Optional<CTConfiguration<?, ?>>
		getCTConfigurationOptionalByResourceClass(Class<?> clazz);

	public Optional<CTConfiguration<?, ?>>
		getCTConfigurationOptionalByResourceClassName(String className);

	public Optional<CTConfiguration<?, ?>>
		getCTConfigurationOptionalByVersionClass(Class<?> clazz);

	public Optional<CTConfiguration<?, ?>>
		getCTConfigurationOptionalByVersionClassName(String className);

}