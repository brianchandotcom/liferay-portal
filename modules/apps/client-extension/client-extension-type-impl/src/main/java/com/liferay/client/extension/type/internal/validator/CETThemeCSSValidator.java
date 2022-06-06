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

package com.liferay.client.extension.type.internal.validator;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.exception.ClientExtensionEntryInvalidURLException;
import com.liferay.client.extension.type.CETThemeCSS;
import com.liferay.client.extension.type.internal.CETThemeCSSImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	property = "type=" + ClientExtensionEntryConstants.TYPE_THEME_CSS,
	service = CETTypeValidator.class
)
public class CETThemeCSSValidator implements CETTypeValidator {

	@Override
	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException {

		CETThemeCSS newCETThemeCSSImpl = new CETThemeCSSImpl(
			newTypeSettingsUnicodeProperties);

		String baseURL = newCETThemeCSSImpl.getBaseURL();

		if (!Validator.isBlank(baseURL) && !Validator.isUrl(baseURL, true)) {
			throw new ClientExtensionEntryInvalidURLException(
				"Invalid base URL " + baseURL);
		}

		String clayURL = newCETThemeCSSImpl.getClayURL();

		if (!Validator.isBlank(clayURL) && !Validator.isUrl(clayURL, true)) {
			throw new ClientExtensionEntryInvalidURLException(
				"Invalid Clay URL " + clayURL);
		}

		String mainURL = newCETThemeCSSImpl.getMainURL();

		if (!Validator.isBlank(mainURL) && !Validator.isUrl(mainURL, true)) {
			throw new ClientExtensionEntryInvalidURLException(
				"Invalid Main URL " + mainURL);
		}
	}

}