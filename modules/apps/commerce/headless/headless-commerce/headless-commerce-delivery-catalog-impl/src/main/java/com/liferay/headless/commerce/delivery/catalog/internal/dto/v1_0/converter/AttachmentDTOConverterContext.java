/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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