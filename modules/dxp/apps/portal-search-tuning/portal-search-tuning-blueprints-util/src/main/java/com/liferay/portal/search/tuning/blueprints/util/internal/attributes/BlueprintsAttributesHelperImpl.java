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

package com.liferay.portal.search.tuning.blueprints.util.internal.attributes;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributesBuilder;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributesBuilderFactory;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.parameter.CustomParameterConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.parameter.ParameterConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.constants.ReservedParameterNames;
import com.liferay.portal.search.tuning.blueprints.facets.constants.FacetConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.facets.constants.FacetsBlueprintContributorKeys;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintService;
import com.liferay.portal.search.tuning.blueprints.util.BlueprintHelper;
import com.liferay.portal.search.tuning.blueprints.util.attributes.BlueprintsAttributesHelper;

import java.util.Optional;
import java.util.TimeZone;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = BlueprintsAttributesHelper.class)
public class BlueprintsAttributesHelperImpl
	implements BlueprintsAttributesHelper {

	@Override
	public BlueprintsAttributesBuilder getBlueprintsRequestAttributesBuilder(
		PortletRequest portletRequest, Blueprint blueprint) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			portletRequest);

		TimeZone timeZone = themeDisplay.getTimeZone();

		BlueprintsAttributesBuilder blueprintsAttributesBuilder =
			_blueprintsAttributesBuilderFactory.builder();

		blueprintsAttributesBuilder.companyId(
			themeDisplay.getCompanyId()
		).locale(
			themeDisplay.getLocale()
		).userId(
			themeDisplay.getUserId()
		).addAttribute(
			ReservedParameterNames.IP_ADDRESS.getKey(),
			httpServletRequest.getRemoteAddr()
		).addAttribute(
			ReservedParameterNames.PLID.getKey(), themeDisplay.getPlid()
		).addAttribute(
			ReservedParameterNames.SCOPE_GROUP_ID.getKey(),
			themeDisplay.getScopeGroupId()
		).addAttribute(
			ReservedParameterNames.TIMEZONE_ID.getKey(), timeZone.getID()
		);

		return _addRequestParameters(
			portletRequest, blueprint, blueprintsAttributesBuilder);
	}

	@Override
	public BlueprintsAttributesBuilder getBlueprintsResponseAttributesBuilder(
		PortletRequest portletRequest, PortletResponse portletResponse,
		Blueprint blueprint, BlueprintsAttributes blueprintsRequestAttributes) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		BlueprintsAttributesBuilder blueprintsAttributesBuilder =
			_blueprintsAttributesBuilderFactory.builder();

		blueprintsAttributesBuilder.companyId(
			themeDisplay.getCompanyId()
		).keywords(
			blueprintsRequestAttributes.getKeywords()
		).locale(
			themeDisplay.getLocale()
		).userId(
			themeDisplay.getUserId()
		).addAttribute(
			"portletRequest", portletRequest
		).addAttribute(
			"portletResponse", portletResponse
		);

		_addShowingInsteadOf(
			blueprintsAttributesBuilder, blueprintsRequestAttributes);

		return blueprintsAttributesBuilder;
	}

	private void _addCustomParameters(
		PortletRequest portletRequest, Blueprint blueprint,
		BlueprintsAttributesBuilder blueprintsAttributesBuilder) {

		Optional<JSONObject> configurationJSONObjectOptional =
			_blueprintHelper.getParameterConfigurationOptional(blueprint);

		if (!configurationJSONObjectOptional.isPresent()) {
			return;
		}

		JSONObject configurationJSONObject =
			configurationJSONObjectOptional.get();

		JSONArray jsonArray = configurationJSONObject.getJSONArray(
			ParameterConfigurationKeys.CUSTOM.getJsonKey());

		if ((jsonArray == null) || (jsonArray.length() == 0)) {
			return;
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String key = jsonObject.getString(
				CustomParameterConfigurationKeys.PARAMETER_NAME.getJsonKey());
			String type = jsonObject.getString(
				CustomParameterConfigurationKeys.TYPE.getJsonKey());

			if (_isArrayValue(type)) {
				_addStringValues(
					portletRequest, blueprintsAttributesBuilder, key);
			}
			else {
				_addStringValue(
					portletRequest, blueprintsAttributesBuilder, key);
			}
		}
	}

	private void _addFacetParameters(
		PortletRequest portletRequest,
		BlueprintsAttributesBuilder blueprintsAttributesBuilder,
		Blueprint blueprint) {

		Optional<JSONArray> configurationJSONArrayOptional =
			_blueprintHelper.getJSONArrayConfigurationOptional(
				blueprint,
				"JSONArray/" +
					FacetsBlueprintContributorKeys.CONFIGURATION_SECTION);

		if (!configurationJSONArrayOptional.isPresent()) {
			return;
		}

		JSONArray configurationJSONArray = configurationJSONArrayOptional.get();

		for (int i = 0; i < configurationJSONArray.length(); i++) {
			JSONObject jsonObject = configurationJSONArray.getJSONObject(i);

			String key = jsonObject.getString(
				FacetConfigurationKeys.PARAMETER_NAME.getJsonKey());

			boolean arrayValue = jsonObject.getBoolean(
				FacetConfigurationKeys.MULTI_VALUE.getJsonKey(), true);

			if (arrayValue) {
				_addStringValues(
					portletRequest, blueprintsAttributesBuilder, key);
			}
			else {
				_addStringValue(
					portletRequest, blueprintsAttributesBuilder, key);
			}
		}
	}

	private void _addIntValue(
		PortletRequest portletRequest,
		BlueprintsAttributesBuilder blueprintsAttributesBuilder, String key) {

		int size = ParamUtil.getInteger(portletRequest, key);

		if (size > 0) {
			blueprintsAttributesBuilder.addAttribute(key, size);
		}
	}

	private void _addKeywordParameter(
		PortletRequest portletRequest, Blueprint blueprint,
		BlueprintsAttributesBuilder blueprintsAttributesBuilder) {

		String parameterName = _blueprintHelper.getKeywordsParameterName(
			blueprint);

		String keywords = ParamUtil.getString(portletRequest, parameterName);

		if (Validator.isBlank(keywords)) {
			return;
		}

		blueprintsAttributesBuilder.keywords(keywords);
	}

	private void _addPagingParameter(
		PortletRequest portletRequest, Blueprint blueprint,
		BlueprintsAttributesBuilder blueprintsAttributesBuilder) {

		String parameterName = _blueprintHelper.getPageParameterName(blueprint);

		_addStringValue(
			portletRequest, blueprintsAttributesBuilder, parameterName);
	}

	private BlueprintsAttributesBuilder _addRequestParameters(
		PortletRequest portletRequest, Blueprint blueprint,
		BlueprintsAttributesBuilder blueprintsAttributesBuilder) {

		_addKeywordParameter(
			portletRequest, blueprint, blueprintsAttributesBuilder);

		_addPagingParameter(
			portletRequest, blueprint, blueprintsAttributesBuilder);

		_addSizeParameter(
			portletRequest, blueprint, blueprintsAttributesBuilder);

		_addSortParameters(
			portletRequest, blueprint, blueprintsAttributesBuilder);

		_addCustomParameters(
			portletRequest, blueprint, blueprintsAttributesBuilder);

		_addFacetParameters(
			portletRequest, blueprintsAttributesBuilder, blueprint);

		return blueprintsAttributesBuilder;
	}

	private void _addShowingInsteadOf(
		BlueprintsAttributesBuilder blueprintsAttributesBuilder,
		BlueprintsAttributes blueprintsRequestAttributes) {

		Optional<Object> showingInsteadOfOptional =
			blueprintsRequestAttributes.getAttributeOptional(
				ReservedParameterNames.SHOWING_INSTEAD_OF.getKey());

		if (showingInsteadOfOptional.isPresent()) {
			blueprintsAttributesBuilder.addAttribute(
				ReservedParameterNames.SHOWING_INSTEAD_OF.getKey(),
				GetterUtil.getString(showingInsteadOfOptional.get()));
		}
	}

	private void _addSizeParameter(
		PortletRequest portletRequest, Blueprint blueprint,
		BlueprintsAttributesBuilder blueprintsAttributesBuilder) {

		String parameterName = _blueprintHelper.getSizeParameterName(blueprint);

		_addIntValue(
			portletRequest, blueprintsAttributesBuilder, parameterName);
	}

	private void _addSortParameters(
		PortletRequest portletRequest, Blueprint blueprint,
		BlueprintsAttributesBuilder blueprintsAttributesBuilder) {

		Optional<JSONArray> jsonArrayOptional =
			_blueprintHelper.getSortParameterConfigurationOptional(blueprint);

		if (!jsonArrayOptional.isPresent()) {
			return;
		}

		JSONArray jsonArray = jsonArrayOptional.get();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String key = jsonObject.getString(
				CustomParameterConfigurationKeys.PARAMETER_NAME.getJsonKey());

			_addStringValue(portletRequest, blueprintsAttributesBuilder, key);
		}
	}

	private void _addStringValue(
		PortletRequest portletRequest,
		BlueprintsAttributesBuilder blueprintsAttributesBuilder, String key) {

		String s = ParamUtil.getString(portletRequest, key);

		if (!Validator.isBlank(s)) {
			blueprintsAttributesBuilder.addAttribute(key, s);
		}
	}

	private void _addStringValues(
		PortletRequest portletRequest,
		BlueprintsAttributesBuilder blueprintsAttributesBuilder, String key) {

		String[] arr = ParamUtil.getStringValues(portletRequest, key);

		if ((arr != null) && (arr.length > 0)) {
			blueprintsAttributesBuilder.addAttribute(key, arr);
		}
	}

	private boolean _isArrayValue(String type) {
		if (StringUtil.equals(type, "string_array") ||
			StringUtil.equals(type, "integer_array") ||
			StringUtil.equals(type, "long_array")) {

			return true;
		}

		return false;
	}

	@Reference
	private BlueprintHelper _blueprintHelper;

	@Reference
	private BlueprintsAttributesBuilderFactory
		_blueprintsAttributesBuilderFactory;

	@Reference
	private BlueprintService _blueprintService;

	@Reference
	private Portal _portal;

}