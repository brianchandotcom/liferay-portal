/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.scope.spi.scope.mapper;

import java.util.Collections;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Represents a transformation between internal scope names to external aliases.
 *
 * @author Carlos Sierra Andrés
 * @review
 */
@ProviderType
public interface ScopeMapper {

	public static final ScopeMapper PASS_THROUGH_SCOPE_MAPPER =
		Collections::singleton;

	/**
	 * Renames an application provided scope to new scope names
	 *
	 * @param  scope application provided scope
	 * @return set of new names for the scope
	 * @review
	 */
	public Set<String> map(String scope);

}