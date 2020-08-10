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
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.Locale;

/**
 * @author Andrea Sbarra
 */
public class SkuDTOConverterContext extends DefaultDTOConverterContext {

	public SkuDTOConverterContext(
		Locale locale, long resourcePrimKey, CPDefinition cpDefinition,
		long companyId, CommerceContext commerceContext) {

		super(resourcePrimKey, locale);

		_cpDefinition = cpDefinition;
		_companyId = companyId;
		_commerceContext = commerceContext;
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

	private final CommerceContext _commerceContext;
	private final long _companyId;
	private final CPDefinition _cpDefinition;

}