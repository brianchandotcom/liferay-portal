/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.factory;

import com.liferay.client.extension.exception.ClientExtensionEntryTypeSettingsException;
import com.liferay.client.extension.type.AudiencesCustomAttributesCET;
import com.liferay.client.extension.type.internal.AudiencesCustomAttributesCETImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;

import jakarta.portlet.PortletRequest;

import java.util.Date;
import java.util.Properties;

/**
 * @author Iván Zaera Avellón
 */
public class AudiencesCustomAttributesCETImplFactoryImpl
	extends BaseCETImplFactoryImpl<AudiencesCustomAttributesCET> {

	public AudiencesCustomAttributesCETImplFactoryImpl() {
		super(AudiencesCustomAttributesCET.class);
	}

	@Override
	public AudiencesCustomAttributesCET create(
		String baseURL, long companyId, Date createDate, String description,
		String externalReferenceCode, Date modifiedDate, String name,
		Properties properties, boolean readOnly, String sourceCodeURL,
		int status, UnicodeProperties typeSettingsUnicodeProperties) {

		return new AudiencesCustomAttributesCETImpl(
			baseURL, companyId, createDate, description, externalReferenceCode,
			modifiedDate, name, properties, readOnly, sourceCodeURL, status,
			typeSettingsUnicodeProperties);
	}

	@Override
	public UnicodeProperties getUnicodeProperties(
		PortletRequest portletRequest) {

		return UnicodePropertiesBuilder.create(
			true
		).put(
			"symbols",
			StringUtil.merge(
				ParamUtil.getStringValues(portletRequest, "symbols"),
				StringPool.NEW_LINE)
		).put(
			"url", ParamUtil.getString(portletRequest, "url")
		).build();
	}

	@Override
	public void validate(
			AudiencesCustomAttributesCET newAudiencesCustomAttributesCET,
			AudiencesCustomAttributesCET oldAudiencesCustomAttributesCET)
		throws PortalException {

		if (Validator.isNull(newAudiencesCustomAttributesCET.getSymbols())) {
			throw new ClientExtensionEntryTypeSettingsException(
				"At least one symbol is required",
				"please-enter-at-least-one-symbol");
		}

		String url = newAudiencesCustomAttributesCET.getURL();

		if (!Validator.isUrl(url, true)) {
			throw new ClientExtensionEntryTypeSettingsException(
				"Invalid JavaScript URL: " + url, "javascript-url-x-is-invalid",
				url);
		}
	}

}