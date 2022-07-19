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

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author Sergio Jiménez del Coso
 */
public class UpgradeDLFolder extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("DLFolder", "externalReferenceCode")) {
			alterTableAddColumn(
				"DLFolder", "externalReferenceCode", "VARCHAR(75)");

			_populateExternalReferenceCode();
		}
	}

	private void _populateExternalReferenceCode() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"update DLFolder set externalReferenceCode = uuid_ where " +
					"externalReferenceCode is null or externalReferenceCode " +
						"= ''")) {

			preparedStatement1.executeUpdate();
		}
	}

}