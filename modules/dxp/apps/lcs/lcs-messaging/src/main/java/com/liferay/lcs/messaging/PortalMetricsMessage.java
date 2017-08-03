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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class PortalMetricsMessage extends MetricsMessage {

	public List<Map<String, Object>> getLayoutPerformanceMetricsList() {
		return _layoutPerformanceMetricsList;
	}

	public List<Map<String, Object>> getPortletPerformanceMetricsList() {
		return _portletPerformanceMetricsList;
	}

	public void setLayoutPerformanceMetricsList(
		List<Map<String, Object>> layoutPerformanceMetricsList) {

		_layoutPerformanceMetricsList = layoutPerformanceMetricsList;
	}

	public void setPortletPerformanceMetricsList(
		List<Map<String, Object>> portletPerformanceMetricsList) {

		_portletPerformanceMetricsList = portletPerformanceMetricsList;
	}

	private List<Map<String, Object>> _layoutPerformanceMetricsList =
		new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> _portletPerformanceMetricsList =
		new ArrayList<Map<String, Object>>();

}