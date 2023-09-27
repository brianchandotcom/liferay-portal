/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.trash;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;

/**
 * @author Alexander Chow
 */
public class TrashHandlerRegistryUtil {

	public static TrashHandler getTrashHandler(String className) {
		return _trashHandlers.getService(className);
	}

	public static List<TrashHandler> getTrashHandlers() {
		return new ArrayList<>(_trashHandlers.values());
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	private static final ServiceTrackerMap<String, TrashHandler>
		_trashHandlers = ServiceTrackerMapFactory.openSingleValueMap(
			_bundleContext, TrashHandler.class, null,
			(serviceReference, emitter) -> {
				TrashHandler trashHandler = _bundleContext.getService(
					serviceReference);

				emitter.emit(trashHandler.getClassName());

				_bundleContext.ungetService(serviceReference);
			});

}