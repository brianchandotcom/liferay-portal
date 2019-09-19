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

package com.liferay.segments.asah.connector.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClient;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClientFactory;
import com.liferay.segments.asah.connector.internal.client.model.DXPVariantSettings;
import com.liferay.segments.asah.connector.internal.client.model.ExperimentSettings;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsExperimentLocalService;
import com.liferay.segments.service.SegmentsExperimentRelLocalService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS_EXPERIMENT,
		"mvc.command.name=/calculate_segments_experiment_estimated_duration"
	},
	service = MVCActionCommand.class
)
public class CalculateSegmentsExperimentEstimatedDurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject jsonObject = null;

		try {
			jsonObject =
				_calculateSegmentsExperimentEstimatedDaysDurationJSONObject(
					actionRequest);
		}
		catch (Throwable t) {
			_log.error(t, t);

			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(actionResponse);

			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			jsonObject = JSONUtil.put(
				"error",
				LanguageUtil.get(
					themeDisplay.getRequest(), "an-unexpected-error-occurred"));
		}

		hideDefaultSuccessMessage(actionRequest);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private Long _calculateSegmentsExperimentEstimatedDaysDuration(
		double confidenceLevel, SegmentsExperiment segmentsExperiment,
		Map<String, Double> segmentsExperienceKeySplitMap) {

		if (_asahFaroBackendClient == null) {
			Optional<AsahFaroBackendClient> asahFaroBackendClientOptional =
				_asahFaroBackendClientFactory.createAsahFaroBackendClient();

			if (!asahFaroBackendClientOptional.isPresent()) {
				return null;
			}

			_asahFaroBackendClient = asahFaroBackendClientOptional.get();
		}

		return _asahFaroBackendClient.calculateExperimentEstimatedDaysDuration(
			segmentsExperiment.getSegmentsExperimentKey(),
			_createExperimentSettings(
				confidenceLevel, segmentsExperienceKeySplitMap,
				segmentsExperiment));
	}

	private JSONObject
			_calculateSegmentsExperimentEstimatedDaysDurationJSONObject(
				ActionRequest actionRequest)
		throws PortalException {

		long segmentsExperimentId = ParamUtil.getLong(
			actionRequest, "segmentsExperimentId");

		SegmentsExperiment segmentsExperiment =
			_segmentsExperimentLocalService.getSegmentsExperiment(
				segmentsExperimentId);

		String segmentsExperimentRels = ParamUtil.getString(
			actionRequest, "segmentsExperimentRels");

		JSONObject segmentsExperimentRelsJSONObject =
			JSONFactoryUtil.createJSONObject(segmentsExperimentRels);

		Iterator<String> iterator = segmentsExperimentRelsJSONObject.keys();

		Map<String, Double> segmentsExperienceKeySplitMap = new HashMap<>();

		while (iterator.hasNext()) {
			String key = iterator.next();

			SegmentsExperimentRel segmentsExperimentRel =
				_segmentsExperimentRelLocalService.getSegmentsExperimentRel(
					GetterUtil.getLong(key));

			segmentsExperienceKeySplitMap.put(
				segmentsExperimentRel.getSegmentsExperienceKey(),
				segmentsExperimentRelsJSONObject.getDouble(key));
		}

		Long segmentsExperimentEstimatedDaysDuration =
			_calculateSegmentsExperimentEstimatedDaysDuration(
				ParamUtil.getDouble(actionRequest, "confidenceLevel"),
				segmentsExperiment, segmentsExperienceKeySplitMap);

		return JSONUtil.put(
			"segmentsExperimentEstimatedDaysDuration",
			segmentsExperimentEstimatedDaysDuration);
	}

	private DXPVariantSettings _createDXPVariantSettings(
		String controlSegmentsExperienceKey, String segmentsExperienceKey,
		Double split) {

		DXPVariantSettings dxpVariantSettings = new DXPVariantSettings();

		dxpVariantSettings.setControl(
			Objects.equals(
				controlSegmentsExperienceKey, segmentsExperienceKey));

		dxpVariantSettings.setTrafficSplit(split * 100);

		dxpVariantSettings.setDXPVariantId(segmentsExperienceKey);

		return dxpVariantSettings;
	}

	private ExperimentSettings _createExperimentSettings(
		double confidenceLevel,
		Map<String, Double> segmentsExperienceKeySplitMap,
		SegmentsExperiment segmentsExperiment) {

		ExperimentSettings experimentSettings = new ExperimentSettings();

		experimentSettings.setConfidenceLevel(confidenceLevel);

		List<DXPVariantSettings> dxpVariantsSettings = new ArrayList<>();

		segmentsExperienceKeySplitMap.forEach(
			(segmentsExperienceKey, split) -> dxpVariantsSettings.add(
				_createDXPVariantSettings(
					segmentsExperiment.getSegmentsExperienceKey(),
					segmentsExperienceKey, split)));
		experimentSettings.setDXPVariantsSettings(dxpVariantsSettings);

		return experimentSettings;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CalculateSegmentsExperimentEstimatedDurationMVCActionCommand.class);

	private AsahFaroBackendClient _asahFaroBackendClient;

	@Reference
	private AsahFaroBackendClientFactory _asahFaroBackendClientFactory;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperimentLocalService _segmentsExperimentLocalService;

	@Reference
	private SegmentsExperimentRelLocalService
		_segmentsExperimentRelLocalService;

}