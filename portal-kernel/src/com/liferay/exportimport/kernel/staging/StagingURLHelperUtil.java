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

package com.liferay.exportimport.kernel.staging;

import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Akos Thurzo
 */
public class StagingURLHelperUtil {

	public static String buildRemoteURL(
		ExportImportConfiguration exportImportConfiguration) {

		return _stagingURLHelper.buildRemoteURL(exportImportConfiguration);
	}

	public static String buildRemoteURL(
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection) {

		return _stagingURLHelper.buildRemoteURL(
			remoteAddress, remotePort, remotePathContext, secureConnection);
	}

	public static String buildRemoteURL(
		UnicodeProperties typeSettingsProperties) {

		return _stagingURLHelper.buildRemoteURL(typeSettingsProperties);
	}

	private static volatile StagingURLHelper _stagingURLHelper =
		ServiceProxyFactory.newServiceTrackedInstance(
			StagingURLHelper.class, StagingURLHelperUtil.class,
			"_stagingURLHelper", false);

}