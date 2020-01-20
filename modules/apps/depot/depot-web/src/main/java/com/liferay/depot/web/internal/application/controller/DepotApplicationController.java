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

package com.liferay.depot.web.internal.application.controller;

import com.liferay.depot.application.DepotApplication;
import com.liferay.depot.model.DepotAppCustomization;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotAppCustomizationLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.ArrayList;
import java.util.Collection;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = DepotApplicationController.class)
public class DepotApplicationController {

	public Collection<DepotApplication> getCustomizableDepotApplications() {
		Collection<DepotApplication> depotApplications = new ArrayList<>();

		for (DepotApplication depotApplication : _serviceTrackerMap.values()) {
			if (depotApplication.isCustomizable()) {
				depotApplications.add(depotApplication);
			}
		}

		return depotApplications;
	}

	public boolean isEnabled(String portletId, long groupId) {
		DepotEntry depotEntry = _depotEntryLocalService.fetchGroupDepotEntry(
			groupId);

		if (depotEntry == null) {
			return false;
		}

		DepotApplication depotApplication = _serviceTrackerMap.getService(
			portletId);

		if (depotApplication == null) {
			return false;
		}

		if (!depotApplication.isCustomizable()) {
			return true;
		}

		DepotAppCustomization depotApplicationCustomization =
			_depotAppCustomizationLocalService.fetchDepotAppCustomization(
				depotEntry.getDepotEntryId(), depotApplication.getPortletId());

		if (depotApplicationCustomization == null) {
			return true;
		}

		return depotApplicationCustomization.isEnabled();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DepotApplication.class, null,
			(serviceReference, emitter) -> {
				DepotApplication depotApplication = bundleContext.getService(
					serviceReference);

				emitter.emit(depotApplication.getPortletId());

				bundleContext.ungetService(serviceReference);
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Reference
	private DepotAppCustomizationLocalService
		_depotAppCustomizationLocalService;

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	private ServiceTrackerMap<String, DepotApplication> _serviceTrackerMap;

}