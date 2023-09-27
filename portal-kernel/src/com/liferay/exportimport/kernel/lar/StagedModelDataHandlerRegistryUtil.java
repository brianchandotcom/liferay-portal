/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lar;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;

/**
 * Provides a utility facade to the staged model data handler registry
 * framework.
 *
 * @author Máté Thurzó
 * @author Brian Wing Shun Chan
 * @since  6.2
 */
public class StagedModelDataHandlerRegistryUtil {

	/**
	 * Returns the registered staged model data handler with the class name.
	 *
	 * @param  className the name of the staged model class
	 * @return the registered staged model data handler with the class name, or
	 *         <code>null</code> if none are registered with the class name
	 */
	public static StagedModelDataHandler<?> getStagedModelDataHandler(
		String className) {

		return _stagedModelDataHandlers.getService(className);
	}

	/**
	 * Returns all the registered staged model data handlers.
	 *
	 * @return the registered staged model data handlers
	 */
	public static List<StagedModelDataHandler<?>> getStagedModelDataHandlers() {
		return new ArrayList<>(_stagedModelDataHandlers.values());
	}

	private StagedModelDataHandlerRegistryUtil() {
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	private static final ServiceTrackerMap<String, StagedModelDataHandler<?>>
		_stagedModelDataHandlers = ServiceTrackerMapFactory.openSingleValueMap(
			_bundleContext,
			(Class<StagedModelDataHandler<?>>)
				(Class<?>)StagedModelDataHandler.class,
			null,
			(serviceReference, emitter) -> {
				StagedModelDataHandler<?> stagedModelDataHandler =
					_bundleContext.getService(serviceReference);

				for (String className :
						stagedModelDataHandler.getClassNames()) {

					emitter.emit(className);
				}

				_bundleContext.ungetService(serviceReference);
			});

}