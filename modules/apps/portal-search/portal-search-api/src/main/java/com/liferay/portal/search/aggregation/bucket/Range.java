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

/**
 * @author Michael C. Han
 */
public class Range {

	public static Range unboundedFrom(Double from) {
		return new Range(from, null);
	}

	public static Range unboundedFrom(String key, Double from) {
		return new Range(key, from, null);
	}

	public static Range unboundedTo(Double to) {
		return new Range(null, to);
	}

	public static Range unboundedTo(String key, Double to) {
		return new Range(key, null, to);
	}

	public Range(Double from, Double to) {
		_from = from;
		_fromAsString = null;
		_to = to;
		_toAsString = null;
	}

	public Range(String key, Double from, Double to) {
		_key = key;
		_from = from;
		_fromAsString = null;
		_to = to;
		_toAsString = null;
	}

	public Range(String fromAsString, String toAsString) {
		_from = null;
		_fromAsString = fromAsString;
		_to = null;
		_toAsString = toAsString;
	}

	public Range(String key, String fromAsString, String toAsString) {
		_key = key;
		_from = null;
		_fromAsString = fromAsString;
		_to = null;
		_toAsString = toAsString;
	}

	public Double getFrom() {
		return _from;
	}

	public String getFromAsString() {
		return _fromAsString;
	}

	public String getKey() {
		return _key;
	}

	public Double getTo() {
		return _to;
	}

	public String getToAsString() {
		return _toAsString;
	}

	private final Double _from;
	private final String _fromAsString;
	private String _key;
	private final Double _to;
	private final String _toAsString;

}