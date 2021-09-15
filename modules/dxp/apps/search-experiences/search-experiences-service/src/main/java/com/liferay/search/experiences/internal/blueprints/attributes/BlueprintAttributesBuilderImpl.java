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

package com.liferay.search.experiences.internal.blueprints.attributes;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class BlueprintAttributesBuilderImpl
	implements BlueprintAttributesBuilder {

	public BlueprintAttributesBuilderImpl() {
	}

	public BlueprintAttributesBuilderImpl(
		BlueprintAttributes blueprintAttributes) {

		_attributes = blueprintAttributes.getAttributes();
		_companyId = blueprintAttributes.getCompanyId();
		_keywords = blueprintAttributes.getKeywords();
		_locale = blueprintAttributes.getLocale();
		_userId = blueprintAttributes.getUserId();
	}

	@Override
	public BlueprintAttributesBuilder addAttribute(String key, Object value) {
		_attributes.putIfAbsent(key, value);

		return this;
	}

	@Override
	public BlueprintAttributes build() {
		BlueprintAttributes blueprintAttributes = new BlueprintAttributesImpl(
			_attributes, _companyId, _keywords, _locale, _userId);

		_validateBlueprintAttributes(blueprintAttributes);

		return blueprintAttributes;
	}

	@Override
	public BlueprintAttributesBuilder companyId(long companyId) {
		_companyId = companyId;

		return this;
	}

	@Override
	public BlueprintAttributesBuilder keywords(String keywords) {
		_keywords = keywords;

		return this;
	}

	@Override
	public BlueprintAttributesBuilder locale(Locale locale) {
		_locale = locale;

		return this;
	}

	@Override
	public BlueprintAttributesBuilder userId(Long userId) {
		_userId = userId;

		return this;
	}

	private void _validateBlueprintAttributes(
		BlueprintAttributes blueprintAttributes) {

		if ((blueprintAttributes.getCompanyId() == null) ||
			(blueprintAttributes.getLocale() == null)) {

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