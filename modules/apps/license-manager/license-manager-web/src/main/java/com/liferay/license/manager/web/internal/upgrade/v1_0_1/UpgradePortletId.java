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

package com.liferay.license.manager.web.internal.upgrade.v1_0_1;

import com.liferay.license.manager.web.internal.constants.LicenseManagerPortletKeys;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletId;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author David Zhang
 * @author Alberto Chaparro
 */
public class UpgradePortletId extends BaseUpgradePortletId {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select id_ from Portlet where portletId = '176'");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				runSQL(
					"delete from Portlet where portletId = '" +
						LicenseManagerPortletKeys.LICENSE_MANAGER + "'");

				super.doUpgrade();
			}
		}
	}

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			new String[] {"176", LicenseManagerPortletKeys.LICENSE_MANAGER}
		};
	}

}