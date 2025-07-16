/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v3_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.GetterUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Javier Moral
 */
public class LayoutUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		processConcurrently(
			StringBundler.concat(
				"select Layout.ctCollectionId, Layout.plid, Layout.companyId, ",
				"Layout.layoutSetPrototypeLayoutERC, ",
				"LayoutSetPrototype.layoutSetPrototypeId from Layout inner ",
				"join (LayoutSet inner join LayoutSetPrototype on ",
				"LayoutSet.layoutSetPrototypeUuid = LayoutSetPrototype.uuid_ ",
				"and LayoutSet.companyId=LayoutSetPrototype.companyId) on ",
				"Layout.groupId =LayoutSet.groupId and Layout.privateLayout = ",
				"LayoutSet.privateLayout where ",
				"Layout.layoutSetPrototypeLayoutERC is not null"),
			"update Layout set layoutSetPrototypeLayoutERC = ? where " +
				"ctCollectionId = ? and plid = ?",
			resultSet -> new Object[] {
				resultSet.getLong("ctCollectionId"), resultSet.getLong("plid"),
				resultSet.getLong("companyId"),
				GetterUtil.getString(
					resultSet.getString("layoutSetPrototypeLayoutERC")),
				resultSet.getLong("layoutSetPrototypeId")
			},
			(values, preparedStatement) -> {
				long companyId = GetterUtil.getLong(values[2]);
				long layoutSetPrototypeId = GetterUtil.getLong(values[4]);

				if ((layoutSetPrototypeId > 0) && (companyId > 0)) {
					Group layoutSetPrototypeGroup =
						GroupLocalServiceUtil.getLayoutSetPrototypeGroup(
							companyId, layoutSetPrototypeId);

					try (PreparedStatement preparedStatement1 =
							connection.prepareStatement(
								"select externalReferenceCode from Layout " +
									"where uuid_ = ? and groupId = ?")) {

						String layoutSetPrototypeLayoutERC =
							GetterUtil.getString(values[3]);

						preparedStatement1.setString(
							1, layoutSetPrototypeLayoutERC);

						preparedStatement1.setLong(
							2, layoutSetPrototypeGroup.getGroupId());

						try (ResultSet resultSet =
								preparedStatement1.executeQuery()) {

							if (resultSet.next()) {
								String externalReferenceCode =
									resultSet.getString(
										"externalReferenceCode");

								if (!externalReferenceCode.equals(
										layoutSetPrototypeLayoutERC)) {

									preparedStatement.setString(
										1, externalReferenceCode);
									preparedStatement.setLong(
										2, GetterUtil.getLong(values[0]));
									preparedStatement.setLong(
										3, GetterUtil.getLong(values[1]));

									preparedStatement.addBatch();
								}
							}
						}
					}
				}
			},
			null);
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.alterColumnName(
				"Layout", "sourcePrototypeLayoutUuid",
				"layoutSetPrototypeLayoutERC VARCHAR(75) null")
		};
	}

}