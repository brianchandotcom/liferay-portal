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

package com.liferay.search.experiences.internal.attributes;

import com.liferay.search.experiences.attributes.SXPAttributes;
import com.liferay.search.experiences.attributes.SXPAttributesBuilder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class SXPAttributesBuilderImpl implements SXPAttributesBuilder {

	public SXPAttributesBuilderImpl() {
	}

	public SXPAttributesBuilderImpl(SXPAttributes sxpAttributes) {
		_attributes = sxpAttributes.getAttributes();
		_companyId = sxpAttributes.getCompanyId();
		_keywords = sxpAttributes.getKeywords();
		_locale = sxpAttributes.getLocale();
		_userId = sxpAttributes.getUserId();
	}

	@Override
	public SXPAttributesBuilder addAttribute(String key, Object value) {
		_attributes.putIfAbsent(key, value);

		return this;
	}

	@Override
	public SXPAttributes build() {
		SXPAttributes sxpAttributes = new SXPAttributesImpl(
			_attributes, _companyId, _keywords, _locale, _userId);

		_validateBlueprintsAttributes(sxpAttributes);

		return sxpAttributes;
	}

	@Override
	public SXPAttributesBuilder companyId(long companyId) {
		_companyId = companyId;

		return this;
	}

	@Override
	public SXPAttributesBuilder keywords(String keywords) {
		_keywords = keywords;

		return this;
	}

	@Override
	public SXPAttributesBuilder locale(Locale locale) {
		_locale = locale;

		return this;
	}

	@Override
	public SXPAttributesBuilder userId(Long userId) {
		_userId = userId;

		return this;
	}

	private void _validateBlueprintsAttributes(SXPAttributes sxpAttributes) {
		if ((sxpAttributes.getCompanyId() == null) ||
			(sxpAttributes.getLocale() == null)) {

			throw new IllegalStateException(
				"Company id and locale are mandatory attributes");
		}
	}

	private Map<String, Object> _attributes = new HashMap<>();
	private Long _companyId;
	private String _keywords;
	private Locale _locale;
	private Long _userId;

}