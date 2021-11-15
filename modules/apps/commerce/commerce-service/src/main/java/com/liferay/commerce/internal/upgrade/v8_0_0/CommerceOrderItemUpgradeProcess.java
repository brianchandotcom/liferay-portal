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

package com.liferay.commerce.internal.upgrade.v8_0_0;

import com.liferay.commerce.internal.upgrade.base.BaseCommerceServiceUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v8_0_0.util.CommerceOrderItemTable;
import com.liferay.commerce.product.constants.CPMeasurementUnitConstants;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.service.CPMeasurementUnitLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.List;

/**
 * @author Luca Pellizzon
 */
public class CommerceOrderItemUpgradeProcess
	extends BaseCommerceServiceUpgradeProcess {

	public CommerceOrderItemUpgradeProcess(
		CPMeasurementUnitLocalService cpMeasurementUnitLocalService) {

		_cpMeasurementUnitLocalService = cpMeasurementUnitLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"CPMeasurementUnitId", "LONG");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"decimalQuantity", "DECIMAL(30, 16) null");

		String commerceOrderItemCompanyIdSQL =
			"select distinct companyId from CommerceOrderItem";

		try (Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(
				commerceOrderItemCompanyIdSQL)) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong("companyId");

				List<CPMeasurementUnit> piece =
					_cpMeasurementUnitLocalService.getCPMeasurementUnits(
						companyId, new String[] {"pc"},
						CPMeasurementUnitConstants.TYPE_PIECE);

				if (piece.isEmpty()) {
					ServiceContext serviceContext = new ServiceContext();

					serviceContext.setCompanyId(companyId);
					serviceContext.setLanguageId(
						UpgradeProcessUtil.getDefaultLanguageId(companyId));

					_cpMeasurementUnitLocalService.importDefaultValues(
						serviceContext);

					piece =
						_cpMeasurementUnitLocalService.getCPMeasurementUnits(
							companyId, new String[] {"pc"},
							CPMeasurementUnitConstants.TYPE_PIECE);
				}

				CPMeasurementUnit cpMeasurementUnit = piece.get(0);

				String updateCommerceOrderItem = StringBundler.concat(
					"update CommerceOrderItem set CPMeasurementUnitId = ",
					cpMeasurementUnit.getCPMeasurementUnitId(),
					" where CPMeasurementUnitId is NULL and companyId = ",
					companyId);

				runSQL(updateCommerceOrderItem);
			}
		}
	}

	private final CPMeasurementUnitLocalService _cpMeasurementUnitLocalService;

}