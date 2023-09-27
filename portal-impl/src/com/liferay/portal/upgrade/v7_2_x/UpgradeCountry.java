/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_2_x;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author Pei-Jung Lan
 */
public class UpgradeCountry extends UpgradeProcess {

	protected void deleteCountryRow() throws Exception {
		runSQL("delete from Country where name = 'hong-kong'");
		runSQL("delete from Country where name = 'macau'");
	}

	@Override
	protected void doUpgrade() throws Exception {
		deleteCountryRow();
		updateAddress();
	}

	protected void updateAddress() throws Exception {

		// Hong Kong

		_updateAddressCountryToChina(5, 2030);

		// Macau

		_updateAddressCountryToChina(131, 2002);
	}

	private void _updateAddressCountryToChina(long oldCountryId, long regionId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update Address set countryId = 2, regionId = ? where " +
					"countryId = ?")) {

			preparedStatement.setLong(1, regionId);
			preparedStatement.setLong(2, oldCountryId);

			preparedStatement.executeUpdate();
		}
	}

}