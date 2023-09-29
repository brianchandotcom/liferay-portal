/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.util;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

/**
 * @author Alessio Antonio Rendina
 */
public interface CommerceQuantityFormatter {

	public BigDecimal format(
			CPInstance cpInstance, BigDecimal quantity, String unitOfMeasureKey)
		throws PortalException;

	public BigDecimal format(
			CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure,
			BigDecimal quantity)
		throws PortalException;

	public BigDecimal format(
			long cpInstanceId, BigDecimal quantity, String unitOfMeasureKey)
		throws PortalException;

	public BigDecimal format(
			long companyId, BigDecimal quantity, String sku,
			String unitOfMeasureKey)
		throws PortalException;

}