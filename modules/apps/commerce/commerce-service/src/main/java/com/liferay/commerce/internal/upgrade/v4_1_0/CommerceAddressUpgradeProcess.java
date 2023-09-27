/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v4_1_0;

import com.liferay.commerce.constants.CommerceAddressConstants;
import com.liferay.commerce.model.impl.CommerceAddressModelImpl;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alec Sloan
 */
public class CommerceAddressUpgradeProcess extends UpgradeProcess {

	public CommerceAddressUpgradeProcess(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("CommerceAccount", "defaultBillingAddressId") ||
			!hasColumn("CommerceAccount", "defaultShippingAddressId")) {

			throw new UpgradeException(
				"Upgrade fails as CommerceAccount does not have both " +
					"columns: 'defaultBillingAddressId' and" +
						"'defaultShippingAddressId'");
		}

		alterTableAddColumn("CommerceAddress", "type_", "INTEGER");

		PreparedStatement preparedStatement = null;

		if (hasColumn(CommerceAddressModelImpl.TABLE_NAME, "defaultBilling")) {
			preparedStatement = connection.prepareStatement(
				"update CommerceAccount set defaultBillingAddressId = ? " +
					"where commerceAccountId = ?");

			_updateCommerceAccountAndSetType(
				preparedStatement,
				_getCommerceAddressResultSet("defaultBilling"));
		}

		if (hasColumn(CommerceAddressModelImpl.TABLE_NAME, "defaultShipping")) {
			preparedStatement = connection.prepareStatement(
				"update CommerceAccount set defaultShippingAddressId = ? " +
					"where commerceAccountId = ?");

			_updateCommerceAccountAndSetType(
				preparedStatement,
				_getCommerceAddressResultSet("defaultShipping"));
		}
	}

	private ResultSet _getCommerceAddressResultSet(String type)
		throws Exception {

		long commerceAccountClassNameId = _classNameLocalService.getClassNameId(
			"com.liferay.commerce.account.model.CommerceAccount");

		PreparedStatement preparedStatement = null;

		if (type.equals("defaultBilling")) {
			preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(
					"select commerceAddressId, classPK, defaultBilling, " +
						"defaultShipping from CommerceAddress where " +
							"classNameId = ? and defaultBilling = [$TRUE$]"));
		}
		else {
			preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(
					"select commerceAddressId, classPK, defaultBilling, " +
						"defaultShipping from CommerceAddress where " +
							"classNameId = ? and defaultShipping = [$TRUE$]"));
		}

		preparedStatement.setLong(1, commerceAccountClassNameId);

		return preparedStatement.executeQuery();
	}

	private void _setType(
			boolean defaultBilling, boolean defaultShipping,
			long commerceAddressId)
		throws Exception {

		PreparedStatement preparedStatement = connection.prepareStatement(
			"update CommerceAddress set type_ = ? where commerceAddressId = ?");

		int type = CommerceAddressConstants.ADDRESS_TYPE_SHIPPING;

		if (defaultBilling && !defaultShipping) {
			type = CommerceAddressConstants.ADDRESS_TYPE_BILLING;
		}
		else if (!defaultBilling && defaultShipping) {
			type = CommerceAddressConstants.ADDRESS_TYPE_SHIPPING;
		}

		preparedStatement.setInt(1, type);
		preparedStatement.setLong(2, commerceAddressId);

		preparedStatement.addBatch();
	}

	private void _updateCommerceAccountAndSetType(
			PreparedStatement preparedStatement, ResultSet resultSet)
		throws Exception {

		while (resultSet.next()) {
			long commerceAddressId = resultSet.getLong("commerceAddressId");

			preparedStatement.setLong(1, commerceAddressId);

			preparedStatement.setLong(2, resultSet.getLong("classPK"));

			_setType(
				resultSet.getBoolean("defaultBilling"),
				resultSet.getBoolean("defaultShipping"), commerceAddressId);

			preparedStatement.addBatch();
		}
	}

	private final ClassNameLocalService _classNameLocalService;

}