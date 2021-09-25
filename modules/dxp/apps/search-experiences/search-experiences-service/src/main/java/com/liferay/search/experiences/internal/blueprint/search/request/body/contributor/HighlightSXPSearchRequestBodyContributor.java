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

package com.liferay.search.experiences.internal.blueprint.search.request.body.contributor;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.highlight.FieldConfigBuilderFactory;
import com.liferay.portal.search.highlight.HighlightBuilder;
import com.liferay.portal.search.highlight.HighlightBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterParser;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.Highlight;
import com.liferay.search.experiences.rest.dto.v1_0.HighlightField;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;

import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class HighlightSXPSearchRequestBodyContributor
	implements SXPSearchRequestBodyContributor {

	public HighlightSXPSearchRequestBodyContributor(
		FieldConfigBuilderFactory fieldConfigBuilderFactory,
		HighlightBuilderFactory highlightBuilderFactory,
		JSONFactory jsonFactory) {

		_fieldConfigBuilderFactory = fieldConfigBuilderFactory;
		_highlightBuilderFactory = highlightBuilderFactory;
		_jsonFactory = jsonFactory;
	}

	@Override
	public void contribute(
		SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint,
		SXPParameterData sxpParameterData) {

		Configuration configuration = sxpBlueprint.getConfiguration();

		Highlight highlight = _substituteParameterValues(
			configuration.getHighlight(), sxpParameterData);

		if (highlight == null) {
			return;
		}

		HighlightBuilder highlightBuilder = _highlightBuilderFactory.builder();

		Map<String, HighlightField> fieldsMap = highlight.getFields();

		if (!MapUtil.isEmpty(fieldsMap)) {
			fieldsMap.forEach(
				(name, highlightField) -> highlightBuilder.addFieldConfig(
					_fieldConfigBuilderFactory.builder(
						name
					).fragmentOffset(
						highlightField.getFragment_offset()
					).fragmentSize(
						highlightField.getFragment_size()
					).numFragments(
						highlightField.getNumber_of_fragments()
					).build()));
		}

		highlightBuilder.fragmentSize(
			highlight.getFragment_size()
		).numOfFragments(
			highlight.getNumber_of_fragments()
		).postTags(
			highlight.getPost_tags()
		).preTags(
			highlight.getPre_tags()
		).requireFieldMatch(
			highlight.getRequire_field_match()
		).highlighterType(
			highlight.getType()
		);

		searchRequestBuilder.highlight(highlightBuilder.build());
	}

	@Override
	public String getName() {
		return "highlight";
	}

	private JSONObject _createJSONObject(String string) {
		try {
			return _jsonFactory.createJSONObject(string);
		}
		catch (JSONException jsonException) {
			throw new RuntimeException(jsonException);
		}
	}

	private Highlight _substituteParameterValues(
		Highlight highlight, SXPParameterData sxpParameterData) {

		if (highlight == null) {
			return null;
		}

		JSONObject jsonObject = SXPParameterParser.parse(
			_createJSONObject(highlight.toString()), sxpParameterData);

		if (jsonObject == null) {
			return null;
		}

		return Highlight.toDTO(jsonObject.toString());
	}

	private final FieldConfigBuilderFactory _fieldConfigBuilderFactory;
	private final HighlightBuilderFactory _highlightBuilderFactory;
	private final JSONFactory _jsonFactory;

}