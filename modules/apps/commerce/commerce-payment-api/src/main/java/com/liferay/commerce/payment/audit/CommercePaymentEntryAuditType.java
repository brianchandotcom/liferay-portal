/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.audit;

import com.liferay.commerce.payment.model.CommercePaymentEntryAudit;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;
import java.util.Map;

/**
 * @author Luca Pellizzon
 */
public interface CommercePaymentEntryAuditType {

	public String formatAmount(
			CommercePaymentEntryAudit commercePaymentEntryAudit, Locale locale)
		throws PortalException;

	public String formatLog(
			CommercePaymentEntryAudit commercePaymentEntryAudit, Locale locale)
		throws Exception;

	public String getLog(Map<String, Object> context);

	public String getType();

}