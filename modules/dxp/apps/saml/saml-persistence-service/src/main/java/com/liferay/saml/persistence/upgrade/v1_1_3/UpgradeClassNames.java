/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
							long currentId = rs2.getLong(1);

							CounterLocalServiceUtil.reset(
								"com.liferay.saml.model." + modelName,
								currentId);

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