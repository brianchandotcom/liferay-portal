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

package com.liferay.saml.persistence.upgrade.v1_1_3;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.upgrade.v7_0_0.UpgradeKernelPackage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jonathan McCann
 */
public class UpgradeClassNames extends UpgradeKernelPackage {

	@Override
	public void doUpgrade() throws UpgradeException {
		updateCounterClassNames();

		super.doUpgrade();
	}

	@Override
	protected String[][] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String[][] getResourceNames() {
		return _RESOURCE_NAMES;
	}

	protected void updateCounterClassNames() throws UpgradeException {
		for (String modelName : _MODEL_NAMES) {
			try (PreparedStatement ps1 = connection.prepareStatement(
					"select count(*) from Counter where name like '%." +
						modelName + "'");
				ResultSet rs1 = ps1.executeQuery()) {

				if (rs1.next()) {
					int count = rs1.getInt(1);

					if (count <= 1) {
						continue;
					}

					try (PreparedStatement ps2 = connection.prepareStatement(
							"select max(currentId) from Counter where name " +
								"like '%." + modelName + "'");
						ResultSet rs2 = ps2.executeQuery()) {

						if (rs2.next()) {
							long maxCurrentId = rs2.getLong(1);

							CounterLocalServiceUtil.reset(
								"com.liferay.saml.model." + modelName,
								maxCurrentId);

							CounterLocalServiceUtil.reset(
								"com.liferay.saml.persistence.model." +
									modelName);
						}
					}
				}
			}
			catch (SQLException sqle) {
				throw new UpgradeException(sqle);
			}
		}
	}

	private static final String[][] _CLASS_NAMES = {
		{"com.liferay.saml.model.", "com.liferay.saml.persistence.model."}
	};

	private static final String[] _MODEL_NAMES = {
		"SamlIdpSpConnection", "SamlIdpSpSession", "SamlIdpSsoSession",
		"SamlSpAuthRequest", "SamlSpIdpConnection", "SamlSpMessage",
		"SamlSpSession"
	};

	private static final String[][] _RESOURCE_NAMES = {
		{"com.liferay.saml", "com.liferay.saml.persistence"}
	};

}