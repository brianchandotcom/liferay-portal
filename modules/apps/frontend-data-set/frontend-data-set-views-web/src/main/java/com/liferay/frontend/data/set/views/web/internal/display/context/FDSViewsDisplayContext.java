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
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceURL;

/**
 * @author Marko Cikos
 */
public class FDSViewsDisplayContext {

	public FDSViewsDisplayContext(
		PortletRequest portletRequest,
		ServiceTrackerList<String> serviceTrackerList) {

		_portletRequest = portletRequest;
		_serviceTrackerList = serviceTrackerList;
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

	public JSONArray getRESTApplicationsJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<String> restApplications = _serviceTrackerList.toList();

		Collections.sort(restApplications);

		for (String restApplication : restApplications) {
			jsonArray.put(restApplication);
		}

		return jsonArray;
	}

	public String getSaveFDSFieldsURL() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceURL resourceURL =
			(ResourceURL)PortalUtil.getControlPanelPortletURL(
				_portletRequest, themeDisplay.getScopeGroup(),
				FDSViewsPortletKeys.FDS_VIEWS, 0, 0,
				PortletRequest.RESOURCE_PHASE);

		resourceURL.setResourceID("/frontend_data_set_views/save_fds_fields");

		return resourceURL.toString();
	}

	private final PortletRequest _portletRequest;
	private final ServiceTrackerList<String> _serviceTrackerList;

}