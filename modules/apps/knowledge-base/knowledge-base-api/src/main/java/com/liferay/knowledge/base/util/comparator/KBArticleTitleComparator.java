/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.util.comparator;

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
public class KBArticleTitleComparator extends OrderByComparator<KBArticle> {

	public static final String ORDER_BY_ASC = "KBArticle.title ASC";

	public static final String ORDER_BY_DESC = "KBArticle.title DESC";

	public static final String[] ORDER_BY_FIELDS = {"title"};

	public KBArticleTitleComparator() {
		this(false);
	}

	public KBArticleTitleComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(KBArticle kbArticle1, KBArticle kbArticle2) {
		String lowerCaseTitle1 = StringUtil.toLowerCase(kbArticle1.getTitle());
		String lowerCaseTitle2 = StringUtil.toLowerCase(kbArticle2.getTitle());

		int value = lowerCaseTitle1.compareTo(lowerCaseTitle2);

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