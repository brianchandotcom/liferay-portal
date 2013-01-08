/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search.postprocess;

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;

/**
 * @author Michael C. Han
 */
public class CollatedSpellCheckHitsPostProcessor implements HitsPostProcessor {

	public boolean postProcess(SearchContext searchContext, Hits hits)
		throws SearchException {

		if (hits.getLength() > 0) {
			return true;
		}

		String collatedKeyWords = SearchEngineUtil.spellCheckKeywords(
			searchContext);

		hits.setCollatedSpellCheckResult(collatedKeyWords);

		return true;
	}

}