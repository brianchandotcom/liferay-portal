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

	public ArticleModifiedDateComparator() {
		this(false);
	}

	public ArticleModifiedDateComparator(boolean ascending) {
		super(ascending);
	}

	@Override
	public String getOrderBy() {
		if (isAscending()) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
	}

}