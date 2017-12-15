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

package com.liferay.lcs.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class ServerMetricsMessage extends MetricsMessage {

	public Map<String, Map<String, Object>> getCurrentThreadsMetrics() {
		return _currentThreadsMetrics;
	}

	public Map<String, Map<String, Object>> getJDBCConnectionPoolsMetrics() {
		return _jdbcConnectionPoolsMetrics;
	}

	public void setCurrentThreadsMetrics(
		Map<String, Map<String, Object>> currentThreadsMetrics) {

		_currentThreadsMetrics = currentThreadsMetrics;
	}

	@JsonProperty("jdbcConnectionPoolsMetrics")
	public void setJDBCConnectionPoolsMetrics(
		Map<String, Map<String, Object>> jdbcConnectionPoolsMetrics) {

		_jdbcConnectionPoolsMetrics = jdbcConnectionPoolsMetrics;
	}

	private Map<String, Map<String, Object>> _currentThreadsMetrics =
		new HashMap<String, Map<String, Object>>();
	private Map<String, Map<String, Object>> _jdbcConnectionPoolsMetrics =
		new HashMap<String, Map<String, Object>>();

}