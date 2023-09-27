/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.model.impl;

import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.service.CommercePricingClassLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Riccardo Alberti
 */
public class CommercePricingClassCPDefinitionRelImpl
	extends CommercePricingClassCPDefinitionRelBaseImpl {

	@Override
	public CommercePricingClass getCommercePricingClass()
		throws PortalException {

		return CommercePricingClassLocalServiceUtil.getCommercePricingClass(
			getCommercePricingClassId());
	}

}