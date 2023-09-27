/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.grouped.util.comparator;

import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Andrea Di Giorgi
 */
public class CPDefinitionGroupedEntryPriorityComparator
	extends OrderByComparator<CPDefinitionGroupedEntry> {

	public static final String ORDER_BY_ASC =
		"CPDefinitionGroupedEntry.priority ASC";

	public static final String ORDER_BY_DESC =
		"CPDefinitionGroupedEntry.priority DESC";

	public static final String[] ORDER_BY_FIELDS = {"priority"};

	public CPDefinitionGroupedEntryPriorityComparator() {
		this(false);
	}

	public CPDefinitionGroupedEntryPriorityComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry1,
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry2) {

		int value = Double.compare(
			cpDefinitionGroupedEntry1.getPriority(),
			cpDefinitionGroupedEntry2.getPriority());

		if (_ascending) {
			return value;
		}

		return Math.negateExact(value);
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