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
import com.liferay.client.extension.type.CETThemeCSS;
import com.liferay.client.extension.type.factory.CETImplFactory;
import com.liferay.client.extension.type.internal.CETThemeCSSImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	property = "type=" + ClientExtensionEntryConstants.TYPE_THEME_CSS,
	service = CETImplFactory.class
)
public class CETThemeCSSImplFactory implements CETImplFactory<CETThemeCSS> {

	@Override
	public CETThemeCSS cet(ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		return new CETThemeCSSImpl(clientExtensionEntry);
	}

	@Override
	public CETThemeCSS cet(PortletRequest portletRequest)
		throws PortalException {

		return new CETThemeCSSImpl(portletRequest);
	}

	@Override
	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException {

		CETThemeCSS newCETThemeCSSImpl = new CETThemeCSSImpl(
			newTypeSettingsUnicodeProperties);

		String baseURL = newCETThemeCSSImpl.getBaseURL();

		if (!Validator.isBlank(baseURL) && !Validator.isUrl(baseURL, true)) {
			throw new ClientExtensionEntryTypeSettingsException(
				"please-enter-a-valid-base-url");
		}

		String clayURL = newCETThemeCSSImpl.getClayURL();

		if (!Validator.isBlank(clayURL) && !Validator.isUrl(clayURL, true)) {
			throw new ClientExtensionEntryTypeSettingsException(
				"please-enter-a-valid-clay-url");
		}

		String mainURL = newCETThemeCSSImpl.getMainURL();

		if (!Validator.isBlank(mainURL) && !Validator.isUrl(mainURL, true)) {
			throw new ClientExtensionEntryTypeSettingsException(
				"please-enter-a-valid-main-url");
		}
	}

}