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

package com.liferay.portal.search.aggregation.bucket;

import com.liferay.portal.search.aggregation.AggregationResult;

import java.util.Collection;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface BucketAggregationResult extends AggregationResult {

	public Bucket addBucket(String key, long docCount);

	public Bucket getBucket(String key);

	public Collection<Bucket> getBuckets();

}