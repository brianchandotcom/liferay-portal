/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.factory;

import com.liferay.client.extension.exception.ClientExtensionEntryTypeSettingsException;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.EditorConfigurationCET;
import com.liferay.client.extension.type.factory.CETImplFactory;
import com.liferay.client.extension.type.internal.EditorConfigurationCETImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Properties;

import javax.portlet.PortletRequest;

/**
 * @author Daniel Sanz
 */
public class EditorConfigurationCETImplFactoryImpl
	implements CETImplFactory<EditorConfigurationCET> {

	@Override
	public EditorConfigurationCET create(
			ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		return new EditorConfigurationCETImpl(clientExtensionEntry);
	}

	@Override
	public EditorConfigurationCET create(PortletRequest portletRequest)
		throws PortalException {

		return new EditorConfigurationCETImpl(portletRequest);
	}

	@Override
	public EditorConfigurationCET create(
			String baseURL, long companyId, String description,
			String externalReferenceCode, String name, Properties properties,
			String sourceCodeURL, UnicodeProperties unicodeProperties)
		throws PortalException {

		return new EditorConfigurationCETImpl(
			baseURL, companyId, description, externalReferenceCode, name,
			properties, sourceCodeURL, unicodeProperties);
	}

	@Override
	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException {

		EditorConfigurationCET editorConfigurationCET =
			new EditorConfigurationCETImpl(
				StringPool.BLANK, newTypeSettingsUnicodeProperties);

		if (Validator.isNull(editorConfigurationCET.getURL())) {
			throw new ClientExtensionEntryTypeSettingsException(
				"At least one JavaScript URL is required",
				"please-enter-at-least-one-javascript-url");
		}
	}

}