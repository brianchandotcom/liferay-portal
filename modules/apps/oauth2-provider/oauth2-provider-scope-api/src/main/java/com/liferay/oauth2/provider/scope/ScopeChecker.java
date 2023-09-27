/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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