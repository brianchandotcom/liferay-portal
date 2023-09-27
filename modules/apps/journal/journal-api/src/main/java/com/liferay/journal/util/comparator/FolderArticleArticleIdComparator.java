/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.util.comparator;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Eudaldo
 */
public class FolderArticleArticleIdComparator
	extends OrderByComparator<Object> {

	public static final String ORDER_BY_ASC = "articleId ASC";

	public static final String ORDER_BY_DESC = "articleId DESC";

	public static final String[] ORDER_BY_FIELDS = {"articleId"};

	public FolderArticleArticleIdComparator() {
		this(false);
	}

	public FolderArticleArticleIdComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Object object1, Object object2) {
		int value = 0;

		if ((object1 instanceof JournalArticle) &&
			(object2 instanceof JournalArticle)) {

			JournalArticle journalArticle1 = (JournalArticle)object1;

			String articleId1 = journalArticle1.getArticleId();

			JournalArticle journalArticle2 = (JournalArticle)object2;

			String articleId2 = journalArticle2.getArticleId();

			value = articleId1.compareTo(articleId2);
		}
		else if (object1 instanceof JournalArticle) {
			value = -1;
		}
		else if (object2 instanceof JournalArticle) {
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