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

package com.liferay.portal.upgrade.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author     Adolfo Pérez
 * @deprecated As of NEXT-VERSION, replaced by {@link
 *             com.liferay.portal.kernel.upgrade.BaseReplacePortletId}
 */
@Deprecated
public class ReplacePortletId extends UpgradePortletId {

	protected boolean hasPortlet(String portletId) throws SQLException {
		return hasRow(
			"select count(*) from Portlet where portletId = ?", portletId);
	}

	protected boolean hasResourceAction(String name) throws SQLException {
		return hasRow(
			"select count(*) from ResourceAction where name = ?", name);
	}

	protected boolean hasResourcePermission(String name) throws SQLException {
		return hasRow(
			"select count(*) from ResourcePermission where name = ?", name);
	}

	protected boolean hasRow(String sql, String value) throws SQLException {
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, value);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					int count = rs.getInt(1);

					if (count > 0) {
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	protected void updatePortletId(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		if (!hasPortlet(newRootPortletId)) {
			super.updatePortletId(oldRootPortletId, newRootPortletId);
		}
	}

	@Override
	protected void updateResourceAction(String oldName, String newName)
		throws Exception {

		if (hasResourceAction(newName)) {
			try (PreparedStatement ps = connection.prepareStatement(
					"delete from ResourceAction where name = ?")) {

				ps.setString(1, oldName);

				ps.execute();
			}
		}
		else {
			super.updateResourceAction(oldName, newName);
		}
	}

	@Override
	protected void updateResourcePermission(
			String oldRootPortletId, String newRootPortletId,
			boolean updateName)
		throws Exception {

		if (hasResourcePermission(newRootPortletId)) {
			try (PreparedStatement ps = connection.prepareStatement(
					"delete from ResourcePermission where name = ?")) {

				ps.setString(1, oldRootPortletId);

				ps.execute();
			}
		}
		else {
			super.updateResourcePermission(
				oldRootPortletId, newRootPortletId, updateName);
		}
	}

}