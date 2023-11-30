/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.factory;

import com.liferay.client.extension.exception.ClientExtensionEntryTypeSettingsException;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.EditorConfigContributorCET;
import com.liferay.client.extension.type.factory.CETImplFactory;
import com.liferay.client.extension.type.internal.EditorConfigContributorCETImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Properties;

import javax.portlet.PortletRequest;

/**
 * @author Daniel Sanz
 */
public class EditorConfigContributorCETImplFactoryImpl
	implements CETImplFactory<EditorConfigContributorCET> {

	@Override
	public EditorConfigContributorCET create(
			ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		return new EditorConfigContributorCETImpl(clientExtensionEntry);
	}

	@Override
	public EditorConfigContributorCET create(PortletRequest portletRequest)
		throws PortalException {

		return new EditorConfigContributorCETImpl(portletRequest);
	}

	@Override
	public EditorConfigContributorCET create(
			String baseURL, long companyId, String description,
			String externalReferenceCode, String name, Properties properties,
			String sourceCodeURL, UnicodeProperties unicodeProperties)
		throws PortalException {

		return new EditorConfigContributorCETImpl(
			baseURL, companyId, description, externalReferenceCode, name,
			properties, sourceCodeURL, unicodeProperties);
	}

	@Override
	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException {

		EditorConfigContributorCET editorConfigContributorCET =
			new EditorConfigContributorCETImpl(
				StringPool.BLANK, newTypeSettingsUnicodeProperties);

		if (Validator.isNull(editorConfigContributorCET.getURL())) {
			throw new ClientExtensionEntryTypeSettingsException(
				"At least one JavaScript URL is required",
				"please-enter-at-least-one-javascript-url");
		}
	}

}