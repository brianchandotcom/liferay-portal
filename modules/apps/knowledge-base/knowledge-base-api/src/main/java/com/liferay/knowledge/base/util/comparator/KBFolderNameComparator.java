/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.util.comparator;

import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Adolfo Pérez
 */
public class KBFolderNameComparator extends OrderByComparator<KBFolder> {

	public static final String ORDER_BY_ASC = "KBFolder.name ASC";

	public static final String ORDER_BY_DESC = "KBFolder.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public KBFolderNameComparator() {
		this(false);
	}

	public KBFolderNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(KBFolder kbFolder1, KBFolder kbFolder2) {
		String name1 = StringUtil.toLowerCase(
			GetterUtil.getString(kbFolder1.getName()));
		String name2 = StringUtil.toLowerCase(
			GetterUtil.getString(kbFolder2.getName()));

		int value = name1.compareTo(name2);

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