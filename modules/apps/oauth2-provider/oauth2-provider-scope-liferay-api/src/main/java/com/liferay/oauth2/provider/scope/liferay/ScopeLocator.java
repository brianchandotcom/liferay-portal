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

package com.liferay.oauth2.provider.scope.liferay;

import java.util.Collection;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Lists scope aliases and matching {@link LiferayOAuth2Scope}s based on a
 * portal instance configuration of the OAuth2 framework. Scope aliases can
 * match multiple {@link LiferayOAuth2Scope}s from OAuth2 frameworks in
 * different portal instances.
 *
 * @author Carlos Sierra Andrés
 */
@ProviderType
public interface ScopeLocator {

	public LiferayOAuth2Scope getLiferayOAuth2Scope(
		long companyId, String applicationName, String scope);

	/**
	 * Returns the application exported scopes matching the given portal
	 * instance's scopes alias.
	 *
	 * @param  companyId the ID of the portal instance containing the scopes
	 * @param  scopesAlias the alias mapped to scopes
	 * @return the matching scopes
	 */
	public Collection<LiferayOAuth2Scope> getLiferayOAuth2Scopes(
		long companyId, String scopesAlias);

	/**
	 * Returns the application exported scopes matching the given portal
	 * instance's scopes alias, filtered by application name.
	 *
	 * @param  companyId the ID of the portal instance containing the scopes
	 * @param  scopesAlias the alias mapped to scopes
	 * @param  applicationName the application containing the scopes
	 * @return the matching scopes, filtered by application name
	 */
	public Collection<LiferayOAuth2Scope> getLiferayOAuth2Scopes(
		long companyId, String scopesAlias, String applicationName);

	/**
	 * Returns the scope aliases available for the given portal instance.
	 *
	 * @param  companyId the ID of the portal instance containing the scope
	 *         aliases
	 * @return the non-<code>null</code> scope aliases
	 */
	public Collection<String> getScopeAliases(long companyId);

	/**
	 * Returns the scope aliases available for the given portal instance,
	 * filtered by application name.
	 *
	 * @param  companyId the ID of the portal instance containing the scope
	 *         aliases
	 * @param  applicationName the application exporting the scopes
	 * @return the non-<code>null</code> scope aliases, filtered by application
	 *         name
	 */
	public Collection<String> getScopeAliases(
		long companyId, String applicationName);

}