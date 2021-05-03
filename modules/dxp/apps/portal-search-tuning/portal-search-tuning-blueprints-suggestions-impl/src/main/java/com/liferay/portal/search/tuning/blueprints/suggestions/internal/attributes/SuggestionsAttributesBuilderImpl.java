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

package com.liferay.portal.search.tuning.blueprints.suggestions.internal.attributes;

import com.liferay.portal.search.tuning.blueprints.suggestions.attributes.SuggestionsAttributes;
import com.liferay.portal.search.tuning.blueprints.suggestions.attributes.SuggestionsAttributesBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class SuggestionsAttributesBuilderImpl
	implements SuggestionsAttributesBuilder {

	@Override
	public SuggestionsAttributesBuilder addAttribute(String key, Object value) {
		_attributes.putIfAbsent(key, value);

		return this;
	}

	@Override
	public SuggestionsAttributes build() {
		SuggestionsAttributes suggestionsAttributes =
			new SuggestionsAttributesImpl(
				_attributes, _companyId, _groupId, _keywords, _languageId,
				_size, _userId);

		_validateSuggestionsAttributes(suggestionsAttributes);

		return suggestionsAttributes;
	}

	@Override
	public SuggestionsAttributesBuilder companyId(long companyId) {
		_companyId = companyId;

		return this;
	}

	@Override
	public SuggestionsAttributesBuilder groupId(Long groupId) {
		_groupId = groupId;

		return this;
	}

	@Override
	public SuggestionsAttributesBuilder keywords(String keywords) {
		_keywords = keywords;

		return this;
	}

	@Override
	public SuggestionsAttributesBuilder languageId(String languageId) {
		_languageId = languageId;

		return this;
	}

	@Override
	public SuggestionsAttributesBuilder size(int size) {
		_size = size;

		return this;
	}

	@Override
	public SuggestionsAttributesBuilder userId(long userId) {
		_userId = userId;

		return this;
	}

	private void _validateSuggestionsAttributes(
		SuggestionsAttributes typeaheadAttributes) {

		if ((typeaheadAttributes.getCompanyId() == null) ||
			(typeaheadAttributes.getKeywords() == null) ||
			(typeaheadAttributes.getLanguageId() == null) ||
			(typeaheadAttributes.getSize() == null)) {

			throw new IllegalStateException(
				"Company id, keyword, languageId and size are mandatory " +
					"attributes");
		}
	}

	private final Map<String, Object> _attributes = new HashMap<>();
	private Long _companyId;
	private Long _groupId;
	private String _keywords;
	private String _languageId;
	private Integer _size;
	private Long _userId;

}