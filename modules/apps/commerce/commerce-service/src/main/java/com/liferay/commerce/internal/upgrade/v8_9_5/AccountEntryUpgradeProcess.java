/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v8_9_5;

import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Crescenzo Rega
 */
public class AccountEntryUpgradeProcess extends UpgradeProcess {

	public AccountEntryUpgradeProcess(
		CommerceChannelAccountEntryRelLocalService
			commerceChannelAccountEntryRelLocalService) {

		_commerceChannelAccountEntryRelLocalService =
			commerceChannelAccountEntryRelLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateDefaultPaymentMethods();
	}

	private void _updateDefaultPaymentMethods() throws Exception {
		String engineKey = "engineKey";

		if (hasColumn(
				"CommercePaymentMethodGroupRel", "paymentIntegrationKey")) {

			engineKey = "paymentIntegrationKey";
		}

		String sql = StringBundler.concat(
			"select AccountEntry.accountEntryId, AccountEntry.userId, ",
			"CommercePaymentMethodGroupRel.CPaymentMethodGroupRelId, ",
			"Group_.classPK from AccountEntry join ",
			"CommercePaymentMethodGroupRel on ",
			"AccountEntry.defaultCPaymentMethodKey = ",
			"CommercePaymentMethodGroupRel.", engineKey, " join Group_ on ",
			"CommercePaymentMethodGroupRel.groupId = Group_.groupId where ",
			"AccountEntry.defaultCPaymentMethodKey is not null and ",
			"CommercePaymentMethodGroupRel.active_ = [$TRUE$]");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(sql));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long userId = resultSet.getLong("userId");
				long accountEntryId = resultSet.getLong("accountEntryId");
				long commercePaymentMethodGroupRelId = resultSet.getLong(
					"CPaymentMethodGroupRelId");
				long commerceChannelId = resultSet.getLong("classPK");

				_commerceChannelAccountEntryRelLocalService.
					addCommerceChannelAccountEntryRel(
						userId, accountEntryId,
						CommercePaymentMethodGroupRel.class.getName(),
						commercePaymentMethodGroupRelId, commerceChannelId,
						false, 0,
						CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT);
			}
		}
	}

	private final CommerceChannelAccountEntryRelLocalService
		_commerceChannelAccountEntryRelLocalService;

}