/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.factory;

import com.liferay.client.extension.exception.ClientExtensionEntryTypeSettingsException;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.ThemeSpritemapCET;
import com.liferay.client.extension.type.factory.CETImplFactory;
import com.liferay.client.extension.type.internal.ThemeSpritemapCETImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Properties;

import javax.portlet.PortletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class ThemeSpritemapCETImplFactoryImpl
	implements CETImplFactory<ThemeSpritemapCET> {

	@Override
	public ThemeSpritemapCET create(ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		return new ThemeSpritemapCETImpl(clientExtensionEntry);
	}

	@Override
	public ThemeSpritemapCET create(PortletRequest portletRequest)
		throws PortalException {

		return new ThemeSpritemapCETImpl(portletRequest);
	}

	@Override
	public ThemeSpritemapCET create(
			String baseURL, long companyId, String description,
			String externalReferenceCode, String name, Properties properties,
			String sourceCodeURL, UnicodeProperties unicodeProperties)
		throws PortalException {

		return new ThemeSpritemapCETImpl(
			baseURL, companyId, description, externalReferenceCode, name,
			properties, sourceCodeURL, unicodeProperties);
	}

	@Override
	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException {

		ThemeSpritemapCET newThemeSpritemapCET = new ThemeSpritemapCETImpl(
			StringPool.NEW_LINE, newTypeSettingsUnicodeProperties);

		if (!Validator.isUrl(newThemeSpritemapCET.getURL())) {
			throw new ClientExtensionEntryTypeSettingsException(
				"please-enter-a-valid-url");
		}
	}

}