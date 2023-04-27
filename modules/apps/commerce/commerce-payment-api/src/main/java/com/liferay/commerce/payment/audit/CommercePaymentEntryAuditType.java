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