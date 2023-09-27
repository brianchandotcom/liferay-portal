/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.factory;

import com.liferay.client.extension.exception.ClientExtensionEntryTypeSettingsException;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.FDSFilterCET;
import com.liferay.client.extension.type.factory.CETImplFactory;
import com.liferay.client.extension.type.internal.FDSFilterCETImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Properties;

import javax.portlet.PortletRequest;

/**
 * @author Bryce Osterhaus
 */
public class FDSFilterCETImplFactoryImpl
	implements CETImplFactory<FDSFilterCET> {

	@Override
	public FDSFilterCET create(ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		return new FDSFilterCETImpl(clientExtensionEntry);
	}

	@Override
	public FDSFilterCET create(PortletRequest portletRequest)
		throws PortalException {

		return new FDSFilterCETImpl(portletRequest);
	}

	@Override
	public FDSFilterCET create(
			String baseURL, long companyId, String description,
			String externalReferenceCode, String name, Properties properties,
			String sourceCodeURL, UnicodeProperties unicodeProperties)
		throws PortalException {

		return new FDSFilterCETImpl(
			baseURL, companyId, description, externalReferenceCode, name,
			properties, sourceCodeURL, unicodeProperties);
	}

	@Override
	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException {

		FDSFilterCET fdsFilterCET = new FDSFilterCETImpl(
			StringPool.BLANK, newTypeSettingsUnicodeProperties);

		if (Validator.isNull(fdsFilterCET.getURL())) {
			throw new ClientExtensionEntryTypeSettingsException(
				"please-enter-at-least-one-url");
		}
	}

}