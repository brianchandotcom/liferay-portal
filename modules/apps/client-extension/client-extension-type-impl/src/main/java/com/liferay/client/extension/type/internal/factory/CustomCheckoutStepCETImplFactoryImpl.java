/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.factory;

import com.liferay.client.extension.exception.ClientExtensionEntryTypeSettingsException;
import com.liferay.client.extension.type.CustomCheckoutStepCET;
import com.liferay.client.extension.type.internal.CustomCheckoutStepCETImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.Properties;

import javax.portlet.PortletRequest;

/**
 * @author Andrea Sbarra
 */
public class CustomCheckoutStepCETImplFactoryImpl
	extends BaseCETImplFactoryImpl<CustomCheckoutStepCET> {

	public CustomCheckoutStepCETImplFactoryImpl() {
		super(CustomCheckoutStepCET.class);
	}

	@Override
	public CustomCheckoutStepCET create(
		String baseURL, long companyId, Date createDate, String description,
		String externalReferenceCode, Date modifiedDate, String name,
		Properties properties, boolean readOnly, String sourceCodeURL,
		int status, UnicodeProperties typeSettingsUnicodeProperties) {

		return new CustomCheckoutStepCETImpl(
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
			"actionURL", ParamUtil.getString(portletRequest, "actionURL")
		).put(
			"active", ParamUtil.getBoolean(portletRequest, "active")
		).put(
			"checkoutStepLabel",
			ParamUtil.getString(
				portletRequest, "checkoutStepLabel", "stepLabel")
		).put(
			"checkoutStepName",
			ParamUtil.getString(portletRequest, "checkoutStepName", "stepName")
		).put(
			"checkoutStepOrder",
			ParamUtil.getString(portletRequest, "checkoutStepOrder", "1")
		).put(
			"oAuth2ApplicationExternalReferenceCode",
			ParamUtil.getString(
				portletRequest, "oAuth2ApplicationExternalReferenceCode")
		).put(
			"order", ParamUtil.getBoolean(portletRequest, "order")
		).put(
			"readyURL", ParamUtil.getString(portletRequest, "readyURL")
		).put(
			"renderURL", ParamUtil.getString(portletRequest, "renderURL")
		).put(
			"sennaDisabled",
			ParamUtil.getBoolean(portletRequest, "sennaDisabled")
		).put(
			"showControls", ParamUtil.getBoolean(portletRequest, "showControls")
		).put(
			"visible", ParamUtil.getBoolean(portletRequest, "visible")
		).build();
	}

	@Override
	public void validate(
			CustomCheckoutStepCET newCET, CustomCheckoutStepCET oldCET)
		throws PortalException {

		String readyURL = newCET.getReadyURL();

		if (Validator.isNotNull(readyURL) && !Validator.isUrl(readyURL, true)) {
			throw new ClientExtensionEntryTypeSettingsException(
				"Invalid Ready URL: " + readyURL, "readyURL", readyURL);
		}

		String renderURL = newCET.getRenderURL();

		if (Validator.isNotNull(renderURL) &&
			!Validator.isUrl(renderURL, true)) {

			throw new ClientExtensionEntryTypeSettingsException(
				"Invalid Render URL: " + renderURL, "renderURL", renderURL);
		}

		String actionURL = newCET.getActionURL();

		if (Validator.isNotNull(actionURL) &&
			!Validator.isUrl(actionURL, true)) {

			throw new ClientExtensionEntryTypeSettingsException(
				"Invalid Action URL: " + actionURL, "actionURL", actionURL);
		}
	}

}