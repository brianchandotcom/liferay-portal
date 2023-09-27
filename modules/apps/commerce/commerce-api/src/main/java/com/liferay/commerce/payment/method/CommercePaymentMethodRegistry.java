/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.method;

import java.util.Map;

/**
 * @author Luca Pellizzon
 */
public interface CommercePaymentMethodRegistry {

	public CommercePaymentMethod getCommercePaymentMethod(String key);

	public Map<String, CommercePaymentMethod> getCommercePaymentMethods();

}