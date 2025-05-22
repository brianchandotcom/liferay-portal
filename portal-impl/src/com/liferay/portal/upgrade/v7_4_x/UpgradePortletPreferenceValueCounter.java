/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.PortletPreferenceValue;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Lourdes Fernández Besada
 */
public class UpgradePortletPreferenceValueCounter extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (Statement statement = connection.createStatement();
			ResultSet resultSet1 = statement.executeQuery(
				StringBundler.concat(
					"select currentId from Counter where name = '",
					PortletPreferenceValue.class.getName(), "'"))) {

			long counter = 0;

			if (resultSet1.next()) {
				counter = resultSet1.getLong("currentId");
			}

			try (ResultSet resultSet2 = statement.executeQuery(
					"select max(portletPreferenceValueId) from " +
						"PortletPreferenceValue")) {

				if (resultSet2.next()) {
					long increment = Math.max(
						0, resultSet2.getLong(1) - counter);

					if (increment > 0) {
						increment(
							PortletPreferenceValue.class.getName(),
							(int)increment);
					}
				}
			}
		}
	}

}