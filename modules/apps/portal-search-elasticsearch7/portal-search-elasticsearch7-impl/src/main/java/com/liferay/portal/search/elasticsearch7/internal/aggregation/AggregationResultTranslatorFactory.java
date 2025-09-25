/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.aggregation;

import com.liferay.portal.search.aggregation.AggregationResultTranslator;

import org.elasticsearch.search.aggregations.Aggregation;

/**
 * @author André de Oliveira
 */
public interface AggregationResultTranslatorFactory {

	public AggregationResultTranslator createAggregationResultTranslator(
		Aggregation aggregation);

}