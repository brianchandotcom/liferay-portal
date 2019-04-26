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

		try (PreparedStatement ps = connection.prepareStatement(
				"update Address set countryId = 2, regionId = ? where " +
					"countryId = ?")) {

			ps.setLong(1, regionId);
			ps.setLong(2, oldCountryId);

			ps.executeUpdate();
		}
	}

}