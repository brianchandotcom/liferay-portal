/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.util.comparator;

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
public class KBArticleCreateDateComparator
	extends OrderByComparator<KBArticle> {

	public static final String ORDER_BY_ASC = "KBArticle.createDate ASC";

	public static final String ORDER_BY_DESC = "KBArticle.createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public static KBArticleCreateDateComparator getInstance(boolean ascending) {
		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(KBArticle kbArticle1, KBArticle kbArticle2) {
		int value = DateUtil.compareTo(
			kbArticle1.getCreateDate(), kbArticle2.getCreateDate());

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

	private KBArticleCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final KBArticleCreateDateComparator _INSTANCE_ASCENDING =
		new KBArticleCreateDateComparator(true);

	private static final KBArticleCreateDateComparator _INSTANCE_DESCENDING =
		new KBArticleCreateDateComparator(false);

	private final boolean _ascending;

}