/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.rest.internal.jaxrs.feature;

import com.liferay.oauth2.provider.scope.spi.scope.finder.ScopeFinder;

import java.util.Collection;

/**
 * @author Carlos Sierra Andrés
 */
public class CollectionScopeFinder implements ScopeFinder {

	public CollectionScopeFinder(Collection<String> scopes) {
		_scopes = scopes;
	}

	@Override
	public Collection<String> findScopes() {
		return _scopes;
	}

	private final Collection<String> _scopes;

}