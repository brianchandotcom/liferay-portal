/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.map.google.maps.internal.service;

import com.liferay.map.constants.MapProviderWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.CompanyLocalServiceWrapper;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.key.secret.SecretVaultUtil;

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
			MapProviderWebKeys.GOOGLE_MAPS_API_KEY);

		if (Validator.isNotNull(googleMapsAPIKey)) {
			unicodeProperties.setProperty(
				MapProviderWebKeys.GOOGLE_MAPS_API_KEY,
				SecretVaultUtil.vault(
					companyId,
					SecretVaultUtil.getIdentifier(
						MapProviderWebKeys.GOOGLE_MAPS_API_KEY,
						"company/" + companyId),
					googleMapsAPIKey));
		}

		super.updatePreferences(companyId, unicodeProperties);
	}

}