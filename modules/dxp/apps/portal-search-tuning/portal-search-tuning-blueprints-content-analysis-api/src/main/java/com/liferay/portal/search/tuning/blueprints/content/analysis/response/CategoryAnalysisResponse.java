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

package com.liferay.portal.search.tuning.blueprints.content.analysis.response;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class CategoryAnalysisResponse {

	public void addCategory(String category, Float confidence) {
		_categories.putIfAbsent(category, confidence);
	}

	public Map<String, Float> getCategories() {
		return _categories;
	}

	private final Map<String, Float> _categories = new HashMap<>();

}