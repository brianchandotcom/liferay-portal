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
import com.liferay.client.extension.type.CETGlobalCSS;
import com.liferay.client.extension.type.factory.CETImplFactory;
import com.liferay.client.extension.type.internal.CETGlobalCSSImpl;
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
	property = "type=" + ClientExtensionEntryConstants.TYPE_GLOBAL_CSS,
	service = CETImplFactory.class
)
public class CETGlobalCSSImplFactory implements CETImplFactory<CETGlobalCSS> {

	@Override
	public CETGlobalCSS cet(ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		return new CETGlobalCSSImpl(clientExtensionEntry);
	}

	@Override
	public CETGlobalCSS cet(PortletRequest portletRequest)
		throws PortalException {

		return new CETGlobalCSSImpl(portletRequest);
	}

	@Override
	public CETGlobalCSS cet(
			String baseURL, long companyId, String description,
			String externalReferenceCode, String name, Properties properties,
			String sourceCodeURL, UnicodeProperties unicodeProperties)
		throws PortalException {

		return new CETGlobalCSSImpl(
			baseURL, companyId, description, externalReferenceCode, name,
			properties, sourceCodeURL, unicodeProperties);
	}

	@Override
	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException {

		CETGlobalCSS newCETGlobalCSSImpl = new CETGlobalCSSImpl(
			newTypeSettingsUnicodeProperties);

		if (!Validator.isUrl(newCETGlobalCSSImpl.getURL())) {
			throw new ClientExtensionEntryTypeSettingsException(
				"please-enter-a-valid-url");
		}
	}

}