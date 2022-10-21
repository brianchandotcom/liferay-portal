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

package com.liferay.commerce.account.internal.upgrade.v10_1_0;

import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelLocalService;
import com.liferay.petra.string.StringBundler;
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
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select AccountEntry.accountEntryId, AccountEntry.userId, ",
					"CommercePaymentMethodGroupRel.CPaymentMethodGroupRelId, ",
					"Group_.classPK from AccountEntry join ",
					"CommercePaymentMethodGroupRel on ",
					"AccountEntry.defaultCPaymentMethodKey = ",
					"CommercePaymentMethodGroupRel.engineKey join Group_ on ",
					"CommercePaymentMethodGroupRel.groupId = Group_.groupId ",
					"where AccountEntry.defaultCPaymentMethodKey is not null ",
					"and CommercePaymentMethodGroupRel.active_ = 1"));
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