/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.entry;

import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public interface CommercePaymentEntryRefundType {

	public String getKey();

	public String getName(Locale locale);

	public Map<Locale, String> getNameMap();

	public int getPriority();

	public boolean isEnabled();

}