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

package com.liferay.headless.commerce.delivery.cart.internal.dto.v1_0.converter;

import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.HashMap;
import java.util.Locale;

/**
 * @author Alessio Antonio Rendina
 */
public class CartItemDTOConverterContext extends DefaultDTOConverterContext {

	public CartItemDTOConverterContext(
		long accountId, Object id, Locale locale) {

		super(false, new HashMap<>(), null, id, locale, null, null);

		_accountId = accountId;
	}

	public long getAccountId() {
		return _accountId;
	}

	private final long _accountId;

}