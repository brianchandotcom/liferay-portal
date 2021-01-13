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

package com.liferay.nested.portlets.web.internal.upgrade.v1_0_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBTypeToSQLMap;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author José Abelenda
 */
public class UpgradePortletPreferencesValue extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateSmallValue();
	}

	protected void updateSmallValue() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("update PortletPreferenceValue set smallValue = ");
		sb.append("'1_2_1_columns_i' where name = 'layoutTemplateId' and ");
		sb.append("smallValue = '1_2_1_columns' ");

		DBTypeToSQLMap dbTypeToSQLMap = new DBTypeToSQLMap(sb.toString());

		runSQL(dbTypeToSQLMap);
	}

}