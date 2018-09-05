/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.knowledge.base.util.comparator;

import com.liferay.knowledge.base.model.KBComment;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Sergio González
 */
public class KBCommentStatusComparator extends OrderByComparator<KBComment> {

	public static final String ORDER_BY_ASC = "KBComment.status ASC";

	public static final String ORDER_BY_DESC = "KBComment.status DESC";

	public static final String[] ORDER_BY_FIELDS = {"status"};

	public KBCommentStatusComparator() {
		this(false);
	}

	public KBCommentStatusComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(KBComment kbComment1, KBComment kbComment2) {
		int value = 0;

		if (kbComment1.getStatus() < kbComment2.getStatus()) {
			value = -1;
		}
		else if (kbComment1.getStatus() > kbComment2.getStatus()) {
			value = 1;
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
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}