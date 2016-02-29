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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author Adolfo Pérez
 */
public class UpgradeRepository extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateRepositoryPortletId();
	}

	protected String[][] getRenamePortletNamesArray() {
		return new String[][] {
			new String[] {"19", "com.liferay.message.boards"},
			new String[] {"33", "com.liferay.blogs"},
			new String[] {"36", "com.liferay.wiki"}
		};
	}

	protected void updateRepositoryPortletId() throws Exception {
		for (String[] renamePortletNames : getRenamePortletNamesArray()) {
			String oldPortletName = renamePortletNames[0];
			String newPortletName = renamePortletNames[1];

			PreparedStatement ps = null;

			try {
				ps = connection.prepareStatement(
					"update Repository set portletId = ?, name = ? where " +
						"portletId = ?");

				ps.setString(1, newPortletName);
				ps.setString(2, newPortletName);
				ps.setString(3, oldPortletName);

				ps.executeUpdate();
			}
			finally {
				DataAccess.cleanUp(ps);
			}
		}
	}

}