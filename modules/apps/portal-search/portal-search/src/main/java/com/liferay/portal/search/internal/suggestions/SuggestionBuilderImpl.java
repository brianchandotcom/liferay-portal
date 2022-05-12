/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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