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

		// TODO Remove globalJS* prefix

		this(
			UnicodePropertiesBuilder.create(
				false
			).put(
				"url", ParamUtil.getString(portletRequest, "globalJSURL")
			).buildString());
	}

	public CETGlobalJSImpl(String typeSettings) {
		super(typeSettings);
	}

	public CETGlobalJSImpl(
		String baseURL, long companyId, String description, String name,
		String primaryKey, Properties properties, String sourceCodeURL,
		String typeSettings) {

		super(
			baseURL, companyId, description, name, primaryKey, properties,
			sourceCodeURL, typeSettings);
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