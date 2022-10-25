/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.asah.connector.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
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
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClientImpl;
import com.liferay.segments.asah.connector.internal.client.JSONWebServiceClient;
import com.liferay.segments.asah.connector.internal.client.model.util.ExperimentSettingsUtil;
import com.liferay.segments.asah.connector.internal.util.AsahUtil;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsExperimentLocalService;
import com.liferay.segments.service.SegmentsExperimentRelLocalService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
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

	@Activate
	protected void activate() {
		_asahFaroBackendClient = new AsahFaroBackendClientImpl(
			_jsonWebServiceClient);
	}

	@Deactivate
	protected void deactivate() {
		_asahFaroBackendClient = null;
	}

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
		catch (Throwable throwable) {
			_log.error(throwable, throwable);

			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(actionResponse);

			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			jsonObject = JSONUtil.put(
				"error",
				_language.get(
					themeDisplay.getRequest(), "an-unexpected-error-occurred"));
		}

		hideDefaultSuccessMessage(actionRequest);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private Long _calculateSegmentsExperimentEstimatedDaysDuration(
		double confidenceLevel, SegmentsExperiment segmentsExperiment,
		Map<String, Double> segmentsExperienceKeySplitMap) {

		if (!AsahUtil.isAnalyticsEnabled(segmentsExperiment.getCompanyId())) {
			return null;
		}

		return _asahFaroBackendClient.calculateExperimentEstimatedDaysDuration(
			segmentsExperiment.getCompanyId(),
			segmentsExperiment.getSegmentsExperimentKey(),
			ExperimentSettingsUtil.toExperimentSettings(
				confidenceLevel, segmentsExperienceKeySplitMap,
				segmentsExperiment));
	}

	private JSONObject
			_calculateSegmentsExperimentEstimatedDaysDurationJSONObject(
				ActionRequest actionRequest)
		throws PortalException {

		return JSONUtil.put(
			"segmentsExperimentEstimatedDaysDuration",
			() -> {
				long segmentsExperimentId = ParamUtil.getLong(
					actionRequest, "segmentsExperimentId");

				SegmentsExperiment segmentsExperiment =
					_segmentsExperimentLocalService.getSegmentsExperiment(
						segmentsExperimentId);

				String segmentsExperimentRels = ParamUtil.getString(
					actionRequest, "segmentsExperimentRels");

				JSONObject segmentsExperimentRelsJSONObject =
					JSONFactoryUtil.createJSONObject(segmentsExperimentRels);

				Iterator<String> iterator =
					segmentsExperimentRelsJSONObject.keys();

				Map<String, Double> segmentsExperienceKeySplitMap =
					new HashMap<>();

				while (iterator.hasNext()) {
					String key = iterator.next();

					SegmentsExperimentRel segmentsExperimentRel =
						_segmentsExperimentRelLocalService.
							getSegmentsExperimentRel(GetterUtil.getLong(key));

					segmentsExperienceKeySplitMap.put(
						segmentsExperimentRel.getSegmentsExperienceKey(),
						segmentsExperimentRelsJSONObject.getDouble(key));
				}

				return _calculateSegmentsExperimentEstimatedDaysDuration(
					ParamUtil.getDouble(actionRequest, "confidenceLevel"),
					segmentsExperiment, segmentsExperienceKeySplitMap);
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CalculateSegmentsExperimentEstimatedDurationMVCActionCommand.class);

	private AsahFaroBackendClient _asahFaroBackendClient;

	@Reference
	private JSONWebServiceClient _jsonWebServiceClient;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperimentLocalService _segmentsExperimentLocalService;

	@Reference
	private SegmentsExperimentRelLocalService
		_segmentsExperimentRelLocalService;

}