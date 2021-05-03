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

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.kernel.search.AddSearchKeywordsQueryContributor;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.SearchContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = AddSearchKeywordsQueryContributor.class)
public class BaseIndexerAddSearchKeywordsQueryContributor
	implements AddSearchKeywordsQueryContributor {

	@Override
	public void contribute(
		BooleanQuery booleanQuery, SearchContext searchContext) {

		addSearchKeywordsQueryContributorHelper.contribute(
			booleanQuery, searchContext);
	}

	@Reference
	protected AddSearchKeywordsQueryContributorHelper
		addSearchKeywordsQueryContributorHelper;

}