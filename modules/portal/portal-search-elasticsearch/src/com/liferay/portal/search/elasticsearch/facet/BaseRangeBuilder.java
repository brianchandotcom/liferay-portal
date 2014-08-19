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

package com.liferay.portal.search.elasticsearch.facet;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.search.aggregations.bucket.range.AbstractRangeBuilder;
import org.elasticsearch.search.aggregations.bucket.range.InternalRange;
import org.elasticsearch.search.aggregations.bucket.range.RangeBuilder;

/**
 * @author Michael C. Han
 */
public class BaseRangeBuilder extends AbstractRangeBuilder<RangeBuilder> {

	public BaseRangeBuilder(String name) {
		super(name, InternalRange.TYPE.name());
	}

	public BaseRangeBuilder addRange(String from, String to) {
		return addRange(null, from, to);
	}

	public BaseRangeBuilder addRange(String key, String from, String to) {
		ranges.add(new Range(key, from, to));

		return this;
	}

	public BaseRangeBuilder addUnboundedFrom(String from) {
		return addUnboundedFrom(null, from);
	}

	public BaseRangeBuilder addUnboundedFrom(String key, String from) {
		ranges.add(new Range(key, from, null));
		return this;
	}

	public BaseRangeBuilder addUnboundedTo(String to) {
		return addUnboundedTo(null, to);
	}

	public BaseRangeBuilder addUnboundedTo(String key, String to) {
		ranges.add(new Range(key, null, to));

		return this;
	}

	public BaseRangeBuilder format(String format) {
		_format = format;

		return this;
	}

	@Override
	protected XContentBuilder doInternalXContent(
			XContentBuilder builder, Params params)
		throws IOException {

		super.doInternalXContent(builder, params);

		if (_format != null) {
			builder.field("format", _format);
		}

		return builder;
	}

	private String _format;

}