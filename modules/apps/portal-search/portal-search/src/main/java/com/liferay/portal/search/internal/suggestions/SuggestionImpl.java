/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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