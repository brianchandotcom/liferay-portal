/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.upgrade.v3_1_3;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;

/**
 * @author Joshua Cords
 */
public class SXPElementUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update SXPElement set elementDefinitionJSON = ? where " +
					"externalReferenceCode = 'LIMIT_SEARCH_TO_THESE_SITES'")) {

			preparedStatement.setString(
				1,
				StringUtil.read(
					getClass(),
					"dependencies/limit_search_to_these_sites.json"));

			preparedStatement.executeUpdate();
		}
	}

}