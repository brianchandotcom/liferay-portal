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

import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsWebKeys;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.model.SegmentsExperimentRelTable;
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
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public JSONArray getSegmentsExperiencesJSONArray() throws PortalException {
		JSONArray segmentsExperiencesJSONArray =
			JSONFactoryUtil.createJSONArray();

		boolean addedDefault = false;

		List<SegmentsExperience> segmentsExperiences =
			SegmentsExperienceLocalServiceUtil.getSegmentsExperiences(
				_themeDisplay.getScopeGroupId(),
				PortalUtil.getClassNameId(Layout.class.getName()),
				_themeDisplay.getPlid(), true);

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			if ((segmentsExperience.getPriority() <
					SegmentsExperienceConstants.PRIORITY_DEFAULT) &&
				!addedDefault) {

				segmentsExperiencesJSONArray.put(
					_getDefaultSegmentsExperienceJSONObject());

				addedDefault = true;
			}

			segmentsExperiencesJSONArray.put(
				_getSegmentsExperienceJSONObject(segmentsExperience));
		}

		if (!addedDefault) {
			segmentsExperiencesJSONArray.put(
				_getDefaultSegmentsExperienceJSONObject());
		}

		_calculateActiveSegmentsExperiencesJSONArray(
			segmentsExperiencesJSONArray);

		return segmentsExperiencesJSONArray;
	}

	public String getSelectedSegmentsExperienceName() {
		long segmentsExperienceId = ParamUtil.getLong(
			_httpServletRequest, "segmentsExperienceId", -1);

		if (segmentsExperienceId == -1) {
			long[] segmentsExperienceIds = GetterUtil.getLongValues(
				_httpServletRequest.getAttribute(
					SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS),
				new long[] {SegmentsExperienceConstants.ID_DEFAULT});

			if (segmentsExperienceIds.length > 0) {
				segmentsExperienceId = segmentsExperienceIds[0];
			}
		}

		SegmentsExperience segmentsExperience =
			SegmentsExperienceLocalServiceUtil.fetchSegmentsExperience(
				segmentsExperienceId);

		if (segmentsExperience == null) {
			return SegmentsExperienceConstants.getDefaultSegmentsExperienceName(
				_themeDisplay.getLocale());
		}

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

			JSONObject firstSegmentsExperienceJSONObject =
				_getFirstSegmentsExperienceJSONObject(
					segmentsExperiencesJSONObject.getLong("segmentsEntryId"),
					segmentsExperiencesJSONArray);

			long firstSegmentsExperienceId =
				firstSegmentsExperienceJSONObject.getLong(
					"segmentsExperienceId");

			if (firstSegmentsExperienceId ==
					segmentsExperiencesJSONObject.getLong(
						"segmentsExperienceId")) {

				segmentsExperiencesJSONObject.put("active", true);

				break;
			}
		}
	}

	private JSONObject _getDefaultSegmentsExperienceJSONObject() {
		return JSONUtil.put(
			"segmentsEntryId", SegmentsEntryConstants.ID_DEFAULT
		).put(
			"segmentsEntryName",
			SegmentsEntryConstants.getDefaultSegmentsEntryName(
				_themeDisplay.getLocale())
		).put(
			"segmentsExperienceId", SegmentsExperienceConstants.ID_DEFAULT
		).put(
			"segmentsExperienceName",
			SegmentsExperienceConstants.getDefaultSegmentsExperienceName(
				_themeDisplay.getLocale())
		).put(
			"url",
			HttpUtil.setParameter(
				PortalUtil.getCurrentURL(_httpServletRequest),
				"segmentsExperienceId", SegmentsExperienceConstants.ID_DEFAULT)
		);
	}

	private JSONObject _getFirstSegmentsExperienceJSONObject(
		long segmentsEntryId, JSONArray segmentsExperiencesJSONArray) {

		JSONObject firstSegmentsExperienceJSONObject =
			JSONFactoryUtil.createJSONObject();

		for (int i = 0; i < segmentsExperiencesJSONArray.length(); i++) {
			JSONObject segmentsExperiencesJSONObject =
				segmentsExperiencesJSONArray.getJSONObject(i);

			if ((segmentsExperiencesJSONObject.getLong("segmentsEntryId") ==
					segmentsEntryId) ||
				(segmentsExperiencesJSONObject.getLong("segmentsEntryId") ==
					SegmentsEntryConstants.ID_DEFAULT)) {

				firstSegmentsExperienceJSONObject =
					segmentsExperiencesJSONObject;

				break;
			}
		}

		return firstSegmentsExperienceJSONObject;
	}

	private SegmentsExperience _getParentSegmentExperience(
		SegmentsExperience segmentsExperience) {

		List<SegmentsExperimentRel> segmentsExperimentRels =
			SegmentsExperimentRelLocalServiceUtil.dslQuery(
				DSLQueryFactoryUtil.select(
					SegmentsExperimentRelTable.INSTANCE
				).from(
					SegmentsExperimentRelTable.INSTANCE
				).where(
					SegmentsExperimentRelTable.INSTANCE.segmentsExperienceId.eq(
						segmentsExperience.getSegmentsExperienceId())
				));

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
			HttpUtil.setParameter(
				PortalUtil.getCurrentURL(_httpServletRequest),
				"segmentsExperienceId",
				segmentsExperience.getSegmentsExperienceId())
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsExperienceSelectorDisplayContext.class);

	private final HttpServletRequest _httpServletRequest;
	private final ThemeDisplay _themeDisplay;

}