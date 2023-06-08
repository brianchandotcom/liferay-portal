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

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Gustavo Lima
 */
public class UpgradePortletPreferenceValueBlueprintOptions
	extends UpgradeProcess {

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

					while (resultSet1.next()) {
						String name = resultSet1.getString("name");

						if (name.equals("sxpBlueprintId")) {
							String smallValueQuoted = StringUtil.quote(
								resultSet1.getString("smallValue"));

							PreparedStatement preparedStatement3 =
								connection.prepareStatement(
									StringBundler.concat(
										"select externalReferenceCode from ",
										"SXPBlueprint where sxpBlueprintId = ",
										smallValueQuoted));

							ResultSet resultSet2 =
								preparedStatement3.executeQuery();

							if (resultSet2.next()) {
								String sxpBlueprintIdQuoted = StringUtil.quote(
									"sxpBlueprintId");
								String externalReferenceCodeQuoted =
									StringUtil.quote(
										resultSet2.getString(
											"externalReferenceCode"));

								PreparedStatement preparedStatement4 =
									connection.prepareStatement(
										StringBundler.concat(
											"update PortletPreferenceValue ",
											"set smallValue = ",
											externalReferenceCodeQuoted,
											" where portletPreferencesId = ",
											portletPreferencesIdQuoted,
											" and name = ",
											sxpBlueprintIdQuoted));

								preparedStatement4.executeUpdate();

								String sxpBlueprintExternalReferenceCodeQuoted =
									StringUtil.quote(
										"sxpBlueprintExternalReferenceCode");

								PreparedStatement preparedStatement5 =
									connection.prepareStatement(
										StringBundler.concat(
											"update PortletPreferenceValue ",
											"set name = ",
											sxpBlueprintExternalReferenceCodeQuoted,
											" where portletPreferencesId = ",
											portletPreferencesIdQuoted,
											" and name = ",
											StringUtil.quote(
												"sxpBlueprintId")));

								preparedStatement5.executeUpdate();
							}
						}
					}
				}
			}
		}
	}

}