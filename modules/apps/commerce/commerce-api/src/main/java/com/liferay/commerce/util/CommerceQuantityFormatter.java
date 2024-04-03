/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.util;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;

import java.math.BigDecimal;

import java.util.Locale;

/**
 * @author Alessio Antonio Rendina
 */
public interface CommerceQuantityFormatter {

	public BigDecimal format(
		CPInstance cpInstance, BigDecimal quantity, String unitOfMeasureKey);

	public BigDecimal format(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure, BigDecimal quantity);

	public BigDecimal format(
		long cpInstanceId, BigDecimal quantity, String unitOfMeasureKey);

	public BigDecimal format(
		long companyId, BigDecimal quantity, String sku,
		String unitOfMeasureKey);

	public String parse(BigDecimal quantity, Locale locale) throws Exception;

}