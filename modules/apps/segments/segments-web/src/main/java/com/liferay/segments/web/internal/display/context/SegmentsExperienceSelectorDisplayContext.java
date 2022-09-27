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

package com.liferay.segments.web.internal.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.manager.SegmentsExperienceManager;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsEntryLocalServiceUtil;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;
import com.liferay.segments.service.SegmentsExperimentLocalServiceUtil;
import com.liferay.segments.service.SegmentsExperimentRelLocalServiceUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SegmentsExperienceSelectorDisplayContext {

	public SegmentsExperienceSelectorDisplayContext(
		HttpServletRequest httpServletRequest,
		SegmentsExperienceManager segmentsExperienceManager) {

		_httpServletRequest = httpServletRequest;
		_segmentsExperienceManager = segmentsExperienceManager;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public JSONArray getSegmentsExperiencesJSONArray() throws PortalException {
		JSONArray segmentsExperiencesJSONArray =
			JSONFactoryUtil.createJSONArray();

		List<SegmentsExperience> segmentsExperiences =
			SegmentsExperienceLocalServiceUtil.getSegmentsExperiences(
				_themeDisplay.getScopeGroupId(),
				PortalUtil.getClassNameId(Layout.class.getName()),
				_themeDisplay.getPlid(), true);

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			segmentsExperiencesJSONArray.put(
				_getSegmentsExperienceJSONObject(segmentsExperience));
		}

		_calculateActiveSegmentsExperiencesJSONArray(
			segmentsExperiencesJSONArray);

		return segmentsExperiencesJSONArray;
	}

	public String getSelectedSegmentsExperienceName() {
		long segmentsExperienceId = ParamUtil.getLong(
			_httpServletRequest, "segmentsExperienceId", -1);

		if (segmentsExperienceId == -1) {
			segmentsExperienceId =
				_segmentsExperienceManager.getSegmentsExperienceId(
					_httpServletRequest);
		}

		SegmentsExperience segmentsExperience =
			SegmentsExperienceLocalServiceUtil.fetchSegmentsExperience(
				segmentsExperienceId);

		SegmentsExperience parentSegmentsExperience =
			_getParentSegmentExperience(segmentsExperience);

		if (parentSegmentsExperience != null) {
			segmentsExperience = parentSegmentsExperience;
		}

		return segmentsExperience.getName(_themeDisplay.getLocale());
	}

	private void _calculateActiveSegmentsExperiencesJSONArray(
		JSONArray segmentsExperiencesJSONArray) {

		for (int i = 0; i < segmentsExperiencesJSONArray.length(); i++) {
			JSONObject segmentsExperiencesJSONObject =
				segmentsExperiencesJSONArray.getJSONObject(i);

			long firstSegmentsExperienceId = _getFirstSegmentsExperienceId(
				segmentsExperiencesJSONObject.getLong("segmentsEntryId"),
				segmentsExperiencesJSONArray);

			if (firstSegmentsExperienceId ==
					segmentsExperiencesJSONObject.getLong(
						"segmentsExperienceId")) {

				segmentsExperiencesJSONObject.put("active", true);
			}
		}
	}

	private long _getFirstSegmentsExperienceId(
		long segmentsEntryId, JSONArray segmentsExperiencesJSONArray) {

		for (int i = 0; i < segmentsExperiencesJSONArray.length(); i++) {
			JSONObject segmentsExperiencesJSONObject =
				segmentsExperiencesJSONArray.getJSONObject(i);

			if ((segmentsExperiencesJSONObject.getLong("segmentsEntryId") ==
					segmentsEntryId) ||
				(segmentsExperiencesJSONObject.getLong("segmentsEntryId") ==
					SegmentsEntryConstants.ID_DEFAULT)) {

				return segmentsExperiencesJSONObject.getLong(
					"segmentsExperienceId");
			}
		}

		return 0;
	}

	private SegmentsExperience _getParentSegmentExperience(
		SegmentsExperience segmentsExperience) {

		List<SegmentsExperimentRel> segmentsExperimentRels =
			SegmentsExperimentRelLocalServiceUtil.
				getSegmentsExperimentRelsBySegmentsExperienceId(
					segmentsExperience.getSegmentsExperienceId());

		if (segmentsExperimentRels.isEmpty()) {
			return null;
		}

		SegmentsExperimentRel segmentsExperimentRel =
			segmentsExperimentRels.get(0);

		try {
			SegmentsExperiment segmentsExperiment =
				SegmentsExperimentLocalServiceUtil.getSegmentsExperiment(
					segmentsExperimentRel.getSegmentsExperimentId());

			return SegmentsExperienceLocalServiceUtil.getSegmentsExperience(
				segmentsExperiment.getSegmentsExperienceId());
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return null;
	}

	private JSONObject _getSegmentsExperienceJSONObject(
		SegmentsExperience segmentsExperience) {

		return JSONUtil.put(
			"segmentsEntryId", segmentsExperience.getSegmentsEntryId()
		).put(
			"segmentsEntryName",
			() -> {
				SegmentsEntry segmentsEntry =
					SegmentsEntryLocalServiceUtil.fetchSegmentsEntry(
						segmentsExperience.getSegmentsEntryId());

				if (segmentsEntry != null) {
					return segmentsEntry.getName(_themeDisplay.getLocale());
				}

				return SegmentsEntryConstants.getDefaultSegmentsEntryName(
					_themeDisplay.getLocale());
			}
		).put(
			"segmentsExperienceId", segmentsExperience.getSegmentsExperienceId()
		).put(
			"segmentsExperienceName",
			segmentsExperience.getName(_themeDisplay.getLocale())
		).put(
			"url",
			HttpComponentsUtil.setParameter(
				PortalUtil.getCurrentURL(_httpServletRequest),
				"segmentsExperienceId",
				segmentsExperience.getSegmentsExperienceId())
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsExperienceSelectorDisplayContext.class);

	private final HttpServletRequest _httpServletRequest;
	private final SegmentsExperienceManager _segmentsExperienceManager;
	private final ThemeDisplay _themeDisplay;

}