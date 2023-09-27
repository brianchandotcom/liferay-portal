/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.suggestions;

import com.liferay.portal.search.suggestions.Suggestion;
import com.liferay.portal.search.suggestions.SuggestionBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class SuggestionBuilderImpl implements SuggestionBuilder {

	public SuggestionBuilderImpl() {
	}

	public SuggestionBuilderImpl(Suggestion suggestion) {
		_attributes = suggestion.getAttributes();
		_score = suggestion.getScore();
		_text = suggestion.getText();
	}

	public SuggestionBuilderImpl attribute(String name, Object value) {
		if (_attributes == null) {
			_attributes = new HashMap<>();
		}

		_attributes.put(name, value);

		return this;
	}

	@Override
	public Suggestion build() {
		return new SuggestionImpl(_attributes, _score, _text);
	}

	@Override
	public SuggestionBuilderImpl score(float score) {
		_score = score;

		return this;
	}

	@Override
	public SuggestionBuilderImpl text(String text) {
		_text = text;

		return this;
	}

	private Map<String, Object> _attributes;
	private float _score;
	private String _text;

}