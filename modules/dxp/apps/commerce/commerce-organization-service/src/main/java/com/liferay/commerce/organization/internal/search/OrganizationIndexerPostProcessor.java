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

package com.liferay.commerce.organization.internal.search;

import com.liferay.portal.kernel.search.BaseIndexerPostProcessor;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.PrefixFilter;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.kernel.model.Organization",
	service = IndexerPostProcessor.class
)
public class OrganizationIndexerPostProcessor extends BaseIndexerPostProcessor {

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter booleanFilter, SearchContext searchContext)
		throws Exception {

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params == null) {
			return;
		}

		String treePath = MapUtil.getString(params, Field.TREE_PATH);

		if (Validator.isNotNull(treePath)) {
			booleanFilter.add(
				new PrefixFilter(Field.TREE_PATH, treePath),
				BooleanClauseOccur.MUST);
		}

		String organizationType = MapUtil.getString(params, Field.TYPE);

		if (Validator.isNotNull(treePath)) {
			booleanFilter.add(
				new PrefixFilter(Field.TYPE, organizationType),
				BooleanClauseOccur.MUST);
		}
	}

}