/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.facet;

import org.opensearch.client.opensearch._types.aggregations.Aggregation;

/**
 * @author André de Oliveira
 * @author Petteri Karttunen
 */
public interface FacetProcessorContext {

	public Aggregation.Builder.ContainerBuilder postProcessAggregationBuilder(
		Aggregation.Builder.ContainerBuilder containerBuilder, String name);

}