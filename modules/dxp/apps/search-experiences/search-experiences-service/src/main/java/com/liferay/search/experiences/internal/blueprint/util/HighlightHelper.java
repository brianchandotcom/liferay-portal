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

package com.liferay.search.experiences.internal.blueprint.util;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.highlight.FieldConfig;
import com.liferay.portal.search.highlight.FieldConfigBuilder;
import com.liferay.portal.search.highlight.FieldConfigBuilderFactory;
import com.liferay.portal.search.highlight.Highlight;
import com.liferay.portal.search.highlight.HighlightBuilder;
import com.liferay.portal.search.highlight.HighlightBuilderFactory;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = HighlightHelper.class)
public class HighlightHelper {

	public Optional<Highlight> getHighlight(
		JSONObject jsonObject, SXPParameterData sxpParameterData) {

		HighlightBuilder highlightBuilder = _highlightBuilderFactory.builder();

		_setterHelper.setIntegerValue(
			jsonObject, "fragment_size", highlightBuilder::fragmentSize);

		_setterHelper.setIntegerValue(
			jsonObject, "number_of_fragments",
			highlightBuilder::numOfFragments);

		_setterHelper.setStringArrayValue(
			jsonObject, "post_tags", highlightBuilder::postTags);

		_setterHelper.setStringArrayValue(
			jsonObject, "pre_tags", highlightBuilder::preTags);

		_setterHelper.setBooleanValue(
			jsonObject, "require_field_match",
			highlightBuilder::requireFieldMatch);

		_setterHelper.setStringValue(
			jsonObject, "type", highlightBuilder::highlighterType);

		_setFieldConfigs(highlightBuilder, jsonObject);

		return Optional.of(highlightBuilder.build());
	}

	private FieldConfig _getFieldConfig(
		JSONObject fieldsJSONObject, String key) {

		JSONObject fieldJSONObject = fieldsJSONObject.getJSONObject(key);

		FieldConfigBuilder fieldConfigBuilder =
			_fieldConfigBuilderFactory.builder(key);

		_setterHelper.setIntegerValue(
			fieldJSONObject, "fragment_offset",
			fieldConfigBuilder::fragmentOffset);

		_setterHelper.setIntegerValue(
			fieldJSONObject, "fragment_size", fieldConfigBuilder::fragmentSize);

		_setterHelper.setIntegerValue(
			fieldJSONObject, "number_of_fragments",
			fieldConfigBuilder::numFragments);

		return fieldConfigBuilder.build();
	}

	private void _setFieldConfigs(
		HighlightBuilder highlightBuilder, JSONObject jsonObject) {

		JSONObject fieldsJSONObject = jsonObject.getJSONObject("fields");

		if (fieldsJSONObject == null) {
			return;
		}

		Set<String> keySet = fieldsJSONObject.keySet();

		Stream<String> stream = keySet.stream();

		stream.forEach(
			key -> highlightBuilder.addFieldConfig(
				_getFieldConfig(fieldsJSONObject, key)));
	}

	@Reference
	private FieldConfigBuilderFactory _fieldConfigBuilderFactory;

	@Reference
	private HighlightBuilderFactory _highlightBuilderFactory;

	@Reference
	private SetterHelper _setterHelper;

}