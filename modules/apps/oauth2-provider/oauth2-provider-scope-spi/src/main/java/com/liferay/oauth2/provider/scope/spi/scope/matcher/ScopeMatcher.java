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

package com.liferay.oauth2.provider.scope.spi.scope.matcher;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.annotation.versioning.ProviderType;

/**
 * This interface represents the strategy used to match scope. Some of these
 * strategies may be:
 *
 * <ul>
 * <li>
 * STRICT: only scope matching a particular string or strings
 * will match
 * </li>
 * <li>
 * HIERARCHICAL: scope following some naming rules might match more
 * general scope. Hierarchy can be described for instance using
 * <i>dot notation</i>. In such scenario <i>everything</i> can also imply
 * longer scope such as <i>everything.readonly</i>.
 * </li>
 * </ul>
 *
 * ScopeMatcher is used together with
 * {@link com.liferay.oauth2.provider.scope.spi.prefixhandler.PrefixHandler}
 * and {@link com.liferay.oauth2.provider.scope.spi.scopemapper.ScopeMapper}
 * to tailor the matching strategy to the framework configuration.
 *
 * @author Carlos Sierra Andrés
 * @review
 */
@ProviderType
public interface ScopeMatcher {

	public static ScopeMatcher NONE = __ -> false;

	/**
	 * Applies the matcher to a collection of scope. Some implementations might
	 * have optimization opportunities.
	 *
	 * @param  names the collection of scope to match.
	 * @return a collection containing those scope that matched.
	 * @review
	 */
	public default Collection<String> filter(Collection<String> names) {
		Stream<String> stream = names.stream();

		return stream.filter(
			this::match
		).collect(
			Collectors.toList()
		);
	}

	/**
	 * Specifies if a given scope matches according to the {@link ScopeMatcher}.
	 *
	 * @param  name
	 * @return <code>true</code> if the input scope is a match for the {@link
	 *         ScopeMatcher}, <code>false</code> otherwise.
	 * @review
	 */
	public boolean match(String name);

}