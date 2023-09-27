/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.util.comparator;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Eduardo Pérez Garcia
 */
public class ArticleResourcePKComparator
	extends OrderByComparator<JournalArticle> {

	public static final String ORDER_BY_ASC =
		"JournalArticle.resourcePrimKey ASC";

	public static final String ORDER_BY_DESC =
		"JournalArticle.resourcePrimKey DESC";

	public static final String[] ORDER_BY_FIELDS = {"resourcePrimKey"};

	public ArticleResourcePKComparator() {
		this(true);
	}

	public ArticleResourcePKComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(JournalArticle article1, JournalArticle article2) {
		long resourcePK1 = article1.getResourcePrimKey();
		long resourcePK2 = article2.getResourcePrimKey();

		int value = 1;

		if (resourcePK1 <= resourcePK2) {
			value = -1;
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