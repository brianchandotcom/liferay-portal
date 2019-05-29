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

package com.liferay.portal.search.internal.searcher;

import com.liferay.portal.search.spi.searcher.SearchRequestContributor;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * @author André de Oliveira
 */
public interface SearchRequestContributorsHolder {

	public Stream<SearchRequestContributor> stream();

	public Stream<SearchRequestContributor> stream(
		Collection<String> includeIds, Collection<String> excludeIds);

}