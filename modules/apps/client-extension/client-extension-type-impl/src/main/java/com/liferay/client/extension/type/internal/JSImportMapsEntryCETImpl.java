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
import com.liferay.client.extension.type.JSImportMapsEntryCET;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.util.Properties;
import java.util.Set;

import javax.portlet.PortletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class JSImportMapsEntryCETImpl
	extends BaseCETImpl implements JSImportMapsEntryCET {

	public JSImportMapsEntryCETImpl(ClientExtensionEntry clientExtensionEntry) {
		super(clientExtensionEntry);
	}

	public JSImportMapsEntryCETImpl(PortletRequest portletRequest) {
		this(
			StringPool.BLANK,
			UnicodePropertiesBuilder.create(
				true
			).put(
				"bareSpecifier",
				ParamUtil.getString(portletRequest, "bareSpecifier")
			).put(
				"url", ParamUtil.getString(portletRequest, "url")
			).build());
	}

	public JSImportMapsEntryCETImpl(
		String baseURL, long companyId, String description,
		String externalReferenceCode, String name, Properties properties,
		String sourceCodeURL, UnicodeProperties typeSettingsUnicodeProperties) {

		super(
			baseURL, companyId, description, externalReferenceCode, name,
			properties, sourceCodeURL, typeSettingsUnicodeProperties);
	}

	public JSImportMapsEntryCETImpl(
		String baseURL, UnicodeProperties typeSettingsUnicodeProperties) {

		super(baseURL, typeSettingsUnicodeProperties);
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

	@Override
	protected boolean isURLCETPropertyName(String name) {
		return _urlCETPropertyNames.contains(name);
	}

	private static final Set<String> _urlCETPropertyNames =
		getURLCETPropertyNames(JSImportMapsEntryCET.class);

}