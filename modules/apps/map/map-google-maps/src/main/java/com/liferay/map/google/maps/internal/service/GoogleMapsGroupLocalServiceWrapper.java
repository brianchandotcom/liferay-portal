/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.map.google.maps.internal.service;

import com.liferay.map.google.maps.internal.constants.GoogleMapsWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceWrapper;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.key.secret.SecretVaultUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pedro Victor Silvestre
 */
@Component(service = ServiceWrapper.class)
public class GoogleMapsGroupLocalServiceWrapper
	extends GroupLocalServiceWrapper {

	@Override
	public Group updateGroup(long groupId, String typeSettings)
		throws PortalException {

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.create(
				true
			).fastLoad(
				typeSettings
			).build();

		String googleMapsAPIKey = typeSettingsUnicodeProperties.getProperty(
			GoogleMapsWebKeys.GOOGLE_MAPS_API_KEY);

		if (Validator.isNull(googleMapsAPIKey)) {
			return super.updateGroup(groupId, typeSettings);
		}

		Group group = getGroup(groupId);

		String vaultedGoogleMapsAPIKey = SecretVaultUtil.vault(
			group.getCompanyId(),
			SecretVaultUtil.getIdentifier(
				GoogleMapsWebKeys.GOOGLE_MAPS_API_KEY, "group/" + groupId),
			googleMapsAPIKey);

		if (vaultedGoogleMapsAPIKey.equals(googleMapsAPIKey)) {
			return super.updateGroup(groupId, typeSettings);
		}

		typeSettingsUnicodeProperties.setProperty(
			GoogleMapsWebKeys.GOOGLE_MAPS_API_KEY, vaultedGoogleMapsAPIKey);

		return super.updateGroup(
			groupId, typeSettingsUnicodeProperties.toString());
	}

}