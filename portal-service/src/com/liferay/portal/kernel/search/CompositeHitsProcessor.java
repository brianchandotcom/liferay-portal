/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.util.Validator;

import java.util.List;

/**
 * @author Michael C. Han
 */
public class CompositeHitsProcessor implements HitsProcessor {

	@Override
	public boolean process(SearchContext searchContext, Hits hits)
		throws SearchException {

		if (Validator.isNull(searchContext.getKeywords())) {
			return false;
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		if (!queryConfig.isHitsProcessingEnabled()) {
			return false;
		}

		for (HitsProcessor hitsProcessor : _hitsProcessors) {
			if (!hitsProcessor.process(searchContext, hits)) {
				break;
			}
		}

		return true;
	}

	public void setHitsProcessors(List<HitsProcessor> hitsProcessors) {
		_hitsProcessors = hitsProcessors;
	}

	private List<HitsProcessor> _hitsProcessors;

}