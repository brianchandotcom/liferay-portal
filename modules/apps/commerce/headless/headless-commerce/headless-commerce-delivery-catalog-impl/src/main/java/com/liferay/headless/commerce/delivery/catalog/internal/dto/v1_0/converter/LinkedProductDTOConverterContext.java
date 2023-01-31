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

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.Locale;
import java.util.Map;

import javax.ws.rs.core.UriInfo;

/**
 * @author Stefano Motta
 */
public class LinkedProductDTOConverterContext
	extends DefaultDTOConverterContext {

	public LinkedProductDTOConverterContext(
		boolean acceptAllLanguages, long accountId,
		Map<String, Map<String, String>> actions, long channelId,
		DTOConverterRegistry dtoConverterRegistry, Object id, Locale locale,
		String productTypeName, UriInfo uriInfo, User user) {

		super(
			acceptAllLanguages, actions, dtoConverterRegistry, id, locale,
			uriInfo, user);

		_accountId = accountId;
		_channelId = channelId;
		_productTypeName = productTypeName;
	}

	public long getAccountId() {
		return _accountId;
	}

	public long getChannelId() {
		return _channelId;
	}

	public String getProductTypeName() {
		return _productTypeName;
	}

	private final long _accountId;
	private final long _channelId;
	private final String _productTypeName;

}