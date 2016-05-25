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

package com.liferay.nested.portlets.web.upgrade.v1_0_0;

import com.liferay.nested.portlets.web.constants.NestedPortletsPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletId;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jürgen Kappler
 */
public class UpgradePortletId extends BaseUpgradePortletId {

	@Override
	protected void doUpgrade() throws Exception {
		super.doUpgrade();

		updateNestedPortletLayoutTypeSettings();
	}

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			new String[] {
				"118", NestedPortletsPortletKeys.NESTED_PORTLETS
			}
		};
	}

	protected void updateNestedPortletLayoutTypeSettings() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select plid, typeSettings from Layout where typeSettings " +
					"LIKE '%nested-column-ids%'");

			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long plid = rs.getLong("plid");
				String typeSettings = rs.getString("typeSettings");

				String oldPortletId = "_118_INSTANCE_";
				String newPortletId =
					"_" + NestedPortletsPortletKeys.NESTED_PORTLETS +
						"_INSTANCE_";

				String newTypeSettings = StringUtil.replace(
					typeSettings, oldPortletId, newPortletId);

				updateLayout(plid, newTypeSettings);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePortletId.class);

}