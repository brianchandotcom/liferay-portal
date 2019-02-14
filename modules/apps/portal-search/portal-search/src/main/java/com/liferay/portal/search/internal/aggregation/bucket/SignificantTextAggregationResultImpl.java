/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.internal.aggregation.bucket;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.search.aggregation.bucket.SignificantTextAggregationResult;

/**
 * @author Michael C. Han
 */
@ProviderType
public class SignificantTextAggregationResultImpl
	extends BaseBucketAggregationResult
	implements SignificantTextAggregationResult {

	public SignificantTextAggregationResultImpl(
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