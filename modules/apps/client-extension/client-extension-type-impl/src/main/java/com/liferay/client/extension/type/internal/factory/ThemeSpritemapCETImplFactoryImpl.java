/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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