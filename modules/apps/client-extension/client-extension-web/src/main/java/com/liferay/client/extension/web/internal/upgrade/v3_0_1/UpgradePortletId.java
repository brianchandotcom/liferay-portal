/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.web.internal.upgrade.v3_0_1;

import com.liferay.client.extension.web.internal.util.CETUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradePortletId extends BasePortletIdUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
				"select externalReferenceCode, clientExtensionEntryId from " +
					"ClientExtensionEntry")) {

			while (resultSet.next()) {
				String externalReferenceCode =
					CETUtil.normalizeExternalReferenceCodeForPortletId(
						resultSet.getString("externalReferenceCode"));

				runSQL(
					StringBundler.concat(
						"delete from Portlet where portletId = '",
						"com_liferay_client_extension_web_internal_portlet_",
						"ClientExtensionEntryPortlet_", externalReferenceCode,
						"'"));

				runSQL(
					StringBundler.concat(
						"delete from ResourcePermission where name = '",
						"com_liferay_client_extension_web_internal_portlet_",
						"ClientExtensionEntryPortlet_", externalReferenceCode,
						"'"));
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		super.doUpgrade();
	}

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return getRenamePortletIdsArray(
			connection,
			"com_liferay_client_extension_web_internal_" +
				"portlet_ClientExtensionEntryPortlet_");
	}

	protected String[][] getRenamePortletIdsArray(
		Connection connection, String portletIdPrefix) {

		List<String[]> portletIds = new ArrayList<>();

		try (Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
				"select externalReferenceCode, companyId from " +
					"ClientExtensionEntry")) {

			while (resultSet.next()) {
				String externalReferenceCode =
					CETUtil.normalizeExternalReferenceCodeForPortletId(
						resultSet.getString("externalReferenceCode"));

				portletIds.add(
					new String[] {
						portletIdPrefix + externalReferenceCode,
						StringBundler.concat(
							portletIdPrefix, resultSet.getLong("companyId"),
							StringPool.UNDERLINE, externalReferenceCode)
					});
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return portletIds.toArray(new String[0][0]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePortletId.class);

}