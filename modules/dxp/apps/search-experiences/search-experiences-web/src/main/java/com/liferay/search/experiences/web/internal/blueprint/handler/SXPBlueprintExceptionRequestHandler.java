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

package com.liferay.search.experiences.web.internal.blueprint.handler;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.search.experiences.exception.SXPBlueprintConfigurationsJSONException;
import com.liferay.search.experiences.exception.SXPElementElementDefinitionJSONException;
import com.liferay.search.experiences.problem.Problem;

import java.io.IOException;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kevin Tan
 */
@Component(
	immediate = true, service = SXPBlueprintExceptionRequestHandler.class
)
public class SXPBlueprintExceptionRequestHandler {

	public void handlePortalException(
		ActionRequest actionRequest, ActionResponse actionResponse,
		PortalException portalException) {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		if (portalException instanceof
				SXPBlueprintConfigurationsJSONException) {

			SXPBlueprintConfigurationsJSONException
				sxpBlueprintConfigurationsJSONException =
					(SXPBlueprintConfigurationsJSONException)portalException;

			_addProblems(
				jsonArray,
				sxpBlueprintConfigurationsJSONException.getProblems(),
				themeDisplay);
		}
		else if (portalException instanceof
					SXPElementElementDefinitionJSONException) {

			SXPElementElementDefinitionJSONException
				sxpElementElementDefinitionJSONException =
					(SXPElementElementDefinitionJSONException)portalException;

			_addProblems(
				jsonArray,
				sxpElementElementDefinitionJSONException.getProblems(),
				themeDisplay);
		}

		if (portalException.getCause() instanceof JSONException) {
			jsonArray.put(
				_language.get(
					themeDisplay.getRequest(),
					"core.error.unable-to-parse-json"));
		}
		else {
			jsonArray.put(
				_language.get(
					themeDisplay.getRequest(), "an-unexpected-error-occurred"));
		}

		JSONObject jsonObject = JSONUtil.put("error", jsonArray);

		try {
			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
		catch (IOException ioException) {
			_log.error(ioException.getMessage(), ioException);
		}
	}

	private void _addProblems(
		JSONArray jsonArray, List<Problem> problems,
		ThemeDisplay themeDisplay) {

		problems.forEach(
			problem -> jsonArray.put(
				_language.get(
					themeDisplay.getRequest(), problem.getLanguageKey())));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintExceptionRequestHandler.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}