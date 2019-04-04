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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.registry.util.StringPlus;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class PortletLayoutFinderRegistryUtil {

	public static PortletLayoutFinder getPortletLayoutFinder(String className) {
		List<PortletLayoutFinder> portletLayoutFinders =
			_serviceTrackerMap.getService(className);

		if (portletLayoutFinders == null) {
			return null;
		}

		return new CompositePortletLayoutFinder(portletLayoutFinders);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletLayoutFinderRegistryUtil.class);

	private static final ServiceTrackerMap<String, List<PortletLayoutFinder>>
		_serviceTrackerMap = ServiceTrackerCollections.openMultiValueMap(
			PortletLayoutFinder.class, "(model.class.name=*)",
			(serviceReference, emitter) -> {
				for (String modelClassName :
						StringPlus.asList(
							serviceReference.getProperty("model.class.name"))) {

					emitter.emit(modelClassName);
				}
			});

	private static class CompositePortletLayoutFinder
		implements PortletLayoutFinder {

		public CompositePortletLayoutFinder(
			Iterable<PortletLayoutFinder> portletLayoutFinders) {

			_portletLayoutFinders = portletLayoutFinders;
		}

		@Override
		public Result find(ThemeDisplay themeDisplay, long groupId) {
			try {
				for (PortletLayoutFinder portletLayoutFinder :
						_portletLayoutFinders) {

					Result result = portletLayoutFinder.find(
						themeDisplay, groupId);

					if (result != null) {
						return result;
					}
				}
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}
			}

			return null;
		}

		private final Iterable<PortletLayoutFinder> _portletLayoutFinders;

	}

}