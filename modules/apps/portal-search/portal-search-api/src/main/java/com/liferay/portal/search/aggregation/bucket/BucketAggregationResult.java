/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.bucket;

import com.liferay.portal.search.aggregation.AggregationResult;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public abstract class BucketAggregationResult extends AggregationResult {

	public BucketAggregationResult(String name) {
		super(name);
	}

	public Bucket addBucket(String key, long docCount) {
		Bucket bucket = new Bucket(key, docCount);

		_buckets.put(bucket.getKey(), bucket);

		return bucket;
	}

	public Bucket getBucket(String key) {
		return _buckets.get(key);
	}

	public Collection<Bucket> getBuckets() {
		return Collections.unmodifiableCollection(_buckets.values());
	}

	private final Map<String, Bucket> _buckets = new LinkedHashMap<>();

}