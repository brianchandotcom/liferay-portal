/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.policy.internal.upgrade.v3_0_1;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Christopher Kian
 */
public class SAPEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		CompanyLocalServiceUtil.forEachCompanyId(
			companyId -> {
				try (PreparedStatement preparedStatement1 =
						connection.prepareStatement(
							"select count(*) from SAPEntry where name = " +
								"'SYSTEM_TEMPLATE_DEFAULT' and companyId = " +
									companyId)) {

					ResultSet resultSet = preparedStatement1.executeQuery();

					resultSet.next();

					int count = resultSet.getInt(1);

					if (count < 1) {
						Timestamp now = new Timestamp(
							System.currentTimeMillis());

						String title =
							"System Service Access Policy for RESTClient " +
								"Requests in Templates";

						String titleMapString =
							LocalizationUtil.updateLocalization(
								HashMapBuilder.put(
									LocaleUtil.fromLanguageId(
										UpgradeProcessUtil.getDefaultLanguageId(
											companyId)),
									title
								).build(),
								title, "Title",
								UpgradeProcessUtil.getDefaultLanguageId(
									companyId));

						StringBuilder sb = new StringBuilder(5);

						sb.append("insert into SAPEntry (uuid_, sapEntryId, ");
						sb.append("companyId, userId, createDate, ");
						sb.append("modifiedDate, allowedServiceSignatures, ");
						sb.append("defaultSAPEntry, enabled, name, title) ");
						sb.append("values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

						PreparedStatement preparedStatement2 =
							connection.prepareStatement(sb.toString());

						preparedStatement2.setString(
							1, PortalUUIDUtil.generate());
						preparedStatement2.setLong(
							2, CounterLocalServiceUtil.increment());
						preparedStatement2.setLong(3, companyId);
						preparedStatement2.setLong(
							4, UserLocalServiceUtil.getGuestUserId(companyId));
						preparedStatement2.setTimestamp(5, now);
						preparedStatement2.setTimestamp(6, now);
						preparedStatement2.setString(7, StringPool.STAR);
						preparedStatement2.setBoolean(8, false);
						preparedStatement2.setBoolean(9, true);
						preparedStatement2.setString(
							10, "SYSTEM_TEMPLATE_DEFAULT");
						preparedStatement2.setString(11, titleMapString);

						preparedStatement2.execute();
					}
				}
			});
	}

}