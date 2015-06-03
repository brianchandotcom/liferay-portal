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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.util.PortalUtil;

/**
 * @author Michael C. Han
 */
public class BaseRelatedEntryIndexer implements RelatedEntryIndexer {

	@Override
	public void addRelatedClassNames(
			BooleanFilter contextFilter, SearchContext searchContext)
		throws Exception {

		searchContext.setAttribute("relatedClassName", Boolean.TRUE);

		String[] relatedEntryClassNames = (String[])searchContext.getAttribute(
			"relatedEntryClassNames");

		if (ArrayUtil.isEmpty(relatedEntryClassNames)) {
			return;
		}

		BooleanFilter relatedFilters = new BooleanFilter();

		for (String relatedEntryClassName : relatedEntryClassNames) {
			Indexer indexer = IndexerRegistryUtil.getIndexer(
				relatedEntryClassName);

			if (indexer == null) {
				continue;
			}

			BooleanFilter relatedFilter = new BooleanFilter();

			indexer.postProcessContextBooleanFilter(
				relatedFilter, searchContext);

			for (IndexerPostProcessor indexerPostProcessor :
					indexer.getIndexerPostProcessors()) {

				indexerPostProcessor.postProcessContextBooleanFilter(
					relatedFilter, searchContext);
			}

			postProcessContextQuery(relatedFilter, searchContext, indexer);

			relatedFilter.addRequiredTerm(
				Field.CLASS_NAME_ID,
				PortalUtil.getClassNameId(relatedEntryClassName));

			relatedFilters.add(relatedFilter, BooleanClauseOccur.SHOULD);
		}

		if (relatedFilters.hasClauses()) {
			contextFilter.add(relatedFilters, BooleanClauseOccur.MUST);
		}

		searchContext.setAttribute("relatedClassName", Boolean.FALSE);
	}

	@Override
	public void addRelatedEntryFields(Document document, Object obj)
		throws Exception {
	}

	@Override
	public void updateFullQuery(SearchContext searchContext) {
	}

	/**
	 * @deprecated As of 7.0.0, added strictly to support backwards
	 *             compatibility of {@link Indexer#postProcessContextQuery(
	 *             BooleanQuery, SearchContext)}
	 */
	@Deprecated
	protected void postProcessContextQuery(
			BooleanFilter relatedFilter, SearchContext searchContext,
			Indexer indexer)
		throws Exception {

		BooleanQuery entityQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		indexer.postProcessContextQuery(entityQuery, searchContext);

		for (IndexerPostProcessor indexerPostProcessor :
				indexer.getIndexerPostProcessors()) {

			indexerPostProcessor.postProcessContextQuery(
				entityQuery, searchContext);
		}

		if (entityQuery.hasClauses()) {
			QueryFilter queryFilter = new QueryFilter(entityQuery);

			relatedFilter.add(queryFilter, BooleanClauseOccur.MUST);
		}
	}

}