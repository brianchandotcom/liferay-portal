/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.subscription.test.util;

import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceOrderLocalServiceUtil;
import com.liferay.commerce.subscription.CommerceSubscriptionEntryHelper;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

import java.math.BigDecimal;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceSubscriptionEntryTestUtil {

	public static void setUpCommerceSubscriptionEntry(
			long userId, long groupId, long maxSubscriptionCycles,
			long commerceCurrencyId,
			CommerceSubscriptionEntryHelper commerceSubscriptionEntryHelper)
		throws Exception {

		User user = UserLocalServiceUtil.getUser(userId);

		CPInstance cpInstance = CPTestUtil.addCPInstance(user.getGroupId());

		cpInstance.setOverrideSubscriptionInfo(true);
		cpInstance.setSubscriptionEnabled(true);
		cpInstance.setSubscriptionLength(1);
		cpInstance.setSubscriptionType("daily");
		cpInstance.setMaxSubscriptionCycles(maxSubscriptionCycles);

		cpInstance = CPInstanceLocalServiceUtil.updateCPInstance(cpInstance);

		CommerceTestUtil.updateBackOrderCPDefinitionInventory(
			cpInstance.getCPDefinition());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			userId, groupId, commerceCurrencyId);

		CommerceAddress commerceAddress =
			CommerceTestUtil.addUserCommerceAddress(groupId, userId);

		commerceOrder.setBillingAddressId(
			commerceAddress.getCommerceAddressId());
		commerceOrder.setShippingAddressId(
			commerceAddress.getCommerceAddressId());

		commerceOrder = CommerceOrderLocalServiceUtil.updateCommerceOrder(
			commerceOrder);

		CommerceTestUtil.addCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), cpInstance.getCPInstanceId(),
			BigDecimal.ONE);

		commerceSubscriptionEntryHelper.checkCommerceSubscriptions(
			commerceOrder);
	}

}