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

package com.liferay.commerce.internal.upgrade.v8_9_3;

import com.liferay.commerce.product.service.CommerceChannelRelLocalService;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Brian I. Kim
 */
public class CommerceCountryUpgradeProcess extends UpgradeProcess {

	public CommerceCountryUpgradeProcess(
		CommerceChannelRelLocalService commerceChannelRelLocalService) {

		_commerceChannelRelLocalService = commerceChannelRelLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(
				SQLTransformer.transform(
					"select countryId from Country where groupFilterEnabled " +
						"= [$FALSE$]"));

			while (resultSet.next()) {
				long countryId = resultSet.getLong("countryId");

				_commerceChannelRelLocalService.deleteCommerceChannelRels(
					Country.class.getName(), countryId);
			}
		}
	}

	private final CommerceChannelRelLocalService
		_commerceChannelRelLocalService;

}