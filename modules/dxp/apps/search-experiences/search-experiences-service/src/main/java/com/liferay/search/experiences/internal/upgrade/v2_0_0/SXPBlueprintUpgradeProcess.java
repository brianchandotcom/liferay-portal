/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.upgrade.v2_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Gustavo Lima
 */
public class SXPBlueprintUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String portletIdQuoted = StringUtil.quote(
			"%com_liferay_search_experiences_web_internal_blueprint_options_" +
				"portlet_SXPBlueprintOptionsPortlet_INSTANCE_%");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select portletPreferencesId from PortletPreferences ",
					"where portletId like ", portletIdQuoted))) {

			ResultSet resultSet = preparedStatement1.executeQuery();

			_upgradeSXPBlueprintOptionsPortlets(resultSet);
		}
	}

	private void _upgradeSmallValueAndName(
			String portletPreferencesIdQuoted, ResultSet resultSet2)
		throws SQLException {

		if (resultSet2.next()) {
			PreparedStatement preparedStatement4 = connection.prepareStatement(
				StringBundler.concat(
					"update PortletPreferenceValue set smallValue = ",
					StringUtil.quote(
						resultSet2.getString("externalReferenceCode")),
					" where portletPreferencesId = ",
					portletPreferencesIdQuoted, " and name = ",
					StringUtil.quote("sxpBlueprintId")));

			preparedStatement4.executeUpdate();

			PreparedStatement preparedStatement5 = connection.prepareStatement(
				StringBundler.concat(
					"update PortletPreferenceValue  set name = ",
					StringUtil.quote("sxpBlueprintExternalReferenceCode"),
					" where portletPreferencesId = ",
					portletPreferencesIdQuoted, " and name = ",
					StringUtil.quote("sxpBlueprintId")));

			preparedStatement5.executeUpdate();
		}
	}

	private void _upgradeSXPBlueprintOptionsPortlet(
			String portletPreferencesIdQuoted, ResultSet resultSet1)
		throws SQLException {

		while (resultSet1.next()) {
			String name = resultSet1.getString("name");

			if (name.equals("sxpBlueprintId")) {
				PreparedStatement preparedStatement3 =
					connection.prepareStatement(
						StringBundler.concat(
							"select externalReferenceCode from SXPBlueprint ",
							"where sxpBlueprintId = ",
							StringUtil.quote(
								resultSet1.getString("smallValue"))));

				ResultSet resultSet2 = preparedStatement3.executeQuery();

				_upgradeSmallValueAndName(
					portletPreferencesIdQuoted, resultSet2);
			}
		}
	}

	private void _upgradeSXPBlueprintOptionsPortlets(ResultSet resultSet)
		throws SQLException {

		while (resultSet.next()) {
			String portletPreferencesIdQuoted = StringUtil.quote(
				resultSet.getString("portletPreferencesId"));

			try (PreparedStatement preparedStatement2 =
					connection.prepareStatement(
						StringBundler.concat(
							"select name, smallValue from ",
							"PortletPreferenceValue where ",
							"portletPreferencesId = ",
							portletPreferencesIdQuoted))) {

				ResultSet resultSet1 = preparedStatement2.executeQuery();

				_upgradeSXPBlueprintOptionsPortlet(
					portletPreferencesIdQuoted, resultSet1);
			}
		}
	}

}