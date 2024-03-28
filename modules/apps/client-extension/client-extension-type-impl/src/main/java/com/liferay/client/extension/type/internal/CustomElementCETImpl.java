/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.type.CustomElementCET;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Date;
import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class CustomElementCETImpl
	extends BaseCETImpl implements CustomElementCET {

	public CustomElementCETImpl(
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
	public String getCSSURLs() {
		return getString("cssURLs");
	}

	@Override
	public String getEditJSP() {
		return "/admin/edit_custom_element.jsp";
	}

	@Override
	public String getFriendlyURLMapping() {
		return getString("friendlyURLMapping");
	}

	@Override
	public String getHTMLElementName() {
		return getString("htmlElementName");
	}

	@Override
	public String getPanelAppOrder() {
		return getString("panelAppOrder");
	}

	@Override
	public String getPanelCategoryKey() {
		return getString("panelCategoryKey");
	}

	@Override
	public String getPortletCategoryName() {
		return getString("portletCategoryName");
	}

	@Override
	public String getType() {
		return ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT;
	}

	@Override
	public String getURLs() {
		return getString("urls");
	}

	@Override
	public boolean hasProperties() {
		return true;
	}

	@Override
	public boolean isInstanceable() {
		return getBoolean("instanceable");
	}

	@Override
	public boolean isUseESM() {
		return getBoolean("useESM");
	}

}