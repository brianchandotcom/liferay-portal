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

import com.liferay.frontend.data.set.views.web.internal.constants.FDSViewsPortletKeys;
import com.liferay.frontend.data.set.views.web.internal.resource.FDSHeadlessResource;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.StringUtil;

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
		return "/o/c/fdsentries";
	}

	public String getFDSEntriesURL() {
		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_portletRequest, FDSViewsPortletKeys.FDS_VIEWS,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/fds_entries.jsp"
		).buildString();
	}

	public String getFDSViewsAPIURL() {
		return "/o/c/fdsviews";
	}

	public String getFDSViewsURL() {
		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_portletRequest, FDSViewsPortletKeys.FDS_VIEWS,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/fds_views.jsp"
		).buildString();
	}

	public String getFDSViewsURL(String fdsEntryId, String fdsEntryLabel) {
		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_portletRequest, FDSViewsPortletKeys.FDS_VIEWS,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/fds_views.jsp"
		).setParameter(
			"fdsEntryId", fdsEntryId
		).setParameter(
			"fdsEntryLabel", fdsEntryLabel
		).buildString();
	}

	public String getFDSViewURL() {
		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_portletRequest, FDSViewsPortletKeys.FDS_VIEWS,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/fds_view.jsp"
		).buildString();
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
					"name", _getName(fdsHeadlessResource)
				).put(
					"version", fdsHeadlessResource.getVersion()
				));
		}

		return jsonArray;
	}

	private String _getName(FDSHeadlessResource fdsHeadlessResource) {
		String name = fdsHeadlessResource.getName();

		if (name.startsWith("ObjectEntry")) {
			String[] nameParts = StringUtil.split(name, "#C_");

			if (nameParts.length == 2) {
				return nameParts[1];
			}
		}

		return name;
	}

	private final PortletRequest _portletRequest;
	private final ServiceTrackerList<FDSHeadlessResource> _serviceTrackerList;

}