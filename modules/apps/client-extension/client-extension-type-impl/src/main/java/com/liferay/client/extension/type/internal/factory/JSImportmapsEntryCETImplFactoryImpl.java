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

import com.liferay.client.extension.exception.ClientExtensionEntryTypeSettingsException;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.JSImportmapsEntryCET;
import com.liferay.client.extension.type.factory.CETImplFactory;
import com.liferay.client.extension.type.internal.JSImportmapsEntryCETImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Properties;

import javax.portlet.PortletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class JSImportmapsEntryCETImplFactoryImpl
	implements CETImplFactory<JSImportmapsEntryCET> {

	@Override
	public JSImportmapsEntryCET create(
			ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		return new JSImportmapsEntryCETImpl(clientExtensionEntry);
	}

	@Override
	public JSImportmapsEntryCET create(PortletRequest portletRequest)
		throws PortalException {

		return new JSImportmapsEntryCETImpl(portletRequest);
	}

	@Override
	public JSImportmapsEntryCET create(
			String baseURL, long companyId, String description,
			String externalReferenceCode, String name, Properties properties,
			String sourceCodeURL, UnicodeProperties unicodeProperties)
		throws PortalException {

		return new JSImportmapsEntryCETImpl(
			baseURL, companyId, description, externalReferenceCode, name,
			properties, sourceCodeURL, unicodeProperties);
	}

	@Override
	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException {

		JSImportmapsEntryCET jsImportmapsEntryCET =
			new JSImportmapsEntryCETImpl(
				StringPool.BLANK, newTypeSettingsUnicodeProperties);

		if (Validator.isNull(jsImportmapsEntryCET.getBareSpecifier())) {
			throw new ClientExtensionEntryTypeSettingsException(
				"please-enter-a-valid-bare-specifier");
		}

		if (!Validator.isUrl(jsImportmapsEntryCET.getURL(), true)) {
			throw new ClientExtensionEntryTypeSettingsException(
				"please-enter-a-valid-url");
		}
	}

}