/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.util.comparator;

import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Marco Leo
 */
public class CPAttachmentFileEntryPriorityComparator
	extends OrderByComparator<CPAttachmentFileEntry> {

	public static final String ORDER_BY_ASC =
		"CPAttachmentFileEntry.priority ASC";

	public static final String ORDER_BY_DESC =
		"CPAttachmentFileEntry.priority DESC";

	public static final String[] ORDER_BY_FIELDS = {"priority"};

	public static CPAttachmentFileEntryPriorityComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		CPAttachmentFileEntry cpAttachmentFileEntry1,
		CPAttachmentFileEntry cpAttachmentFileEntry2) {

		int value = Double.compare(
			cpAttachmentFileEntry1.getPriority(),
			cpAttachmentFileEntry2.getPriority());

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

	private CPAttachmentFileEntryPriorityComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final CPAttachmentFileEntryPriorityComparator
		_INSTANCE_ASCENDING = new CPAttachmentFileEntryPriorityComparator(true);

	private static final CPAttachmentFileEntryPriorityComparator
		_INSTANCE_DESCENDING = new CPAttachmentFileEntryPriorityComparator(
			false);

	private final boolean _ascending;

}