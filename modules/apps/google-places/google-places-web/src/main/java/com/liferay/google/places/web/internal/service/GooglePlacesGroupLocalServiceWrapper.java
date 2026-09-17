/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.google.places.web.internal.service;

import com.liferay.google.places.constants.GooglePlacesWebKeys;
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
public class GooglePlacesGroupLocalServiceWrapper
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

		String googlePlacesAPIKey = typeSettingsUnicodeProperties.getProperty(
			GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY);

		if (Validator.isNull(googlePlacesAPIKey)) {
			return super.updateGroup(groupId, typeSettings);
		}

		Group group = getGroup(groupId);

		String vaultedGooglePlacesAPIKey = SecretVaultUtil.vault(
			group.getCompanyId(),
			SecretVaultUtil.getIdentifier(
				GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY, "group/" + groupId),
			googlePlacesAPIKey);

		if (vaultedGooglePlacesAPIKey.equals(googlePlacesAPIKey)) {
			return super.updateGroup(groupId, typeSettings);
		}

		typeSettingsUnicodeProperties.setProperty(
			GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY,
			vaultedGooglePlacesAPIKey);

		return super.updateGroup(
			groupId, typeSettingsUnicodeProperties.toString());
	}

}