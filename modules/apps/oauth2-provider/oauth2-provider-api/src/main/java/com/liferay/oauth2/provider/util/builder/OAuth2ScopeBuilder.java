/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.util.builder;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author Stian Sigvartsen
 * @author Carlos Sierra
 */
public interface OAuth2ScopeBuilder {

	public void forApplication(
		String applicationName, String bundleSymbolicName,
		Consumer<OAuth2ScopeBuilder.ApplicationScopeAssigner>
			applicationScopeAssignerConsumer);

	public interface ApplicationScope {

		public void mapToScopeAlias(Collection<String> scopeAliases);

		public default void mapToScopeAlias(String... scopeAlias) {
			mapToScopeAlias(Arrays.asList(scopeAlias));
		}

	}

	public interface ApplicationScopeAssigner {

		public OAuth2ScopeBuilder.ApplicationScope assignScope(
			Collection<String> scopes);

		public default OAuth2ScopeBuilder.ApplicationScope assignScope(
			String... scope) {

			return assignScope(Arrays.asList(scope));
		}

	}

}