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

import com.liferay.client.extension.exception.ClientExtensionEntryCustomElementCSSURLsException;
import com.liferay.client.extension.exception.ClientExtensionEntryCustomElementHTMLElementNameException;
import com.liferay.client.extension.exception.ClientExtensionEntryCustomElementURLsException;
import com.liferay.client.extension.exception.ClientExtensionEntryFriendlyURLMappingException;
import com.liferay.client.extension.type.internal.CETCustomElementImpl;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 */
public class CETCustomElementValidator implements CETTypeValidator {

	@Override
	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException {

		CETCustomElementImpl newCETCustomElementImpl = new CETCustomElementImpl(
			newTypeSettingsUnicodeProperties);

		String cssURLs = newCETCustomElementImpl.getCSSURLs();

		if (Validator.isNotNull(cssURLs)) {
			for (String cssURL : cssURLs.split(StringPool.NEW_LINE)) {
				if (!Validator.isUrl(cssURL, true)) {
					throw new ClientExtensionEntryCustomElementCSSURLsException(
						"Invalid custom element CSS URL " + cssURL);
				}
			}
		}

		String friendlyURLMapping =
			newCETCustomElementImpl.getFriendlyURLMapping();

		Matcher matcher = _friendlyURLMappingPattern.matcher(
			friendlyURLMapping);

		if (!matcher.matches()) {
			throw new ClientExtensionEntryFriendlyURLMappingException(
				"Invalid friendly URL mapping " + friendlyURLMapping);
		}

		String htmlElementName = newCETCustomElementImpl.getHTMLElementName();

		if (Validator.isNull(htmlElementName)) {
			throw new ClientExtensionEntryCustomElementHTMLElementNameException(
				"Custom element HTML element name is null");
		}

		char[] htmlElementNameCharArray = htmlElementName.toCharArray();

		if (!Validator.isChar(htmlElementNameCharArray[0]) ||
			!Character.isLowerCase(htmlElementNameCharArray[0])) {

			throw new ClientExtensionEntryCustomElementHTMLElementNameException(
				"Custom element HTML element name must start with a " +
					"lowercase letter");
		}

		boolean containsDash = false;

		for (char c : htmlElementNameCharArray) {
			if (c == CharPool.DASH) {
				containsDash = true;
			}

			if ((Validator.isChar(c) && Character.isLowerCase(c)) ||
				Validator.isNumber(String.valueOf(c)) || (c == CharPool.DASH) ||
				(c == CharPool.PERIOD) || (c == CharPool.UNDERLINE)) {
			}
			else {
				throw new ClientExtensionEntryCustomElementHTMLElementNameException(
					"Custom element HTML element name contains an invalid " +
						"character");
			}
		}

		if (!containsDash) {
			throw new ClientExtensionEntryCustomElementHTMLElementNameException(
				"Custom element HTML element name must contain at least one " +
					"hyphen");
		}

		if (_reservedHTMLElementNames.contains(htmlElementName)) {
			throw new ClientExtensionEntryCustomElementHTMLElementNameException(
				"Reserved custom element HTML element name " + htmlElementName);
		}

		String urls = newCETCustomElementImpl.getURLs();

		if (Validator.isNull(urls)) {
			throw new ClientExtensionEntryCustomElementURLsException(
				"Invalid custom element URLs " + urls);
		}

		for (String url : urls.split(StringPool.NEW_LINE)) {
			if (!Validator.isUrl(url, true)) {
				throw new ClientExtensionEntryCustomElementURLsException(
					"Invalid custom element URL " + url);
			}
		}

		if (oldTypeSettingsUnicodeProperties != null) {
			CETCustomElementImpl oldCETCustomElementImpl =
				new CETCustomElementImpl(oldTypeSettingsUnicodeProperties);

			if (newCETCustomElementImpl.isInstanceable() !=
					oldCETCustomElementImpl.isInstanceable()) {

				// TODO Use a different exception

				throw new IllegalArgumentException();
			}
		}
	}

	private static final Pattern _friendlyURLMappingPattern = Pattern.compile(
		"[A-Za-z0-9-_]*");

	private final Set<String> _reservedHTMLElementNames = SetUtil.fromArray(
		"annotation-xml", "color-profile", "font-face", "font-face-format",
		"font-face-name", "font-face-src", "font-face-uri", "missing-glyph");

}