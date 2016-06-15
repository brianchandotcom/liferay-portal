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
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Roberto Díaz
 */
public class KBObjectsTitleComparator<T> extends OrderByComparator<T> {

	public static final String ORDER_BY_ASC = "title ASC";

	public static final String ORDER_BY_DESC = "title DESC";

	public static final String[] ORDER_BY_FIELDS = {"title"};

	public static final String ORDER_BY_MODEL_ASC =
		"modelFolder DESC, title ASC";

	public static final String ORDER_BY_MODEL_DESC =
		"modelFolder DESC, title DESC";

	public KBObjectsTitleComparator() {
		this(false, false);
	}

	public KBObjectsTitleComparator(boolean ascending) {
		this(ascending, false);
	}

	public KBObjectsTitleComparator(boolean ascending, boolean orderByModel) {
		_ascending = ascending;
		_orderByModel = orderByModel;
	}

	@Override
	public int compare(T t1, T t2) {
		int value = 0;

		String name1 = getName(t1);
		String name2 = getName(t2);

		if (_orderByModel) {
			if (t1 instanceof KBFolder && t2 instanceof KBFolder) {
				name1.compareToIgnoreCase(name2);
			}
			else if (t1 instanceof KBFolder) {
				value = -1;
			}
			else if (t2 instanceof KBFolder) {
				value = 1;
			}
			else {
				name1.compareToIgnoreCase(name2);
			}
		}
		else {
			value = name1.compareToIgnoreCase(name2);
		}

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (_orderByModel) {
			if (_ascending) {
				return ORDER_BY_MODEL_ASC;
			}
			else {
				return ORDER_BY_MODEL_DESC;
			}
		}
		else {
			if (_ascending) {
				return ORDER_BY_ASC;
			}
			else {
				return ORDER_BY_DESC;
			}
		}
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	protected String getName(Object obj) {
		if (obj instanceof KBArticle) {
			KBArticle kbArticle = (KBArticle)obj;

			return kbArticle.getTitle();
		}
		else {
			KBFolder kbFolder = (KBFolder)obj;

			return kbFolder.getName();
		}
	}

	private final boolean _ascending;
	private final boolean _orderByModel;

}