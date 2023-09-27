/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.aggregation.bucket;

import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;

/**
 * @author Michael C. Han
 */
public class TermsAggregationResultImpl
	extends BaseBucketAggregationResult implements TermsAggregationResult {

	public TermsAggregationResultImpl(
		String name, long errorDocCounts, long otherDocCounts) {

		super(name);

		_errorDocCounts = errorDocCounts;
		_otherDocCounts = otherDocCounts;
	}

	@Override
	public long getErrorDocCounts() {
		return _errorDocCounts;
	}

	@Override
	public long getOtherDocCounts() {
		return _otherDocCounts;
	}

	private final long _errorDocCounts;
	private final long _otherDocCounts;

}