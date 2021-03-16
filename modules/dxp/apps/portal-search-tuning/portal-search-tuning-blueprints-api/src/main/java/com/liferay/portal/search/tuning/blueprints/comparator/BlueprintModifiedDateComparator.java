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

package com.liferay.portal.search.tuning.blueprints.comparator;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;

/**
 * @author Petteri Karttunen
 */
public class BlueprintModifiedDateComparator
	extends OrderByComparator<Blueprint> {

	public static final String ORDER_BY_ASC =
		"Blueprint.modifiedDate ASC, Blueprint.blueprintId ASC";

	public static final String[] ORDER_BY_CONDITION_FIELDS = {"modifiedDate"};

	public static final String ORDER_BY_DESC =
		"Blueprint.modifiedDate DESC, Blueprint.blueprintId DESC";

	public static final String[] ORDER_BY_FIELDS = {
		"modifiedDate", "blueprintId"
	};

	public BlueprintModifiedDateComparator() {
		this(false);
	}

	public BlueprintModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Blueprint blueprint1, Blueprint blueprint2) {
		int value = DateUtil.compareTo(
			blueprint1.getModifiedDate(), blueprint2.getModifiedDate());

		if (value == 0) {
			if (blueprint1.getBlueprintId() < blueprint2.getBlueprintId()) {
				value = -1;
			}
			else if (blueprint1.getBlueprintId() >
						blueprint2.getBlueprintId()) {

				value = 1;
			}
		}

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
	public String[] getOrderByConditionFields() {
		return ORDER_BY_CONDITION_FIELDS;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private static final long serialVersionUID = 1L;

	private final boolean _ascending;

}