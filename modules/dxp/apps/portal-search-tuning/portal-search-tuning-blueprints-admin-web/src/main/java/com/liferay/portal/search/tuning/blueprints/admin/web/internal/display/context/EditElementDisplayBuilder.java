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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.util.BlueprintsAdminRequestUtil;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterDefinition;
import com.liferay.portal.search.tuning.blueprints.engine.util.BlueprintsEngineContextHelper;
import com.liferay.portal.search.tuning.blueprints.model.Element;
import com.liferay.portal.search.tuning.blueprints.service.ElementService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Kevin Tan
 * @author Petteri Karttunen
 */
public class EditElementDisplayBuilder extends EditEntryDisplayBuilder {

	public EditElementDisplayBuilder(
		RenderRequest renderRequest, RenderResponse renderResponse,
		BlueprintsEngineContextHelper blueprintsEngineContextHelper,
		ElementService elementService, JSONFactory jsonFactory,
		Language language) {

		super(
			renderRequest, renderResponse, elementService, jsonFactory,
			language);

		_blueprintsEngineContextHelper = blueprintsEngineContextHelper;

		_element = _getElement();

		_elementId = BlueprintsAdminRequestUtil.getElementId(renderRequest);
	}

	public EntryDisplayContext build() {
		EntryDisplayContext entryDisplayContext = new EntryDisplayContext();

		entryDisplayContext.setId(_elementId);

		_setType(entryDisplayContext);
		setData(entryDisplayContext, _getProps());
		_setPageTitle(entryDisplayContext);
		setRedirect(entryDisplayContext);

		return entryDisplayContext;
	}

	private Element _getElement() {
		Optional<Element> optional = BlueprintsAdminRequestUtil.getElement(
			renderRequest, renderResponse);

		return optional.orElse(null);
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
			"elementId", _elementId
		).put(
			"redirectURL", getRedirect()
		).put(
			"submitFormURL",
			getSubmitFormURL(
				BlueprintsAdminMVCCommandNames.EDIT_ELEMENT, _element != null)
		).put(
			"type", _getType()
		).build();

		if (_element != null) {
			props.put(
				"initialConfigurationString", _element.getConfiguration());
			props.put(
				"initialDescription",
				getDescriptionJSONObject(_element.getDescriptionMap()));
			props.put(
				"initialTitle", getTitleJSONObject(_element.getTitleMap()));
			props.put(
				"predefinedVariables", _getPredefinedVariablesJSONArray());
		}

		return props;
	}

	private int _getType() {
		if (_element != null) {
			return _element.getType();
		}

		return BlueprintsAdminRequestUtil.getElementType(renderRequest);
	}

	private void _setPageTitle(EntryDisplayContext entryDisplayContext) {
		StringBundler sb = new StringBundler(2);

		sb.append((_element != null) ? "edit-" : "add-");
		sb.append("element");

		entryDisplayContext.setPageTitle(
			language.get(httpServletRequest, sb.toString()));
	}

	private void _setType(EntryDisplayContext entryDisplayContext) {
		entryDisplayContext.setType(_getType());
	}

	private final BlueprintsEngineContextHelper _blueprintsEngineContextHelper;
	private final Element _element;
	private final long _elementId;

}