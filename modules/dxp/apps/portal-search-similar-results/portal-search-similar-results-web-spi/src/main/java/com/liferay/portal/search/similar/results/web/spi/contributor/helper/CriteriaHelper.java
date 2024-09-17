/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.spi.contributor.helper;

/**
 * @author André de Oliveira
 */
public interface CriteriaHelper {

	/**
	 * @return The group ID of the current context.
	 */
	public long getGroupId();

	/**
	 * Retrieves the value of a route parameter with the specified key. This
	 * method is used by {@link SimilarResultsContributor} implementations to
	 * access attributes added to the route during {@link
	 * SimilarResultsContributor#detectRoute} via {@link RouteBuilder}. These
	 * attributes can be used to add criteria to {@link CriteriaBuilder}.
	 *
	 * @param  key The key of the route parameter to retrieve.
	 * @return The value of the route parameter with the specified key.
	 */
	public Object getRouteParameter(String key);

}