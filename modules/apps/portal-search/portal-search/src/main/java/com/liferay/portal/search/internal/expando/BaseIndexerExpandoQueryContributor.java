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

package com.liferay.portal.search.internal.expando;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ExpandoQueryContributor;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Arrays;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = ExpandoQueryContributor.class)
public class BaseIndexerExpandoQueryContributor
	implements ExpandoQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery, String[] classNames,
		SearchContext searchContext) {

		if (shouldSuppressIndexerProvidedClauses(searchContext)) {
			return;
		}

		expandoQueryContributorHelper.contribute(
			keywords, booleanQuery, Arrays.asList(classNames), searchContext);
	}

	protected boolean shouldSuppressIndexerProvidedClauses(
		SearchContext searchContext) {

		return GetterUtil.getBoolean(
			searchContext.getAttribute(
				"search.full.query.suppress.indexer.provided.clauses"));
	}

	@Reference
	protected ExpandoQueryContributorHelper expandoQueryContributorHelper;

}