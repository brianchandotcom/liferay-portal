/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.type.ThemeCSSCET;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Date;
import java.util.Properties;

/**
 * @author Iván Zaera Avellón
 */
public class ThemeCSSCETImpl extends BaseCETImpl implements ThemeCSSCET {

	public ThemeCSSCETImpl(
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
	public String getClayRTLURL() {
		return getString("clayRTLURL");
	}

	@Override
	public String getClayURL() {
		return getString("clayURL");
	}

	@Override
	public String getEditJSP() {
		return "/admin/edit_theme_css.jsp";
	}

	@Override
	public String getFrontendTokenDefinitionJSON() {
		return getString("frontendTokenDefinitionJSON");
	}

	@Override
	public String getMainRTLURL() {
		return getString("mainRTLURL");
	}

	@Override
	public String getMainURL() {
		return getString("mainURL");
	}

	@Override
	public String getScope() {
		return getString("scope");
	}

	@Override
	public String getType() {
		return ClientExtensionEntryConstants.TYPE_THEME_CSS;
	}

	@Override
	public String getViewJSP() {
		return "/admin/view_theme_css.jsp";
	}

	@Override
	public boolean hasProperties() {
		return false;
	}

}