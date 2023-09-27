/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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