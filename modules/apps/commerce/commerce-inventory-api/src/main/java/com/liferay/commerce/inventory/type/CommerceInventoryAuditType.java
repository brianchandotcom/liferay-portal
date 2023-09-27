/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.type;

import java.math.BigDecimal;

import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Alessio Antonio Rendina
 * @author Luca Pellizzon
 */
@ProviderType
public interface CommerceInventoryAuditType {

	public String formatLog(long userId, String context, Locale locale)
		throws Exception;

	public String formatQuantity(BigDecimal quantity, Locale locale);

	public String getLog(Map<String, String> context);

	public String getType();

}