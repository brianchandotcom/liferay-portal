/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.aggregation.bucket;

import com.liferay.portal.search.aggregation.bucket.DiversifiedSamplerAggregationResult;

/**
 * @author Michael C. Han
 */
public class DiversifiedSamplerAggregationResultImpl
	extends SamplerAggregationResultImpl
	implements DiversifiedSamplerAggregationResult {

	public DiversifiedSamplerAggregationResultImpl(String name, long docCount) {
		super(name, docCount);
	}

}