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

package com.liferay.nested.portlets.web.internal.upgrade.v1_0_0;

import com.liferay.nested.portlets.web.internal.constants.NestedPortletsPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jürgen Kappler
 */
public class UpgradePortletId extends BasePortletIdUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		super.doUpgrade();

		_updateNestedPortletLayoutRevisionTypeSettings();
		_updateNestedPortletLayoutTypeSettings();
	}

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			{"118", NestedPortletsPortletKeys.NESTED_PORTLETS}
		};
	}

	private void _updateNestedPortletLayoutRevisionTypeSettings()
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select layoutRevisionId, typeSettings from LayoutRevision " +
					"where typeSettings LIKE '%nested-column-ids%'");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long layoutRevisionId = resultSet.getLong("layoutRevisionId");
				String typeSettings = resultSet.getString("typeSettings");

				String oldPortletId = "_118_INSTANCE_";
				String newPortletId =
					"_" + NestedPortletsPortletKeys.NESTED_PORTLETS +
						"_INSTANCE_";

				String newTypeSettings = StringUtil.replace(
					typeSettings, oldPortletId, newPortletId);

				updateLayoutRevision(layoutRevisionId, newTypeSettings);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}
	}

	private void _updateNestedPortletLayoutTypeSettings() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select plid, typeSettings from Layout where typeSettings " +
					"LIKE '%nested-column-ids%'");

			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long plid = resultSet.getLong("plid");
				String typeSettings = resultSet.getString("typeSettings");

				String oldPortletId = "_118_INSTANCE_";
				String newPortletId =
					"_" + NestedPortletsPortletKeys.NESTED_PORTLETS +
						"_INSTANCE_";

				String newTypeSettings = StringUtil.replace(
					typeSettings, oldPortletId, newPortletId);

				updateLayout(plid, newTypeSettings);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePortletId.class);

}