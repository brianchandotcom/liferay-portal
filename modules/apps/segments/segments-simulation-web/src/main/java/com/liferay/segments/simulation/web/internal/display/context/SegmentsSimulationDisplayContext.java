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

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.configuration.provider.SegmentsConfigurationProvider;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryServiceUtil;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo García
 */
public class SegmentsSimulationDisplayContext {

	public SegmentsSimulationDisplayContext(
		HttpServletRequest httpServletRequest,
		SegmentsConfigurationProvider segmentsConfigurationProvider) {

		RenderResponse renderResponse =
			(RenderResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		_liferayPortletResponse = PortalUtil.getLiferayPortletResponse(
			renderResponse);

		_segmentsConfigurationProvider = segmentsConfigurationProvider;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public PortletURL getDeactivateSimulationURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse, SegmentsPortletKeys.SEGMENTS_SIMULATION
		).setActionName(
			"/segments_simulation/deactivate_simulation"
		).buildPortletURL();
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
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse, SegmentsPortletKeys.SEGMENTS_SIMULATION
		).setActionName(
			"/segments_simulation/simulate_segments_entries"
		).buildPortletURL();
	}

	public boolean isSegmentationEnabled() {
		try {
			return _segmentsConfigurationProvider.isSegmentationEnabled(
				_themeDisplay.getCompanyId());
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
		}

		return false;
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

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsSimulationDisplayContext.class);

	private Long _groupId;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final SegmentsConfigurationProvider _segmentsConfigurationProvider;
	private List<SegmentsEntry> _segmentsEntries;
	private Boolean _showEmptyMessage;
	private final ThemeDisplay _themeDisplay;

}