/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.util.comparator;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Brian Wing Shun Chan
 */
public class ArticleReviewDateComparator
	extends OrderByComparator<JournalArticle> {

	public static final String ORDER_BY_ASC =
		"JournalArticle.reviewDate ASC, JournalArticle.version ASC";

	public static final String ORDER_BY_DESC =
		"JournalArticle.reviewDate DESC, JournalArticle.version DESC";

	public static final String[] ORDER_BY_FIELDS = {"reviewDate", "version"};

	public ArticleReviewDateComparator() {
		this(false);
	}

	public ArticleReviewDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(JournalArticle article1, JournalArticle article2) {
		int value = DateUtil.compareTo(
			article1.getReviewDate(), article2.getReviewDate());

		if (value == 0) {
			if (article1.getVersion() < article2.getVersion()) {
				value = -1;
			}
			else if (article1.getVersion() > article2.getVersion()) {
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
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}