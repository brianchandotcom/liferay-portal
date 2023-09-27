/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.aggregation.bucket;

import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.BucketAggregationResult;
import com.liferay.portal.search.internal.aggregation.BaseAggregationResult;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class BaseBucketAggregationResult
	extends BaseAggregationResult implements BucketAggregationResult {

	public BaseBucketAggregationResult(String name) {
		super(name);
	}

	@Override
	public Bucket addBucket(String key, long docCount) {
		Bucket bucket = new BucketImpl(key, docCount);

		_buckets.put(bucket.getKey(), bucket);

		return bucket;
	}

	@Override
	public Bucket getBucket(String key) {
		return _buckets.get(key);
	}

	@Override
	public Collection<Bucket> getBuckets() {
		return Collections.unmodifiableCollection(_buckets.values());
	}

	private final Map<String, Bucket> _buckets = new LinkedHashMap<>();

}