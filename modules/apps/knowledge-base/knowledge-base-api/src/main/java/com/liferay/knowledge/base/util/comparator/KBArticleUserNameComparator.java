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
public class KBArticleUserNameComparator extends OrderByComparator<KBArticle> {

	public static final String ORDER_BY_ASC = "KBArticle.userName ASC";

	public static final String ORDER_BY_DESC = "KBArticle.userName DESC";

	public static final String[] ORDER_BY_FIELDS = {"userName", "title"};

	public KBArticleUserNameComparator() {
		this(false);
	}

	public KBArticleUserNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(KBArticle kbArticle1, KBArticle kbArticle2) {
		String lowerCaseUserName1 = StringUtil.toLowerCase(
			kbArticle1.getUserName());
		String lowerCaseUserName2 = StringUtil.toLowerCase(
			kbArticle2.getUserName());

		int value = lowerCaseUserName1.compareTo(lowerCaseUserName2);

		if (value == 0) {
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