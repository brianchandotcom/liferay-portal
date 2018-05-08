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

package com.liferay.portal.search.elasticsearch.internal.facet;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.search.aggregations.bucket.range.AbstractRangeBuilder;
import org.elasticsearch.search.aggregations.bucket.range.InternalRange;
import org.elasticsearch.search.aggregations.bucket.range.RangeBuilder;

/**
 * @author Michael C. Han
 */
public class DefaultRangeBuilder extends AbstractRangeBuilder<RangeBuilder> {

	public DefaultRangeBuilder(String name) {
		super(name, InternalRange.TYPE.name());
	}

	public DefaultRangeBuilder addRange(String from, String to) {
		return addRange(null, from, to);
	}

	public DefaultRangeBuilder addRange(String key, String from, String to) {
		ranges.add(new Range(key, from, to));

		return this;
	}

	public DefaultRangeBuilder addUnboundedFrom(String from) {
		return addUnboundedFrom(null, from);
	}

	public DefaultRangeBuilder addUnboundedFrom(String key, String from) {
		ranges.add(new Range(key, from, null));

		return this;
	}

	public DefaultRangeBuilder addUnboundedTo(String to) {
		return addUnboundedTo(null, to);
	}

	public DefaultRangeBuilder addUnboundedTo(String key, String to) {
		ranges.add(new Range(key, null, to));

		return this;
	}

	public DefaultRangeBuilder format(String format) {
		_format = format;

		return this;
	}

	public boolean hasRanges() {
		return !ranges.isEmpty();
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