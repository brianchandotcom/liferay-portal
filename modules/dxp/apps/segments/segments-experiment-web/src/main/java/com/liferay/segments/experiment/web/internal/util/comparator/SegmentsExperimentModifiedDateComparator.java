/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.experiment.web.internal.util.comparator;

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

	public SegmentsExperimentModifiedDateComparator() {
		this(false);
	}

	public SegmentsExperimentModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
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

	private final boolean _ascending;

}