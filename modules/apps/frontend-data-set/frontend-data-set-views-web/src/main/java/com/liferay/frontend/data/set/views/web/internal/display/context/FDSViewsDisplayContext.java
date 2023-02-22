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

package com.liferay.frontend.data.set.views.web.internal.display.context;

import com.liferay.frontend.data.set.views.web.internal.resource.FDSHeadlessResource;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Comparator;
import java.util.List;

import javax.portlet.PortletRequest;

/**
 * @author Marko Cikos
 */
public class FDSViewsDisplayContext {

	public FDSViewsDisplayContext(
		PortletRequest portletRequest,
		ServiceTrackerList<FDSHeadlessResource> serviceTrackerList) {

		_portletRequest = portletRequest;
		_serviceTrackerList = serviceTrackerList;
	}

	public String getFDSEntriesAPIURL() {
		return "/o/c/fdsentries/";
	}

	public JSONArray getHeadlessResourcesJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<FDSHeadlessResource> fdsHeadlessResources =
			_serviceTrackerList.toList();

		fdsHeadlessResources.sort(
			Comparator.comparing(FDSHeadlessResource::getBundleLabel));

		fdsHeadlessResources.sort(
			Comparator.comparing(FDSHeadlessResource::getName));

		for (FDSHeadlessResource fdsHeadlessResource : fdsHeadlessResources) {
			jsonArray.put(
				JSONUtil.put(
					"bundleLabel", fdsHeadlessResource.getBundleLabel()
				).put(
					"entityClassName", fdsHeadlessResource.getEntityClassName()
				).put(
					"name", fdsHeadlessResource.getName()
				).put(
					"version", fdsHeadlessResource.getVersion()
				));
		}

		return jsonArray;
	}

	private final PortletRequest _portletRequest;
	private final ServiceTrackerList<FDSHeadlessResource> _serviceTrackerList;

}