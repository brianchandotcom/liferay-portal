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

import java.util.Collections;
import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class SuggestionImpl implements Suggestion {

	public SuggestionImpl(
		Map<String, Object> attributes, float score, String text) {

		_attributes = attributes;
		_score = score;
		_text = text;
	}

	@Override
	public Object getAttribute(String name) {
		if (_attributes == null) {
			return null;
		}

		return _attributes.get(name);
	}

	@Override
	public Map<String, Object> getAttributes() {
		if (_attributes == null) {
			return Collections.emptyMap();
		}

		return _attributes;
	}

	@Override
	public float getScore() {
		return _score;
	}

	@Override
	public String getText() {
		return _text;
	}

	private final Map<String, Object> _attributes;
	private final float _score;
	private final String _text;

}