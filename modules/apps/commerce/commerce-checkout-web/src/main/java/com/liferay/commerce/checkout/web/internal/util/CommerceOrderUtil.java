/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.checkout.web.internal.util;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Set;

/**
 * @author Luca Pellizzon
 */
public class CommerceOrderUtil {

	public static int getCommerceOrderDeliveryGroupNamesCount(
		CommerceOrder commerceOrder) {

		Set<String> deliveryGroupNames = SetUtil.fromArray(
			TransformUtil.transformToArray(
				commerceOrder.getCommerceOrderItems(),
				CommerceOrderItem::getDeliveryGroupName, String.class));

		return deliveryGroupNames.size();
	}

	public static boolean isCommerceOrderMultishipping(
		CommerceOrder commerceOrder) {

		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			if (Validator.isNotNull(commerceOrderItem.getDeliveryGroupName())) {
				return true;
			}
		}

		return false;
	}

}