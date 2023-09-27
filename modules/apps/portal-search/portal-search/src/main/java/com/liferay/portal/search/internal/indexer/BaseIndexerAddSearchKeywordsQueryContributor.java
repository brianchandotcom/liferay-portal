/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.kernel.search.AddSearchKeywordsQueryContributor;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.internal.indexer.helper.AddSearchKeywordsQueryContributorHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = AddSearchKeywordsQueryContributor.class)
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