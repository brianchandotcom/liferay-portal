/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.segments.model.SegmentsExperience;

/**
 * @author Shuyang Zhou
 */
public class SegmentsExperiencePriorityComparator
	extends OrderByComparator<SegmentsExperience> {

	public static SegmentsExperiencePriorityComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		SegmentsExperience segmentsExperience1,
		SegmentsExperience segmentsExperience2) {

		int value = Integer.compare(
			segmentsExperience1.getPriority(),
			segmentsExperience2.getPriority());

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return _ORDER_BY_ASC;
		}

		return _ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return _ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private SegmentsExperiencePriorityComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final SegmentsExperiencePriorityComparator
		_INSTANCE_ASCENDING = new SegmentsExperiencePriorityComparator(true);

	private static final SegmentsExperiencePriorityComparator
		_INSTANCE_DESCENDING = new SegmentsExperiencePriorityComparator(false);

	private static final String _ORDER_BY_ASC =
		"SegmentsExperience.priority ASC";

	private static final String _ORDER_BY_DESC =
		"SegmentsExperience.priority DESC";

	private static final String[] _ORDER_BY_FIELDS = {"priority"};

	private final boolean _ascending;

}