/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.organization.web.internal.upgrade.v1_0_1;

import com.liferay.commerce.organization.constants.CommerceOrganizationPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Fabio Monaco
 */
public class CommerceOrganizationPortletPreferencesUpgradeProcess
	extends UpgradeProcess {

	public CommerceOrganizationPortletPreferencesUpgradeProcess(
		OrganizationLocalService organizationLocalService) {

		_organizationLocalService = organizationLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					StringBundler.concat(
						"select PortletPreferenceValue.name, ",
						"PortletPreferenceValue.smallValue from ",
						"PortletPreferenceValue inner join PortletPreferences ",
						"on PortletPreferenceValue.portletPreferencesId = ",
						"PortletPreferences.portletPreferencesId where ",
						"PortletPreferences.portletId = ? and (",
						"PortletPreferenceValue.name = 'rootOrganizationId'",
						")"));
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update PortletPreferenceValue set name = ?, smallValue " +
						"= ? where name = ? and smallValue = ?")) {

			selectPreparedStatement.setString(
				1, CommerceOrganizationPortletKeys.COMMERCE_ORGANIZATION);

			try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String name = resultSet.getString(1);
					String smallValue = resultSet.getString(2);

					if (name.equals("rootOrganizationId")) {
						updatePreparedStatement.setString(
							1, "rootOrganizationExternalReferenceCode");

						Organization organization =
							_organizationLocalService.fetchOrganization(
								GetterUtil.getLong(smallValue));

						if (organization == null) {
							updatePreparedStatement.setString(2, null);
						}
						else {
							updatePreparedStatement.setString(
								2, organization.getExternalReferenceCode());
						}
					}

					updatePreparedStatement.setString(3, name);
					updatePreparedStatement.setString(4, smallValue);

					updatePreparedStatement.addBatch();
				}
			}

			updatePreparedStatement.executeBatch();
		}
	}

	private final OrganizationLocalService _organizationLocalService;

}