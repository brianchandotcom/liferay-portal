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
import com.liferay.client.extension.type.CETGlobalJS;
import com.liferay.client.extension.type.factory.CETImplFactory;
import com.liferay.client.extension.type.internal.CETGlobalJSImpl;
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
	property = "type=" + ClientExtensionEntryConstants.TYPE_GLOBAL_JS,
	service = CETImplFactory.class
)
public class CETGlobalJSImplFactory implements CETImplFactory<CETGlobalJS> {

	@Override
	public CETGlobalJS cet(ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		return new CETGlobalJSImpl(clientExtensionEntry);
	}

	@Override
	public CETGlobalJS cet(PortletRequest portletRequest)
		throws PortalException {

		return new CETGlobalJSImpl(portletRequest);
	}

	@Override
	public CETGlobalJS cet(
			String baseURL, long companyId, String description,
			String externalReferenceCode, String name, Properties properties,
			String sourceCodeURL, UnicodeProperties unicodeProperties)
		throws PortalException {

		return new CETGlobalJSImpl(
			baseURL, companyId, description, externalReferenceCode, name,
			properties, sourceCodeURL, unicodeProperties);
	}

	@Override
	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException {

		CETGlobalJS newCETGlobalJSImpl = new CETGlobalJSImpl(
			newTypeSettingsUnicodeProperties);

		if (!Validator.isUrl(newCETGlobalJSImpl.getURL())) {
			throw new ClientExtensionEntryTypeSettingsException(
				"please-enter-a-valid-url");
		}
	}

}