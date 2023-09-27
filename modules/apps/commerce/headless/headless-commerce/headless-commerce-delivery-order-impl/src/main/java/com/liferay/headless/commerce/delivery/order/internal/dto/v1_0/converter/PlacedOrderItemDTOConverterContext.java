/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.order.internal.dto.v1_0.converter;

import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.HashMap;
import java.util.Locale;

/**
 * @author Andrea Sbarra
 */
public class PlacedOrderItemDTOConverterContext
	extends DefaultDTOConverterContext {

	public PlacedOrderItemDTOConverterContext(
		long accountId, Object id, Locale locale) {

		super(false, new HashMap<>(), null, id, locale, null, null);

		_accountId = accountId;
	}

	public long getAccountId() {
		return _accountId;
	}

	private final long _accountId;

}