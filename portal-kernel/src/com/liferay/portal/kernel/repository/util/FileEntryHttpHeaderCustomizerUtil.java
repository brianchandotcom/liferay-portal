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

package com.liferay.portal.kernel.repository.util;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.repository.http.header.customizer.FileEntryHttpHeaderCustomizer;
import com.liferay.portal.kernel.repository.model.FileEntry;

/**
 * @author Adolfo Pérez
 */
public class FileEntryHttpHeaderCustomizerUtil {

	public static String getHttpHeaderValue(
		FileEntry fileEntry, String httpHeaderName, String currentValue) {

		FileEntryHttpHeaderCustomizer fileEntryHttpHeaderCustomizer =
			_serviceTrackerMap.getService(httpHeaderName);

		if (fileEntryHttpHeaderCustomizer == null) {
			return currentValue;
		}

		return fileEntryHttpHeaderCustomizer.getHttpHeaderValue(
			fileEntry, currentValue);
	}

	private static final ServiceTrackerMap
		<String, FileEntryHttpHeaderCustomizer> _serviceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				SystemBundleUtil.getBundleContext(),
				FileEntryHttpHeaderCustomizer.class, "http.header.name");

}