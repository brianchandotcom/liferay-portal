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

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class FolderArticleModifiedDateComparator
	extends OrderByComparator<Object> {

	public static final String ORDER_BY_ASC =
		"modelFolder DESC, modifiedDate ASC";

	public static final String ORDER_BY_DESC =
		"modelFolder DESC, modifiedDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"modifiedDate"};

	public FolderArticleModifiedDateComparator() {
		this(false);
	}

	public FolderArticleModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Object object1, Object object2) {
		int value = 0;

		if ((object1 instanceof JournalFolder) &&
			(object2 instanceof JournalFolder)) {

			value = DateUtil.compareTo(
				getModifiedDate(object1), getModifiedDate(object2));
		}
		else if (object1 instanceof JournalFolder) {
			value = -1;
		}
		else if (object2 instanceof JournalFolder) {
			value = 1;
		}
		else {
			value = DateUtil.compareTo(
				getModifiedDate(object1), getModifiedDate(object2));
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

	protected Date getModifiedDate(Object object) {
		if (object instanceof JournalArticle) {
			JournalArticle article = (JournalArticle)object;

			return article.getModifiedDate();
		}
		else {
			JournalFolder folder = (JournalFolder)object;

			return folder.getModifiedDate();
		}
	}

	private final boolean _ascending;

}