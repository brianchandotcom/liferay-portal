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