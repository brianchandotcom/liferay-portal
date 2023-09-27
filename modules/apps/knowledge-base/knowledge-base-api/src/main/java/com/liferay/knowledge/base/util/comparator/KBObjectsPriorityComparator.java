/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.util.comparator;

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Roberto Díaz
 */
public class KBObjectsPriorityComparator<T> extends OrderByComparator<T> {

	public static final String ORDER_BY_ASC =
		"modelFolder DESC, priority ASC, title ASC";

	public static final String ORDER_BY_DESC =
		"modelFolder DESC, priority DESC, title ASC";

	public static final String[] ORDER_BY_FIELDS = {"priority", "title"};

	public KBObjectsPriorityComparator() {
		this(false);
	}

	public KBObjectsPriorityComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(T t1, T t2) {
		int value = 0;

		double priority1 = getPriority(t1);
		double priority2 = getPriority(t2);

		String title1 = getTitle(t1);
		String title2 = getTitle(t1);

		if ((t1 instanceof KBFolder) && (t2 instanceof KBFolder)) {
			value = title1.compareToIgnoreCase(title2);
		}
		else if (t1 instanceof KBFolder) {
			value = -1;
		}
		else if (t2 instanceof KBFolder) {
			value = 1;
		}
		else {
			if (priority1 < priority2) {
				value = -1;
			}
			else if (priority1 > priority2) {
				value = 1;
			}
			else {
				value = title1.compareToIgnoreCase(title2);
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

	protected double getPriority(Object object) {
		if (object instanceof KBArticle) {
			KBArticle kbArticle = (KBArticle)object;

			return kbArticle.getPriority();
		}

		return 0.0;
	}

	protected String getTitle(Object object) {
		if (object instanceof KBArticle) {
			KBArticle kbArticle = (KBArticle)object;

			return kbArticle.getTitle();
		}

		KBFolder kbFolder = (KBFolder)object;

		return kbFolder.getName();
	}

	private final boolean _ascending;

}