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
import com.liferay.client.extension.type.CETCustomElement;
import com.liferay.client.extension.type.factory.CETImplFactory;
import com.liferay.client.extension.type.internal.CETCustomElementImpl;
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

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	property = "type=" + ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT,
	service = CETImplFactory.class
)
public class CETCustomElementImplFactory
	implements CETImplFactory<CETCustomElement> {

	@Override
	public CETCustomElement cet(ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		return new CETCustomElementImpl(clientExtensionEntry);
	}

	@Override
	public CETCustomElement cet(PortletRequest portletRequest)
		throws PortalException {

		return new CETCustomElementImpl(portletRequest);
	}

	public CETCustomElement cet(
			String baseURL, long companyId, String description,
			String externalReferenceCode, String name, Properties properties,
			String sourceCodeURL, UnicodeProperties unicodeProperties)
		throws PortalException {

		return new CETCustomElementImpl(
			baseURL, companyId, description, externalReferenceCode, name,
			properties, sourceCodeURL, unicodeProperties);
	}

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
					throw new ClientExtensionEntryTypeSettingsException(
						"css-url-x-is-invalid", cssURL);
				}
			}
		}

		Matcher matcher = _friendlyURLMappingPattern.matcher(
			newCETCustomElementImpl.getFriendlyURLMapping());

		if (!matcher.matches()) {
			throw new ClientExtensionEntryTypeSettingsException(
				"please-enter-a-valid-friendly-url-mapping");
		}

		String htmlElementName = newCETCustomElementImpl.getHTMLElementName();

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

		String urls = newCETCustomElementImpl.getURLs();

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
			CETCustomElementImpl oldCETCustomElementImpl =
				new CETCustomElementImpl(oldTypeSettingsUnicodeProperties);

			if (newCETCustomElementImpl.isInstanceable() !=
					oldCETCustomElementImpl.isInstanceable()) {

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