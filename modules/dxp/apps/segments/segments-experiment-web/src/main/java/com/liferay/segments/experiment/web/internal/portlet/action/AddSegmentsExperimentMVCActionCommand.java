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
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.experiment.web.internal.util.SegmentsExperimentUtil;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsExperimentRelService;
import com.liferay.segments.service.SegmentsExperimentService;

import java.util.concurrent.Callable;

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
		"mvc.command.name=/segments_experiment/add_segments_experiment"
	},
	service = MVCActionCommand.class
)
public class AddSegmentsExperimentMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Callable<JSONObject> callable = new AddSegmentsExperimentCallable(
			actionRequest);

		JSONObject jsonObject = null;

		try {
			jsonObject = TransactionInvokerUtil.invoke(
				_transactionConfig, callable);
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

	private JSONObject _addSegmentsExperiment(ActionRequest actionRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		SegmentsExperiment segmentsExperiment =
			_segmentsExperimentService.addSegmentsExperiment(
				ParamUtil.getLong(actionRequest, "segmentsExperienceId"),
				ParamUtil.getLong(actionRequest, "classNameId"),
				ParamUtil.getLong(actionRequest, "classPK"),
				ParamUtil.getString(actionRequest, "name"),
				ParamUtil.getString(actionRequest, "description"),
				ParamUtil.getString(actionRequest, "goal"),
				ParamUtil.getString(actionRequest, "goalTarget"),
				ServiceContextFactory.getInstance(actionRequest));

		JSONObject jsonObject = JSONUtil.put(
			"segmentsExperiment",
			SegmentsExperimentUtil.toSegmentsExperimentJSONObject(
				themeDisplay.getLocale(), segmentsExperiment));

		SegmentsExperimentRel segmentsExperimentRel =
			_segmentsExperimentRelService.getSegmentsExperimentRel(
				segmentsExperiment.getSegmentsExperimentId(),
				segmentsExperiment.getSegmentsExperienceId());

		jsonObject.put(
			"segmentsExperimentRel",
			SegmentsExperimentUtil.toSegmentsExperimentRelJSONObject(
				themeDisplay.getLocale(), segmentsExperimentRel));

		return jsonObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddSegmentsExperimentMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperimentRelService _segmentsExperimentRelService;

	@Reference
	private SegmentsExperimentService _segmentsExperimentService;

	private class AddSegmentsExperimentCallable
		implements Callable<JSONObject> {

		@Override
		public JSONObject call() throws Exception {
			return _addSegmentsExperiment(_actionRequest);
		}

		private AddSegmentsExperimentCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}