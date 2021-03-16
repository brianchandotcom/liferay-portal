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
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributesBuilder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class BlueprintsAttributesBuilderImpl
	implements BlueprintsAttributesBuilder {

	public BlueprintsAttributesBuilderImpl() {
	}

	public BlueprintsAttributesBuilderImpl(
		BlueprintsAttributes blueprintsAttributes) {

		_attributes = blueprintsAttributes.getAttributes();
		_companyId = blueprintsAttributes.getCompanyId();
		_keywords = blueprintsAttributes.getKeywords();
		_locale = blueprintsAttributes.getLocale();
		_userId = blueprintsAttributes.getUserId();
	}

	@Override
	public BlueprintsAttributesBuilder addAttribute(String key, Object value) {
		_attributes.putIfAbsent(key, value);

		return this;
	}

	@Override
	public BlueprintsAttributes build() {
		BlueprintsAttributes blueprintsAttributes =
			new BlueprintsAttributesImpl(
				_attributes, _companyId, _keywords, _locale, _userId);

		_validateBlueprintsAttributes(blueprintsAttributes);

		return blueprintsAttributes;
	}

	@Override
	public BlueprintsAttributesBuilder companyId(long companyId) {
		_companyId = companyId;

		return this;
	}

	@Override
	public BlueprintsAttributesBuilder keywords(String keywords) {
		_keywords = keywords;

		return this;
	}

	@Override
	public BlueprintsAttributesBuilder locale(Locale locale) {
		_locale = locale;

		return this;
	}

	@Override
	public BlueprintsAttributesBuilder userId(long userId) {
		_userId = userId;

		return this;
	}

	private void _validateBlueprintsAttributes(
		BlueprintsAttributes blueprintsAttributes) {

		if ((blueprintsAttributes.getCompanyId() == null) ||
			(blueprintsAttributes.getLocale() == null) ||
			(blueprintsAttributes.getUserId() == null)) {

			throw new IllegalStateException(
				"Company id, locale and user id are mandatory attributes");
		}
	}

	private Map<String, Object> _attributes = new HashMap<>();
	private Long _companyId;
	private String _keywords;
	private Locale _locale;
	private Long _userId;

}