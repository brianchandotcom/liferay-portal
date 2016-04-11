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

package com.liferay.portal.search.elasticsearch.internal.facet;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch.facet.FacetProcessor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Raymond Augé
 * @author Tibor Lipusz
 */
@Component(
	immediate = true,
	property = {
		"class.name=com.liferay.portal.kernel.search.facet.ModifiedFacet"
	},
	service = FacetProcessor.class
)
public class ModifiedFacetProcessor extends RangeFacetProcessor {

	@Override
	protected void doProcessFacet(
		DefaultRangeBuilder defaultRangeBuilder, Facet facet) {

		super.doProcessFacet(defaultRangeBuilder, facet);

		SearchContext searchContext = facet.getSearchContext();

		String rangeParam = GetterUtil.getString(
			searchContext.getAttribute(facet.getFieldId()));

		if (Validator.isNull(rangeParam)) {
			return;
		}

		String[] range = RangeParserUtil.parserRange(rangeParam);

		defaultRangeBuilder.addRange(range[0], range[1]);
	}

}