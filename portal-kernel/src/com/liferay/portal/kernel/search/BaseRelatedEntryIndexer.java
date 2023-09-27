/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Michael C. Han
 */
public class BaseRelatedEntryIndexer implements RelatedEntryIndexer {

	@Override
	public void addRelatedClassNames(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		searchContext.setAttribute("relatedClassName", Boolean.TRUE);

		String[] relatedEntryClassNames = (String[])searchContext.getAttribute(
			"relatedEntryClassNames");

		if (ArrayUtil.isEmpty(relatedEntryClassNames)) {
			return;
		}

		BooleanFilter relatedBooleanFilters = new BooleanFilter();

		for (String relatedEntryClassName : relatedEntryClassNames) {
			Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
				relatedEntryClassName);

			if (indexer == null) {
				continue;
			}

			BooleanFilter relatedBooleanFilter = new BooleanFilter();

			indexer.postProcessContextBooleanFilter(
				relatedBooleanFilter, searchContext);

			for (IndexerPostProcessor indexerPostProcessor :
					indexer.getIndexerPostProcessors()) {

				indexerPostProcessor.postProcessContextBooleanFilter(
					relatedBooleanFilter, searchContext);
			}

			postProcessContextQuery(
				relatedBooleanFilter, searchContext, indexer);

			relatedBooleanFilter.addRequiredTerm(
				Field.CLASS_NAME_ID,
				PortalUtil.getClassNameId(relatedEntryClassName));

			relatedBooleanFilters.add(
				relatedBooleanFilter, BooleanClauseOccur.SHOULD);
		}

		if (relatedBooleanFilters.hasClauses()) {
			contextBooleanFilter.add(
				relatedBooleanFilters, BooleanClauseOccur.MUST);
		}

		searchContext.setAttribute("relatedClassName", Boolean.FALSE);
	}

	@Override
	public void addRelatedEntryFields(Document document, Object object)
		throws Exception {
	}

	@Override
	public boolean isVisibleRelatedEntry(long classPK, int status)
		throws Exception {

		return true;
	}

	@Override
	public void updateFullQuery(SearchContext searchContext) {
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), added strictly to support
	 *             backwards compatibility of {@link
	 *             Indexer#postProcessContextQuery(BooleanQuery, SearchContext)}
	 */
	@Deprecated
	protected void postProcessContextQuery(
			BooleanFilter relatedBooleanFilter, SearchContext searchContext,
			Indexer<?> indexer)
		throws Exception {

		BooleanQuery entityQuery = new BooleanQueryImpl();

		indexer.postProcessContextQuery(entityQuery, searchContext);

		if (entityQuery.hasClauses()) {
			QueryFilter queryFilter = new QueryFilter(entityQuery);

			relatedBooleanFilter.add(queryFilter, BooleanClauseOccur.MUST);
		}
	}

}