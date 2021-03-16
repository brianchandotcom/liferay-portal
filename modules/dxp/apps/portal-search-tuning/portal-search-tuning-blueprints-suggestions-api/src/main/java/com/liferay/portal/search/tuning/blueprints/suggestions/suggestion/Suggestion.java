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

package com.liferay.portal.search.tuning.blueprints.suggestions.suggestion;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class Suggestion {

	public Suggestion(String text, float score, String providerName) {
		_text = text;
		_score = score;

		_attributes.put("provider", providerName);
	}

	public Map<String, String> getAttributes() {
		return _attributes;
	}

	public float getScore() {
		return _score;
	}

	public String getText() {
		return _text;
	}

	public void setAttributes(Map<String, String> attributes) {
		_attributes = attributes;
	}

	public void setScore(Float score) {
		_score = score;
	}

	public void setText(String text) {
		_text = text;
	}

	private Map<String, String> _attributes = new HashMap<>();
	private float _score;
	private String _text;

}