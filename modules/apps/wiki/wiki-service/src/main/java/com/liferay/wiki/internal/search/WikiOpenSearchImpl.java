/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.internal.search;

import com.liferay.portal.kernel.search.HitsOpenSearchImpl;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.wiki.model.WikiPage;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = OpenSearch.class)
public class WikiOpenSearchImpl extends HitsOpenSearchImpl {

	public static final String SEARCH_PATH = "/c/wiki/open_search";

	public static final String TITLE = "Liferay Wiki Search: ";

	@Override
	public String getClassName() {
		return WikiPage.class.getName();
	}

	@Override
	public Indexer<WikiPage> getIndexer() {
		return IndexerRegistryUtil.getIndexer(WikiPage.class);
	}

	@Override
	public String getSearchPath() {
		return SEARCH_PATH;
	}

	@Override
	public String getTitle(String keywords) {
		return TITLE + keywords;
	}

}