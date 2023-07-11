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

	private void _upgradeSearchBarPortlets() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select PortletPreferenceValue.largeValue, ",
					"PortletPreferenceValue.portletPreferencesId from ",
					"PortletPreferenceValue inner join PortletPreferences on ",
					"PortletPreferences.portletPreferencesId  = ",
					"PortletPreferenceValue.portletPreferencesId where ",
					"PortletPreferences.portletId like ",
					"'%com_liferay_portal_search_web_search_bar_portlet_",
					"SearchBarPortlet_INSTANCE_%' and ",
					"PortletPreferenceValue.name = ",
					"'suggestionsContributorConfigurations'"));
			ResultSet resultSet1 = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"select externalReferenceCode from SXPBlueprint where " +
					"sxpBlueprintId = ?");
			PreparedStatement preparedStatement3 = connection.prepareStatement(
				"update PortletPreferenceValue set largeValue = ? where " +
					"portletPreferencesId = ? and name = " +
						"'suggestionsContributorConfigurations'")) {

			while (resultSet1.next()) {
				String largeValue = resultSet1.getString("largeValue");

				preparedStatement2.setLong(1, _getSXPBlueprintId(largeValue));

				ResultSet resultSet2 = preparedStatement2.executeQuery();

				if (!resultSet2.next()) {
					return;
				}

				String newLargeValue = StringUtil.replace(
					largeValue,
					StringBundler.concat(
						StringUtil.quote("sxpBlueprintId", "\""), ":",
						_getSXPBlueprintId(largeValue)),
					StringBundler.concat(
						StringUtil.quote(
							"sxpBlueprintExternalReferenceCode", "\""),
						":",
						StringUtil.quote(
							resultSet2.getString("externalReferenceCode"),
							"\"")));

				preparedStatement3.setString(1, newLargeValue);

				preparedStatement3.setLong(
					2, resultSet1.getLong("portletPreferencesId"));

				preparedStatement3.addBatch();
			}

			preparedStatement3.executeBatch();
		}
	}

	private void _upgradeSXPBlueprintOptionsPortlets() {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select PortletPreferenceValue.smallValue, ",
					"PortletPreferenceValue.portletPreferencesId from ",
					"PortletPreferenceValue inner join PortletPreferences on ",
					"PortletPreferences.portletPreferencesId  = ",
					"PortletPreferenceValue.portletPreferencesId where ",
					"PortletPreferences.portletId like ",
					"'%com_liferay_search_experiences_web_internal_blueprint_",
					"options_portlet_SXPBlueprintOptionsPortlet_INSTANCE_%' ",
					"and PortletPreferenceValue.name = 'sxpBlueprintId'"));
			ResultSet resultSet1 = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"select externalReferenceCode from SXPBlueprint where " +
					"sxpBlueprintId = ?");
			PreparedStatement preparedStatement3 = connection.prepareStatement(
				"update PortletPreferenceValue set name = ?, smallValue = ? " +
					"where portletPreferencesId = ? and name = " +
						"'sxpBlueprintId'")) {

			while (resultSet1.next()) {
				preparedStatement2.setString(
					1, resultSet1.getString("smallValue"));

				ResultSet resultSet2 = preparedStatement2.executeQuery();

				if (!resultSet2.next()) {
					return;
				}

				preparedStatement3.setString(
					1, "sxpBlueprintExternalReferenceCode");
				preparedStatement3.setString(
					2, resultSet2.getString("externalReferenceCode"));
				preparedStatement3.setLong(
					3, resultSet1.getLong("portletPreferencesId"));

				preparedStatement3.addBatch();
			}

			preparedStatement3.executeBatch();
		}
		catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		}
	}

}