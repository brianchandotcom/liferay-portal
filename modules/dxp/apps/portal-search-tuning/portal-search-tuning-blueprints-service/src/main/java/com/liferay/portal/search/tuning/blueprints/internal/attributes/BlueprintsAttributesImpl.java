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

package com.liferay.portal.search.tuning.blueprints.internal.attributes;

import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * @author Petteri Karttunen
 */
public class BlueprintsAttributesImpl implements BlueprintsAttributes {

	public BlueprintsAttributesImpl(
		Map<String, Object> attributes, Long companyId, String keywords,
		Locale locale, Long userId) {

		_attributes = attributes;
		_companyId = companyId;
		_keywords = keywords;
		_locale = locale;
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
	public String getKeywords() {
		return _keywords;
	}

	@Override
	public Locale getLocale() {
		return _locale;
	}

	@Override
	public Long getUserId() {
		return _userId;
	}

	private final Map<String, Object> _attributes;
	private final Long _companyId;
	private final String _keywords;
	private final Locale _locale;
	private final Long _userId;

}