/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.persistence.internal.upgrade.v1_1_4;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
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
		_updateCounterClassNames();

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

	private void _updateCounterClassNames() throws UpgradeException {
		for (String modelName : _MODEL_NAMES) {
			try (PreparedStatement preparedStatement1 =
					connection.prepareStatement(
						"select count(*) from Counter where name like '%." +
							modelName + "'");
				ResultSet resultSet1 = preparedStatement1.executeQuery()) {

				if (resultSet1.next()) {
					int count = resultSet1.getInt(1);

					if (count <= 1) {
						continue;
					}

					try (PreparedStatement preparedStatement2 =
							connection.prepareStatement(
								StringBundler.concat(
									"select max(currentId) from Counter where ",
									"name like '%.", modelName, "'"));
						ResultSet resultSet2 =
							preparedStatement2.executeQuery()) {

						if (resultSet2.next()) {
							long currentId = resultSet2.getLong(1);

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
			catch (SQLException sqlException) {
				throw new UpgradeException(sqlException);
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