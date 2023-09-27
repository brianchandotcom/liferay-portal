/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model.impl;

import com.liferay.commerce.model.CommerceAvailabilityEstimate;
import com.liferay.commerce.service.CommerceAvailabilityEstimateLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDAvailabilityEstimateImpl
	extends CPDAvailabilityEstimateBaseImpl {

	@Override
	public CommerceAvailabilityEstimate getCommerceAvailabilityEstimate()
		throws PortalException {

		if (getCommerceAvailabilityEstimateId() > 0) {
			return CommerceAvailabilityEstimateLocalServiceUtil.
				getCommerceAvailabilityEstimate(
					getCommerceAvailabilityEstimateId());
		}

		return null;
	}

}