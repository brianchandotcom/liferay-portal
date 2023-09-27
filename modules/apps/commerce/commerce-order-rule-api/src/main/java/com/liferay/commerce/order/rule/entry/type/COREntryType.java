/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.rule.entry.type;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Luca Pellizzon
 */
public interface COREntryType {

	public boolean evaluate(COREntry corEntry, CommerceOrder commerceOrder)
		throws PortalException;

	public String getErrorMessage(
			COREntry corEntry, CommerceOrder commerceOrder, Locale locale)
		throws PortalException;

	public String getKey();

	public String getLabel(Locale locale);

}