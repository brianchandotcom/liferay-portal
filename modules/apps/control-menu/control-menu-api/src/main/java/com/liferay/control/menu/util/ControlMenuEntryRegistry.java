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

package com.liferay.control.menu.util;

import com.liferay.control.menu.ControlMenuCategory;
import com.liferay.control.menu.ControlMenuEntry;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PredicateFilter;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Julio Camarero
 */
@Component(immediate = true, service = ControlMenuEntryRegistry.class)
public class ControlMenuEntryRegistry {

	public List<ControlMenuEntry> getControlMenuEntries(
		ControlMenuCategory controlMenuCategory) {

		List<ControlMenuEntry> controlMenuEntries =
			_serviceTrackerMap.getService(controlMenuCategory.getKey());

		if (controlMenuEntries == null) {
			return Collections.emptyList();
		}

		return controlMenuEntries;
	}

	public List<ControlMenuEntry> getControlMenuEntries(
		ControlMenuCategory controlMenuCategory,
		final HttpServletRequest request) {

		List<ControlMenuEntry> controlMenuEntries = getControlMenuEntries(
			controlMenuCategory);

		if (controlMenuEntries.isEmpty()) {
			return controlMenuEntries;
		}

		return ListUtil.filter(
			controlMenuEntries,
			new PredicateFilter<ControlMenuEntry>() {

				@Override
				public boolean filter(ControlMenuEntry controlMenuEntry) {
					try {
						return controlMenuEntry.isShow(request);
					}
					catch (PortalException pe) {
						_log.error(pe, pe);
					}

					return false;
				}

			});
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, ControlMenuEntry.class,
			"(control.menu.category.key=*)",
			new ControlMenuEntryServiceReferenceMapper(),
			new ServiceRankingPropertyServiceReferenceComparator());
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ControlMenuEntryRegistry.class);

	private ServiceTrackerMap<String, List<ControlMenuEntry>>
		_serviceTrackerMap;

	private static class ServiceRankingPropertyServiceReferenceComparator
		extends PropertyServiceReferenceComparator<ControlMenuEntry> {

		public ServiceRankingPropertyServiceReferenceComparator() {
			super("service.ranking");
		}

		@Override
		public int compare(
			ServiceReference<ControlMenuEntry> serviceReference1,
			ServiceReference<ControlMenuEntry> serviceReference2) {

			return -(super.compare(serviceReference1, serviceReference2));
		}

	}

}