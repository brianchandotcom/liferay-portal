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
import com.liferay.client.extension.exception.ClientExtensionEntryFriendlyURLMappingException;
import com.liferay.client.extension.exception.ClientExtensionEntryIFrameURLException;
import com.liferay.client.extension.exception.ClientExtensionEntryInstanceableChangedException;
import com.liferay.client.extension.type.internal.CETIFrameImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "type=" + ClientExtensionEntryConstants.TYPE_IFRAME,
	service = CETTypeValidator.class
)
public class CETIFrameValidator implements CETTypeValidator {

	@Override
	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException {

		CETIFrameImpl newCETIFrameImpl = new CETIFrameImpl(
			newTypeSettingsUnicodeProperties);

		String friendlyURLMapping = newCETIFrameImpl.getFriendlyURLMapping();

		Matcher matcher = _friendlyURLMappingPattern.matcher(
			friendlyURLMapping);

		if (!matcher.matches()) {
			throw new ClientExtensionEntryFriendlyURLMappingException(
				"Invalid friendly URL mapping " + friendlyURLMapping);
		}

		String url = newCETIFrameImpl.getURL();

		if (!Validator.isUrl(url)) {
			throw new ClientExtensionEntryIFrameURLException(
				"Invalid URL " + url);
		}

		if (oldTypeSettingsUnicodeProperties != null) {
			CETIFrameImpl oldCETIFrameImpl = new CETIFrameImpl(
				oldTypeSettingsUnicodeProperties);

			if (newCETIFrameImpl.isInstanceable() !=
					oldCETIFrameImpl.isInstanceable()) {

				throw new ClientExtensionEntryInstanceableChangedException();
			}
		}
	}

	private static final Pattern _friendlyURLMappingPattern = Pattern.compile(
		"[A-Za-z0-9-_]*");

}