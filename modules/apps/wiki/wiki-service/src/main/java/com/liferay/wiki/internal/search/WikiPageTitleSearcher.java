/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.internal.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeService;

import java.util.Collections;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	property = "model.class.name=com.liferay.wiki.model.WikiPage",
	service = BaseSearcher.class
)
public class WikiPageTitleSearcher extends BaseSearcher {

	public static final String[] CLASS_NAMES = {WikiPage.class.getName()};

	public WikiPageTitleSearcher() {
		setDefaultSelectedFieldNames(Field.TITLE);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String[] getSearchClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		addStatus(contextBooleanFilter, searchContext);

		long[] nodeIds = searchContext.getNodeIds();

		if (ArrayUtil.isNotEmpty(nodeIds)) {
			TermsFilter nodesIdTermsFilter = new TermsFilter(Field.NODE_ID);

			for (long nodeId : nodeIds) {
				try {
					_wikiNodeService.getNode(nodeId);
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to get wiki node " + nodeId, exception);
					}

					continue;
				}

				nodesIdTermsFilter.addValue(String.valueOf(nodeId));
			}

			if (!nodesIdTermsFilter.isEmpty()) {
				contextBooleanFilter.add(
					nodesIdTermsFilter, BooleanClauseOccur.MUST);
			}
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		MatchQuery matchQuery = new MatchQuery(
			Field.TITLE, StringUtil.toLowerCase(searchContext.getKeywords()));

		matchQuery.setType(MatchQuery.Type.PHRASE_PREFIX);

		searchQuery.add(matchQuery, BooleanClauseOccur.MUST);
	}

	@Override
	protected Map<String, Query> addSearchKeywords(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		return Collections.emptyMap();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WikiPageTitleSearcher.class);

	@Reference
	private WikiNodeService _wikiNodeService;

}