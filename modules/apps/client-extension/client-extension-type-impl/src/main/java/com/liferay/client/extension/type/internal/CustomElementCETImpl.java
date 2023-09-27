/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.CustomElementCET;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.util.Properties;
import java.util.Set;

import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class CustomElementCETImpl
	extends BaseCETImpl implements CustomElementCET {

	public CustomElementCETImpl(ClientExtensionEntry clientExtensionEntry) {
		super(clientExtensionEntry);
	}

	public CustomElementCETImpl(PortletRequest portletRequest) {
		this(
			StringPool.BLANK,
			UnicodePropertiesBuilder.create(
				true
			).put(
				"cssURLs",
				StringUtil.merge(
					ParamUtil.getStringValues(portletRequest, "cssURLs"),
					StringPool.NEW_LINE)
			).put(
				"friendlyURLMapping",
				ParamUtil.getString(portletRequest, "friendlyURLMapping")
			).put(
				"htmlElementName",
				ParamUtil.getString(portletRequest, "htmlElementName")
			).put(
				"instanceable",
				ParamUtil.getBoolean(portletRequest, "instanceable")
			).put(
				"portletCategoryName",
				ParamUtil.getString(portletRequest, "portletCategoryName")
			).put(
				"urls",
				StringUtil.merge(
					ParamUtil.getStringValues(portletRequest, "urls"),
					StringPool.NEW_LINE)
			).put(
				"useESM", ParamUtil.getBoolean(portletRequest, "useESM")
			).build());
	}

	public CustomElementCETImpl(
		String baseURL, long companyId, String description,
		String externalReferenceCode, String name, Properties properties,
		String sourceCodeURL, UnicodeProperties typeSettingsUnicodeProperties) {

		super(
			baseURL, companyId, description, externalReferenceCode, name,
			properties, sourceCodeURL, typeSettingsUnicodeProperties);
	}

	public CustomElementCETImpl(
		String baseURL, UnicodeProperties typeSettingsUnicodeProperties) {

		super(baseURL, typeSettingsUnicodeProperties);
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

	@Override
	protected boolean isURLCETPropertyName(String name) {
		return _urlCETPropertyNames.contains(name);
	}

	private static final Set<String> _urlCETPropertyNames =
		getURLCETPropertyNames(CustomElementCET.class);

}