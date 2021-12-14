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
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.HashMap;
import java.util.Locale;

/**
 * @author Andrea Sbarra
 */
public class MappedProductDTOConverterContext
	extends DefaultDTOConverterContext {

	public MappedProductDTOConverterContext(
		CommerceContext commerceContext, long companyId, Object id,
		Locale locale) {

		super(false, new HashMap<>(), null, id, locale, null, null);

		_commerceContext = commerceContext;
		_companyId = companyId;
	}

	public CommerceContext getCommerceContext() {
		return _commerceContext;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Long getReplacementCPInstanceId() {
		return _replacementCPInstanceId;
	}

	public Long getReplacementCProductId() {
		return _replacementCProductId;
	}

	public void setReplacementCPInstanceId(Long replacementCPInstanceId) {
		_replacementCPInstanceId = replacementCPInstanceId;
	}

	public void setReplacementCProductId(Long replacementCProductId) {
		_replacementCProductId = replacementCProductId;
	}

	private final CommerceContext _commerceContext;
	private final long _companyId;
	private Long _replacementCPInstanceId;
	private Long _replacementCProductId;

}