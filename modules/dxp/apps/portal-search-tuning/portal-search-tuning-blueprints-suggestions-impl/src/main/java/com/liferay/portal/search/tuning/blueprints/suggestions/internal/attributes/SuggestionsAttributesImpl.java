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

import java.util.Map;
import java.util.Optional;

/**
 * @author Petteri Karttunen
 */
public class SuggestionsAttributesImpl implements SuggestionsAttributes {

	public SuggestionsAttributesImpl(
		Map<String, Object> attributes, Long companyId, Long groupId,
		String keywords, String languageId, int size, Long userId) {

		_attributes = attributes;
		_companyId = companyId;
		_groupId = groupId;
		_keywords = keywords;
		_languageId = languageId;
		_size = size;
		_userId = userId;
	}

	@Override
	public Optional<Object> getAttributeOptional(String key) {
		return Optional.ofNullable(_attributes.get(key));
	}

	@Override
	public Map<String, Object> getAttributes() {
		return _attributes;
	}

	@Override
	public Long getCompanyId() {
		return _companyId;
	}

	@Override
	public Long getGroupId() {
		return _groupId;
	}

	@Override
	public String getKeywords() {
		return _keywords;
	}

	@Override
	public String getLanguageId() {
		return _languageId;
	}

	@Override
	public Integer getSize() {
		return _size;
	}

	@Override
	public Long getUserId() {
		return _userId;
	}

	private final Map<String, Object> _attributes;
	private final Long _companyId;
	private final Long _groupId;
	private final String _keywords;
	private final String _languageId;
	private final Integer _size;
	private final Long _userId;

}