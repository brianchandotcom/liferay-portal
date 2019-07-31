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

package com.liferay.portal.search.elasticsearch6.internal.aggregation.pipeline;

import com.liferay.portal.search.aggregation.pipeline.GapPolicy;

import org.elasticsearch.search.aggregations.pipeline.BucketHelpers;

/**
 * @author Michael C. Han
 */
public class GapPolicyTranslator {

	public BucketHelpers.GapPolicy translate(GapPolicy gapPolicy) {
		if (gapPolicy == GapPolicy.INSTANT_ZEROS) {
			return BucketHelpers.GapPolicy.INSERT_ZEROS;
		}
		else if (gapPolicy == GapPolicy.SKIP) {
			return BucketHelpers.GapPolicy.SKIP;
		}
		else {
			throw new IllegalArgumentException(
				"Invalid gap policy" + gapPolicy);
		}
	}

}