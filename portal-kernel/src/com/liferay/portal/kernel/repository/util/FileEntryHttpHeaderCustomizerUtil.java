/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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