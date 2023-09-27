/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.model;

import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

import java.util.Locale;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Marco Leo
 */
@ProviderType
public interface CommerceMoney {

	public String format(Locale locale) throws PortalException;

	public CommerceCurrency getCommerceCurrency();

	public BigDecimal getPrice();

	public boolean isEmpty();

	public boolean isPriceOnApplication();

}