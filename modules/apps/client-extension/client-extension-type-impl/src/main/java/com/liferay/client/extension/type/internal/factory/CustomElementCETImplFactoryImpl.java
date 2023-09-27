/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.factory;

import com.liferay.client.extension.exception.ClientExtensionEntryTypeSettingsException;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.CustomElementCET;
import com.liferay.client.extension.type.factory.CETImplFactory;
import com.liferay.client.extension.type.internal.CustomElementCETImpl;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class CustomElementCETImplFactoryImpl
	implements CETImplFactory<CustomElementCET> {

	@Override
	public CustomElementCET create(ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		return new CustomElementCETImpl(clientExtensionEntry);
	}

	@Override
	public CustomElementCET create(PortletRequest portletRequest)
		throws PortalException {

		return new CustomElementCETImpl(portletRequest);
	}

	@Override
	public CustomElementCET create(
			String baseURL, long companyId, String description,
			String externalReferenceCode, String name, Properties properties,
			String sourceCodeURL, UnicodeProperties unicodeProperties)
		throws PortalException {

		return new CustomElementCETImpl(
			baseURL, companyId, description, externalReferenceCode, name,
			properties, sourceCodeURL, unicodeProperties);
	}

	@Override
	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException {

		CustomElementCET newCustomElementCET = new CustomElementCETImpl(
			StringPool.NEW_LINE, newTypeSettingsUnicodeProperties);

		String cssURLs = newCustomElementCET.getCSSURLs();

		if (Validator.isNotNull(cssURLs)) {
			for (String cssURL : cssURLs.split(StringPool.NEW_LINE)) {
				if (!Validator.isUrl(cssURL, true)) {
					throw new ClientExtensionEntryTypeSettingsException(
						"css-url-x-is-invalid", cssURL);
				}
			}
		}

		Matcher matcher = _friendlyURLMappingPattern.matcher(
			newCustomElementCET.getFriendlyURLMapping());

		if (!matcher.matches()) {
			throw new ClientExtensionEntryTypeSettingsException(
				"please-enter-a-valid-friendly-url-mapping");
		}

		String htmlElementName = newCustomElementCET.getHTMLElementName();

		if (Validator.isNull(htmlElementName)) {
			throw new ClientExtensionEntryTypeSettingsException(
				"html-element-name-is-empty");
		}

		char[] htmlElementNameCharArray = htmlElementName.toCharArray();

		if (!Validator.isChar(htmlElementNameCharArray[0]) ||
			!Character.isLowerCase(htmlElementNameCharArray[0])) {

			throw new ClientExtensionEntryTypeSettingsException(
				"html-element-name-must-start-with-a-lowercase-letter");
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
				throw new ClientExtensionEntryTypeSettingsException(
					"html-element-name-contains-invalid-character-x", c);
			}
		}

		if (!containsDash) {
			throw new ClientExtensionEntryTypeSettingsException(
				"html-element-name-must-contain-at-least-one-hyphen");
		}

		if (_reservedHTMLElementNames.contains(htmlElementName)) {
			throw new ClientExtensionEntryTypeSettingsException(
				"x-is-a-reserved-html-element-name", htmlElementName);
		}

		String urls = newCustomElementCET.getURLs();

		if (Validator.isNull(urls)) {
			throw new ClientExtensionEntryTypeSettingsException(
				"please-enter-at-least-one-url");
		}

		for (String url : urls.split(StringPool.NEW_LINE)) {
			if (!Validator.isUrl(url, true)) {
				throw new ClientExtensionEntryTypeSettingsException(
					"url-x-is-invalid", url);
			}
		}

		if (oldTypeSettingsUnicodeProperties != null) {
			CustomElementCET oldCustomElementCET = new CustomElementCETImpl(
				StringPool.NEW_LINE, oldTypeSettingsUnicodeProperties);

			if (newCustomElementCET.isInstanceable() !=
					oldCustomElementCET.isInstanceable()) {

				throw new ClientExtensionEntryTypeSettingsException(
					"the-instanceable-value-cannot-be-changed");
			}
		}
	}

	private static final Pattern _friendlyURLMappingPattern = Pattern.compile(
		"[A-Za-z0-9-_]*");

	private final Set<String> _reservedHTMLElementNames = SetUtil.fromArray(
		"annotation-xml", "color-profile", "font-face", "font-face-format",
		"font-face-name", "font-face-src", "font-face-uri", "missing-glyph");

}