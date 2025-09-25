/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.facet;

import com.liferay.portal.kernel.search.facet.Facet;

import org.elasticsearch.search.aggregations.AggregationBuilder;

/**
 * @author Michael C. Han
 */
public interface FacetProcessor<T> {

	public AggregationBuilder processFacet(Facet facet);

}