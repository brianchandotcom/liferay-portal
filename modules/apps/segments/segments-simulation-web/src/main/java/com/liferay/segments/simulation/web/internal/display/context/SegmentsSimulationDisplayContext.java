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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.configuration.provider.SegmentsConfigurationProvider;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryServiceUtil;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.util.List;
import java.util.Map;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo García
 */
public class SegmentsSimulationDisplayContext {

	public SegmentsSimulationDisplayContext(
		HttpServletRequest httpServletRequest,
		SegmentsConfigurationProvider segmentsConfigurationProvider) {

		_httpServletRequest = httpServletRequest;
		_segmentsConfigurationProvider = segmentsConfigurationProvider;

		RenderResponse renderResponse =
			(RenderResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		_liferayPortletResponse = PortalUtil.getLiferayPortletResponse(
			renderResponse);

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"deactivateSimulationURL", getDeactivateSimulationURL()
		).put(
			"namespace", getPortletNamespace()
		).put(
			"segmentationEnabled", isSegmentationEnabled()
		).put(
			"segmentsCompanyConfigurationURL",
			getSegmentsCompanyConfigurationURL()
		).put(
			"segmentsEntries", _getSegmentsEntriesJSONArray()
		).put(
			"segmentsExperiences", _getSegmentsExperiencesJSONArray()
		).put(
			"showEmptyMessage", isShowEmptyMessage()
		).put(
			"simulateSegmentsEntriesURL", getSimulateSegmentsEntriesURL()
		).build();
	}

	public String getDeactivateSimulationURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse, SegmentsPortletKeys.SEGMENTS_SIMULATION
		).setActionName(
			"/segments_simulation/deactivate_simulation"
		).buildString();
	}

	public String getPortletNamespace() {
		return PortalUtil.getPortletNamespace(
			SegmentsPortletKeys.SEGMENTS_SIMULATION);
	}

	public String getSegmentsCompanyConfigurationURL() {
		try {
			return _segmentsConfigurationProvider.getCompanyConfigurationURL(
				_httpServletRequest);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return StringPool.BLANK;
	}

	public List<SegmentsEntry> getSegmentsEntries() {
		if (_segmentsEntries != null) {
			return _segmentsEntries;
		}

		_segmentsEntries = SegmentsEntryServiceUtil.getSegmentsEntries(
			_getStagingAwareGroupId(), true);

		return _segmentsEntries;
	}

	public String getSimulateSegmentsEntriesURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse, SegmentsPortletKeys.SEGMENTS_SIMULATION
		).setActionName(
			"/segments_simulation/simulate_segments_entries"
		).buildString();
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

	private JSONArray _getSegmentsEntriesJSONArray() throws Exception {
		if (_segmentsEntriesJSONArray != null) {
			return _segmentsEntriesJSONArray;
		}

		_segmentsEntriesJSONArray = JSONUtil.toJSONArray(
			getSegmentsEntries(),
			segmentsEntry -> JSONUtil.put(
				"id", segmentsEntry.getSegmentsEntryId()
			).put(
				"name", segmentsEntry.getName(_themeDisplay.getLocale())
			));

		return _segmentsEntriesJSONArray;
	}

	private JSONArray _getSegmentsExperiencesJSONArray() throws Exception {
		if (_segmentsExperiencesJSONArray != null) {
			return _segmentsExperiencesJSONArray;
		}

		_segmentsExperiencesJSONArray = JSONUtil.toJSONArray(
			SegmentsExperienceLocalServiceUtil.getSegmentsExperiences(
				_themeDisplay.getScopeGroupId(), _themeDisplay.getPlid()),
			segmentsExperience -> JSONUtil.put(
				"id", segmentsExperience.getSegmentsExperienceId()
			).put(
				"name", segmentsExperience.getName(_themeDisplay.getLocale())
			));

		return _segmentsExperiencesJSONArray;
	}

	private long _getStagingAwareGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		_groupId = stagingGroupHelper.getStagedPortletGroupId(
			_themeDisplay.getScopeGroupId(), SegmentsPortletKeys.SEGMENTS);

		return _groupId;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsSimulationDisplayContext.class);

	private Long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final SegmentsConfigurationProvider _segmentsConfigurationProvider;
	private List<SegmentsEntry> _segmentsEntries;
	private JSONArray _segmentsEntriesJSONArray;
	private JSONArray _segmentsExperiencesJSONArray;
	private Boolean _showEmptyMessage;
	private final ThemeDisplay _themeDisplay;

}