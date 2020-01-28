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

package com.liferay.segments.experiment.web.internal.portlet.action;

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
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.experiment.web.internal.util.SegmentsExperimentUtil;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsExperimentRelLocalService;
import com.liferay.segments.service.SegmentsExperimentService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS_EXPERIMENT,
		"mvc.command.name=/segments_experiment/run_segments_experiment"
	},
	service = MVCActionCommand.class
)
public class RunSegmentsExperimentMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject jsonObject = null;

		try {
			jsonObject = _runSegmentsExperiment(actionRequest);
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

	private JSONObject _runSegmentsExperiment(ActionRequest actionRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long segmentsExperimentId = ParamUtil.getLong(
			actionRequest, "segmentsExperimentId");

		String segmentsExperimentRels = ParamUtil.getString(
			actionRequest, "segmentsExperimentRels");

		JSONObject segmentsExperimentRelsJSONObject =
			JSONFactoryUtil.createJSONObject(segmentsExperimentRels);

		Iterator<String> iterator = segmentsExperimentRelsJSONObject.keys();

		Map<Long, Double> segmentsExperienceIdSplitMap = new HashMap<>();

		while (iterator.hasNext()) {
			String key = iterator.next();

			SegmentsExperimentRel segmentsExperimentRel =
				_segmentsExperimentRelLocalService.getSegmentsExperimentRel(
					GetterUtil.getLong(key));

			segmentsExperienceIdSplitMap.put(
				segmentsExperimentRel.getSegmentsExperienceId(),
				segmentsExperimentRelsJSONObject.getDouble(key));
		}

		SegmentsExperiment segmentsExperiment =
			_segmentsExperimentService.runSegmentsExperiment(
				segmentsExperimentId,
				ParamUtil.getDouble(actionRequest, "confidenceLevel"),
				segmentsExperienceIdSplitMap);

		return JSONUtil.put(
			"segmentsExperiment",
			SegmentsExperimentUtil.toSegmentsExperimentJSONObject(
				themeDisplay.getLocale(), segmentsExperiment)
		).put(
			"segmentsExperimentRels", segmentsExperimentRelsJSONObject
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RunSegmentsExperimentMVCActionCommand.class);

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperimentRelLocalService
		_segmentsExperimentRelLocalService;

	@Reference
	private SegmentsExperimentService _segmentsExperimentService;

}