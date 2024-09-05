/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.type.GlobalJSCET;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Date;
import java.util.Properties;

/**
 * @author Eudaldo Alonso
 */
public class GlobalJSCETImpl extends BaseCETImpl implements GlobalJSCET {

	public GlobalJSCETImpl(
		String baseURL, long companyId, Date createDate, String description,
		String externalReferenceCode, Date modifiedDate, String name,
		Properties properties, boolean readOnly, String sourceCodeURL,
		int status, UnicodeProperties typeSettingsUnicodeProperties) {

		super(
			baseURL, companyId, createDate, description, externalReferenceCode,
			modifiedDate, name, properties, readOnly, sourceCodeURL, status,
			typeSettingsUnicodeProperties);
	}

	@Override
	public String getEditJSP() {
		return "/admin/edit_global_js.jsp";
	}

	@Override
	public String getScope() {
		return getString("scope");
	}

	@Override
	public String getScriptElementAttributesJSON() {
		return getString("scriptElementAttributesJSON");
	}

	@Override
	public String getScriptLocation() {
		return getString("scriptLocation");
	}

	@Override
	public String getType() {
		return ClientExtensionEntryConstants.TYPE_GLOBAL_JS;
	}

	@Override
	public String getURL() {
		return getString("url");
	}

	@Override
	public String getViewJSP() {
		return "/admin/view_global_js.jsp";
	}

	@Override
	public boolean hasProperties() {
		return false;
	}

}