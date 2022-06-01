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
import com.liferay.client.extension.type.CETGlobalCSS;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.util.Properties;

import javax.portlet.PortletRequest;

/**
 * @author Eudaldo Alonso
 */
public class CETGlobalCSSImpl extends BaseCETImpl implements CETGlobalCSS {

	public CETGlobalCSSImpl(ClientExtensionEntry clientExtensionEntry) {
		super(clientExtensionEntry);
	}

	public CETGlobalCSSImpl(PortletRequest portletRequest) {

		// TODO Remove globalCSS* prefix

		this(
			UnicodePropertiesBuilder.create(
				false
			).put(
				"url", ParamUtil.getString(portletRequest, "globalCSSURL")
			).buildString());
	}

	public CETGlobalCSSImpl(String typeSettings) {
		super(typeSettings);
	}

	public CETGlobalCSSImpl(
		String baseURL, long companyId, String description, String name,
		String primaryKey, Properties properties, String sourceCodeURL,
		String typeSettings) {

		super(
			baseURL, companyId, description, name, primaryKey, properties,
			sourceCodeURL, typeSettings);
	}

	@Override
	public String getType() {
		return ClientExtensionEntryConstants.TYPE_GLOBAL_CSS;
	}

	@Override
	public String getURL() {
		return getString("url");
	}

}