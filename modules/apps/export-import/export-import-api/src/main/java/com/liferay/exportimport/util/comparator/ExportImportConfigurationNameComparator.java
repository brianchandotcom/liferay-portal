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

package com.liferay.exportimport.util.comparator;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Daniel Kocsis
 */
@ProviderType
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
		ExportImportConfiguration configuration1,
		ExportImportConfiguration configuration2) {

		String name1 = StringUtil.toLowerCase(configuration1.getName());
		String name2 = StringUtil.toLowerCase(configuration2.getName());

		int value = name1.compareTo(name2);

		if (value == 0) {
			value = DateUtil.compareTo(
				configuration1.getCreateDate(), configuration2.getCreateDate());
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