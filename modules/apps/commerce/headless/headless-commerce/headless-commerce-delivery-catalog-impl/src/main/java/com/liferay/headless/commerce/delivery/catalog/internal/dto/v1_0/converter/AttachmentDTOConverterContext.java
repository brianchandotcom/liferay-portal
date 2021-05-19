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

package com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter;

import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.Locale;

/**
 * @author Alessio Antonio Rendina
 */
public class AttachmentDTOConverterContext extends DefaultDTOConverterContext {

	public AttachmentDTOConverterContext(
		Object id, Locale locale, long commerceAccountId) {

		super(id, locale);

		_commerceAccountId = commerceAccountId;
	}

	public long getCommerceAccountId() {
		return _commerceAccountId;
	}

	private final long _commerceAccountId;

}