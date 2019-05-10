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

package com.liferay.oauth2.provider.scope.spi.scope.mapper;

import java.util.Collections;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Represents a transformation between internal scope names to external aliases.
 *
 * @author Carlos Sierra Andrés
 * @review
 */
@ProviderType
public interface ScopeMapper {

	public static final ScopeMapper PASS_THROUGH_SCOPE_MAPPER =
		Collections::singleton;

	/**
	 * Renames an application provided scope to new scope names
	 *
	 * @param  scope application provided scope
	 * @return set of new names for the scope
	 * @review
	 */
	public Set<String> map(String scope);

}