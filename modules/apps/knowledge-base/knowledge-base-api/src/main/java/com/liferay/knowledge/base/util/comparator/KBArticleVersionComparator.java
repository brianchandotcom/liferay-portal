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

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
public class KBArticleVersionComparator extends OrderByComparator<KBArticle> {

	public static final String ORDER_BY_ASC = "KBArticle.version ASC";

	public static final String ORDER_BY_DESC = "KBArticle.version DESC";

	public static final String[] ORDER_BY_FIELDS = {"version", "title"};

	public KBArticleVersionComparator() {
		this(false);
	}

	public KBArticleVersionComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(KBArticle kbArticle1, KBArticle kbArticle2) {
		int value = 0;

		if (kbArticle1.getVersion() < kbArticle2.getVersion()) {
			value = -1;
		}
		else if (kbArticle1.getVersion() > kbArticle2.getVersion()) {
			value = 1;
		}
		else {
			String title1 = kbArticle1.getTitle();
			String title2 = kbArticle2.getTitle();

			value = title1.compareToIgnoreCase(title2);
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