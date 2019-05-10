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

package com.liferay.oauth2.provider.scope;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides a programmatic interface for applications to check scope
 * authorization in a JAX-RS request.
 *
 * @author Carlos Sierra Andrés
 */
@ProviderType
public interface ScopeChecker {

	/**
	 * Returns <code>true</code> if the current request has been authorized for
	 * all given scopes.
	 *
	 * @param  scopes the scopes to check the request for authorization
	 * @return <code>true</code> if the request has been authorized for all
	 *         given scopes; <code>false</code> otherwise
	 */
	public default boolean checkAllScopes(String... scopes) {
		for (String scope : scopes) {
			if (!checkScope(scope)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns <code>true</code> if the current request has been authorized for
	 * any of the given scopes.
	 *
	 * @param  scopes the scopes to check the request for authorization
	 * @return <code>true</code> if the request has been authorized for any of
	 *         the given scopes; <code>false</code> otherwise
	 */
	public default boolean checkAnyScope(String... scopes) {
		for (String scope : scopes) {
			if (checkScope(scope)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the current request has been authorized for
	 * the given scope.
	 *
	 * @param  scope the scope to check the request for authorization
	 * @return <code>true</code> if the request has been authorized for the
	 *         given scope; <code>false</code> otherwise
	 */
	public boolean checkScope(String scope);

}