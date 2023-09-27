/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.factory;

import com.liferay.client.extension.exception.ClientExtensionEntryTypeSettingsException;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.FDSCellRendererCET;
import com.liferay.client.extension.type.factory.CETImplFactory;
import com.liferay.client.extension.type.internal.FDSCellRendererCETImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Properties;

import javax.portlet.PortletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class FDSCellRendererCETImplFactoryImpl
	implements CETImplFactory<FDSCellRendererCET> {

	@Override
	public FDSCellRendererCET create(ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		return new FDSCellRendererCETImpl(clientExtensionEntry);
	}

	@Override
	public FDSCellRendererCET create(PortletRequest portletRequest)
		throws PortalException {

		return new FDSCellRendererCETImpl(portletRequest);
	}

	@Override
	public FDSCellRendererCET create(
			String baseURL, long companyId, String description,
			String externalReferenceCode, String name, Properties properties,
			String sourceCodeURL, UnicodeProperties unicodeProperties)
		throws PortalException {

		return new FDSCellRendererCETImpl(
			baseURL, companyId, description, externalReferenceCode, name,
			properties, sourceCodeURL, unicodeProperties);
	}

	@Override
	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException {

		FDSCellRendererCET fdsCellRendererCET = new FDSCellRendererCETImpl(
			StringPool.BLANK, newTypeSettingsUnicodeProperties);

		if (Validator.isNull(fdsCellRendererCET.getURL())) {
			throw new ClientExtensionEntryTypeSettingsException(
				"please-enter-at-least-one-url");
		}
	}

}