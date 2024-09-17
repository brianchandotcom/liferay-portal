/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.internal.contributor;

import com.liferay.portal.search.similar.results.web.spi.contributor.helper.CriteriaBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.CriteriaHelper;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.DestinationBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.DestinationHelper;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteHelper;

/**
 * Implement this interface to contribute content to the Similar Results widget.
 * For more implementation details, see
 * https://learn.liferay.com/web/guest/w/dxp/using-search/developer-guide/contributing-custom-content-to-the-similar-results-widget
 *
 * @author André de Oliveira
 */
public interface SimilarResultsContributor {

	/**
	 * Detects the type of similar results contributor to apply, given the URL
	 * route of the originating asset. Implementations should use
	 * SearchStringUtil.requireEquals() to check if the url route parameter is
	 * correct for the type of asset. When the URL Route matches their type of
	 * contributor, implementations should use the provided {@link RouteBuilder}
	 * to add attributes necessary later for {@link #resolveCriteria} from the
	 * URL available in the {@link RouteHelper}.
	 *
	 * @param routeBuilder An instance of {@link RouteBuilder} to be used for
	 *        adding attributes.
	 * @param routeHelper An instance of {@link RouteHelper} to be used for
	 *        retrieving the URL.
	 */
	public void detectRoute(RouteBuilder routeBuilder, RouteHelper routeHelper);

	/**
	 * Adds the criteria of the originating asset to run a search query based
	 * on that asset. Implementations should add the asset's UID and
	 * (optionally) className to {@link CriteriaBuilder} and use {@link
	 * CriteriaHelper} to access any attributes added to the routeBuilder during
	 * {@link #detectRoute}.
	 *
	 * @param criteriaBuilder An instance of {@link CriteriaBuilder} to be used
	 *        for adding any necessary criteria
	 * @param criteriaHelper An instance of {@link CriteriaHelper} to be used to
	 *        access any attributes added to the routeBuilder during {@link
	 *        #detectRoute}.
	 */
	public void resolveCriteria(
		CriteriaBuilder criteriaBuilder, CriteriaHelper criteriaHelper);

	/**
	 * Overwrites parts of the destination URL for similar results. This method
	 * is called for each similar result. Implementations should use the
	 * provided {@link DestinationBuilder} to modify the destination URL and the
	 * {@link DestinationHelper} to retrieve necessary information about the
	 * asset.
	 *
	 * @param destinationBuilder An instance of {@link DestinationBuilder} to be
	 *        used for constructing the destination.
	 * @param destinationHelper An instance of {@link DestinationHelper} to be
	 *        used for retrieving necessary information about the asset.
	 */
	public void writeDestination(
		DestinationBuilder destinationBuilder,
		DestinationHelper destinationHelper);

}