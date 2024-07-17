/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.util.comparator;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.segments.model.SegmentsExperiment;

/**
 * @author Sarai Díaz
 */
public class SegmentsExperimentModifiedDateComparator
	extends OrderByComparator<SegmentsExperiment> {

	public static final String ORDER_BY_ASC =
		"SegmentsExperiment.modifiedDate ASC";

	public static final String ORDER_BY_DESC =
		"SegmentsExperiment.modifiedDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"modifiedDate"};

	public static SegmentsExperimentModifiedDateComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		SegmentsExperiment segmentsExperiment1,
		SegmentsExperiment segmentsExperiment2) {

		int value = DateUtil.compareTo(
			segmentsExperiment1.getModifiedDate(),
			segmentsExperiment2.getModifiedDate());

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

	private SegmentsExperimentModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final SegmentsExperimentModifiedDateComparator
		_INSTANCE_ASCENDING = new SegmentsExperimentModifiedDateComparator(
			true);

	private static final SegmentsExperimentModifiedDateComparator
		_INSTANCE_DESCENDING = new SegmentsExperimentModifiedDateComparator(
			false);

	private final boolean _ascending;

}