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

package com.liferay.portal.search.tuning.blueprints.admin.web.internal.display.context;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterDefinition;
import com.liferay.portal.search.tuning.blueprints.engine.util.BlueprintsEngineContextHelper;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintService;

import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Kevin Tan
 */
public class EditElementDisplayBuilder extends EditEntryDisplayBuilder {

	public EditElementDisplayBuilder(
		BlueprintsEngineContextHelper blueprintsEngineContextHelper,
		BlueprintService blueprintService,
		HttpServletRequest httpServletRequest, Language language,
		JSONFactory jsonFactory, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		super(
			blueprintService, httpServletRequest, language, jsonFactory,
			renderRequest, renderResponse);

		_blueprintsEngineContextHelper = blueprintsEngineContextHelper;
	}

	public BlueprintDisplayContext build() {
		BlueprintDisplayContext blueprintDisplayContext =
			new BlueprintDisplayContext();

		setConfigurationId(blueprintDisplayContext);
		setConfigurationType(blueprintDisplayContext);
		setData(blueprintDisplayContext, _getProps());
		setPageTitle(blueprintDisplayContext);
		setRedirect(blueprintDisplayContext);

		return blueprintDisplayContext;
	}

	private JSONArray _getPredefinedVariablesJSONArray() {
		JSONArray predefinedVariablesJSONArray = jsonFactory.createJSONArray();

		Map<String, List<ParameterDefinition>> contributedParameterDefinitions =
			_blueprintsEngineContextHelper.getContributedParameterDefinitions();

		for (Map.Entry<String, List<ParameterDefinition>> entry :
				contributedParameterDefinitions.entrySet()) {

			JSONObject jsonObject = jsonFactory.createJSONObject();

			jsonObject.put(
				"categoryName",
				language.get(httpServletRequest, entry.getKey()));

			JSONArray parameterDefinitionsJSONArray =
				jsonFactory.createJSONArray();

			for (ParameterDefinition parameterDefinition : entry.getValue()) {
				JSONObject parameterDefinitionJSONObject =
					jsonFactory.createJSONObject();

				parameterDefinitionJSONObject.put(
					"className", parameterDefinition.getClassName()
				).put(
					"description",
					language.get(
						httpServletRequest,
						parameterDefinition.getDescriptionKey())
				).put(
					"variable", parameterDefinition.getVariable()
				);

				parameterDefinitionsJSONArray.put(
					parameterDefinitionJSONObject);
			}

			jsonObject.put(
				"parameterDefinitions", parameterDefinitionsJSONArray);

			predefinedVariablesJSONArray.put(jsonObject);
		}

		return predefinedVariablesJSONArray;
	}

	private Map<String, Object> _getProps() {
		Map<String, Object> props = HashMapBuilder.<String, Object>put(
			"blueprintId", blueprintId
		).put(
			"blueprintType", blueprintType
		).put(
			"redirectURL", getRedirect()
		).put(
			"submitFormURL",
			getSubmitFormURL(BlueprintsAdminMVCCommandNames.EDIT_ELEMENT)
		).build();

		if (blueprint != null) {
			props.put(
				"initialConfigurationString", blueprint.getConfiguration());
			props.put("initialDescription", getDescriptionJSONObject());
			props.put("initialTitle", getTitleJSONObject());
			props.put(
				"predefinedVariables", _getPredefinedVariablesJSONArray());
		}

		return props;
	}

	private final BlueprintsEngineContextHelper _blueprintsEngineContextHelper;

}