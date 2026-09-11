/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.map.google.maps.internal.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.CompanyLocalServiceWrapper;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pedro Victor Silvestre
 */
@Component(service = ServiceWrapper.class)
public class GoogleMapsCompanyLocalServiceWrapper
	extends CompanyLocalServiceWrapper {

	@Override
	public void updatePreferences(
			long companyId, UnicodeProperties unicodeProperties)
		throws PortalException {

		String googleMapsAPIKey = unicodeProperties.getProperty(
			"googleMapsAPIKey");

		if (Validator.isNotNull(googleMapsAPIKey)) {
			unicodeProperties.setProperty(
				"googleMapsAPIKey",
				GoogleMapsAPIKeyVaultUtil.vault(
					companyId,
					GoogleMapsAPIKeyVaultUtil.getCompanyIdentifier(companyId),
					googleMapsAPIKey));
		}

		super.updatePreferences(companyId, unicodeProperties);
	}

}