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
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

/**
 * @author Michael C. Han
 */
public class CompositeHitsPostProcessor implements HitsPostProcessor {

	public boolean postProcess(
		SearchContext searchContext, Hits originalSearchHits)
		throws SearchException {

		if (Validator.isNull(searchContext.getKeywords()) ||
			!searchContext.getQueryConfig().isHitsPostProcessingEnabled()) {
			return false;
		}

		for (HitsPostProcessor hitsPostProcessor : _hitsPostProcessors) {
			boolean postrocessNext = hitsPostProcessor.postProcess(
				searchContext, originalSearchHits);

			if (!postrocessNext) {
				break;
			}
		}

		return true;
	}

	public void setHitsPostProcessors(
		List<HitsPostProcessor> hitsPostProcessors) {

		_hitsPostProcessors = hitsPostProcessors;
	}

	private List<HitsPostProcessor> _hitsPostProcessors;

}