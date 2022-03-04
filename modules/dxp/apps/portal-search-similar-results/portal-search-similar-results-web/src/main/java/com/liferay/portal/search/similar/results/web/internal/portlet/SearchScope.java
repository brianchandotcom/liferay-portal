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

package com.liferay.portal.search.similar.results.web.internal.portlet;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Petteri Karttunen
 */
public enum SearchScope {

	EVERYTHING("everything"), THIS_SITE("this-site");

	public static SearchScope getSearchScope(String parameterString) {
		Stream<SearchScope> stream = Arrays.stream(SearchScope.values());

		SearchScope searchScope = stream.filter(
			value -> value._parameterString.equals(parameterString)
		).findFirst(
		).orElse(
			null
		);

		if (searchScope == null) {
			throw new IllegalArgumentException(
				"The string " + parameterString +
					" does not correspond to a valid search scope");
		}

		return searchScope;
	}

	public String getParameterString() {
		return _parameterString;
	}

	private SearchScope(String parameterString) {
		_parameterString = parameterString;
	}

	private final String _parameterString;

}