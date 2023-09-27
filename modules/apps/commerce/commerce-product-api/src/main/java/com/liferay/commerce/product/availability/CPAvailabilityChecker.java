/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.availability;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

/**
 * @author Alessio Antonio Rendina
 */
public interface CPAvailabilityChecker {

	public boolean check(
			long commerceChannelGroupId, CPInstance cpInstance,
			String unitOfMeasure, BigDecimal quantity)
		throws PortalException;

	public boolean isAvailable(
			long commerceChannelGroupId, CPInstance cpInstance,
			String unitOfMeasure, BigDecimal quantity)
		throws PortalException;

	public boolean isPurchasable(CPInstance cpInstance) throws PortalException;

}