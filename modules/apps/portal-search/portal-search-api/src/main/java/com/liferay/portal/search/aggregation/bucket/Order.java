/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.bucket;

import java.util.Objects;

/**
 * @author Michael C. Han
 */
public class Order {

	public static final String COUNT_METRIC_NAME = "_count";

	public static final String KEY_METRIC_NAME = "_key";

	public static Order count(boolean ascending) {
		Order order = new Order(null);

		order.setMetricName(COUNT_METRIC_NAME);
		order.setAscending(ascending);

		return order;
	}

	public static Order key(boolean ascending) {
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

		Order order = (Order)object;

		if ((_ascending != order._ascending) ||
			!Objects.equals(_metricName, order._metricName) ||
			!Objects.equals(_path, order._path)) {

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