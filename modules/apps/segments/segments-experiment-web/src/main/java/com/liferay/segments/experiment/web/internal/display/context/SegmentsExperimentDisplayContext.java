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

package com.liferay.segments.experiment.web.internal.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperienceService;
import com.liferay.segments.service.SegmentsExperimentService;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo García
 */
public class SegmentsExperimentDisplayContext {

	public SegmentsExperimentDisplayContext(
		HttpServletRequest httpServletRequest,
		SegmentsExperienceService segmentsExperienceService,
		SegmentsExperimentService segmentsExperimentService) {

		_httpServletRequest = httpServletRequest;
		_segmentsExperienceService = segmentsExperienceService;
		_segmentsExperimentService = segmentsExperimentService;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public JSONArray getSegmentsExperiencesJSONArray(Locale locale)
		throws PortalException {

		List<SegmentsExperience> segmentsExperiences =
			_segmentsExperienceService.getSegmentsExperiences(
				_themeDisplay.getScopeGroupId(),
				PortalUtil.getClassNameId(Layout.class),
				_themeDisplay.getPlid(), true);

		JSONArray segmentsExperiencesJSONArray =
			JSONFactoryUtil.createJSONArray();

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			Optional<SegmentsExperiment> segmentsExperimentOptional =
				_getDraftSegmentsExperimentOptional(
					segmentsExperience.getSegmentsExperienceId());

			segmentsExperiencesJSONArray.put(
				JSONUtil.put(
					"name", segmentsExperience.getName(locale)
				).put(
					"segmentsExperienceId",
					String.valueOf(segmentsExperience.getSegmentsExperienceId())
				).put(
					"segmentsExperiment",
					_getSegmentsExperimentJSONObject(segmentsExperimentOptional)
				));
		}

		Optional<SegmentsExperiment> segmentsExperimentOptional =
			_getDraftSegmentsExperimentOptional(
				SegmentsExperienceConstants.ID_DEFAULT);

		segmentsExperiencesJSONArray.put(
			JSONUtil.put(
				"name",
				SegmentsExperienceConstants.getDefaultSegmentsExperienceName(
					locale)
			).put(
				"segmentsExperienceId",
				String.valueOf(SegmentsExperienceConstants.ID_DEFAULT)
			).put(
				"segmentsExperiment",
				_getSegmentsExperimentJSONObject(segmentsExperimentOptional)
			));

		return segmentsExperiencesJSONArray;
	}

	public JSONObject getSegmentsExperimentJSONObject() throws PortalException {
		Optional<SegmentsExperiment> segmentsExperimentOptional =
			_getDraftSegmentsExperimentOptional(
				getSelectedSegmentsExperienceId());

		return _getSegmentsExperimentJSONObject(segmentsExperimentOptional);
	}

	public long getSelectedSegmentsExperienceId() {
		if (Validator.isNotNull(_segmentsExperienceId)) {
			return _segmentsExperienceId;
		}

		HttpServletRequest originalHttpServletRequest =
			PortalUtil.getOriginalServletRequest(_httpServletRequest);

		_segmentsExperienceId = ParamUtil.getLong(
			originalHttpServletRequest, "segmentsExperienceId",
			SegmentsExperienceConstants.ID_DEFAULT);

		return _segmentsExperienceId;
	}

	private Optional<SegmentsExperiment> _getDraftSegmentsExperimentOptional(
			long segmentsExperienceId)
		throws PortalException {

		List<SegmentsExperiment> segmentsExperienceSegmentsExperiments =
			_segmentsExperimentService.getSegmentsExperienceSegmentsExperiments(
				segmentsExperienceId, PortalUtil.getClassNameId(Layout.class),
				_themeDisplay.getPlid(),
				SegmentsExperimentConstants.STATUS_DRAFT);

		Stream<SegmentsExperiment> segmentsExperienceSegmentsExperimentsStream =
			segmentsExperienceSegmentsExperiments.stream();

		return segmentsExperienceSegmentsExperimentsStream.findFirst();
	}

	private JSONObject _getSegmentsExperimentJSONObject(
		Optional<SegmentsExperiment> segmentsExperimentOptional) {

		if (!segmentsExperimentOptional.isPresent()) {
			return null;
		}

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentOptional.get();

		return JSONUtil.put(
			"description", segmentsExperiment.getDescription()
		).put(
			"name", segmentsExperiment.getName()
		).put(
			"segmentsExperienceId",
			String.valueOf(segmentsExperiment.getSegmentsExperienceId())
		).put(
			"segmentsExperimentId",
			String.valueOf(segmentsExperiment.getSegmentsExperimentId())
		);
	}

	private final HttpServletRequest _httpServletRequest;
	private Long _segmentsExperienceId;
	private final SegmentsExperienceService _segmentsExperienceService;
	private final SegmentsExperimentService _segmentsExperimentService;
	private final ThemeDisplay _themeDisplay;

}