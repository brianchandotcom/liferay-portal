/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.web.internal.util;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.source.provider.SegmentsSourceDetailsProvider;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alejandro Tardín
 */
@Component(service = {})
public class SegmentsSourceDetailsProviderUtil {

	public static SegmentsSourceDetailsProvider
		getSegmentsSourceDetailsProvider(SegmentsEntry segmentsEntry) {

		SegmentsSourceDetailsProvider segmentsSourceDetailsProvider =
			_serviceTrackerMap.getService(segmentsEntry.getSource());

		if (segmentsSourceDetailsProvider != null) {
			return segmentsSourceDetailsProvider;
		}

		return _serviceTrackerMap.getService(
			SegmentsEntryConstants.SOURCE_DEFAULT);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, SegmentsSourceDetailsProvider.class,
			"segments.source");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static ServiceTrackerMap<String, SegmentsSourceDetailsProvider>
		_serviceTrackerMap;

}