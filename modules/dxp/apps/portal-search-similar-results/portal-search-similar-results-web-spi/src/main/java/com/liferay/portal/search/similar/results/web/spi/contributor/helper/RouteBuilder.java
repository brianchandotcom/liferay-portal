/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.spi.contributor.helper;

/**
 * @author André de Oliveira
 */
public interface RouteBuilder {

	/**
	 * Adds an attribute to the route with the specified name and value. This
	 * method is used by {@link SimilarResultsContributor#detectRoute} to store
	 * information extracted from the URL (obtained via {@link
	 * SimilarResultsContributor#routeHelper}) that is used later during
	 * {@link SimilarResultsContributor#resolveCriteria}.
	 *
	 * @param  name The name of the attribute to add.
	 * @param  value The value of the attribute to add.
	 * @return This {@link RouteBuilder} instance.
	 */
	public RouteBuilder addAttribute(String name, Object value);

}