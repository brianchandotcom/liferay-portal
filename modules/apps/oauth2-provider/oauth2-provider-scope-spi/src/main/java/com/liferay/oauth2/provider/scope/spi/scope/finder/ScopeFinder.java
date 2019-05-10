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

package com.liferay.oauth2.provider.scope.spi.scope.finder;

import java.util.Collection;

import org.osgi.annotation.versioning.ProviderType;

/**
 * This class is the entry point to the OAuth2 Scopes framework. Applications
 * can define a custom ScopeFinder to expose supported scopes.
 *
 * @author Carlos Sierra Andrés
 * @review
 */
@ProviderType
public interface ScopeFinder {

	/**
	 * Returns the list of scopes, internal to the application.
	 *
	 * @return a collection of the available scopes.
	 * @review
	 */
	public Collection<String> findScopes();

}