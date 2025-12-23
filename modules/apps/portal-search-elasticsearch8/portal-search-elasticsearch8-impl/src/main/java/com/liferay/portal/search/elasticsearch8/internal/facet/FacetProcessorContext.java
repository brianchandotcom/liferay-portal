/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.facet;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;

/**
 * @author André de Oliveira
 */
public interface FacetProcessorContext {

	public Aggregation.Builder.ContainerBuilder postProcessAggregationBuilder(
		Aggregation.Builder.ContainerBuilder containerBuilder, String name);

}