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

package com.liferay.exportimport.internal.staging;

import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.staging.StagingURLHelper;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Akos Thurzo
 */
@Component(immediate = true, service = StagingURLHelper.class)
public class StagingURLHelperImpl implements StagingURLHelper {

	@Override
	public String buildRemoteURL(
		ExportImportConfiguration exportImportConfiguration) {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		String remoteAddress = MapUtil.getString(settingsMap, "remoteAddress");
		int remotePort = MapUtil.getInteger(settingsMap, "remotePort");
		String remotePathContext = MapUtil.getString(
			settingsMap, "remotePathContext");
		boolean secureConnection = MapUtil.getBoolean(
			settingsMap, "secureConnection");

		return buildRemoteURL(
			remoteAddress, remotePort, remotePathContext, secureConnection);
	}

	@Override
	public String buildRemoteURL(
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection) {

		StringBundler sb = new StringBundler(5);

		if (secureConnection) {
			sb.append(Http.HTTPS_WITH_SLASH);
		}
		else {
			sb.append(Http.HTTP_WITH_SLASH);
		}

		sb.append(remoteAddress);

		if (remotePort > 0) {
			sb.append(StringPool.COLON);
			sb.append(remotePort);
		}

		if (Validator.isNotNull(remotePathContext)) {
			sb.append(remotePathContext);
		}

		return sb.toString();
	}

	@Override
	public String buildRemoteURL(UnicodeProperties typeSettingsProperties) {
		String remoteAddress = typeSettingsProperties.getProperty(
			"remoteAddress");
		int remotePort = GetterUtil.getInteger(
			typeSettingsProperties.getProperty("remotePort"));
		String remotePathContext = typeSettingsProperties.getProperty(
			"remotePathContext");
		boolean secureConnection = GetterUtil.getBoolean(
			typeSettingsProperties.getProperty("secureConnection"));

		return buildRemoteURL(
			remoteAddress, remotePort, remotePathContext, secureConnection);
	}

}