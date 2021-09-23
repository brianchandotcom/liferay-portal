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

package com.liferay.search.experiences.web.internal.blueprint.portlet.action.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.search.experiences.problem.Problem;

import java.util.List;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = ProblemToJSONTranslator.class)
public class ProblemToJSONTranslator {

	public String translate(
		List<Problem> problems, ResourceBundle resourceBundle) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (Problem problem : problems) {
			jsonArray.put(translate(problem, resourceBundle));
		}

		jsonObject.put("errors", jsonArray);

		return jsonObject.toString();
	}

	public JSONObject translate(
		Problem problem, ResourceBundle resourceBundle) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			"className", problem.getClassName()
		).put(
			"elementKey", problem.getElementKey()
		).put(
			"localizedMessage",
			_language.get(resourceBundle, problem.getLanguageKey())
		).put(
			"message", problem.getMessage()
		).put(
			"rootConfiguration", problem.getRootConfiguration()
		).put(
			"rootObject", problem.getRootObject()
		).put(
			"rootProperty", problem.getRootProperty()
		).put(
			"rootValue", problem.getRootValue()
		).put(
			"severity", problem.getSeverity()
		).put(
			"throwable", problem.getThrowable()
		);

		return jsonObject;
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}