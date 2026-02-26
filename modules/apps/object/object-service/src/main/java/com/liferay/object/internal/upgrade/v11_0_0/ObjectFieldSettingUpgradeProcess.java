/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v11_0_0;

import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author Alberto Sousa
 */
public class ObjectFieldSettingUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				SQLTransformer.transform(
					"update ObjectFieldSetting set name = ? where name = '" +
						"showFilesInDocumentsAndMedia'"));
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				SQLTransformer.transform(
					"update ObjectFieldSetting set value = ? where name = ? " +
						"and value like 'userComputer'"))) {

			preparedStatement1.setString(
				1, ObjectFieldSettingConstants.NAME_SHOW_FILES_IN_LIBRARY);

			preparedStatement1.executeUpdate();

			preparedStatement2.setString(
				1,
				ObjectFieldSettingConstants.
					VALUE_USER_COMPUTER_TO_DOCS_AND_MEDIA);
			preparedStatement2.setString(
				2, ObjectFieldSettingConstants.NAME_FILE_SOURCE);

			preparedStatement2.executeUpdate();
		}
	}

}