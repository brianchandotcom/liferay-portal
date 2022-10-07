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

package com.liferay.dynamic.data.mapping.internal.background.task;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.DDMStructureIndexer;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Igor Fabiano Nazar
 * @author Lucas Marques de Paula
 */
@Component(service = DDMStructureIndexerTracker.class)
public class DDMStructureIndexerTracker {

	public DDMStructureIndexer getDDMStructureIndexer(String className)
		throws PortalException {

		DDMStructureIndexer ddmStructureIndexer = _serviceTrackerMap.getService(
			className);

		if (ddmStructureIndexer == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No dynamic data mapping structure indexer exists for " +
						className);
			}

			return null;
		}

		return ddmStructureIndexer;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DDMStructureIndexer.class,
			"ddm.structure.indexer.class.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMStructureIndexerTracker.class);

	private ServiceTrackerMap<String, DDMStructureIndexer> _serviceTrackerMap;

}