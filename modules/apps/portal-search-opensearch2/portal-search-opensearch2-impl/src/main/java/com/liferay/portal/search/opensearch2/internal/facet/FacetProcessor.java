/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.facet;

import com.liferay.portal.kernel.search.facet.Facet;

import org.opensearch.client.opensearch._types.aggregations.Aggregation;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
public interface FacetProcessor<T> {

	public Aggregation.Builder.ContainerBuilder processFacet(Facet facet);

}