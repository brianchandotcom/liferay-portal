/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.scope.liferay;

import org.osgi.framework.Bundle;

/**
 * Represents an application exported scope for Liferay's OAuth2 Provider
 * framework.
 *
 * @author Carlos Sierra Andrés
 */
public interface LiferayOAuth2Scope {

	/**
	 * Returns the name of the application that provides the scope. This usually
	 * refers to the JAX-RS application name.
	 *
	 * @return the non-<code>null</code> application name
	 */
	public String getApplicationName();

	/**
	 * Returns the OSGi bundle context where the application and scope are
	 * published.
	 *
	 * @return the non-<code>null</code> OSGi bundle
	 */
	public Bundle getBundle();

	/**
	 * Returns the scope name as registered in the OAuth2 Provider framework.
	 *
	 * @return the non-<code>null</code> scope name
	 */
	public String getScope();

}