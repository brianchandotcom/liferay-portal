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

import java.util.Objects;

/**
 * @author Michael C. Han
 */
public class Order {

	public static final String COUNT_METRIC_NAME = "_count";

	public static final String KEY_METRIC_NAME = "_key";

	public static final Order count(boolean ascending) {
		Order order = new Order(null);

		order.setMetricName(COUNT_METRIC_NAME);
		order.setAscending(ascending);

		return order;
	}

	public static final Order key(boolean ascending) {
		Order order = new Order(null);

		order.setMetricName(KEY_METRIC_NAME);
		order.setAscending(ascending);

		return order;
	}

	public Order(String path) {
		_path = path;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if ((object == null) || (getClass() != object.getClass())) {
			return false;
		}

		final Order order = (Order)object;

		if (_ascending != order._ascending) {
			return false;
		}

		if (!Objects.equals(_metricName, order._metricName)) {
			return false;
		}

		if (!Objects.equals(_path, order._path)) {
			return false;
		}

		return true;
	}

	public String getMetricName() {
		return _metricName;
	}

	public String getPath() {
		return _path;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_ascending, _metricName, _path);
	}

	public boolean isAscending() {
		return _ascending;
	}

	public void setAscending(boolean ascending) {
		_ascending = ascending;
	}

	public void setMetricName(String metricName) {
		_metricName = metricName;
	}

	private boolean _ascending;
	private String _metricName;
	private final String _path;

}