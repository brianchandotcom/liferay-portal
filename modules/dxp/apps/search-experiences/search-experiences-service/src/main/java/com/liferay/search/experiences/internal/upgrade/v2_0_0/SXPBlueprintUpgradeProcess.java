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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
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
		_upgradeSearchBarPortlets();
		_upgradeSXPBlueprintOptionsPortlets();
	}

	private long _getSXPBlueprintId(String largeValue) throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			StringBundler.concat(
				StringPool.OPEN_BRACKET, largeValue, StringPool.CLOSE_BRACKET));

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			JSONObject attributesJSONObject = jsonObject.getJSONObject(
				"attributes");

			if ((attributesJSONObject != null) &&
				attributesJSONObject.has("sxpBlueprintId")) {

				return attributesJSONObject.getLong("sxpBlueprintId");
			}
		}

		return 0;
	}

	private void _upgradeLargeValue(
			String largeValue, Long portletPreferencesId, ResultSet resultSet)
		throws Exception {

		if (!resultSet.next()) {
			return;
		}

		String newLargeValue = StringUtil.replace(
			largeValue,
			StringBundler.concat(
				StringUtil.quote("sxpBlueprintId", "\""), ":",
				_getSXPBlueprintId(largeValue)),
			StringBundler.concat(
				StringUtil.quote("sxpBlueprintExternalReferenceCode", "\""),
				":",
				StringUtil.quote(
					resultSet.getString("externalReferenceCode"), "\"")));

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update PortletPreferenceValue set largeValue = ? where " +
					"portletPreferencesId = ? and name = ?")) {

			preparedStatement.setString(1, newLargeValue);
			preparedStatement.setLong(2, portletPreferencesId);
			preparedStatement.setString(
				3, "suggestionsContributorConfigurations");

			preparedStatement.executeUpdate();
		}
	}

	private void _upgradeSearchBarPortlet(
			Long portletPreferenceId, ResultSet resultSet)
		throws Exception {

		while (resultSet.next()) {
			String name = resultSet.getString("name");

			if (name.equals("suggestionsContributorConfigurations")) {
				String largeValue = resultSet.getString("largeValue");

				try (PreparedStatement preparedStatement =
						connection.prepareStatement(
							"select externalReferenceCode from SXPBlueprint " +
								"where sxpBlueprintId = ?")) {

					preparedStatement.setLong(
						1, _getSXPBlueprintId(largeValue));

					_upgradeLargeValue(
						largeValue, portletPreferenceId,
						preparedStatement.executeQuery());
				}
			}
		}
	}

	private void _upgradeSearchBarPortlets() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select portletPreferencesId from PortletPreferences ",
					"where portletId like %com_liferay_portal_search_web_",
					"search_bar_portlet_SearchBarPortlet_INSTANCE_%"));
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				StringBundler.concat(
					"select name, largeValue from PortletPreferenceValue ",
					"where portletPreferencesId = ?"));
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long portletPreferencesId = resultSet.getLong(
					"portletPreferencesId");

				preparedStatement2.setLong(1, portletPreferencesId);

				_upgradeSearchBarPortlet(
					portletPreferencesId, preparedStatement2.executeQuery());
			}
		}
	}

	private void _upgradeSmallValueAndName(
			Long portletPreferencesId, ResultSet resultSet)
		throws SQLException {

		if (resultSet.next()) {
			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"update PortletPreferenceValue set smallValue = ? " +
							"where portletPreferencesId = ? and name = ?")) {

				preparedStatement.setString(
					1, resultSet.getString("externalReferenceCode"));
				preparedStatement.setLong(2, portletPreferencesId);
				preparedStatement.setString(3, "sxpBlueprintId");

				preparedStatement.executeUpdate();
			}

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"update PortletPreferenceValue  set name = ? where " +
							"portletPreferencesId = ? and name = ?")) {

				preparedStatement.setString(
					1, "sxpBlueprintExternalReferenceCode");
				preparedStatement.setLong(2, portletPreferencesId);
				preparedStatement.setString(3, "sxpBlueprintId");

				preparedStatement.executeUpdate();
			}
		}
	}

	private void _upgradeSXPBlueprintOptionsPortlet(
			Long portletPreferencesId, ResultSet resultSet)
		throws SQLException {

		while (resultSet.next()) {
			String name = resultSet.getString("name");

			if (!name.equals("sxpBlueprintId")) {
				continue;
			}

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select externalReferenceCode from SXPBlueprint " +
							"where sxpBlueprintId = ?")) {

				preparedStatement.setString(
					1, resultSet.getString("smallValue"));

				_upgradeSmallValueAndName(
					portletPreferencesId, preparedStatement.executeQuery());
			}
		}
	}

	private void _upgradeSXPBlueprintOptionsPortlets() {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select portletPreferencesId from PortletPreferences ",
					"where portletId like %com_liferay_search_experiences_web_",
					"internal_blueprint_options_portlet_",
					"SXPBlueprintOptionsPortlet_INSTANCE_%"))) {

			ResultSet resultSet = preparedStatement1.executeQuery();

			while (resultSet.next()) {
				long portletPreferencesId = resultSet.getLong(
					"portletPreferencesId");

				try (PreparedStatement preparedStatement2 =
						connection.prepareStatement(
							StringBundler.concat(
								"select name, smallValue from ",
								"PortletPreferenceValue where ",
								"portletPreferencesId = ?"))) {

					preparedStatement2.setLong(1, portletPreferencesId);

					_upgradeSXPBlueprintOptionsPortlet(
						portletPreferencesId,
						preparedStatement2.executeQuery());
				}
			}
		}
		catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		}
	}

}