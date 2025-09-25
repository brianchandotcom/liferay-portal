/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket;

import com.liferay.portal.search.aggregation.bucket.SignificantTermsAggregation;

import org.elasticsearch.search.aggregations.bucket.terms.SignificantTermsAggregationBuilder;

/**
 * @author Michael C. Han
 */
public interface SignificantTermsAggregationTranslator {

	public SignificantTermsAggregationBuilder translate(
		SignificantTermsAggregation significantTermsAggregation);

}