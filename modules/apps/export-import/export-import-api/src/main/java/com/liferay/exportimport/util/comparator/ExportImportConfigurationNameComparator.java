/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.util.comparator;

import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Daniel Kocsis
 */
public class ExportImportConfigurationNameComparator
	extends OrderByComparator<ExportImportConfiguration> {

	public static final String ORDER_BY_ASC = "name ASC, createDate DESC";

	public static final String ORDER_BY_DESC = "name DESC, createDate ASC";

	public static final String[] ORDER_BY_FIELDS = {"name", "createDate"};

	public ExportImportConfigurationNameComparator() {
		this(false);
	}

	public ExportImportConfigurationNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		ExportImportConfiguration exportImportConfiguration1,
		ExportImportConfiguration exportImportConfiguration2) {

		String name1 = StringUtil.toLowerCase(
			exportImportConfiguration1.getName());
		String name2 = StringUtil.toLowerCase(
			exportImportConfiguration2.getName());

		int value = name1.compareTo(name2);

		if (value == 0) {
			value = DateUtil.compareTo(
				exportImportConfiguration1.getCreateDate(),
				exportImportConfiguration2.getCreateDate());
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