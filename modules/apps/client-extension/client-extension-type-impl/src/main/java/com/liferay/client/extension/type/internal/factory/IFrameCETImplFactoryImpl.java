/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.factory;

import com.liferay.client.extension.exception.ClientExtensionEntryTypeSettingsException;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.IFrameCET;
import com.liferay.client.extension.type.factory.CETImplFactory;
import com.liferay.client.extension.type.internal.IFrameCETImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class IFrameCETImplFactoryImpl implements CETImplFactory<IFrameCET> {

	@Override
	public IFrameCET create(ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		return new IFrameCETImpl(clientExtensionEntry);
	}

	@Override
	public IFrameCET create(PortletRequest portletRequest)
		throws PortalException {

		return new IFrameCETImpl(portletRequest);
	}

	@Override
	public IFrameCET create(
			String baseURL, long companyId, String description,
			String externalReferenceCode, String name, Properties properties,
			String sourceCodeURL, UnicodeProperties unicodeProperties)
		throws PortalException {

		return new IFrameCETImpl(
			baseURL, companyId, description, externalReferenceCode, name,
			properties, sourceCodeURL, unicodeProperties);
	}

	@Override
	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException {

		IFrameCET newIFrameCET = new IFrameCETImpl(
			StringPool.NEW_LINE, newTypeSettingsUnicodeProperties);

		Matcher matcher = _friendlyURLMappingPattern.matcher(
			newIFrameCET.getFriendlyURLMapping());

		if (!matcher.matches()) {
			throw new ClientExtensionEntryTypeSettingsException(
				"please-enter-a-valid-friendly-url-mapping");
		}

		if (!Validator.isUrl(newIFrameCET.getURL())) {
			throw new ClientExtensionEntryTypeSettingsException(
				"please-enter-a-valid-url");
		}

		if (oldTypeSettingsUnicodeProperties != null) {
			IFrameCET oldIFrameCET = new IFrameCETImpl(
				StringPool.NEW_LINE, oldTypeSettingsUnicodeProperties);

			if (newIFrameCET.isInstanceable() !=
					oldIFrameCET.isInstanceable()) {

				throw new ClientExtensionEntryTypeSettingsException(
					"the-instanceable-value-cannot-be-changed");
			}
		}
	}

	private static final Pattern _friendlyURLMappingPattern = Pattern.compile(
		"[A-Za-z0-9-_]*");

}