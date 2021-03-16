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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributesBuilder;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributesBuilderFactory;
import com.liferay.portal.search.tuning.blueprints.engine.constants.ReservedParameterNames;
import com.liferay.portal.search.tuning.blueprints.engine.exception.BlueprintsEngineException;
import com.liferay.portal.search.tuning.blueprints.engine.util.BlueprintsEngineHelper;
import com.liferay.portal.search.tuning.blueprints.keyword.index.util.KeywordIndexHelper;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.misspellings.processor.MisspellingsProcessor;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.SearchResponseJSONTranslator;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.constants.JSONKeys;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.constants.ResponseAttributeKeys;
import com.liferay.portal.search.tuning.blueprints.suggestions.attributes.SuggestionsAttributes;
import com.liferay.portal.search.tuning.blueprints.suggestions.attributes.SuggestionsAttributesBuilder;
import com.liferay.portal.search.tuning.blueprints.suggestions.spellcheck.SpellCheckService;
import com.liferay.portal.search.tuning.blueprints.suggestions.suggestion.Suggestion;
import com.liferay.portal.search.tuning.blueprints.util.attributes.BlueprintsAttributesHelper;
import com.liferay.portal.search.tuning.blueprints.web.internal.constants.BlueprintsWebPortletKeys;
import com.liferay.portal.search.tuning.blueprints.web.internal.constants.ResourceRequestKeys;
import com.liferay.portal.search.tuning.blueprints.web.internal.portlet.preferences.BlueprintsWebPortletPreferences;
import com.liferay.portal.search.tuning.blueprints.web.internal.portlet.preferences.BlueprintsWebPortletPreferencesImpl;
import com.liferay.portal.search.tuning.blueprints.web.internal.util.BlueprintsWebPortletHelper;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BlueprintsWebPortletKeys.BLUEPRINTS_WEB,
		"mvc.command.name=" + ResourceRequestKeys.GET_SEARCH_RESULTS
	},
	service = MVCResourceCommand.class
)
public class GetSearchResultsMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONObject responseJSONObject = null;

		Optional<Blueprint> blueprintOptional =
			_blueprintsWebPortletHelper.getBlueprint(resourceRequest);

		Blueprint blueprint = blueprintOptional.get();

		try {
			BlueprintsWebPortletPreferences blueprintsWebPortletPreferences =
				new BlueprintsWebPortletPreferencesImpl(
					resourceRequest.getPreferences());

			Messages messages = new Messages();

			BlueprintsAttributes blueprintsRequestAttributes =
				_getBlueprintsRequestAttributes(
					resourceRequest, blueprint,
					blueprintsWebPortletPreferences);

			SearchResponse searchResponse = _blueprintsEngineHelper.search(
				blueprint, blueprintsRequestAttributes, messages);

			responseJSONObject = _createResponseObject(
				resourceRequest, resourceResponse, searchResponse, blueprint,
				blueprintsRequestAttributes, blueprintsWebPortletPreferences,
				messages);

			if (_shouldIndexKeywords(
					blueprintsWebPortletPreferences,
					searchResponse.getTotalHits())) {

				_indexKeyword(
					resourceRequest, blueprintsRequestAttributes.getKeywords());
			}
		}
		catch (BlueprintsEngineException blueprintsEngineException) {
			_log.error(
				blueprintsEngineException.getMessage(),
				blueprintsEngineException);

			List<Message> errorMessages =
				blueprintsEngineException.getMessages();

			Stream<Message> stream = errorMessages.stream();

			stream.forEach(message -> _log.error(message));

			responseJSONObject = JSONUtil.put(
				JSONKeys.ERRORS, blueprintsEngineException.getMessage());
		}
		catch (PortalException portalException) {
			_log.error(portalException.getMessage(), portalException);

			responseJSONObject = JSONUtil.put(
				JSONKeys.ERRORS, portalException.getMessage());
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, responseJSONObject);
	}

	private void _addDidYouMean(
		ResourceRequest resourceRequest,
		BlueprintsWebPortletPreferences blueprintsWebPortletPreferences,
		String keywords, JSONObject responseJSONObject) {

		if (_spellCheckService == null) {
			return;
		}

		List<Suggestion> suggestions = _spellCheckService.getSuggestions(
			_getDidYouMeanSuggestionsAttributes(
				resourceRequest, keywords, blueprintsWebPortletPreferences));

		if (suggestions.isEmpty()) {
			return;
		}

		_addDidYouMeanSuggestions(responseJSONObject, suggestions);
	}

	private void _addDidYouMeanSuggestions(
		JSONObject responseJSONObject, List<Suggestion> suggestions) {

		JSONArray suggestionsJSONArray = _jsonFactory.createJSONArray();

		Stream<Suggestion> stream = suggestions.stream();

		stream.forEach(
			suggestion -> suggestionsJSONArray.put(suggestion.getText()));

		responseJSONObject.put("didYouMean", suggestionsJSONArray);
	}

	private boolean _allowMisspellings(PortletRequest portletRequest) {
		return ParamUtil.getBoolean(
			portletRequest, ReservedParameterNames.ALLOW_MISSPELLINGS.getKey());
	}

	private JSONObject _createResponseObject(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			SearchResponse searchResponse, Blueprint blueprint,
			BlueprintsAttributes blueprintsRequestAttributes,
			BlueprintsWebPortletPreferences blueprintsWebPortletPreferences,
			Messages messages)
		throws PortalException {

		BlueprintsAttributes blueprintsResponseAttributes =
			_getBlueprintsResponseAttributes(
				resourceRequest, resourceResponse, blueprint,
				blueprintsRequestAttributes);

		JSONObject responseJSONObject = _searchResponseJSONTranslator.translate(
			searchResponse, blueprint, blueprintsResponseAttributes,
			_getResourceBundle(resourceRequest), messages);

		if (_shouldAddDidYouMean(
				blueprintsWebPortletPreferences,
				searchResponse.getTotalHits()) &&
			!Validator.isBlank(blueprintsRequestAttributes.getKeywords())) {

			_addDidYouMean(
				resourceRequest, blueprintsWebPortletPreferences,
				blueprintsRequestAttributes.getKeywords(), responseJSONObject);
		}

		return responseJSONObject;
	}

	private BlueprintsAttributes _getBlueprintsRequestAttributes(
		ResourceRequest resourceRequest, Blueprint blueprint,
		BlueprintsWebPortletPreferences blueprintsWebPortletPreferences) {

		BlueprintsAttributesBuilder blueprintsAttributesBuilder =
			_blueprintsAttributesHelper.getBlueprintsRequestAttributesBuilder(
				resourceRequest, blueprint);

		if ((_misspellingsProcessor != null) &&
			blueprintsWebPortletPreferences.isMisspellingsEnabled() &&
			!_allowMisspellings(resourceRequest)) {

			return _processMisspellings(
				resourceRequest, blueprintsAttributesBuilder);
		}

		return blueprintsAttributesBuilder.build();
	}

	private BlueprintsAttributes _getBlueprintsResponseAttributes(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse,
		Blueprint blueprint, BlueprintsAttributes blueprintsRequestAttributes) {

		BlueprintsAttributesBuilder blueprintsAttributesBuilder =
			_blueprintsAttributesHelper.getBlueprintsResponseAttributesBuilder(
				resourceRequest, resourceResponse, blueprint,
				blueprintsRequestAttributes);

		blueprintsAttributesBuilder.addAttribute(
			ResponseAttributeKeys.INCLUDE_RESULT, true);

		return blueprintsAttributesBuilder.build();
	}

	private SuggestionsAttributes _getDidYouMeanSuggestionsAttributes(
		ResourceRequest resourceRequest, String keywords,
		BlueprintsWebPortletPreferences blueprintsWebPortletPreferences) {

		SuggestionsAttributesBuilder suggestionsAttributesBuilder =
			_blueprintsWebPortletHelper.getSuggestionsAttributesBuilder(
				resourceRequest);

		return suggestionsAttributesBuilder.keywords(
			keywords
		).size(
			blueprintsWebPortletPreferences.getMaxDidYouMeanSuggestions()
		).build();
	}

	private ResourceBundle _getResourceBundle(ResourceRequest resourceRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return ResourceBundleUtil.getBundle(
			"content.Language", themeDisplay.getLocale(), getClass());
	}

	private void _indexKeyword(
		ResourceRequest resourceRequest, String keywords) {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_keywordIndexHelper.indexKeywords(
			themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
			themeDisplay.getLanguageId(), keywords);
	}

	private BlueprintsAttributes _processMisspellings(
		ResourceRequest resourceRequest,
		BlueprintsAttributesBuilder blueprintsAttributesBuilder1) {

		BlueprintsAttributes blueprintsAttributes =
			blueprintsAttributesBuilder1.build();

		String keywords = blueprintsAttributes.getKeywords();

		BlueprintsAttributesBuilder blueprintsAttributesBuilder2 =
			_blueprintsAttributesBuilderFactory.builder(blueprintsAttributes);

		if (Validator.isBlank(keywords)) {
			return blueprintsAttributesBuilder2.build();
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String misspellCheckedWords = _misspellingsProcessor.process(
			themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
			themeDisplay.getLanguageId(), keywords);

		if (!keywords.equals(misspellCheckedWords)) {
			blueprintsAttributesBuilder2.addAttribute(
				ReservedParameterNames.SHOWING_INSTEAD_OF.getKey(), keywords);
			blueprintsAttributesBuilder2.keywords(misspellCheckedWords);
		}

		return blueprintsAttributesBuilder2.build();
	}

	private boolean _shouldAddDidYouMean(
		BlueprintsWebPortletPreferences blueprintsWebPortletPreferences,
		int hitCount) {

		if (blueprintsWebPortletPreferences.isDidYouMeanEnabled() &&
			(blueprintsWebPortletPreferences.getDidYouMeanHitsThreshold() >=
				hitCount)) {

			return true;
		}

		return false;
	}

	private boolean _shouldIndexKeywords(
		BlueprintsWebPortletPreferences blueprintsWebPortletPreferences,
		int hitCount) {

		int threshold =
			blueprintsWebPortletPreferences.getKeywordIndexingHitsThreshold();

		if (blueprintsWebPortletPreferences.isKeywordIndexingEnabled() &&
			(threshold <= hitCount)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetSearchResultsMVCResourceCommand.class);

	@Reference
	private BlueprintsAttributesBuilderFactory
		_blueprintsAttributesBuilderFactory;

	@Reference
	private BlueprintsAttributesHelper _blueprintsAttributesHelper;

	@Reference
	private BlueprintsEngineHelper _blueprintsEngineHelper;

	@Reference
	private BlueprintsWebPortletHelper _blueprintsWebPortletHelper;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private KeywordIndexHelper _keywordIndexHelper;

	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	private volatile MisspellingsProcessor _misspellingsProcessor;

	@Reference
	private Portal _portal;

	@Reference
	private SearchResponseJSONTranslator _searchResponseJSONTranslator;

	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	private SpellCheckService _spellCheckService;

}