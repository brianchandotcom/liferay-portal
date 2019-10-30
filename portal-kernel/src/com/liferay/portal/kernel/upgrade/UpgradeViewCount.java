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

package com.liferay.portal.kernel.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;

/**
 * @author Samuel Trong Tran
 */
public class UpgradeViewCount extends UpgradeProcess {

	public UpgradeViewCount(
		String tableName, Class<?> clazz, String primaryColumnName,
		String viewCountColumnName) {

		_tableName = tableName;
		_clazz = clazz;
		_primaryColumnName = primaryColumnName;
		_viewCountColumnName = viewCountColumnName;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn(_tableName, _viewCountColumnName)) {
			return;
		}

		try (PreparedStatement ps = connection.prepareStatement(
				StringBundler.concat(
					"insert into ViewCountEntry (companyId, classNameId, ",
					"classPK, viewCount) select companyId, ",
					PortalUtil.getClassNameId(_clazz), ", ", _primaryColumnName,
					", ", _viewCountColumnName, " from ", _tableName))) {

			ps.executeUpdate();
		}

		runSQL(
			StringBundler.concat(
				"alter table ", _tableName, " drop column ",
				_viewCountColumnName));
	}

	private final Class<?> _clazz;
	private final String _primaryColumnName;
	private final String _tableName;
	private final String _viewCountColumnName;

}