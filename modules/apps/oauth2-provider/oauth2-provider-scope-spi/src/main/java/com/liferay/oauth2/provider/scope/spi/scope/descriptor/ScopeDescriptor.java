/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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