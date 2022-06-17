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

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.exception.ClientExtensionEntryTypeSettingsException;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.ThemeCSSCET;
import com.liferay.client.extension.type.factory.CETImplFactory;
import com.liferay.client.extension.type.internal.ThemeCSSCETImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Properties;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	property = "type=" + ClientExtensionEntryConstants.TYPE_THEME_CSS,
	service = CETImplFactory.class
)
public class ThemeCSSCETImplFactoryImpl implements CETImplFactory<ThemeCSSCET> {

	@Override
	public ThemeCSSCET create(ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		return new ThemeCSSCETImpl(clientExtensionEntry);
	}

	@Override
	public ThemeCSSCET create(PortletRequest portletRequest)
		throws PortalException {

		return new ThemeCSSCETImpl(portletRequest);
	}

	@Override
	public ThemeCSSCET create(
			String baseURL, long companyId, String description,
			String externalReferenceCode, String name, Properties properties,
			String sourceCodeURL, UnicodeProperties unicodeProperties)
		throws PortalException {

		return new ThemeCSSCETImpl(
			baseURL, companyId, description, externalReferenceCode, name,
			properties, sourceCodeURL, unicodeProperties);
	}

	@Override
	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException {

		ThemeCSSCET newThemeCSSCET = new ThemeCSSCETImpl(
			StringPool.NEW_LINE, newTypeSettingsUnicodeProperties);

		String baseURL = newThemeCSSCET.getBaseURL();

		if (!Validator.isBlank(baseURL) && !Validator.isUrl(baseURL, true)) {
			throw new ClientExtensionEntryTypeSettingsException(
				"please-enter-a-valid-base-url");
		}

		String clayURL = newThemeCSSCET.getClayURL();

		if (!Validator.isBlank(clayURL) && !Validator.isUrl(clayURL, true)) {
			throw new ClientExtensionEntryTypeSettingsException(
				"please-enter-a-valid-clay-url");
		}

		String mainURL = newThemeCSSCET.getMainURL();

		if (!Validator.isBlank(mainURL) && !Validator.isUrl(mainURL, true)) {
			throw new ClientExtensionEntryTypeSettingsException(
				"please-enter-a-valid-main-url");
		}
	}

}