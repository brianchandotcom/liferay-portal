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

package com.liferay.portal.search.searcher;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.search.Query;

/**
 * Holds parameters to be used when performing a search.
 *
 * @author André de Oliveira
 *
 * @review
 */
@ProviderType
public interface SearchRequest {

	/**
	 * Provides a secondary query to reorder the top documents returned.
	 *
	 * @return the rescore query
	 *
	 * @review
	 */
	public Query getRescoreQuery();

	/**
	 * Enables explanation for each hit on how its score was computed.
	 *
	 * @return whether to explain scores
	 *
	 * @review
	 */
	public boolean isExplain();

	/**
	 * Enables inclusion of the search engine's response string with results.
	 *
	 * @return whether to include the response string
	 *
	 * @review
	 */
	public boolean isIncludeResponseString();

}