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

package com.liferay.portal.security.service.access.quota.impl;

import com.liferay.portal.security.service.access.quota.persistence.SAQImpression;

import java.util.Map;

/**
 * @author Stian Sigvartsen
 */
public class TestSAQImpression implements SAQImpression {

	public TestSAQImpression(
		String key, Map<String, String> metrics, long createdMillis) {

		this(key, metrics, createdMillis, 1);
	}

	public TestSAQImpression(
		String key, Map<String, String> metrics, long createdMillis,
		int weight) {

		_key = key;
		_metrics = metrics;
		_createdMillis = createdMillis;
		_weight = weight;
	}

	@Override
	public long getCreatedMillis() {
		return _createdMillis;
	}

	@Override
	public String getKey() {
		return _key;
	}

	@Override
	public Map<String, String> getMetrics() {
		return _metrics;
	}

	@Override
	public int getWeight() {
		return _weight;
	}

	@Override
	public String toString() {
		return "{key=" + _key + ",metrics=" + _metrics.toString() + "}";
	}

	private final long _createdMillis;
	private final String _key;
	private final Map<String, String> _metrics;
	private final int _weight;

}