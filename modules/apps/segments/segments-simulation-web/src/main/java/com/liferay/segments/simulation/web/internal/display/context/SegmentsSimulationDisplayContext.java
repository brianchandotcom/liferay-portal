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

package com.liferay.segments.simulation.web.internal.display.context;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryServiceUtil;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo García
 */
public class SegmentsSimulationDisplayContext {

	public SegmentsSimulationDisplayContext(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse) {

		_liferayPortletResponse = PortalUtil.getLiferayPortletResponse(
			renderResponse);

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public PortletURL getDeactivateSimulationURL() {
		PortletURL portletURL = _liferayPortletResponse.createActionURL(
			SegmentsPortletKeys.SEGMENTS_SIMULATION);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "deactivateSimulation");

		return portletURL;
	}

	public String getPortletNamespace() {
		return PortalUtil.getPortletNamespace(
			SegmentsPortletKeys.SEGMENTS_SIMULATION);
	}

	public List<SegmentsEntry> getSegmentsEntries() {
		if (_segmentsEntries != null) {
			return _segmentsEntries;
		}

		_segmentsEntries = SegmentsEntryServiceUtil.getSegmentsEntries(
			_getStagingAwareGroupId(), true);

		return _segmentsEntries;
	}

	public PortletURL getSimulateSegmentsEntriesURL() {
		PortletURL portletURL = _liferayPortletResponse.createActionURL(
			SegmentsPortletKeys.SEGMENTS_SIMULATION);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "simulateSegmentsEntries");

		return portletURL;
	}

	public boolean isShowEmptyMessage() {
		if (_showEmptyMessage != null) {
			return _showEmptyMessage;
		}

		List<SegmentsEntry> segmentsEntries = getSegmentsEntries();

		_showEmptyMessage = segmentsEntries.isEmpty();

		return _showEmptyMessage;
	}

	private long _getStagingAwareGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		long groupId = _themeDisplay.getScopeGroupId();

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		if (stagingGroupHelper.isStagingGroup(groupId) &&
			!stagingGroupHelper.isStagedPortlet(
				groupId, SegmentsPortletKeys.SEGMENTS)) {

			Group group = stagingGroupHelper.fetchLiveGroup(groupId);

			if (group != null) {
				groupId = group.getGroupId();
			}
		}

		_groupId = groupId;

		return groupId;
	}

	private Long _groupId;
	private final LiferayPortletResponse _liferayPortletResponse;
	private List<SegmentsEntry> _segmentsEntries;
	private Boolean _showEmptyMessage;
	private final ThemeDisplay _themeDisplay;

}