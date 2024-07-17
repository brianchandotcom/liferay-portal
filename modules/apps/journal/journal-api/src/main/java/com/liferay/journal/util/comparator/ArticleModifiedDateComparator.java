/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.util.comparator;

import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.journal.model.JournalArticle;

/**
 * @author Brian Wing Shun Chan
 */
public class ArticleModifiedDateComparator
	extends StagedModelModifiedDateComparator<JournalArticle> {

	public static final String ORDER_BY_ASC = "JournalArticle.modifiedDate ASC";

	public static final String ORDER_BY_DESC =
		"JournalArticle.modifiedDate DESC";

	public static ArticleModifiedDateComparator getInstance(boolean ascending) {
		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public String getOrderBy() {
		if (isAscending()) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
	}

	private ArticleModifiedDateComparator(boolean ascending) {
		super(ascending);
	}

	private static final ArticleModifiedDateComparator _INSTANCE_ASCENDING =
		new ArticleModifiedDateComparator(true);

	private static final ArticleModifiedDateComparator _INSTANCE_DESCENDING =
		new ArticleModifiedDateComparator(false);

}