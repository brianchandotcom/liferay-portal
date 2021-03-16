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

package com.liferay.portal.search.tuning.blueprints.web.internal.portlet.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.suggestions.attributes.SuggestionsAttributes;
import com.liferay.portal.search.tuning.blueprints.suggestions.attributes.SuggestionsAttributesBuilder;
import com.liferay.portal.search.tuning.blueprints.suggestions.suggestion.Suggestion;
import com.liferay.portal.search.tuning.blueprints.suggestions.typeahead.TypeaheadService;
import com.liferay.portal.search.tuning.blueprints.web.internal.constants.BlueprintsWebPortletKeys;
import com.liferay.portal.search.tuning.blueprints.web.internal.constants.ResourceRequestKeys;
import com.liferay.portal.search.tuning.blueprints.web.internal.portlet.preferences.BlueprintsWebPortletPreferences;
import com.liferay.portal.search.tuning.blueprints.web.internal.portlet.preferences.BlueprintsWebPortletPreferencesImpl;
import com.liferay.portal.search.tuning.blueprints.web.internal.util.BlueprintsWebPortletHelper;

import java.util.List;
import java.util.stream.Stream;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BlueprintsWebPortletKeys.BLUEPRINTS_WEB,
		"mvc.command.name=" + ResourceRequestKeys.GET_TYPEAHEAD
	},
	service = MVCResourceCommand.class
)
public class GetTypeaheadMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		BlueprintsWebPortletPreferences blueprintsWebPortletPreferences =
			new BlueprintsWebPortletPreferencesImpl(
				resourceRequest.getPreferences());

		String keywords = ParamUtil.getString(resourceRequest, "q");

		if (!_isTypeaheadEnabled(blueprintsWebPortletPreferences) ||
			(keywords.length() <= 1)) {

			return;
		}

		SuggestionsAttributes suggestionsAttributes =
			_getTypeaheadSuggestionsAttributes(
				resourceRequest, keywords, blueprintsWebPortletPreferences);

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			_getResponseJSONObject(
				_typeaheadService.getSuggestions(suggestionsAttributes)));
	}

	private JSONObject _getResponseJSONObject(List<Suggestion> suggestions) {
		JSONObject responseJSONObject = _jsonFactory.createJSONObject();

		if (suggestions.isEmpty()) {
			return responseJSONObject;
		}

		JSONArray suggestionsJSONArray = _jsonFactory.createJSONArray();

		Stream<Suggestion> stream = suggestions.stream();

		stream.forEach(
			suggestion -> suggestionsJSONArray.put(suggestion.getText()));

		return responseJSONObject.put("suggestions", suggestionsJSONArray);
	}

	private String[] _getTitleTypeaheadEntryClassNames(
		BlueprintsWebPortletPreferences blueprintsWebPortletPreferences) {

		String entryClassNames =
			blueprintsWebPortletPreferences.getTitleTypeaheadEntryClassNames();

		if (Validator.isBlank(entryClassNames)) {
			return new String[0];
		}

		String[] arr1 = entryClassNames.split(",");

		String[] arr2 = new String[arr1.length];

		for (int i = 0; i < arr1.length; i++) {
			arr2[i] = StringUtil.trim(arr1[i]);
		}

		return arr2;
	}

	private SuggestionsAttributes _getTypeaheadSuggestionsAttributes(
		ResourceRequest resourceRequest, String keywords,
		BlueprintsWebPortletPreferences blueprintsWebPortletPreferences) {

		SuggestionsAttributesBuilder suggestionsAttributesBuilder =
			_blueprintsWebPortletHelper.getSuggestionsAttributesBuilder(
				resourceRequest);

		return suggestionsAttributesBuilder.addAttribute(
			"data.provider.titles.entry_class_names",
			_getTitleTypeaheadEntryClassNames(blueprintsWebPortletPreferences)
		).keywords(
			keywords
		).size(
			blueprintsWebPortletPreferences.getMaxTypeaheadSuggestions()
		).build();
	}

	private boolean _isTypeaheadEnabled(
		BlueprintsWebPortletPreferences blueprintsWebPortletPreferences) {

		return blueprintsWebPortletPreferences.isTypeaheadEnabled();
	}

	@Reference
	private BlueprintsWebPortletHelper _blueprintsWebPortletHelper;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private TypeaheadService _typeaheadService;

}