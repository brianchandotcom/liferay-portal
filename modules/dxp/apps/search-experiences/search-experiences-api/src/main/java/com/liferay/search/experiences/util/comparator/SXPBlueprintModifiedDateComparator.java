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

package com.liferay.search.experiences.util.comparator;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.search.experiences.model.SXPBlueprint;

/**
 * @author Petteri Karttunen
 */
public class SXPBlueprintModifiedDateComparator
	extends OrderByComparator<SXPBlueprint> {

	public static final String ORDER_BY_ASC =
		"SXPBlueprint.modifiedDate ASC, SXPBlueprint.sxpBlueprintId ASC";

	public static final String[] ORDER_BY_CONDITION_FIELDS = {"modifiedDate"};

	public static final String ORDER_BY_DESC =
		"SXPBlueprint.modifiedDate DESC, SXPBlueprint.sxpBlueprintId DESC";

	public static final String[] ORDER_BY_FIELDS = {
		"modifiedDate", "sxpBlueprintId"
	};

	public SXPBlueprintModifiedDateComparator() {
		this(false);
	}

	public SXPBlueprintModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(SXPBlueprint sxpBlueprint1, SXPBlueprint sxpBlueprint2) {
		int value = DateUtil.compareTo(
			sxpBlueprint1.getModifiedDate(), sxpBlueprint2.getModifiedDate());

		if (value == 0) {
			if (sxpBlueprint1.getSXPBlueprintId() <
					sxpBlueprint2.getSXPBlueprintId()) {

				value = -1;
			}
			else if (sxpBlueprint1.getSXPBlueprintId() >
						sxpBlueprint2.getSXPBlueprintId()) {

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