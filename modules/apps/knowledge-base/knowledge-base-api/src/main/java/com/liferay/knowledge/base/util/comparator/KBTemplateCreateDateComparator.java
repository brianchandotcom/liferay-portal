/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.util.comparator;

import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
public class KBTemplateCreateDateComparator
	extends OrderByComparator<KBTemplate> {

	public static final String ORDER_BY_ASC = "KBTemplate.createDate ASC";

	public static final String ORDER_BY_DESC = "KBTemplate.createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public static KBTemplateCreateDateComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(KBTemplate kbTemplate1, KBTemplate kbTemplate2) {
		int value = DateUtil.compareTo(
			kbTemplate1.getCreateDate(), kbTemplate2.getCreateDate());

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

	private KBTemplateCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final KBTemplateCreateDateComparator _INSTANCE_ASCENDING =
		new KBTemplateCreateDateComparator(true);

	private static final KBTemplateCreateDateComparator _INSTANCE_DESCENDING =
		new KBTemplateCreateDateComparator(false);

	private final boolean _ascending;

}