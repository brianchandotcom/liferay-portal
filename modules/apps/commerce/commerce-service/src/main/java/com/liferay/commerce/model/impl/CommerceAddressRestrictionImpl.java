/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.service.CountryLocalServiceUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAddressRestrictionImpl
	extends CommerceAddressRestrictionBaseImpl {

	@Override
	public Country getCountry() throws PortalException {
		return CountryLocalServiceUtil.getCountry(getCountryId());
	}

}