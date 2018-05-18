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

	public CPAttachmentFileEntryPriorityComparator() {
		this(false);
	}

	public CPAttachmentFileEntryPriorityComparator(boolean ascending) {
		_ascending = ascending;
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
		else {
			return Math.negateExact(value);
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
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