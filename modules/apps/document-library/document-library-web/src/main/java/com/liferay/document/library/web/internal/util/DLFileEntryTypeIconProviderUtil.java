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

package com.liferay.document.library.web.internal.util;

import com.liferay.document.library.icon.provider.DLFileEntryTypeIconProvider;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alejandro Tardín
 */
@Component(service = {})
public class DLFileEntryTypeIconProviderUtil {

	public static String getIcon(DLFileEntryType fileEntryType) {
		DLFileEntryTypeIconProvider dlFileEntryTypeIconProvider =
			_serviceTrackerMap.getService(fileEntryType.getFileEntryTypeKey());

		if (dlFileEntryTypeIconProvider != null) {
			return dlFileEntryTypeIconProvider.getIcon();
		}

		return null;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DLFileEntryTypeIconProvider.class,
			"file.entry.type.key");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static ServiceTrackerMap<String, DLFileEntryTypeIconProvider>
		_serviceTrackerMap;

}