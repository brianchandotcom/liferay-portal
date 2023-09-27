/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.util.comparator;

import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
public class KBTemplateTitleComparator extends OrderByComparator<KBTemplate> {

	public static final String ORDER_BY_ASC = "KBTemplate.title ASC";

	public static final String ORDER_BY_DESC = "KBTemplate.title DESC";

	public static final String[] ORDER_BY_FIELDS = {"title"};

	public KBTemplateTitleComparator() {
		this(false);
	}

	public KBTemplateTitleComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(KBTemplate kbTemplate1, KBTemplate kbTemplate2) {
		String lowerCaseTitle1 = StringUtil.toLowerCase(kbTemplate1.getTitle());
		String lowerCaseTitle2 = StringUtil.toLowerCase(kbTemplate2.getTitle());

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