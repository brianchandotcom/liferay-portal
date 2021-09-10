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

package com.liferay.portal.kernel.servlet.taglib;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Carlos Sierra Andrés
 * @author Raymond Augé
 */
public class DynamicIncludeUtil {

	public static List<DynamicInclude> getDynamicIncludes(String key) {
		return _dynamicIncludes.getService(key);
	}

	public static boolean hasDynamicInclude(String key) {
		if (ListUtil.isEmpty(getDynamicIncludes(key))) {
			return false;
		}

		return true;
	}

	public static void include(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String key,
		boolean ascendingPriority) {

		List<DynamicInclude> dynamicIncludes = getDynamicIncludes(key);

		if (ListUtil.isEmpty(dynamicIncludes)) {
			return;
		}

		Iterator<DynamicInclude> iterator = null;

		if (ascendingPriority) {
			iterator = dynamicIncludes.iterator();
		}
		else {
			iterator = ListUtil.reverseIterator(dynamicIncludes);
		}

		while (iterator.hasNext()) {
			DynamicInclude dynamicInclude = iterator.next();

			try {
				dynamicInclude.include(
					httpServletRequest, httpServletResponse, key);
			}
			catch (Exception exception) {
				_log.error(exception, exception);
			}
		}
	}

	private DynamicIncludeUtil() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DynamicIncludeUtil.class);

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	private static final ServiceTrackerMap<String, List<DynamicInclude>>
		_dynamicIncludes = ServiceTrackerMapFactory.openMultiValueMap(
			_bundleContext, DynamicInclude.class, null,
			new ServiceReferenceMapper<String, DynamicInclude>() {

				@Override
				public void map(
					ServiceReference<DynamicInclude> serviceReference,
					final Emitter<String> emitter) {

					DynamicInclude dynamicInclude = _bundleContext.getService(
						serviceReference);

					dynamicInclude.register(
						new DynamicInclude.DynamicIncludeRegistry() {

							@Override
							public void register(String key) {
								emitter.emit(key);
							}

						});

					_bundleContext.ungetService(serviceReference);
				}

			});

}