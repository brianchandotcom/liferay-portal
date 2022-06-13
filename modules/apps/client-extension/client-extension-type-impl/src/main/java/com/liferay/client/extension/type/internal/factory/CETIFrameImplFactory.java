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
import com.liferay.client.extension.type.CETIFrame;
import com.liferay.client.extension.type.factory.CETImplFactory;
import com.liferay.client.extension.type.internal.CETIFrameImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	property = "type=" + ClientExtensionEntryConstants.TYPE_IFRAME,
	service = CETImplFactory.class
)
public class CETIFrameImplFactory implements CETImplFactory<CETIFrame> {

	@Override
	public CETIFrame cet(ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		return new CETIFrameImpl(clientExtensionEntry);
	}

	@Override
	public CETIFrame cet(PortletRequest portletRequest) throws PortalException {
		return new CETIFrameImpl(portletRequest);
	}

	@Override
	public CETIFrame cet(
			String baseURL, long companyId, String description,
			String externalReferenceCode, String name, Properties properties,
			String sourceCodeURL, UnicodeProperties unicodeProperties)
		throws PortalException {

		return new CETIFrameImpl(
			baseURL, companyId, description, externalReferenceCode, name,
			properties, sourceCodeURL, unicodeProperties);
	}

	@Override
	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException {

		CETIFrameImpl newCETIFrameImpl = new CETIFrameImpl(
			newTypeSettingsUnicodeProperties);

		Matcher matcher = _friendlyURLMappingPattern.matcher(
			newCETIFrameImpl.getFriendlyURLMapping());

		if (!matcher.matches()) {
			throw new ClientExtensionEntryTypeSettingsException(
				"please-enter-a-valid-friendly-url-mapping");
		}

		if (!Validator.isUrl(newCETIFrameImpl.getURL())) {
			throw new ClientExtensionEntryTypeSettingsException(
				"please-enter-a-valid-url");
		}

		if (oldTypeSettingsUnicodeProperties != null) {
			CETIFrameImpl oldCETIFrameImpl = new CETIFrameImpl(
				oldTypeSettingsUnicodeProperties);

			if (newCETIFrameImpl.isInstanceable() !=
					oldCETIFrameImpl.isInstanceable()) {

				throw new ClientExtensionEntryTypeSettingsException(
					"the-instanceable-value-cannot-be-changed");
			}
		}
	}

	private static final Pattern _friendlyURLMappingPattern = Pattern.compile(
		"[A-Za-z0-9-_]*");

}