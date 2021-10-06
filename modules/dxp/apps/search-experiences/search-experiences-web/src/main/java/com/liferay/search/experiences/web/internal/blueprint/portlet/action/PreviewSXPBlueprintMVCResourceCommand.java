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

package com.liferay.search.experiences.web.internal.blueprint.portlet.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.search.experiences.constants.SXPPortletKeys;
import com.liferay.search.experiences.problems.Problem;
import com.liferay.search.experiences.problems.ProblemsHolder;
import com.liferay.search.experiences.problems.ProblemsHolderBuilder;
import com.liferay.search.experiences.problems.ProblemsHolderBuilderFactory;
import com.liferay.search.experiences.searchresponse.json.translator.SearchResponseJSONTranslator;
import com.liferay.search.experiences.searchresponse.json.translator.constants.ResponseAttributeKeys;
import com.liferay.search.experiences.sxpBlueprints.engine.attributes.BlueprintsAttributes;
import com.liferay.search.experiences.sxpBlueprints.engine.attributes.BlueprintsAttributesBuilder;
import com.liferay.search.experiences.sxpBlueprints.engine.attributes.BlueprintsAttributesBuilderFactory;
import com.liferay.search.experiences.sxpBlueprints.engine.portlet.attributes.BlueprintsAttributesHelper;
import com.liferay.search.experiences.sxpBlueprints.engine.util.BlueprintsEngineHelper;
import com.liferay.search.experiences.sxpBlueprints.service.BlueprintLocalService;
import com.liferay.search.experiences.sxpBlueprints.validator.BlueprintValidator;
import com.liferay.search.experiences.web.internal.blueprint.constants.SXPBlueprintMVCCommandNames;
import com.liferay.search.experiences.web.internal.blueprint.portlet.action.util.ProblemToJSONTranslator;
import com.liferay.search.experiences.web.internal.blueprint.util.SXPBlueprintRequestUtil;

import java.util.List;
import java.util.ResourceBundle;

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
		"javax.portlet.name=" + SXPPortletKeys.SXP_BLUEPRINT,
		"mvc.command.name=" + SXPBlueprintMVCCommandNames.PREVIEW_SXP_BLUEPRINT
	},
	service = MVCResourceCommand.class
)
public class PreviewSXPBlueprintMVCResourceCommand
	extends BaseMVCResourceCommand {

	// TODO rewrite for DTO approach

	public static class PreviewBlueprint implements SXPBlueprint {

		public PreviewBlueprint(String configuration) {
			_configuration = configuration;
		}

		@Override
		public long getBlueprintId() {
			return 0;
		}

		@Override
		public String getConfiguration() {
			return _configuration;
		}

		private final String _configuration;

	}

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			getResponseJSONString(resourceRequest, resourceResponse));
	}

	protected String getResponseJSONString(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		try {
			SXPBlueprint sxpBlueprint = new PreviewBlueprint(
				SXPBlueprintRequestUtil.getConfiguration(resourceRequest));

			_sxpBlueprintValidator.validateConfiguration(
				sxpBlueprint.getConfigurationsJSON());

			BlueprintsAttributes requestBlueprintsAttributes =
				_getRequestBlueprintsAttributes(resourceRequest, sxpBlueprint);

			ProblemsHolderBuilder problemsHolderBuilder =
				_problemsHolderBuilderFactory.builder();

			SearchResponse searchResponse = _sxpBlueprintsEngineHelper.search(
				sxpBlueprint, requestBlueprintsAttributes,
				problemsHolderBuilder);

			BlueprintsAttributes responseBlueprintsAttributes =
				_getResponseBlueprintsAttributes(
					resourceRequest, resourceResponse, sxpBlueprint,
					requestBlueprintsAttributes);

			String jsonString = _searchResponseJSONTranslator.translate(
				searchResponse, sxpBlueprint, responseBlueprintsAttributes,
				_getResourceBundle(resourceRequest),
				problemsHolderBuilder::addExceptions, problemsHolderBuilder);

			ProblemsHolder problemsHolder = problemsHolderBuilder.build();

			List<Problem> problems = problemsHolder.getAllProblems();

			if (problems.isEmpty()) {
				return jsonString;
			}

			JSONObject jsonObject1 = _createJSONObject(jsonString);

			JSONObject jsonObject2 = _createJSONObject(
				_problemToJSONTranslator.translate(
					problems, _getResourceBundle(resourceRequest)));

			jsonObject1.put("errors", jsonObject2.get("errors"));

			return jsonObject1.toString();
		}
		catch (SXPBlueprintsEngineException sxpBlueprintsEngineException) {
			_log.error(
				sxpBlueprintsEngineException.getMessage(),
				sxpBlueprintsEngineException);

			return _problemToJSONTranslator.translate(
				sxpBlueprintsEngineException.getProblems(),
				_getResourceBundle(resourceRequest));
		}
		catch (SXPBlueprintValidationException
					sxpBlueprintValidationException) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					sxpBlueprintValidationException.getMessage(),
					sxpBlueprintValidationException);
			}

			return _problemToJSONTranslator.translate(
				sxpBlueprintValidationException.getProblems(),
				_getResourceBundle(resourceRequest));
		}
	}

	private JSONObject _createJSONObject(String translate) {
		try {
			return _jsonFactory.createJSONObject(translate);
		}
		catch (JSONException jsonException) {
			throw new RuntimeException(jsonException);
		}
	}

	private JSONArray _getPreviewAttributesJSONArray(
		ResourceRequest resourceRequest) {

		String previewAttributesString =
			SXPBlueprintRequestUtil.getPreviewAttributes(resourceRequest);

		if (Validator.isBlank(previewAttributesString)) {
			return JSONFactoryUtil.createJSONArray();
		}

		try {
			return JSONFactoryUtil.createJSONArray(previewAttributesString);
		}
		catch (JSONException jsonException) {
			_log.error(
				"Unable to create a JSON array from: " +
					previewAttributesString,
				jsonException);

			return JSONFactoryUtil.createJSONArray();
		}
	}

	private BlueprintsAttributes _getRequestBlueprintsAttributes(
		ResourceRequest resourceRequest, SXPBlueprint sxpBlueprint) {

		BlueprintsAttributesBuilder sxpBlueprintsAttributesBuilder =
			_sxpBlueprintsAttributesHelper.
				getBlueprintsRequestAttributesBuilder(
					resourceRequest, sxpBlueprint);

		sxpBlueprintsAttributesBuilder.addAttribute("explain", true);

		sxpBlueprintsAttributesBuilder.addAttribute("preview", true);

		sxpBlueprintsAttributesBuilder.addAttribute(
			"include_response_string", true);

		for (Object object : _getPreviewAttributesJSONArray(resourceRequest)) {
			JSONObject jsonObject = (JSONObject)object;

			sxpBlueprintsAttributesBuilder.addAttribute(
				jsonObject.getString("key"), jsonObject.get("value"));
		}

		return sxpBlueprintsAttributesBuilder.build();
	}

	private ResourceBundle _getResourceBundle(ResourceRequest resourceRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return ResourceBundleUtil.getBundle(
			"content.Language", themeDisplay.getLocale(), getClass());
	}

	private BlueprintsAttributes _getResponseBlueprintsAttributes(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse,
		SXPBlueprint sxpBlueprint,
		BlueprintsAttributes requestBlueprintsAttributes) {

		BlueprintsAttributesBuilder sxpBlueprintsAttributesBuilder =
			_sxpBlueprintsAttributesHelper.
				getBlueprintsResponseAttributesBuilder(
					resourceRequest, resourceResponse, sxpBlueprint,
					requestBlueprintsAttributes);

		sxpBlueprintsAttributesBuilder.addAttribute(
			ResponseAttributeKeys.INCLUDE_DOCUMENT, true);

		sxpBlueprintsAttributesBuilder.addAttribute(
			ResponseAttributeKeys.INCLUDE_REQUEST_STRING, true);

		sxpBlueprintsAttributesBuilder.addAttribute(
			ResponseAttributeKeys.INCLUDE_RESULT, true);

		sxpBlueprintsAttributesBuilder.addAttribute(
			ResponseAttributeKeys.RESULT_FIELDS, _getResultFields());

		return sxpBlueprintsAttributesBuilder.build();
	}

	private List<String> _getResultFields() {
		return ListUtil.fromArray(
			"id", "score", "b_assetEntryId", "b_author", "b_created",
			"b_modified", "b_summary", "b_title", "b_type");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PreviewSXPBlueprintMVCResourceCommand.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

	@Reference
	private ProblemsHolderBuilderFactory _problemsHolderBuilderFactory;

	@Reference
	private ProblemToJSONTranslator _problemToJSONTranslator;

	@Reference
	private SearchResponseJSONTranslator _searchResponseJSONTranslator;

	@Reference
	private BlueprintLocalService _sxpBlueprintLocalService;

	@Reference
	private BlueprintsAttributesBuilderFactory
		_sxpBlueprintsAttributesBuilderFactory;

	@Reference
	private BlueprintsAttributesHelper _sxpBlueprintsAttributesHelper;

	@Reference
	private BlueprintsEngineHelper _sxpBlueprintsEngineHelper;

	@Reference
	private BlueprintValidator _sxpBlueprintValidator;

}