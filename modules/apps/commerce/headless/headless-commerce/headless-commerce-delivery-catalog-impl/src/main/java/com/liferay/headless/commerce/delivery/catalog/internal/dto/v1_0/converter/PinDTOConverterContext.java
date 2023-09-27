/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.HashMap;
import java.util.Locale;

/**
 * @author Andrea Sbarra
 */
public class PinDTOConverterContext extends DefaultDTOConverterContext {

	public PinDTOConverterContext(
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

	private final CommerceContext _commerceContext;
	private final long _companyId;

}