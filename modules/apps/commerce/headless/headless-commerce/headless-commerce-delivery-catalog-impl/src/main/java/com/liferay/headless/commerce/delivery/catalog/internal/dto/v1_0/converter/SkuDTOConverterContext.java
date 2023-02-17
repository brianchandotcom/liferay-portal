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

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.Locale;

import javax.ws.rs.core.UriInfo;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
public class SkuDTOConverterContext extends DefaultDTOConverterContext {

	public SkuDTOConverterContext(
		CommerceContext commerceContext, long companyId,
		CPDefinition cpDefinition, Locale locale, int quantity,
		long resourcePrimKey, UriInfo uriInfo, User user) {

		super(resourcePrimKey, locale, uriInfo, user);

		_commerceContext = commerceContext;
		_companyId = companyId;
		_cpDefinition = cpDefinition;
		_quantity = quantity;
	}

	public CommerceContext getCommerceContext() {
		return _commerceContext;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public CPDefinition getCPDefinition() {
		return _cpDefinition;
	}

	public int getQuantity() {
		return _quantity;
	}

	private final CommerceContext _commerceContext;
	private final long _companyId;
	private final CPDefinition _cpDefinition;
	private final int _quantity;

}