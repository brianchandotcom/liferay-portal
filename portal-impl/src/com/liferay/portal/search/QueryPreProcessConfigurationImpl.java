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

package com.liferay.portal.search;

import com.liferay.portal.kernel.search.QueryPreProcessConfiguration;
import com.liferay.portal.search.lucene.PerFieldAnalyzer;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class QueryPreProcessConfigurationImpl
	implements QueryPreProcessConfiguration {

	@Override
	public boolean isSubstringSearchAlways(String fieldName) {
		return _perFieldAnalyzer.isSubstringSearchAlways(fieldName);
	}

	public void setAnalyzer(PerFieldAnalyzer perFieldAnalyzer) {
		_perFieldAnalyzer = perFieldAnalyzer;
	}

	private PerFieldAnalyzer _perFieldAnalyzer;

}