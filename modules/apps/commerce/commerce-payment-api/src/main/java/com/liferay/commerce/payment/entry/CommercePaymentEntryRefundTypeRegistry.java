/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.entry;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public interface CommercePaymentEntryRefundTypeRegistry {

	public CommercePaymentEntryRefundType getCommercePaymentEntryRefundType(
		String key);

	public List<CommercePaymentEntryRefundType>
		getCommercePaymentEntryRefundTypes();

}