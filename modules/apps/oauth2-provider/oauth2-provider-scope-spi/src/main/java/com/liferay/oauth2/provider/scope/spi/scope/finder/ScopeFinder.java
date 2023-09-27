/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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