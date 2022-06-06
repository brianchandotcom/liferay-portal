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
import com.liferay.client.extension.type.CETGlobalJS;
import com.liferay.client.extension.type.internal.CETGlobalJSImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	property = "type=" + ClientExtensionEntryConstants.TYPE_GLOBAL_JS,
	service = CETTypeValidator.class
)
public class CETGlobalJSValidator implements CETTypeValidator {

	@Override
	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException {

		CETGlobalJS newCETGlobalJSImpl = new CETGlobalJSImpl(
			newTypeSettingsUnicodeProperties);

		String url = newCETGlobalJSImpl.getURL();

		if (!Validator.isUrl(url)) {
			throw new ClientExtensionEntryInvalidURLException(
				"Invalid URL " + url);
		}
	}

}