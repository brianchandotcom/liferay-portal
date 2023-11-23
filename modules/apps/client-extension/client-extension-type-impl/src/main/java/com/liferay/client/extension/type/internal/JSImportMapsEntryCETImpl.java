/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.type.JSImportMapsEntryCET;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Date;
import java.util.Properties;

/**
 * @author Iván Zaera Avellón
 */
public class JSImportMapsEntryCETImpl
	extends BaseCETImpl implements JSImportMapsEntryCET {

	public JSImportMapsEntryCETImpl(
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
	public String getBareSpecifier() {
		return getString("bareSpecifier");
	}

	@Override
	public String getEditJSP() {
		return "/admin/edit_js_import_maps_entry.jsp";
	}

	@Override
	public String getType() {
		return ClientExtensionEntryConstants.TYPE_JS_IMPORT_MAPS_ENTRY;
	}

	@Override
	public String getURL() {
		return getString("url");
	}

	@Override
	public boolean hasProperties() {
		return false;
	}

}