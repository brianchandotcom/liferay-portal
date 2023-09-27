/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;

/**
 * @author Samuel Trong Tran
 */
public class ViewCountUpgradeProcess extends UpgradeProcess {

	public ViewCountUpgradeProcess(
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

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into ViewCountEntry (companyId, classNameId, ",
					"classPK, viewCount) select companyId, ",
					PortalUtil.getClassNameId(_clazz), ", ", _primaryColumnName,
					", ", _viewCountColumnName, " from ", _tableName))) {

			preparedStatement.executeUpdate();
		}
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropColumns(_tableName, _viewCountColumnName)
		};
	}

	private final Class<?> _clazz;
	private final String _primaryColumnName;
	private final String _tableName;
	private final String _viewCountColumnName;

}