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
import com.liferay.client.extension.type.CETCustomElement;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.util.Properties;

import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class CETCustomElementImpl
	extends BaseCETImpl implements CETCustomElement {

	public CETCustomElementImpl(ClientExtensionEntry clientExtensionEntry) {
		super(clientExtensionEntry);
	}

	public CETCustomElementImpl(PortletRequest portletRequest) {
		this(
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

	public CETCustomElementImpl(
		String baseURL, long companyId, String description,
		String externalReferenceCode, String name, Properties properties,
		String sourceCodeURL, UnicodeProperties typeSettingsUnicodeProperties) {

		super(
			baseURL, companyId, description, externalReferenceCode, name,
			properties, sourceCodeURL, typeSettingsUnicodeProperties);
	}

	public CETCustomElementImpl(
		UnicodeProperties typeSettingsUnicodeProperties) {

		super(typeSettingsUnicodeProperties);
	}

	public String getCSSURLs() {
		return getString("cssURLs");
	}

	@Override
	public String getEditJSP() {
		return "/admin/edit_custom_element.jsp";
	}

	public String getFriendlyURLMapping() {
		return getString("friendlyURLMapping");
	}

	public String getHTMLElementName() {
		return getString("htmlElementName");
	}

	public String getPortletCategoryName() {
		return getString("portletCategoryName");
	}

	@Override
	public String getType() {
		return ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT;
	}

	public String getURLs() {
		return getString("urls");
	}

	public boolean isInstanceable() {
		return getBoolean("instanceable");
	}

	public boolean isUseESM() {
		return getBoolean("useESM");
	}

}