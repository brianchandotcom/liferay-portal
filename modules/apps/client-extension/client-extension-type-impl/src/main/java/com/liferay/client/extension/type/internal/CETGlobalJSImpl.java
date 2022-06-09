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

package com.liferay.client.extension.type.internal;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.CETGlobalJS;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.util.Properties;

import javax.portlet.PortletRequest;

/**
 * @author Eudaldo Alonso
 */
public class CETGlobalJSImpl extends BaseCETImpl implements CETGlobalJS {

	public CETGlobalJSImpl(ClientExtensionEntry clientExtensionEntry) {
		super(clientExtensionEntry);
	}

	public CETGlobalJSImpl(PortletRequest portletRequest) {
		this(
			UnicodePropertiesBuilder.create(
				true
			).put(
				"url", ParamUtil.getString(portletRequest, "url")
			).build());
	}

	public CETGlobalJSImpl(
		String baseURL, long companyId, String description,
		String externalReferenceCode, String name, Properties properties,
		String sourceCodeURL, UnicodeProperties typeSettingsUnicodeProperties) {

		super(
			baseURL, companyId, description, externalReferenceCode, name,
			properties, sourceCodeURL, typeSettingsUnicodeProperties);
	}

	public CETGlobalJSImpl(UnicodeProperties typeSettingsUnicodeProperties) {
		super(typeSettingsUnicodeProperties);
	}

	@Override
	public String getEditJSP() {
		return "/admin/edit_global_js.jsp";
	}

	@Override
	public String getType() {
		return ClientExtensionEntryConstants.TYPE_GLOBAL_JS;
	}

	@Override
	public String getURL() {
		return getString("url");
	}

}