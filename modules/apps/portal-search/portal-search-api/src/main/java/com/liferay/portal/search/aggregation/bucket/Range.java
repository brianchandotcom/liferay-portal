/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		_to = to;

		_fromAsString = null;
		_toAsString = null;
	}

	public Range(String key, Double from, Double to) {
		_key = key;
		_from = from;
		_to = to;

		_fromAsString = null;
		_toAsString = null;
	}

	public Range(String fromAsString, String toAsString) {
		_fromAsString = fromAsString;
		_toAsString = toAsString;

		_from = null;
		_to = null;
	}

	public Range(String key, String fromAsString, String toAsString) {
		_key = key;
		_fromAsString = fromAsString;
		_toAsString = toAsString;

		_from = null;
		_to = null;
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