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

/**
 * This interface allows to list scope aliases and matching
 * {@link LiferayOAuth2Scope}s based on a portal instance configuration of
 * OAuth2 Framework.<br />
 * Scope alias can match multiple {@link LiferayOAuth2Scope}s based on
 * particular portal instance configuration of OAuth2 Framework.
 *
 * @author Carlos Sierra Andrés
 * @review
 */
public interface ScopeLocator {

	/**
	 * Returns a list of scope aliases available for the given portal instance.
	 * <br />
	 *
	 * @param companyId the portal instance context
	 * @return a non-null collection of scope aliases from the portal instance
	 * @review
	 */
	public Collection<String> locateScopeAliases(long companyId);

	/**
	 * Returns a list of scope aliases available for the given portal instance,
	 * filtered by application name.
	 *
	 * @param companyId the portal instance context
	 * @param applicationName name of application exporting the scopes
	 * @return a non-null collection of scope aliases from the portal instance
	 * filtered by {@code applicationName}
	 * @review
	 */
	public Collection<String> locateScopeAliasesForApplication(
		long companyId, String applicationName);

	/**
	 * Returns a collection of scopes matching {@code scopesAlias} in the given
	 * portal instance.
	 * @param companyId the company for which the scopes are to be located
	 * @param scopesAlias the scope alias the scopes are mapped to
	 * @return a collection of one or more matching scopes for the given company
	 * and scope alias
	 * @review
	 */
	public Collection<LiferayOAuth2Scope> locateScopes(
		long companyId, String scopesAlias);

	/**
	 * Returns a collection of scopes matching {@code scopesAlias} in the given
	 * portal instance filtered by {@code applicationName}.
	 *
	 * @param companyId the company for which the scopes are to be located
	 * @param scopesAlias the scope alias the scopes are mapped to
	 * @param applicationName the application for which the scopes are to be
	 * located
	 * @return a collection of one or more matching scopes for the given company
	 * and scope alias, filtered by {@code applicationName}
	 * @review
	 */
	public Collection<LiferayOAuth2Scope> locateScopesForApplication(
		long companyId, String scopesAlias, String applicationName);

}