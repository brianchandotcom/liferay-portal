/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.util.comparator;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.segments.asah.connector.internal.client.model.Metric;

/**
 * @author Riccardo Ferrari
 */
public class MetricProcessedDateComparator extends OrderByComparator<Metric> {

	public static final String ORDER_BY_ASC = "Metric.processedDate ASC";

	public static final String ORDER_BY_DESC = "Metric.processedDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"processedDate"};

	public MetricProcessedDateComparator() {
		this(false);
	}

	public MetricProcessedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Metric metric1, Metric metric2) {
		int value = DateUtil.compareTo(
			metric1.getProcessedDate(), metric2.getProcessedDate());

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}