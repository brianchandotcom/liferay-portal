/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.aggregation.bucket;

import com.liferay.portal.search.aggregation.bucket.BucketCountThresholds;

/**
 * @author Michael C. Han
 */
public class BucketCountThresholdsImpl implements BucketCountThresholds {

	public BucketCountThresholdsImpl(
		long minDocCount, long shardMinDocCount, int requiredSize,
		int shardSize) {

		_minDocCount = minDocCount;
		_shardMinDocCount = shardMinDocCount;
		_requiredSize = requiredSize;
		_shardSize = shardSize;
	}

	@Override
	public long getMinDocCount() {
		return _minDocCount;
	}

	@Override
	public int getRequiredSize() {
		return _requiredSize;
	}

	@Override
	public long getShardMinDocCount() {
		return _shardMinDocCount;
	}

	@Override
	public int getShardSize() {
		return _shardSize;
	}

	private final long _minDocCount;
	private final int _requiredSize;
	private final long _shardMinDocCount;
	private final int _shardSize;

}