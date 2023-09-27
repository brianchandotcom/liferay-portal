/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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