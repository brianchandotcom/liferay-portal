/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.entry;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 * @author Crescenzo Rega
 */
public interface CommercePaymentEntryRefundTypeRegistry {

	public CommercePaymentEntryRefundType getCommercePaymentEntryRefundType(
		long companyId, String key);

	public List<CommercePaymentEntryRefundType>
		getCommercePaymentEntryRefundTypes(long companyId);

}