/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.spi.contributor.helper;

/**
 * @author André de Oliveira
 */
public interface RouteHelper {

	/**
	 * Retrieves the current URL string associated with the route. This method
	 * is used by {@link SimilarResultsContributor#detectRoute} to extract
	 * information from the URL that is used to populate attributes in the
	 * {@link RouteBuilder}. These attributes can then be accessed during {@link
	 * SimilarResultsContributor#resolveCriteria}.
	 *
	 * @return The current URL string associated with the route.
	 */
	public String getURLString();

}