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
import com.liferay.client.extension.type.CETIFrame;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.util.Properties;

import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class CETIFrameImpl extends BaseCETImpl implements CETIFrame {

	public CETIFrameImpl(ClientExtensionEntry clientExtensionEntry) {
		super(clientExtensionEntry);
	}

	public CETIFrameImpl(PortletRequest portletRequest) {
		this(
			UnicodePropertiesBuilder.create(
				true
			).put(
				"friendlyURLMapping",
				ParamUtil.getString(portletRequest, "friendlyURLMapping")
			).put(
				"instanceable",
				ParamUtil.getBoolean(portletRequest, "instanceable")
			).put(
				"portletCategoryName",
				ParamUtil.getString(portletRequest, "portletCategoryName")
			).put(
				"url", ParamUtil.getString(portletRequest, "url")
			).build());
	}

	public CETIFrameImpl(
		String baseURL, long companyId, String description,
		String externalReferenceCode, String name, Properties properties,
		String sourceCodeURL, UnicodeProperties typeSettingsUnicodeProperties) {

		super(
			baseURL, companyId, description, externalReferenceCode, name,
			properties, sourceCodeURL, typeSettingsUnicodeProperties);
	}

	public CETIFrameImpl(UnicodeProperties typeSettingsUnicodeProperties) {
		super(typeSettingsUnicodeProperties);
	}

	@Override
	public String getEditJSP() {
		return "/admin/edit_iframe.jsp";
	}

	public String getFriendlyURLMapping() {
		return getString("friendlyURLMapping");
	}

	public String getPortletCategoryName() {
		return getString("portletCategoryName");
	}

	@Override
	public String getType() {
		return ClientExtensionEntryConstants.TYPE_IFRAME;
	}

	public String getURL() {
		return getString("url");
	}

	public boolean isInstanceable() {
		return getBoolean("instanceable");
	}

}