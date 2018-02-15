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

import java.util.Map;

/**
 * Represents a Liferay Cloud Services Protocol metrics message. Metrics
 * messages transfer metrics data in their payload.
 *
 * <p>
 * The payload must include metrics of a single type, being either cache, JVM,
 * layout, portal, portlet, server, or service type metrics. These metrics types
 * are represented in this class's public metrics type constants.
 * </p>
 *
 * @author  Miguel Pastor
 * @author  Ivica Cardic
 * @author  Igor Beslic
 * @version 2.1.1
 * @since   LCS 0.1
 */
public class MetricsMessage extends Message {

	public static final String METRICS_TYPE_CACHE = "cache";

	public static final String METRICS_TYPE_JVM = "jvm";

	public static final String METRICS_TYPE_LAYOUT = "layout";

	public static final String METRICS_TYPE_PORTAL = "portal";

	public static final String METRICS_TYPE_PORTLET = "portlet";

	public static final String METRICS_TYPE_SERVER = "server";

	public static final String METRICS_TYPE_SERVICE = "service";

	/**
	 * Returns the metrics type from the message's payload.
	 *
	 * @return the metrics type from the message's payload
	 * @since  LCS 0.1
	 */
	public String getMetricsType() {
		return _metricsType;
	}

	/**
	 * Sets the metrics type from the message's payload.
	 *
	 * @param metricsType the metrics type
	 * @since LCS 0.1
	 */
	public void setMetricsType(String metricsType) {
		_metricsType = metricsType;
	}

	@Override
	public String toString() {
		if (_toString != null) {
			return _toString;
		}

		StringBuilder sb = new StringBuilder(11);

		sb.append("{className=");

		Class<?> clazz = getClass();

		sb.append(clazz.getName());

		sb.append(", createTime=");
		sb.append(getCreateTime());
		sb.append(", key=");
		sb.append(getKey());
		sb.append(", metricsType=");
		sb.append(_metricsType);
		sb.append(", payload=");
		sb.append(getPayload());
		sb.append(", values=");

		Map<String, Object> values = getValues();

		if (values != null) {
			sb.append(values.toString());
		}

		sb.append("}");

		_toString = sb.toString();

		return _toString;
	}

	private String _metricsType;
	private String _toString;

}