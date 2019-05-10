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

package com.liferay.oauth2.provider.scope.spi.scope.descriptor;

import java.util.Locale;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Represents localization information for the scopes of OAuth2 applications.
 *
 * @author Carlos Sierra Andrés
 * @review
 */
@ProviderType
public interface ScopeDescriptor {

	/**
	 * Localize a scope for a given locale.
	 *
	 * @param  scope the scope to be described.
	 * @param  locale the locale requested for the description.
	 * @return a description for the scope in the requested locale.
	 * @review
	 */
	public String describeScope(String scope, Locale locale);

}