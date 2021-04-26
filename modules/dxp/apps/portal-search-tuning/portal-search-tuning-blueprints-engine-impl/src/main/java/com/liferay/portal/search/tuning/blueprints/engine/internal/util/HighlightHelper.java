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

package com.liferay.portal.search.tuning.blueprints.engine.internal.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.search.highlight.FieldConfigBuilder;
import com.liferay.portal.search.highlight.Highlight;
import com.liferay.portal.search.highlight.HighlightBuilder;
import com.liferay.portal.search.highlight.Highlights;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.highlight.HighlightingConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.message.Messages;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = HighlightHelper.class)
public class HighlightHelper {

	public Optional<Highlight> getHighlight(
		JSONObject highlightJSONObject, ParameterData parameterData,
		Messages messages) {

		if ((highlightJSONObject == null) ||
			(highlightJSONObject.length() == 0)) {

			return Optional.empty();
		}

		HighlightBuilder highlightBuilder = _highlights.builder();

		if (highlightJSONObject.has(
				HighlightingConfigurationKeys.FRAGMENT_SIZE.getJsonKey())) {

			highlightBuilder.fragmentSize(
				highlightJSONObject.getInt(
					HighlightingConfigurationKeys.FRAGMENT_SIZE.getJsonKey()));
		}

		if (highlightJSONObject.has(
				HighlightingConfigurationKeys.NUMBER_OF_FRAGMENTS.
					getJsonKey())) {

			highlightBuilder.numOfFragments(
				highlightJSONObject.getInt(
					HighlightingConfigurationKeys.NUMBER_OF_FRAGMENTS.
						getJsonKey()));
		}

		if (highlightJSONObject.has(
				HighlightingConfigurationKeys.POST_TAGS.getJsonKey())) {

			highlightBuilder.postTags(
				JSONUtil.toStringArray(
					highlightJSONObject.getJSONArray(
						HighlightingConfigurationKeys.POST_TAGS.getJsonKey())));
		}

		if (highlightJSONObject.has(
				HighlightingConfigurationKeys.PRE_TAGS.getJsonKey())) {

			highlightBuilder.preTags(
				JSONUtil.toStringArray(
					highlightJSONObject.getJSONArray(
						HighlightingConfigurationKeys.PRE_TAGS.getJsonKey())));
		}

		if (highlightJSONObject.has(
				HighlightingConfigurationKeys.REQUIRE_FIELD_MATCH.
					getJsonKey())) {

			highlightBuilder.requireFieldMatch(
				highlightJSONObject.getBoolean(
					HighlightingConfigurationKeys.REQUIRE_FIELD_MATCH.
						getJsonKey()));
		}

		if (highlightJSONObject.has(
				HighlightingConfigurationKeys.TYPE.getJsonKey())) {

			highlightBuilder.highlighterType(
				highlightJSONObject.getString(
					HighlightingConfigurationKeys.TYPE.getJsonKey()));
		}

		if (highlightJSONObject.has(
				HighlightingConfigurationKeys.FIELDS.getJsonKey())) {

			_addFieldConfigs(
				highlightBuilder,
				highlightJSONObject.getJSONArray(
					HighlightingConfigurationKeys.FIELDS.getJsonKey()));
		}

		return Optional.of(highlightBuilder.build());
	}

	private void _addFieldConfigs(
		HighlightBuilder highlightBuilder, JSONArray fieldsJSONArray) {

		for (int i = 0; i < fieldsJSONArray.length(); i++) {
			JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(i);

			if (!fieldJSONObject.has(
					HighlightingConfigurationKeys.FIELD.getJsonKey())) {

				continue;
			}

			FieldConfigBuilder fieldConfigBuilder =
				_highlights.fieldConfigBuilder();

			fieldConfigBuilder.field(
				fieldJSONObject.getString(
					HighlightingConfigurationKeys.FIELD.getJsonKey()));

			if (fieldJSONObject.has(
					HighlightingConfigurationKeys.FRAGMENT_OFFSET.
						getJsonKey())) {

				fieldConfigBuilder.fragmentOffset(
					fieldJSONObject.getInt(
						HighlightingConfigurationKeys.FRAGMENT_OFFSET.
							getJsonKey()));
			}

			if (fieldJSONObject.has(
					HighlightingConfigurationKeys.FRAGMENT_SIZE.getJsonKey())) {

				fieldConfigBuilder.fragmentSize(
					fieldJSONObject.getInt(
						HighlightingConfigurationKeys.FRAGMENT_SIZE.
							getJsonKey()));
			}

			if (fieldJSONObject.has(
					HighlightingConfigurationKeys.NUMBER_OF_FRAGMENTS.
						getJsonKey())) {

				fieldConfigBuilder.numFragments(
					fieldJSONObject.getInt(
						HighlightingConfigurationKeys.NUMBER_OF_FRAGMENTS.
							getJsonKey()));
			}

			highlightBuilder.addFieldConfig(fieldConfigBuilder.build());
		}
	}

	@Reference
	private Highlights _highlights;

}